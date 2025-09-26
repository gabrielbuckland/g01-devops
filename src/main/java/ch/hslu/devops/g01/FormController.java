package ch.hslu.devops.g01;

import io.micronaut.http.annotation.*;

@Controller("/form")
public class FormController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}