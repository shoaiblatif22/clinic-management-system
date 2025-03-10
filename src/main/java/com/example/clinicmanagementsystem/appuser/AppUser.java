package com.example.clinicmanagementsystem.appuser;

import com.example.clinicmanagementsystem.model.AppUserModel;
import com.example.clinicmanagementsystem.model.AppUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AppUser implements UserDetails {

    private final AppUserModel appUserModel;

    public AppUser(AppUserModel appUserModel) {
        this.appUserModel = appUserModel;
    }

    private Long id;
    private AppUserRole appUserRole;
    private Boolean locked;
    private Boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return appUserModel.getPassword();
    }

    @Override
    public String getUsername() {
        return appUserModel.getEmailAddress();
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
        return false;
    }
}
