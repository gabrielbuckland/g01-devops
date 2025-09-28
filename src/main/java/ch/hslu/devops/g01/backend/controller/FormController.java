package ch.hslu.devops.g01.backend.controller;

import ch.hslu.devops.g01.backend.entity.Form;
import ch.hslu.devops.g01.backend.repository.FormRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public HttpResponse<Form> create(@Body Form form){
        var saved = repository.save(form);
        return HttpResponse.created(saved);
    }

    @Delete("delete/{id}")
    public HttpResponse<?> delete(UUID id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }

    @Get("/get/{id}")
    public HttpResponse<Form> getById(UUID id){
        return repository.findById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }



}