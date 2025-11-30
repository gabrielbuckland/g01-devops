package ch.hslu.devops.g01;

import io.micronaut.http.annotation.*;

@Controller("/")
public class IndexController {

    @Get(uri = "/", produces = "text/plain")
    public String index() {
        return "Example Response";
    }
}
