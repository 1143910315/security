package com.linjiahao.security.service;

import com.linjiahao.security.bean.Role;
import com.linjiahao.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByRolename(String rolename) {
        return roleRepository.findFirstByRolename(rolename);
    }

    public List<Role> getRolesOfUser(int userId) {
        return roleRepository.findRolesOfUser(userId);
    }

    public List<Role> getRolesOfResource(int resourceId) {
        return roleRepository.findRolesOfResource(resourceId);
    }
}
