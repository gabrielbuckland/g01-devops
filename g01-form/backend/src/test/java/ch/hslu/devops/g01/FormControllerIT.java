package ch.hslu.devops.g01;

import ch.hslu.devops.g01.backend.dto.CreateFormRequest;
import ch.hslu.devops.g01.backend.entity.Form;
import ch.hslu.devops.g01.backend.repository.FormRepository;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@MicronautTest
public class FormControllerIT {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    FormRepository repository;

    @BeforeEach
    void resetMocks() {
        Mockito.reset(repository);
    }

    @Test
    void getAll_returnsForms() {
        var f1 = new Form("a@example.com", "Anna", "Alpen");
        var f2 = new Form("b@example.com", "Bert", "Berg");
        when(repository.findAll()).thenReturn(List.of(f1, f2));

        var req = HttpRequest.GET("/form/");
        HttpResponse<List<Form>> rsp = client.toBlocking()
                .exchange(req, Argument.listOf(Form.class));

        assertEquals(HttpStatus.OK, rsp.getStatus());
        assertNotNull(rsp.body());
        assertEquals(2, rsp.body().size());
        assertEquals("a@example.com", rsp.body().get(0).getEmail());
        verify(repository).findAll();
    }

    @Test
    void create_persistsAndNormalizesEmail() {

        var input = new CreateFormRequest("  ZuErich@EXAMPLE.Com  ", "Max", "Meier");

        when(repository.existsById("  ZuErich@EXAMPLE.Com  ")).thenReturn(false);

        when(repository.save(any(Form.class)))
                .thenAnswer(inv -> inv.getArgument(0, Form.class));

        var req = HttpRequest.POST("/form/", input);
        HttpResponse<Form> rsp = client.toBlocking()
                .exchange(req, Form.class);

        assertEquals(HttpStatus.CREATED, rsp.getStatus());
        assertNotNull(rsp.body());
        assertEquals("zuerich@example.com", rsp.body().getEmail());
        assertEquals("Max", rsp.body().getVorname());
        assertEquals("Meier", rsp.body().getNachname());

        verify(repository).save(argThat(f ->
                "zuerich@example.com".equals(f.getEmail())
                        && "Max".equals(f.getVorname())
                        && "Meier".equals(f.getNachname())
        ));
    }

    @Test
    void create_conflictWhenEmailExists() {
        var input = new CreateFormRequest("exists@example.com", "Eva", "Edel");

        when(repository.existsById("exists@example.com")).thenReturn(true);

        var req = HttpRequest.POST("/form/", input);
        var ex = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange(req, Form.class));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        verify(repository, never()).save(any());
    }

    @Test
    void delete_returns204WhenDeleted() {
        String email = "del@example.com";
        when(repository.existsById(email)).thenReturn(true);
        doNothing().when(repository).deleteById(email);

        var req = HttpRequest.DELETE("/form/delete/" + email);
        HttpResponse<?> rsp = client.toBlocking().exchange(req);

        assertEquals(HttpStatus.NO_CONTENT, rsp.getStatus());
        verify(repository).deleteById(email);
    }

    @Test
    void delete_returns404WhenNotFound() {
        String email = "missing@example.com";
        when(repository.existsById(email)).thenReturn(false);

        var req = HttpRequest.DELETE("/form/delete/" + email);
        var ex = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange(req));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        verify(repository, never()).deleteById(anyString());
    }

    @Test
    void getById_returnsEntityWhenFound() {
        String email = "get@example.com";
        var form = new Form(email, "Gina", "Gipfel");
        when(repository.findById(email)).thenReturn(Optional.of(form));

        var req = HttpRequest.GET("/form/get/" + email);
        HttpResponse<Form> rsp = client.toBlocking().exchange(req, Form.class);

        assertEquals(HttpStatus.OK, rsp.getStatus());
        assertNotNull(rsp.body());
        assertEquals(email, rsp.body().getEmail());
    }

    @Test
    void getById_returns404WhenMissing() {
        String email = "notfound@example.com";
        when(repository.findById(email)).thenReturn(Optional.empty());

        var req = HttpRequest.GET("/form/get/" + email);
        var ex = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange(req, Form.class));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void validation_invalidEmailYields400_onGet() {
        var req = HttpRequest.GET("/form/get/not-an-email");
        var ex = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange(req, Form.class));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @MockBean(FormRepository.class)
    FormRepository mockRepository() {
        return Mockito.mock(FormRepository.class);
    }
}
