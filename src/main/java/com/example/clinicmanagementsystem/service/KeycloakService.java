package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.model.UserModel;
import org.keycloak.models.DefaultActionTokenKey;
import org.keycloak.models.SingleUseObjectKeyModel;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;
import org.keycloak.representations.JsonWebToken;

@Service
public class KeycloakService {

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.my_realm}")
    private String keycloakRealm;

    @Value("${keycloak.client-id}")
    private String keycloakClientId;

    @Value("${keycloak.client-secret}")
    private String keycloakClientSecret;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getAdminAccessToken() {
        String url = keycloakServerUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakClientId);
        body.add("grant_type", "password");
        body.add("username", adminUsername);
        body.add("password", adminPassword);
        body.add("client_secret", keycloakClientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        return response.getBody().get("access_token").toString();
    }

    public String registerUser(UserModel userModel) {
        // Obtain the admin access token
        String adminToken = getAdminAccessToken();

        // Use the token to create the user via Keycloak REST API
        String url = keycloakServerUrl + "/admin/realms/" + keycloakRealm + "/users";

        // Build user representation
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userModel.getEmailAddress());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmailAddress());
        user.setEnabled(true);

        // Add custom attributes
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("dateOfBirth", Collections.singletonList(String.valueOf(userModel.getDateOfBirth())));
        attributes.put("gender", Collections.singletonList(userModel.getGender()));
        attributes.put("phoneNumber", Collections.singletonList(userModel.getPhoneNumber()));
        attributes.put("addressLine1", Collections.singletonList(userModel.getAddressLineOne()));
        attributes.put("addressLine2", Collections.singletonList(userModel.getAddressLineTwo()));
        attributes.put("townCity", Collections.singletonList(userModel.getTownOrCity()));
        attributes.put("country", Collections.singletonList(userModel.getCountry()));
        attributes.put("postcode", Collections.singletonList(userModel.getPostcode()));

        user.setAttributes(attributes);

        // Create HTTP request with admin access token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + adminToken);

        HttpEntity<UserRepresentation> request = new HttpEntity<>(user, headers);

        // Send request to Keycloak to create the user
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        // Check response and return appropriate message
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return "User registered successfully";
        } else {
            return "User registration failed";
        }
    }

//    public class VerifyEmailActionToken extends DefaultActionToken {
//
//
//    }

//    public class DefaultActionToken extends JsonWebToken implements SingleUseObjectKeyModel {
//        @Override
//        public String getUserId() {
//            return "";
//        }
//
//        @Override
//        public String getActionId() {
//            return "";
//        }
//
//        @Override
//        public UUID getActionVerificationNonce() {
//            return null;
//        }
//        public DefaultActionTokenKey(String userId,
//                                     String actionId,
//                                     int absoluteExpirationInSecs,
//                                     UUID actionVerificationNonce);
//    }
}



