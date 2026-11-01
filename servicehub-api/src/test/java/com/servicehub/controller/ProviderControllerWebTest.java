package com.servicehub.controller;

import com.servicehub.service.ProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProviderController.class)
@ContextConfiguration(classes = {ProviderController.class})
public class ProviderControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listar_deveRetornar200() throws Exception {
        mockMvc.perform(get("/providers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
