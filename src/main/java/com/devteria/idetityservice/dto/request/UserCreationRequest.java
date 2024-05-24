package com.devteria.idetityservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
// chứa những request từ người dùng

//@Getter
//@Setter
@Data // tu dong tao get set contructer
@NoArgsConstructor // tao ra mot contructer rong
@AllArgsConstructor // tao ra all contructer

@Builder // them du lieu nhanh bang builder
@FieldDefaults(level = AccessLevel.PRIVATE) // nhung cai nao kh dinh nghia thi la private
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String firstname;
    String lastname;
    LocalDate dob;

//    @NotBlank @NotEmpty @Email @NotNull co the custom

}
