package com.example.luv2code.jobportal.services;

import com.example.luv2code.jobportal.entity.Users;
import com.example.luv2code.jobportal.repository.UsersRepository;
import com.example.luv2code.jobportal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //tell spring security how to retrieve user from DB
        Users user=usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not find user"));
        return new CustomUserDetails(user);
    }
}
