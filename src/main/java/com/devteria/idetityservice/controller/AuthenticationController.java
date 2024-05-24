package com.devteria.idetityservice.controller;

import com.devteria.idetityservice.dto.request.AuthenticationRequest;
import com.devteria.idetityservice.dto.request.IntrospectRequest;
import com.devteria.idetityservice.dto.response.ApiResponse;
import com.devteria.idetityservice.dto.response.AuthenticationResponse;
import com.devteria.idetityservice.dto.response.IntrospectResponse;
import com.devteria.idetityservice.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // autowire
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // neu bien kh khai bao   j thi se la private va final
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
       var result =  authenticationService.authenticate(request); // tra ve tru thi dung, false thi sai
//        truyen result ben tren vao aauthenticationrespone r truyen  authenticationresponse vao result cua ApiResponse
//        kieu khoi tao apirespone voi  result la result ben tren
       return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
         throws ParseException, JOSEException {
        var result =  authenticationService.introspect(request); // tra ve tru thi dung, false thi sai
//        truyen result ben tren vao aauthenticationrespone r truyen  authenticationresponse vao result cua ApiResponse
//        kieu khoi tao apirespone voi  result la result ben tren
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

}
