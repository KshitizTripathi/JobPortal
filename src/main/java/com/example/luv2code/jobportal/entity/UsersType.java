package com.example.luv2code.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users_type")
@Data
public class UsersType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userTypeId;
    private String userTypeName;

    @OneToMany(targetEntity = Users.class,mappedBy = "userTypeId",cascade = CascadeType.ALL)
    private List<Users> users;

    @Override
    public String toString() {
        return "UsersType{" +
                "userTypeName='" + userTypeName + '\'' +
                ", userTypeId=" + userTypeId +
                '}';
    }
}
