package org.angisource.bookshop.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;

public class ExceptionResponse {
    private Date timestamp;
    private HashMap<String, String> content;
    private String url;
    private HttpStatus status;

    public ExceptionResponse(Date timestamp, HashMap<String, String> content, String url, HttpStatus status) {
        super();
        this.timestamp = timestamp;
        this.content = content;
        this.url = url;
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public HashMap<String, String> getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public HttpStatus getStatus() {
        return status;
    }

}