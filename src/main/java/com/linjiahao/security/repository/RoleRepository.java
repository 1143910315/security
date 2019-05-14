package com.linjiahao.security.repository;

import com.linjiahao.security.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findFirstByRolename(String rolename);
}
