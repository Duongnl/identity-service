package com.devteria.idetityservice.configuration;

import com.devteria.idetityservice.entity.User;
import com.devteria.idetityservice.enums.Role;
import com.devteria.idetityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor // khong can autowire
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j // de log ra man hinh
// class nay de khoi tao mot tai khoan admin khi minh khoi chay ung dung lan dau tien
public class ApplicationInitConfig {


    PasswordEncoder passwordEncoder;

//   ham nay duoc chay moi khi applicattion cua mimnh start len
    @Bean
    ApplicationRunner applicationRunner (UserRepository userRepository){
        return args -> {
//            kiem tra xem admin co ton tai hay chua
//           neu chua ton tai
            if (userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been creted with default password: admin, please change it");
           }
        };
    }
}
