package com.jindanupajit.jedi.plugins.jedidemo.model;

import com.jindanupajit.jedi.plugins.jedidemo.jedi.security.DataLossPrevention.DataLossPrevention;
import com.jindanupajit.jedi.plugins.jedidemo.jedi.security.DataLossPrevention.DiodeMode;
import com.jindanupajit.jedi.plugins.jedidemo.jedi.security.service.JediPasswordEncoder;

import javax.persistence.*;

@Entity
public class Demo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name = "";

    @DataLossPrevention(diode = DiodeMode.WRITE_ENCODED)
    private String secret  = "";

    public Demo() {
    }

    @Override
    public String toString() {
        return "Demo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
