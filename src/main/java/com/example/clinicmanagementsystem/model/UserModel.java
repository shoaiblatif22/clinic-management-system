package com.example.clinicmanagementsystem.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;

public class UserModel {

    @NonNull
    @Size(min = 2, max = 30)
    private String firstName;

    @NonNull
    @Size(min = 2, max = 30)
    private String lastName;

    @NonNull
    @Min(5)
    private Integer dateOfBirth;
    @Getter
    private String gender;
    @Getter
    private Integer phoneNumber;
    @Getter
    private String emailAddress;
    @Getter
    private String addressLineOne;
    @Getter
    private String addressLineTwo;
    @Getter
    private String townOrcity;
    @Getter
    private String postcode;
    @Getter
    private String country;

    //overloaded constructor
    public UserModel(@NonNull String firstName, @NonNull String lastName, @NonNull Integer dateOfBirth, String gender, Integer phoneNumber, String emailAddress, String addressLineOne, String addressLineTwo, String townOrcity, String postcode, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.townOrcity = townOrcity;
        this.postcode = postcode;
        this.country = country;
    }
    //default constructor
    public UserModel(){}

    public @NonNull @Size(min = 2, max = 30) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull @Size(min = 2, max = 30) String firstName) {
        if(firstName.isEmpty()){
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if(!firstName.matches("^[a-zA-Z]*$")){
            throw new IllegalArgumentException("First name contains invalid characters");
        } else {
            this.firstName = firstName;
        }
    }

    public @NonNull @Size(min = 2, max = 30) String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull @Size(min = 2, max = 30) String lastName) {
        if(lastName.isEmpty()){
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if(!lastName.matches("^[a-zA-Z]*$")){
            throw new IllegalArgumentException("First name contains invalid characters");
        } else {
            this.lastName = lastName;
        }

    }

    public @NonNull @Min(5) Integer getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NonNull @Min(5) Integer dateOfBirth) {
        if(dateOfBirth < 5){
            throw new IllegalArgumentException("Date of birth cannot be less than 5");
        }
        if(dateOfBirth.describeConstable().isEmpty()){
            throw new IllegalArgumentException("Date of birth cannot be empty");
        } else {
            this.dateOfBirth = dateOfBirth;
        }
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTownOrcity(String townOrcity) {
        this.townOrcity = townOrcity;
    }
}
