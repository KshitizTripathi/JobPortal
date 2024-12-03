package com.example.luv2code.jobportal.services;

import com.example.luv2code.jobportal.entity.UsersType;
import com.example.luv2code.jobportal.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    @Autowired
    private UsersTypeRepository usersTypeRepository;

    public List<UsersType> getAll(){
        return usersTypeRepository.findAll();
    }
}
