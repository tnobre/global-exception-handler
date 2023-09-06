package com.tn.app.user.controller;

import com.tn.app.exception.RestApiReportedException;
import com.tn.app.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tn.app.exception.ErrorCode.NOT_IMPLEMENTED;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto userDto) {
        throw new RestApiReportedException(NOT_IMPLEMENTED);
    }

}