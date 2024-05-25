package com.devteria.idetityservice.dto.response;

import com.devteria.idetityservice.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

// những thuộc tính cần trả về ở result
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
     String id;
     String username;
     String firstname;
     String lastname;
     LocalDate dob;
     Set<Role> roles;
}
