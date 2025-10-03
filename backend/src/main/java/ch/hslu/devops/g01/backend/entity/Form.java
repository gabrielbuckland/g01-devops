package ch.hslu.devops.g01.backend.entity;

import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.util.UUID;

@MappedEntity("form_db")
public class Form {

    @Id
    @AutoPopulated
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
