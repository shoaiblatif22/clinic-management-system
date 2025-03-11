package com.example.clinicmanagementsystem.entity;

import com.example.clinicmanagementsystem.model.AppUserModel;
import com.example.clinicmanagementsystem.model.AppUserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class AppUserEntity implements UserDetails {
    @Id
    @SequenceGenerator(name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    private Long id;

    @Column(unique = true, nullable = false)
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private String addressLineOne;
    private String addressLineTwo;
    private String townOrCity;
    private String postcode;
    private String county;
    private String country;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean locked;
    private Boolean enabled;

    public AppUserEntity(AppUserModel appUserModel, String keycloakId) {
    }

    public AppUserEntity(String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String emailAddress, String addressLineOne, String addressLineTwo, String townOrCity, String postcode, String county, String country, String password, AppUserRole appUserRole, Boolean locked, Boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.townOrCity = townOrCity;
        this.postcode = postcode;
        this.county = county;
        this.country = country;
        this.password = password;
        this.appUserRole = appUserRole;
        this.locked = locked;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

