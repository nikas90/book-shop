package org.angisource.bookshop.response;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ApiResponse {

    private Date timestamp;
    private Object content;
    private String url;
    private HttpStatus status;

    public ApiResponse(Object content, String url, HttpStatus status) {
        super();
        this.timestamp = new Date();
        this.content = content;
        this.url = url;
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Object getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
