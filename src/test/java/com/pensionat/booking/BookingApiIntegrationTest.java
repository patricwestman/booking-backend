package com.pensionat.booking;

import com.pensionat.booking.dto.CreateBookingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookingApiIntegrationTest {

    private static final WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        wireMockServer.start();

        registry.add(
                "customer.service.url",
                wireMockServer::baseUrl
        );
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    @BeforeEach
    void setUp(){
        url = "http://localhost:" + port + "/api/bookings";

        wireMockServer.resetAll();

        wireMockServer.stubFor(
                get(urlEqualTo("/api/customers/99999"))
                        .willReturn(
                                aResponse()
                                        .withStatus(404)

                        )
        );
    }

    @Test
    void shouldReturn404WhenCustomerDoesNotExist() {
        CreateBookingRequest request = new CreateBookingRequest(
                99999L,
                1L,
                LocalDate.of(2026,11,1),
                LocalDate.of(2026,11,4),false
        );

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url,
                        request,
                        String.class
                );


        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    void shouldReturn200WhenGettingBookings(){

        ResponseEntity<String> response =
                restTemplate.getForEntity(url,String.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}
