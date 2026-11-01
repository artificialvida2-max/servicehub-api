package com.servicehub.controller;

import com.servicehub.dto.ProviderRequest;
import com.servicehub.service.ProviderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProviderController.class)
@ContextConfiguration(classes = {ProviderController.class})
public class ProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /providers deve retornar 200 OK com lista JSON")
    void getProviders_deveRetornar200ComListaJson() throws Exception {
        mockMvc.perform(get("/providers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /providers com body válido deve retornar 201 Created")
    void postProviders_comBodyValido_deveRetornar201() throws Exception {
        String json = "{\"name\":\"João\",\"email\":\"joao@email.com\"}";
        mockMvc.perform(post("/providers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /providers com body inválido deve retornar 400 Bad Request")
    void postProviders_comBodyInvalido_deveRetornar400() throws Exception {
        String json = "{\"name\":\"\",\"email\":\"invalido\"}";
        mockMvc.perform(post("/providers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /providers/{id} inexistente deve retornar 404 Not Found")
    void getProviderInexistente_deveRetornar404() throws Exception {
        mockMvc.perform(get("/providers/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
