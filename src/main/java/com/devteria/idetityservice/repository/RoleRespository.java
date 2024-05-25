package com.devteria.idetityservice.repository;

import com.devteria.idetityservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
//String la id cua entity
public interface RoleRespository extends JpaRepository<Role, String> {


}
