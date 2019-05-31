package com.linjiahao.security.service;

import com.linjiahao.security.bean.Resource;
import com.linjiahao.security.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource getResourceByUrl(String url) {
        return resourceRepository.findFirstByUrl(url);
    }
}
