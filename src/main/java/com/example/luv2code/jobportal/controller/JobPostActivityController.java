package com.example.luv2code.jobportal.controller;

import com.example.luv2code.jobportal.entity.JobSeekerProfile;
import com.example.luv2code.jobportal.entity.RecruiterProfile;
import com.example.luv2code.jobportal.entity.Users;
import com.example.luv2code.jobportal.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobPostActivityController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/dashboard/")
    public String searchJobs(Model model){
        Object currentUserProfile=usersService.getCurrentUserProfile();
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername=authentication.getName();
            model.addAttribute("username",currentUsername);
            System.out.println("Username "+currentUsername);
        }
        System.out.println("User "+currentUserProfile);
        model.addAttribute("user",currentUserProfile);
        return "dashboard";
    }

}
