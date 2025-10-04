package ch.hslu.devops.g01.backend.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Serdeable
@Introspected
public record CreateFormRequest (
    @NotBlank @Email String email,
    @NotBlank String vorname,
    @NotBlank String nachname
) {}
