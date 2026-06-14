package com.example.clinicmanagementsystem.webConfig;

import com.example.clinicmanagementsystem.login.service.JwtService;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

    @Mock JwtService jwtService;
    @Mock UserDetailsService userDetailsService;

    @InjectMocks
    JwtAuthFilter filter;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
        user = UserEntity.builder()
                .emailAddress("test@example.com")
                .password("encoded")
                .userRole(com.example.clinicmanagementsystem.registration.model.UserRole.USER)
                .locked(false)
                .enabled(true)
                .build();
    }

    @Test
    void noAuthHeader_chainsWithoutSettingAuth() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(req, res, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertNotNull(chain.getRequest()); // chain was called
    }

    @Test
    void nonBearerHeader_chainsWithoutSettingAuth() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Basic dXNlcjpwYXNz");
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(req, new MockHttpServletResponse(), chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void validBearerToken_setsAuthentication() throws Exception {
        when(jwtService.extractUsername("valid-token")).thenReturn("test@example.com");
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(user);
        when(jwtService.isTokenValid("valid-token", user)).thenReturn(true);

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer valid-token");
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(req, new MockHttpServletResponse(), chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("test@example.com",
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void invalidToken_chainsWithoutSettingAuth() throws Exception {
        when(jwtService.extractUsername("bad-token")).thenThrow(new RuntimeException("bad"));

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer bad-token");
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(req, new MockHttpServletResponse(), chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertNotNull(chain.getRequest()); // chain still called
    }

    @Test
    void tokenValidFalse_doesNotSetAuthentication() throws Exception {
        when(jwtService.extractUsername("token")).thenReturn("test@example.com");
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(user);
        when(jwtService.isTokenValid("token", user)).thenReturn(false);

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer token");

        filter.doFilter(req, new MockHttpServletResponse(), new MockFilterChain());

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
