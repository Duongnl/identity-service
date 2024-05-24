package com.devteria.idetityservice.controller;

import com.devteria.idetityservice.dto.response.ApiResponse;
import com.devteria.idetityservice.dto.request.UserCreationRequest;
import com.devteria.idetityservice.dto.request.UserUpdateRequest;
import com.devteria.idetityservice.dto.response.UserResponse;
import com.devteria.idetityservice.entity.User;
import com.devteria.idetityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;
//    tra ve mot response chuan
    @PostMapping()
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping()
    public List<User> getUsers() {

//        lay thong tin dang dang nhap hien tai
      var authentication =  SecurityContextHolder.getContext().getAuthentication();
      log.info("Username: {}", authentication.getName());
      authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));


        return userService.getUsers();
    }

//    tim kiem theo ma
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId){
        ApiResponse<UserResponse>  apiResponse = new ApiResponse<>();


        apiResponse.setResult(userService.getUser(userId));

        return apiResponse;
    }

//    dung token de lay thong tin cua chinh tai khoan da dang nhap
    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo () {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                 .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String userId ,@RequestBody @Valid UserUpdateRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(); // khoi tao mot chuan api response

        // no se chay ham update neu khong ra exception gi thi no set vao result cua apiresponse
        // va khi do la tu hieu la thanh cong, va code auto la 1000 va message khong co gi het
        apiResponse.setResult(userService.updateUser(userId,request));
        // auto thanh cong roi thi tra ve response cho nguoi dung o day
        return apiResponse;
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable String userId) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.deleteUser(userId));
        return apiResponse;
    }
}
