package com.example.clinicmanagementsystem.webConfig;

import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JwtAuthConverter is responsible for extracting roles from a JWT token issued by Keycloak.
 * <p>
 * It converts the JWT token into a collection of {@link GrantedAuthority} instances for use
 * in Spring Security authorization.
 * <p>
 * This converter retrieves:
 * - **Realm Roles** from the `realm_access` claim
 * - **Client Roles** from the `resource_access` claim (specific to `rest-api-doctor`)
 * <p>
 * Roles are **prefixed with "ROLE_"** to comply with Spring Security's expectations.
 */
public class JwtAuthConverter implements org.springframework.core.convert.converter.Converter<Jwt, Collection<GrantedAuthority>> {

    private final JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {

        Collection<GrantedAuthority> authorities = new HashSet<>(defaultConverter.convert(jwt));

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            try {
                List<String> realmRoles = (List<String>) realmAccess.get("roles");
                authorities.addAll(realmRoles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList());
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        // Extract client roles (Keycloak client roles)
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null && resourceAccess.containsKey("rest-api-doctor")) {
            Map<String, Object> client = (Map<String, Object>) resourceAccess.get("rest-api-doctor");
            List<String> roles = (List<String>) client.get("roles");

            authorities.addAll(roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList()));
        }
        return authorities;
    }
}
