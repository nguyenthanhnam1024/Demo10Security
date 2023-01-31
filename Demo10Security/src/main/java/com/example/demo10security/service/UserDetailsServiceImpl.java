package com.example.demo10security.service;

import com.example.demo10security.configure.UserDetailsImpl;
import com.example.demo10security.entity.User;
import com.example.demo10security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetailsImpl loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("user not fould with name "+userName));
        return UserDetailsImpl.build(user);
    }
}
