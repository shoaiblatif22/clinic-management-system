package com.example.clinicmanagementsystem.user.registration.service;

import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapUserCache implements UserCache {

    private final ConcurrentMap<String, UserDetails> cache = new ConcurrentHashMap<>();

    @Override
    public UserDetails getUserFromCache(String username) {
        UserDetails user = cache.get(username);
        System.out.println("Getting user from cache: " + username + " - " + user);
        return user;
    }

    @Override
    public void putUserInCache(UserDetails user) {
        System.out.println("Putting user in cache: " + user.getUsername() + " - " + user);
        cache.put(user.getUsername(), user);
    }

    @Override
    public void removeUserFromCache(String username) {
        System.out.println("Removing user from cache: " + username);
        cache.remove(username);
    }
}