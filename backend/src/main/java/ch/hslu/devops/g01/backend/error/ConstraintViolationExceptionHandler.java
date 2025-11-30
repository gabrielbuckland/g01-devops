package ch.hslu.devops.g01.backend.error;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Replaces(io.micronaut.validation.exceptions.ConstraintExceptionHandler.class)
public class ConstraintViolationExceptionHandler
        implements ExceptionHandler<ConstraintViolationException, HttpResponse<?>> {

    @Serdeable
    public static record FieldError(String field, String message) {
    }

    @Serdeable
    public static record ValidationErrorResponse(String message, List<FieldError> errors) {
    }

    @Override
    public HttpResponse<?> handle(io.micronaut.http.HttpRequest request,
            ConstraintViolationException ex) {
        var errors = ex.getConstraintViolations().stream()
                .map(v -> new FieldError(fieldName(v), v.getMessage()))
                .toList();

        var body = new ValidationErrorResponse("Validation failed", errors);
        return HttpResponse.badRequest(body);
    }

    private String fieldName(ConstraintViolation<?> v) {
        var path = v.getPropertyPath().toString();
        int idx = path.lastIndexOf('.');
        return idx >= 0 ? path.substring(idx + 1) : path;
    }

}
