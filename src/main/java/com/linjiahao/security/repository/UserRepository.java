package com.linjiahao.security.repository;

import com.linjiahao.security.bean.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstByUsernameAndPassword(String username, String password);

    User findFirstByUsername(String username);
}
