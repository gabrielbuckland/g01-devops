package ch.hslu.devops.g01.backend.controller;

import io.micronaut.http.annotation.*;

@Controller("/")
public class IndexController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Index Route";
    }
}
