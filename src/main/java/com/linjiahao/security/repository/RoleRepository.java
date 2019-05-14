package com.linjiahao.security.repository;

import com.linjiahao.security.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    Role findFirstByRolename(String rolename);

    //自定义sql语句并且开启本地sql
    //根据用户主键查找该用户所有权限
    @Query(value = "select r.* from t_role r, t_user_roles ur where ur.user_c_id = ?1 and ur.user_c_id = r.c_id", nativeQuery = true)
    List<Role> findRolesOfUser(int userId);

    //根据resource的主键查找resource允许的所有权限
    @Query(value = "select r.* from t_role r, t_resource_roles rr where rr.resource_c_id = ?1 and rr.roles_c_id = r.c_id", nativeQuery = true)
    List<Role> findRolesOfResource(int resourceId);
}
