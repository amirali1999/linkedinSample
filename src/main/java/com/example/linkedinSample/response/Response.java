package com.example.linkedinSample.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Getter
@Setter
public class Response {
    private HttpStatus status;
    private String message;
    private List<?> results;
    private Object result;
    private Date date;
    private Integer total;

    public Response(HttpStatus status, String message, List<?> results, Integer total) {
        this.status = status;
        this.message = message;
        this.results = results;
        this.total = total;
        setDate();
    }

    public Response(HttpStatus status, String message, Object result, Integer total) {
        this.status = status;
        this.message = message;
        this.result = result;
        this.total = total;
        setDate();
    }

    public void setDate() {
        date = new Date();
    }

    public ResponseEntity<?> createResponseEntity() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status.toString());
        map.put("message", message);

        if (results == null){
            map.put("result", result);
        } else if (result == null) {
            map.put("result", results);
        }

        map.put("date", date.toString());
        map.put("total", total);
        final HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<Object>(map, httpHeaders, status);
    }

}
