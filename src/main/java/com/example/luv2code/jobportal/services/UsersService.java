package com.example.luv2code.jobportal.services;

import com.example.luv2code.jobportal.entity.JobSeekerProfile;
import com.example.luv2code.jobportal.entity.RecruiterProfile;
import com.example.luv2code.jobportal.entity.Users;
import com.example.luv2code.jobportal.repository.JobSeekerProfileRepository;
import com.example.luv2code.jobportal.repository.RecruiterProfileRepository;
import com.example.luv2code.jobportal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Users addNewUser(Users user){

        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser=usersRepository.save(user);
        int userTypeId=user.getUserTypeId().getUserTypeId();
        if(userTypeId==1){
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }
        else{
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        return savedUser;
    }
    public Optional<Users>findUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {

        //We can get current logged in user from SecurityContextHolder
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username=authentication.getName();
            Users user=usersRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
            int userId=user.getUserId();
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                //if the user is a recruiter
                RecruiterProfile recruiterProfile=recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
                return recruiterProfile;
            }
            else{
                //if the user is a job seeker
                JobSeekerProfile jobSeekerProfile=jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
                return jobSeekerProfile;
            }
        }
        return null;
    }
}
