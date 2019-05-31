package com.linjiahao.security.repository;

import com.linjiahao.security.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstByUsername(String username);
}
