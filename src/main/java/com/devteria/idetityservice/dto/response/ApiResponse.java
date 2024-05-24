package com.devteria.idetityservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
//Chứa cấu trúc trả về

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//chuan hoa du lieu tra ve
@JsonInclude(JsonInclude.Include.NON_NULL) // an nhung truong null di
public class ApiResponse <T> {
    @Builder.Default
    private int code = 1000; // 1000 la thanh cong
    private String message;
    private T result;

}
