package com.linjiahao.security.service;

import com.linjiahao.security.bean.Resource;
import com.linjiahao.security.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    public Resource getResource(String url) {
        return resourceRepository.findFirstByUrl(url);
    }
}
