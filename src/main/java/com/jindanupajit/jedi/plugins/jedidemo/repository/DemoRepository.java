package com.jindanupajit.jedi.plugins.jedidemo.repository;

import com.jindanupajit.jedi.plugins.jedidemo.model.Demo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoRepository extends CrudRepository<Demo, Long> {

}
