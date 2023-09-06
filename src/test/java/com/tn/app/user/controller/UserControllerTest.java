package com.tn.app.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tn.app.exception.ResponseError;
import com.tn.app.user.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createUserWithValidData() throws Exception {

        UserDto user = UserDto.builder().firstName("Duck").lastName("Donald").email("ddonald@disney.com").build();

        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotImplemented())
                .andReturn();

        ResponseError responseError = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseError.class);

        Assertions.assertEquals("MP00100", responseError.getCode());

    }

    @Test
    void createUserWithInvalidData() throws Exception {

        UserDto user = UserDto.builder().firstName("").lastName("").email("ddonald@disney").build();

        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ResponseError responseError = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseError.class);

        Assertions.assertEquals("MP00101", responseError.getCode());

    }

}