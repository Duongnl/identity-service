package com.devteria.idetityservice.service;

import com.devteria.idetityservice.dto.request.UserCreationRequest;
import com.devteria.idetityservice.dto.request.UserUpdateRequest;
import com.devteria.idetityservice.dto.response.UserResponse;
import com.devteria.idetityservice.entity.User;
import com.devteria.idetityservice.enums.Role;
import com.devteria.idetityservice.exception.AppException;
import com.devteria.idetityservice.exception.ErrorCode;
import com.devteria.idetityservice.mapper.UserMapper;
import com.devteria.idetityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
// kh khai bao gi het thi no autowired va private
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
     UserRepository userRepository;
     UserMapper userMapper;
     PasswordEncoder passwordEncoder;

    public UserResponse createUser (UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // builder de them du lieu nhanh vao entity
      /*  UserCreationRequest request1 = UserCreationRequest.builder()
                .username("")
                .firstname("")
                .build(); */

//        mapping request vao user
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

//        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')") // pre de vao duoc ham getusers phai co role admin moi duoc goi ham nay
    public List<User> getUsers(){
        log.info("In method get user");
        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.username == authentication.name") // post nay se thuc hien ham roi moi kiem tra xem co quyen admin khong roi moi tra ve
//    phai dung id cua chinh minh moi co the lay duoc thong tin
    public UserResponse getUser(String id) {
        log.info("In method get user by id");
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOTFOUND))) ;
    }

    public UserResponse updateUser(String userId,UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.USER_NOTFOUND));
//        mapping request vao user
        userMapper.updateUser(user,request);
       return userMapper.toUserResponse(  userRepository.save(user));
    }

    public String deleteUser(String userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.USER_NOTFOUND));
        userRepository.deleteById(user.getId());
        return "User has been deleted";
    }

//    lay thong tin cua user dang dang nhap
    public UserResponse getMyInfo () {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user =   userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOTFOUND));
        return userMapper.toUserResponse(user);
    }

}
