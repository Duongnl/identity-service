package com.devteria.idetityservice.mapper;

import com.devteria.idetityservice.dto.request.PermissionRequest;
import com.devteria.idetityservice.dto.request.RoleRequest;
import com.devteria.idetityservice.dto.response.PermissionResponse;
import com.devteria.idetityservice.dto.response.RoleResponse;
import com.devteria.idetityservice.entity.Permission;
//import com.devteria.idetityservice.enums.Role;
import com.devteria.idetityservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
