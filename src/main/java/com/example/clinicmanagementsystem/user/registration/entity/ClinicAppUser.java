package com.example.clinicmanagementsystem.user.registration.entity;

import com.example.clinicmanagementsystem.user.registration.model.AppUserModelRegister;
import com.example.clinicmanagementsystem.user.registration.model.AppUserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class ClinicAppUser implements UserDetails {
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
    @Column(name = "enabled")
    private Boolean enabled;

    public ClinicAppUser(AppUserModelRegister appUserModelRegister) {
    }

    public ClinicAppUser(String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String emailAddress, String addressLineOne, String addressLineTwo, String townOrCity, String postcode, String county, String country, String password, AppUserRole appUserRole, Boolean locked, Boolean enabled) {
        super();
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
        this.enabled = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(appUserRole.name()));
        return authorities;
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

