package com.devteria.idetityservice.service;

import com.devteria.idetityservice.dto.request.PermissionRequest;
import com.devteria.idetityservice.dto.response.PermissionResponse;
import com.devteria.idetityservice.entity.Permission;
import com.devteria.idetityservice.mapper.PermissionMapper;
import com.devteria.idetityservice.repository.PermissonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissonRepository permissonRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request){

//        map tu request vao entity
        Permission permission = permissionMapper.toPermission(request) ;
//        save vao database
        permission = permissonRepository.save(permission);
//        map entity vao response roi tra ve
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll(){
        var permissions = permissonRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission){
        permissonRepository.deleteById(permission);
    }

}
