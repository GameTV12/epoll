package cz.cvut.kbss.ear.epoll.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HttpStatusExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {HttpStatusException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        HttpStatusException httpStatusException = (HttpStatusException) ex;

        return handleExceptionInternal(ex, httpStatusException.getMessage(), new HttpHeaders(), httpStatusException.getHttpStatus(), request);
    }
}
