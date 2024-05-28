package com.devteria.idetityservice.dto.request;

import com.devteria.idetityservice.validator.DobConstraint;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;

    private String firstname;
    private String lastname;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    private LocalDate dob;

    List<String> roles;


}
