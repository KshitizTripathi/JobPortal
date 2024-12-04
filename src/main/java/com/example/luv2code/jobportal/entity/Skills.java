package com.example.luv2code.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "skills")
@Data
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String experienceLevel;
    private String yearsOfExperience;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobSeekerProfile",referencedColumnName = "userAccountId")
    private JobSeekerProfile jobSeekerProfile;
}
