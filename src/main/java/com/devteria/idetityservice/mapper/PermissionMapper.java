package com.devteria.idetityservice.mapper;

import com.devteria.idetityservice.dto.request.PermissionRequest;
import com.devteria.idetityservice.dto.request.UserCreationRequest;
import com.devteria.idetityservice.dto.request.UserUpdateRequest;
import com.devteria.idetityservice.dto.response.PermissionResponse;
import com.devteria.idetityservice.dto.response.UserResponse;
import com.devteria.idetityservice.entity.Permission;
import com.devteria.idetityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
