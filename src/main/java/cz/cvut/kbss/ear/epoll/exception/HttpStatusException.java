package cz.cvut.kbss.ear.epoll.exception;

import org.springframework.http.HttpStatus;

public class HttpStatusException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;

    public HttpStatusException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
