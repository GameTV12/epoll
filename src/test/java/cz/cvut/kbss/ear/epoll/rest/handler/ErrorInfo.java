package cz.cvut.kbss.ear.epoll.rest.handler;

/**
 * Contains information about an error and can be send to client as JSON to let them know what went wrong.
 */
public class ErrorInfo {

    private String message;

    private String requestUri;


    public ErrorInfo(String message, String requestUri) {
        this.message = message;
        this.requestUri = requestUri;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" + requestUri + ", message = " + message + "}";
    }
}
