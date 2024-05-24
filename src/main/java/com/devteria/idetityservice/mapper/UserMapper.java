package com.devteria.idetityservice.mapper;

import com.devteria.idetityservice.dto.request.UserCreationRequest;
import com.devteria.idetityservice.dto.request.UserUpdateRequest;
import com.devteria.idetityservice.dto.response.UserResponse;
import com.devteria.idetityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
// c1 : mapp request vao user
    User toUser(UserCreationRequest request);

//    @Mapping(source = "fristName", target = "lastName") mapp du lieu cua lastName vao fristName
//    @Mapping(target = "lastname", ignore = true) // mapp cho thng lastNme la null
    UserResponse toUserResponse(User user);

//  c2 : mapping request vao user
    void updateUser(@MappingTarget User user, UserUpdateRequest request);


}
