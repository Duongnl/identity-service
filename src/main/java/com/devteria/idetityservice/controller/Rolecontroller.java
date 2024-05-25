package com.devteria.idetityservice.controller;

import com.devteria.idetityservice.dto.request.PermissionRequest;
import com.devteria.idetityservice.dto.request.RoleRequest;
import com.devteria.idetityservice.dto.response.ApiResponse;
import com.devteria.idetityservice.dto.response.PermissionResponse;
import com.devteria.idetityservice.dto.response.RoleResponse;
import com.devteria.idetityservice.service.PermissionService;
import com.devteria.idetityservice.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class Rolecontroller {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create (@RequestBody RoleRequest request) {
        return  ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete (@PathVariable  String role){
        roleService.delete(role);
        return ApiResponse.<Void>builder().build();
    }




}
