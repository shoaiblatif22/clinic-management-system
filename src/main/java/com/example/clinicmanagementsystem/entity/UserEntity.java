package com.example.clinicmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Users")
@Data
public class UserEntity {

    //define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String role;

    //define no-args constructor
    public UserEntity() {}

    //define constructor
    public UserEntity(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}





