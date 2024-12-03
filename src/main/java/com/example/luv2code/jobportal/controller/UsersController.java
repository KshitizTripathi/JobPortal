package com.example.luv2code.jobportal.controller;

import com.example.luv2code.jobportal.entity.Users;
import com.example.luv2code.jobportal.entity.UsersType;
import com.example.luv2code.jobportal.services.UsersService;
import com.example.luv2code.jobportal.services.UsersTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    @Autowired
    private UsersTypeService usersTypeService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/register")
    public String register(Model model){
        List<UsersType> usersTypeList = usersTypeService.getAll();
        model.addAttribute("getAllTypes",usersTypeList);
        model.addAttribute("user",new Users());
        return "register";
    }
    @PostMapping("/register/new")
    public String userRegistration(@Valid Users user,Model model){
        System.out.println("User "+user);
        Optional<Users> userFound=usersService.findUserByEmail(user.getEmail());
        if(userFound.isPresent()){
            model.addAttribute("error","Email already registered,try to login or register with different email");
            List<UsersType> usersTypeList = usersTypeService.getAll();
            model.addAttribute("getAllTypes",usersTypeList);
            model.addAttribute("user",new Users());
            return "register";
        }
        Users userSaved=usersService.addNewUser(user);
        return "dashboard";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "rdirect:/";
    }
}
