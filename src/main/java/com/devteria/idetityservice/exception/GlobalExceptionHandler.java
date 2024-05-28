package com.devteria.idetityservice.exception;


import com.devteria.idetityservice.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

// xu ly exception
@ControllerAdvice
public class GlobalExceptionHandler {

    private  static final String MIN_ATTRIBUTE = "min";

//    bat tat ca exception kha appException va valid
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){
//        chuan hoa du lieu tra ve
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse); // tra ve noi dung
    }

//    bat exception da cau hinh
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode  = exception.getErrorCode();

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode
                        .getStatusCode())
                .body(apiResponse); // tra ve noi dung
    }


    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

// loi khi valid
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage(); // lay ra ten bien define

        ErrorCode errorCode = ErrorCode.KEY_INVALID; // khi chua bt loi cu the

        Map<String, Object> attributes = null;
        try {
            errorCode  = ErrorCode.valueOf(enumKey); // chuyen thanh doi tuong errorcode bang key

            var constrainViolation = exception.getBindingResult()
                    .getAllErrors().getFirst().unwrap(ConstraintViolation.class);

             attributes = constrainViolation.getConstraintDescriptor().getAttributes();


        }catch (IllegalArgumentException e){

        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(Objects.nonNull(attributes) ?
                mapAttribute(errorCode.getMessage(), attributes)
                : errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    private String mapAttribute(String massage, Map<String, Object> attribute ){
//        lay value ra
        String minvalue = String.valueOf(attribute.get(MIN_ATTRIBUTE));

        return massage.replace("{" + MIN_ATTRIBUTE + "}", minvalue);
    }


}
