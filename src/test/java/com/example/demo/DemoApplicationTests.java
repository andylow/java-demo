package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {

    private static final String DEMO_USER = "demo";
    private static final String DEMO_PASS = "demo-password";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test '/ukpostcodes' api without credential.")
    public void testNoCredential() throws Exception {
        mockMvc.perform(get("/v1/ukpostcodes/distance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("from", "SW1W 0NY")
                        .param("to", "PO16 7GZ"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test '/ukpostcodes/distance' with valid postcodes")
    public void testGetDistance() throws Exception {

        final String fromPostcode = "AB10 1XG";
        final String toPostcode = "AB12 5GL";

        // test account using `local` SecurityConfig
        final String authHeader = getDemoAuthHeader();

        mockMvc.perform(get("/v1/ukpostcodes/distance").header(HttpHeaders.AUTHORIZATION, authHeader).contentType(MediaType.APPLICATION_JSON).param("from", fromPostcode).param("to", toPostcode))
                //.andDo(MockMvcResultHandlers.print()) // debug print
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.from.postcode", is(fromPostcode))).andExpect(jsonPath("$.to.postcode", is(toPostcode))).andExpect(jsonPath("$.from.lat", is(57.144156))).andExpect(jsonPath("$.from.lon", is(-2.114864))).andExpect(jsonPath("$.to.lat", is(57.081938))).andExpect(jsonPath("$.to.lon", is(-2.246567))).andExpect(jsonPath("$.distance", is(10.539642725457576)));
    }

    @Test
    @DisplayName("Test '/ukpostcodes/distance' with postcodes in lowercase.")
    public void testGetDistanceInLowercase() throws Exception {

        final String fromPostcode = "ab10 1xg";
        final String toPostcode = "AB12 5GL";

        // test account using `local` SecurityConfig
        final String authHeader = getDemoAuthHeader();

        mockMvc.perform(get("/v1/ukpostcodes/distance").header("Authorization", authHeader).contentType(MediaType.APPLICATION_JSON).param("from", fromPostcode).param("to", toPostcode))
                //.andDo(MockMvcResultHandlers.print()) // debug print
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.from.postcode", equalToIgnoringCase(fromPostcode)))
                .andExpect(jsonPath("$.to.postcode", equalToIgnoringCase(toPostcode)))
                .andExpect(jsonPath("$.from.lat", is(57.144156)))
                .andExpect(jsonPath("$.from.lon", is(-2.114864)))
                .andExpect(jsonPath("$.to.lat", is(57.081938)))
                .andExpect(jsonPath("$.to.lon", is(-2.246567)))
                .andExpect(jsonPath("$.distance", is(10.539642725457576)));
    }

    @Test
    @DisplayName("Test '/ukpostcodes/distance' given only `to` postcode.")
    public void testGetDistanceWithoutFrom() throws Exception {
        final String postcode = "AB10 1XG";

        // test account using `local` SecurityConfig
        final String authHeader = getDemoAuthHeader();
        mockMvc.perform(get("/v1/ukpostcodes/distance").header("Authorization", authHeader).contentType(MediaType.APPLICATION_JSON).param("to", postcode))
//                .andDo(MockMvcResultHandlers.print()) // debug print
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test '/ukpostcodes/distance' given only `from` postcode.")
    public void testGetDistanceWithoutTo() throws Exception {
        final String postcode = "AB10 1XG";

        // test account using `local` SecurityConfig
        final String authHeader = getDemoAuthHeader();
        mockMvc.perform(get("/v1/ukpostcodes/distance").header("Authorization", authHeader).contentType(MediaType.APPLICATION_JSON).param("from", postcode))
//                .andDo(MockMvcResultHandlers.print()) // debug print
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test '/ukpostcodes/distance' with invalid postcode.")
    public void testGetDistanceWithInvalidPostcode() throws Exception {
        final String postcode = "ABCD";

        final String expectedMsg = "Invalid UK postcode";

        // test account using `local` SecurityConfig
        final String authHeader = getDemoAuthHeader();
        mockMvc.perform(get("/v1/ukpostcodes/distance").header("Authorization", authHeader).contentType(MediaType.APPLICATION_JSON).param("from", postcode).param("to", postcode)).andDo(MockMvcResultHandlers.print()) // debug print
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.detail", containsString(expectedMsg)));
    }

    private String getDemoAuthHeader() {
        return "Basic " + HttpHeaders.encodeBasicAuth(DEMO_USER, DEMO_PASS, Charset.forName("utf-8"));
    }
}
