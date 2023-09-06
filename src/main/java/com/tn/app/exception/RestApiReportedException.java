/*
 * Copyright (c) 2020. All Rights Reserved. BO1 AG
 * Confidential: Restricted Internal Distribution
 */

package com.tn.app.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class RestApiReportedException extends RuntimeException {

    private final ErrorCode error;

    private List<Object> messages;

    public RestApiReportedException(ErrorCode error) {
        super();
        this.error = error;
    }

    public RestApiReportedException(ErrorCode error, List<Object> messages) {
        super();
        this.error = error;
        this.messages = messages;
    }

}