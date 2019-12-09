package com.jindanupajit.jedi.plugins.jedidemo.jedi.security.repository;

import com.jindanupajit.jedi.plugins.jedidemo.jedi.security.model.UserDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetail, Long> {
    UserDetail findByUsername(String username);
}
