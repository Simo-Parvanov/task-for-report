package com.example.taskleadconsult.exceptions;

import org.springframework.http.HttpStatusCode;

public class ApiError {
    private HttpStatusCode status;
    private String message;

    public ApiError(HttpStatusCode status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public void setStatus(HttpStatusCode status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
