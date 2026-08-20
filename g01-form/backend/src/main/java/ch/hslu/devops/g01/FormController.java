package ch.hslu.devops.g01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.hslu.devops.g01.backend.dto.CreateFormRequest;
import ch.hslu.devops.g01.backend.entity.Form;
import ch.hslu.devops.g01.backend.repository.FormRepository;
import io.getunleash.Unleash;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

@Validated
@Controller("/form")
public class FormController {

    private final FormRepository repository;

    private final Unleash unleash;

    private final Logger logger = LoggerFactory.getLogger(FormController.class);

    public FormController(FormRepository repository, Unleash unleash) {
        this.repository = repository;
        this.unleash = unleash;
    }

    @Get("/")
    public Iterable<Form> all() {
        return repository.findAll();
    }

    @Post("/")
    @ExecuteOn(TaskExecutors.BLOCKING)
    public HttpResponse<Form> create(@Valid @Body CreateFormRequest req) {
        // Normalise before the lookup, otherwise DUP@Example.COM slips past the check
        // and only fails later on the unique constraint.
        var email = req.email().trim().toLowerCase();
        if (repository.existsById(email)) {
            throw new HttpStatusException(HttpStatus.CONFLICT, "Email already exists!");
        }
        logger.info("Loading fix is enabled: " + this.unleash.isEnabled("fixloading"));
        if (!this.unleash.isEnabled("fixloading")) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Request interrupted");
            }
        }
        try {
            var saved = repository.save(new Form(email, req.vorname(), req.nachname()));
            return HttpResponse.created(saved);
        } catch (DataAccessException e) {
            // Two concurrent requests can both pass the check above; let the unique
            // constraint decide instead of leaking the database error to the client.
            throw new HttpStatusException(HttpStatus.CONFLICT, "Email already exists!");
        }
    }

    @Delete("delete/{email}")
    public HttpResponse<?> delete(@Email @PathVariable String email) {
        if (repository.existsById(email)) {
            repository.deleteById(email);
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }

    @Get("/get/{email}")
    public HttpResponse<Form> getById(@Email @PathVariable String email) {
        return repository.findById(email)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

}
