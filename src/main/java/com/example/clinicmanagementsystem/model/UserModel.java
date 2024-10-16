package com.example.clinicmanagementsystem.model;

public class UserModel {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private boolean pendingValidation;
    private boolean validated;

    //no-args constructor
    public UserModel() {}

    public UserModel(int id, String firstName, String lastName, String email, String password, String role, boolean pendingValidation, boolean validated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.pendingValidation = pendingValidation;
        this.validated = validated;
    }

    public boolean isPendingValidation() {
        return pendingValidation;
    }

    public void setPendingValidation(boolean pendingValidation) {
        this.pendingValidation = pendingValidation;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
