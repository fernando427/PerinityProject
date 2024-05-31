package com.fernando.PerinityProject.exceptions;

import com.fernando.PerinityProject.exceptions.error.ErrorDetails;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void whenResourceNotFoundExceptionThenReturnAnResponseEntity() {
        ResponseEntity<ErrorDetails> response = globalExceptionHandler
                .handleResourceNotFoundException(
                        new ResourceNotFoundException("Objeto não encontrado"),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ErrorDetails.class, response.getBody().getClass());
        assertEquals("Objeto não encontrado", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void whenResourceNotEqualExceptionThenReturnAmResponseEntity() {
        ResponseEntity<ErrorDetails> response = globalExceptionHandler
                .handleResourceNotEqualException(
                        new ResourceNotEqualException("Objeto não são iguais"),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ErrorDetails.class, response.getBody().getClass());
        assertEquals("Objeto não são iguais", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }
}