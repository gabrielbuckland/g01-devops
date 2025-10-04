package ch.hslu.devops.g01.backend.entity;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
@MappedEntity("form_db")
public class Form {

    @Id
    @GeneratedValue
    private UUID id;
    private String vorname;
    private String nachname;
    private String email;

    public Form(){}

    public Form(String vorname, String nachname, String email){
        this.vorname  = vorname;
        this.nachname = nachname;
        this.email = email;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id) { this.id = id; }

    public String getVorname(){
        return vorname;
    }

    public void setVorname(String vorname){
        this.vorname = vorname;
    }

    public String getNachname(){
        return nachname;
    }

    public void setNachname(String nachname){
        this.nachname = nachname;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
