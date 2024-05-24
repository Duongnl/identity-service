package com.devteria.idetityservice.repository;

import com.devteria.idetityservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
//    Kiem tra username co ton tai hay khong
    boolean existsByUsername(String username);
    Optional<User> findByUsername (String username);

}
