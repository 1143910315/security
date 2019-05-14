package com.linjiahao.security.repository;

import com.linjiahao.security.bean.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource,Integer> {
    Resource findFirstByUrl(String url);
}
