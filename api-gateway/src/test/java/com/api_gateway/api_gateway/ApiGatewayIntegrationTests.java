package com.api_gateway.api_gateway;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ApiGatewayIntegrationTests {
	@Autowired
	private WebTestClient webTestClient;

	private WireMockServer wireMockServer;

	private String jsonBody;

	@BeforeEach
	void setUpMockServer() {
		wireMockServer = new WireMockServer(8080);
		wireMockServer.start();
		// Define the JSON body
		jsonBody = """
            {
              "firstName": "Joe",
              "lastName": "Bloggs",
              "emailAddress": "joe.bloggs@example.com",
              "password": "PlainTextPassword123!",
              "phoneNumber": "5559876543",
              "dateOfBirth": "1995-07-14",
              "gender": "Female",
              "addressLineOne": "321 Oak Avenue",
              "addressLineTwo": "Apt 12B",
              "townOrCity": "Riverton",
              "county": "Northvale",
              "country": "USA",
              "postcode": "30567",
              "appUserRole": "USER"
            }
            """;
			// Mock the user service endpoint
			wireMockServer.stubFor(post("/user/api/v1/auth/register")
            .willReturn(aResponse()
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    {
                      "id": "123",
                      "firstName": "Joe",
                      "lastName": "Bloggs",
                      "emailAddress": "joe.bloggs@example.com"
                    }
                    """)));
	}

	@AfterEach
	void tearDownMockServer() {
		wireMockServer.stop();
	}

	@Test
	void test_post_request_for_user_service() {
		webTestClient.post()
				.uri("/user/api/v1/auth/register")
				.contentType(APPLICATION_JSON)
				.bodyValue(jsonBody)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.firstName").isEqualTo("Joe")
				.jsonPath("$.lastName").isEqualTo("Bloggs")
				.jsonPath("$.emailAddress").isEqualTo("joe.bloggs@example.com");
	}

	@Test
	void test_path_matcher_for_user_service_registration() {
		webTestClient.post()
				.uri("/user/api/v1/auth/register")
				.exchange()
				.expectStatus().is2xxSuccessful();
	}

//	@Disabled("Skipping this test temporarily")
//	@Test
//	void test_path_matcher_for_user_service_login() {
//		webTestClient.post()
//				.uri("/user/api/v1/auth/login")
//				.exchange()
//				.expectStatus().is2xxSuccessful();
//	}
}
