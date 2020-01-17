package org.upp.scholar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.upp.scholar.entity.Authority;
import org.upp.scholar.repository.AuthorityRepository;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority findByName(final String name) {
        Assert.notNull(name, "Authority name can't be null!");
        return this.authorityRepository.findByName(name);
    }

}
