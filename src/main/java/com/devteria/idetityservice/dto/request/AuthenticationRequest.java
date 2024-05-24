package com.devteria.idetityservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
// cung cap ussername va password de dang nhap
public class AuthenticationRequest {
    String username;
    String password;

}
