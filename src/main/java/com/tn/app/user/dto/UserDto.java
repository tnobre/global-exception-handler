package com.tn.app.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tn.app.validation.patterns.Patterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.tn.app.validation.messages.Messages.EMAIL;
import static com.tn.app.validation.messages.Messages.FIRST_NAME;
import static com.tn.app.validation.messages.Messages.LAST_NAME;
import static com.tn.app.validation.patterns.Patterns.VALID_NAME;
import static jakarta.validation.constraints.Pattern.Flag.CASE_INSENSITIVE;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(NON_EMPTY)
public class UserDto {

    private Long id;

    @NotBlank(message = FIRST_NAME)
    @Pattern(regexp = VALID_NAME, flags = CASE_INSENSITIVE, message = FIRST_NAME)
    private String firstName;

    @NotBlank(message = LAST_NAME)
    @Pattern(regexp = VALID_NAME, flags = CASE_INSENSITIVE, message = LAST_NAME)
    private String lastName;

    @NotBlank(message = EMAIL)
    @Pattern(regexp = Patterns.EMAIL, flags = CASE_INSENSITIVE, message = EMAIL)
    private String email;

}
