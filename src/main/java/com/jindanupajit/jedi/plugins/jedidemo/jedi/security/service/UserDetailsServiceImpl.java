package com.jindanupajit.jedi.plugins.jedidemo.jedi.security.service;


import com.jindanupajit.jedi.plugins.jedidemo.jedi.security.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails userDetails = userDetailsRepository.findByUsername(userName);

        if (userDetails == null)
            throw new UsernameNotFoundException("User '"+userName+"' not found.");

        return userDetails;
    }


}
