package com.example.clinicmanagementsystem.service;

import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;



    public class ConcurrentMapUserCache implements UserCache {

        private final ConcurrentMap<String, UserDetails> cache = new ConcurrentHashMap<>();

        @Override
        public UserDetails getUserFromCache(String username) {
            return cache.get(username);
        }

        @Override
        public void putUserInCache(UserDetails user) {
            cache.put(user.getUsername(), user);
        }

        @Override
        public void removeUserFromCache(String username) {
            cache.remove(username);
        }
    }
