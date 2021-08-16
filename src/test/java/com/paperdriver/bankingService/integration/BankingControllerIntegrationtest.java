package com.paperdriver.bankingService.integration;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
public class BankingControllerIntegrationtest extends BaseWiremockTest {

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/banking/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(getHeaders())
                        .param("email", "yuxuan.mao.1@gmail.com")
        )
                .andExpect(status().is(200));
    }

    @Test
    public void testParallel() throws Exception {
        List<CompletableFuture<Object>> allRequests = new ArrayList();

        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders.get("/api/banking/get")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(getHeaders())
                .param("email", "yuxuan.mao.1@gmail.com");

        for(int i = 0; i < 5; i++){
            allRequests.add(CompletableFuture.supplyAsync(() -> {
                try {
                    return mockMvc.perform(request1).andExpect(status().is(200));
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }));
        }

        CompletableFuture.allOf(allRequests.toArray(new CompletableFuture[0])).join();

//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/banking/get")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .headers(getHeaders())
//                                .param("email", "yuxuan.mao.1@gmail.com")
//                )
//                .andExpect(status().is(200));
    }
}
