package com.devteria.idetityservice.repository;

import com.devteria.idetityservice.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissonRepository extends JpaRepository<Permission, String> {



}
