package com.example.clinicmanagementsystem.user.entity;


import com.example.clinicmanagementsystem.role.Role;
import com.example.clinicmanagementsystem.role.entity.RoleEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    //CODE TO GENERATE ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    //FIRST_NAME FOR USER
    @Column(name = "first_name", nullable = false)
    private String firstName;

    //LAST_NAME FOR USER
    @Column(name = "last_name", nullable = false)
    private String lastName;

    //EMAIL_ADDRESS FOR USER
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    //PASSWORD_FOR USER(STILL NEEDS HASHING)
    @Column(name = "password", nullable = false)
    private String password;

    //USING ENUM FOR ROLE
    @Enumerated(EnumType.STRING) //MAPS ENUM VALUE TO A STRING IN DATABASE
    @Column(name = "role", nullable = false)
    private Role role;

    //PENDING_VALIDATION
    @Column(name = "pending_validation", nullable = false)
    private boolean pendingValidation = true;

    //VALIDATED
    @Column(name = "validated", nullable = false)
    private boolean validated = false;

    //MAPS USER_ROLES TABLE
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    //DEFAULT CONSTRUCTOR
    public UserEntity() {}

    //CONSTRUCTOR
    public UserEntity(String firstName, String lastName, String email, String password, Role role, boolean pendingValidation, boolean validated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.pendingValidation = pendingValidation;
        this.validated = validated;
    }

    //TO STRING
    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", pendingValidation=" + pendingValidation +
                ", validated=" + validated +
                '}';
    }
}
