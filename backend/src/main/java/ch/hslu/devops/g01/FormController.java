package ch.hslu.devops.g01;

import ch.hslu.devops.g01.backend.entity.Form;
import ch.hslu.devops.g01.backend.repository.FormRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;


@Validated
@Controller("/form")
public class FormController {

    private final FormRepository repository;

    public FormController(FormRepository repository){
        this.repository = repository;
    }

    @Get("/")
    public Iterable<Form> all(){
        return repository.findAll();
    }

    @Post("/")
    public HttpResponse<Form> create(@Valid @Body Form form){
        if(repository.existsById(form.getEmail())){
            throw new HttpStatusException(HttpStatus.CONFLICT, "Email already exists!");
        }
        var saved = repository.save(form);
        return HttpResponse.created(saved);
    }

    @Delete("delete/{email}")
    public HttpResponse<?> delete(@Email @PathVariable String email){
        if (repository.existsById(email)){
            repository.deleteById(email);
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }

    @Get("/get/{email}")
    public HttpResponse<Form> getById(@Email @PathVariable String email){
        return repository.findById(email)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }



}