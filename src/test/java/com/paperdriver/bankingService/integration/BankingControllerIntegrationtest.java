package com.paperdriver.bankingService.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
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
    public void testGetParallel() throws Exception {
        List<CompletableFuture<Object>> allRequests = new ArrayList();

        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders.get("/api/banking/get")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(getHeaders())
                .param("email", "yuxuan.mao.1@gmail.com");

        for(int i = 0; i < 1000; i++){
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
    }

    @Test
    public void testSendPaymentParallel() throws Exception {
        List<CompletableFuture<Object>> allRequests = new ArrayList();

        String payload = "{\"fromEmail\": \"yuxuan.mao.1@gmail.com\", \"toEmail\": \"josh.sammut@gmail.com\", \"amount\": 100}";

        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders.post("/api/banking/send")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(getHeaders())
                .content(payload);

        for(int i = 0; i < 1000; i++){
            allRequests.add(CompletableFuture.supplyAsync(() -> {
                try {
                    return mockMvc.perform(request1).andExpect(status().is(202));
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }));
        }

        CompletableFuture.allOf(allRequests.toArray(new CompletableFuture[0])).join();
    }
}
