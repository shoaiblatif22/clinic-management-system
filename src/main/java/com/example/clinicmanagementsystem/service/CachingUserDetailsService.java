package com.example.clinicmanagementsystem.service;

import lombok.Setter;
import org.springframework.security.core.userdetails.*;

public class CachingUserDetailsService implements UserDetailsService {

    private final UserDetailsService delegate;
    @Setter
    private UserCache userCache;

    public CachingUserDetailsService(UserDetailsService delegate) {
        this.delegate = delegate;
        this.userCache = new ConcurrentMapUserCache();  // Using the custom cache implementation
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails cachedUser = userCache.getUserFromCache(username);
        if (cachedUser != null) {
            return cachedUser;
        } else {
            cachedUser = delegate.loadUserByUsername(username);
            userCache.putUserInCache(cachedUser);
            return cachedUser;
        }
    }
}
