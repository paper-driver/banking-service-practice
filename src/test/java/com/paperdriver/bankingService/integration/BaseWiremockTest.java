package com.paperdriver.bankingService.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseWiremockTest extends BaseIntegrationTest {

    protected WireMockServer wireMockServer;
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void setupBaseWiremockTest() {
        this.wireMockServer = new WireMockServer(8082);
        this.wireMockServer.start();

        this.objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDownBaseWiremockTest() {
        this.wireMockServer.stop();
    }

}
