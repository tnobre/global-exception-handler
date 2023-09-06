package com.tn.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.tn.app.exception.DisplayMessage.FIX_BEFORE;
import static com.tn.app.exception.DisplayMessage.MODAL;
import static com.tn.app.exception.DisplayMessage.TOAST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE;


@Getter
public enum ErrorCode {

    // General
    MALFORMED_REQUEST("MP00005", BAD_REQUEST, "Malformed request", "Malformed request", FIX_BEFORE),
    LARGE_REQUEST("MP00006", PAYLOAD_TOO_LARGE, "Request too big", "Request too big", FIX_BEFORE),

    // Users,
    NOT_IMPLEMENTED("MP00100", HttpStatus.NOT_IMPLEMENTED, "User creation", "Not implemented", TOAST),
    INVALID_USER_DATA("MP00101", BAD_REQUEST, "User creation", "Invalid user data", MODAL);

    private final String code;

    private final HttpStatus httpStatus;
    private final String title;
    private final String message;
    private final DisplayMessage display;

    ErrorCode(String code, HttpStatus httpStatus, String title, String message, DisplayMessage display) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.title = title;
        this.message = message;
        this.display = display;
    }

}
