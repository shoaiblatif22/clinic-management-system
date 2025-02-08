package com.example.clinicmanagementsystem.entity;

import com.example.clinicmanagementsystem.model.UserModel;
import jakarta.persistence.*;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String keycloakId;

    private String firstName;
    private String lastName;
    private Integer dateOfBirth;
    private String gender;
    private Integer phoneNumber;
    private String emailAddress;
    private String addressLineOne;
    private String addressLineTwo;
    private String townOrCity;
    private String postcode;
    private String country;

    public UserEntity(UserModel userModel, String keycloakId) {
        this.keycloakId = keycloakId;
        this.firstName = userModel.getFirstName();
        this.lastName = userModel.getLastName();
        this.dateOfBirth = userModel.getDateOfBirth();
        this.gender = userModel.getGender();
        this.phoneNumber = userModel.getPhoneNumber();
        this.emailAddress = userModel.getEmailAddress();
        this.addressLineOne = userModel.getAddressLineOne();
        this.addressLineTwo = userModel.getAddressLineTwo();
        this.townOrCity = userModel.getTownOrcity();
        this.postcode = userModel.getPostcode();
        this.country = userModel.getCountry();
    }

    public UserEntity() {
    }

    // Getters and Setters for all the fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Integer dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public String getTownOrCity() {
        return townOrCity;
    }

    public void setTownOrCity(String townOrCity) {
        this.townOrCity = townOrCity;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

