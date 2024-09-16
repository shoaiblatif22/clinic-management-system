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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "pending_validation")
    private boolean pendingValidation;

    @Column(name = "validated")
    private boolean validated;

    //define no-args constructor
    public UserEntity() {}

    //define constructor
    public UserEntity(String firstName, String lastName, String email, String password, String role, boolean pendingValidation, boolean validated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.pendingValidation = false;
        this.validated = false;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", pendingValidation=" + pendingValidation +
                ", validated=" + validated +
                '}';
    }
}





