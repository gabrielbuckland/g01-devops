package ch.hslu.devops.g01;

import ch.hslu.devops.g01.backend.entity.Form;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
public class FormEntityTest {

    String email = "max.muster@mail.com";
    String name = "Max";
    String surname = "Muster";

    Form form = new Form(email, name, surname);

    @Test
    void testGetEmail(){
        Assertions.assertEquals(email, form.getEmail());
    }

    @Test
    void testSetEmail(){
        String newEmail = "hans.mueller@mail.com";
        form.setEmail(newEmail);

        Assertions.assertEquals(newEmail, form.getEmail());
    }

    @Test
    void testGetName(){
        Assertions.assertEquals(name, form.getVorname());
    }

    @Test
    void testSetName(){
        String newName = "Hans";
        form.setVorname(newName);

        Assertions.assertEquals(newName, form.getVorname());
    }

    @Test
    void testGetSurname(){
        Assertions.assertEquals(surname, form.getNachname());
    }

    @Test
    void testSetSurname(){
        String newSurname = "Mueller";
        form.setNachname(newSurname);

        Assertions.assertEquals(newSurname, form.getNachname());
    }
}
