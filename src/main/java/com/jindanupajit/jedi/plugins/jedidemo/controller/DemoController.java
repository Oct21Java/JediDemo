package com.jindanupajit.jedi.plugins.jedidemo.controller;

import com.jindanupajit.jedi.plugins.jedidemo.jedi.SCrudController;
import com.jindanupajit.jedi.plugins.jedidemo.jedi.Jedi;
import com.jindanupajit.jedi.plugins.jedidemo.jedi.security.service.JediPasswordEncoder;
import com.jindanupajit.jedi.plugins.jedidemo.model.Demo;
import com.jindanupajit.jedi.plugins.jedidemo.repository.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class DemoController implements SCrudController<DemoRepository, Demo> {

    @Autowired
    private DemoRepository demoRepository;

    @Override
    public DemoRepository getRepository() {
        return demoRepository;
    }

    @Override
    public Demo newEntity() {
        Demo demo = new Demo();
        demo.setName("New");
        return demo;
    }

}
