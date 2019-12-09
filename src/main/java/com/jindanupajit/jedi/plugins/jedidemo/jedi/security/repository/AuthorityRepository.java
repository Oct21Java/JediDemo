package com.jindanupajit.jedi.plugins.jedidemo.jedi.security.repository;

import com.jindanupajit.jedi.plugins.jedidemo.jedi.security.model.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Authority findByAuthority(String authority);
}
