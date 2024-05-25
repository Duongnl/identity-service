package com.devteria.idetityservice.service;

import com.devteria.idetityservice.dto.request.RoleRequest;
import com.devteria.idetityservice.dto.response.RoleResponse;
import com.devteria.idetityservice.mapper.RoleMapper;
import com.devteria.idetityservice.repository.PermissonRepository;
import com.devteria.idetityservice.repository.RoleRespository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class RoleService {
    RoleRespository roleRespository;
    PermissonRepository permissonRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);

      var permissions =  permissonRepository.findAllById(request.getPermissions());
      role.setPermissions(new HashSet<>(permissions));

     role = roleRespository.save(role);

     return roleMapper.toRoleResponse(role);

    }

    public List<RoleResponse> getAll(){
        return roleRespository
                .findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void delete(String role){
        roleRespository.deleteById(role);
    }
}
