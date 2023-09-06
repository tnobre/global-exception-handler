package com.tn.app.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
public class ResponseError {

    private String code;
    private DisplayMessage display;
    private String title;

    private String description;
    @JsonInclude(NON_NULL)
    private List<Object> messages;

    public ResponseError(String code, DisplayMessage display, String title, String description, List<Object> messages) {
        this.code = code;
        this.display = display;
        this.title = title;
        this.description = description;
        this.messages = messages;
    }
}