package ch.hslu.devops.g01.backend.entity;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@MappedEntity("form_db")
public class Form {

    @Id
    private String email;
    private String vorname;
    private String nachname;

    public Form() {
    }

    public Form(String email, String vorname, String nachname) {
        this.email = email;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
}
