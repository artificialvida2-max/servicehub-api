package com.servicehub;

import com.servicehub.config.TestContainersConfig;
import com.servicehub.model.Provider;
import com.servicehub.repository.ProviderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProviderIntegrationTest extends TestContainersConfig {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ProviderRepository providerRepository;

    @Test
    @DisplayName("fluxo completo: criar e buscar provider")
    void deveCriarEBuscarProvider() {
        // Arrange: banco PostgreSQL real via Testcontainers; Flyway aplica migrations automaticamente
        Map<String, String> body = new HashMap<>();
        body.put("name", "Integração Provider");
        body.put("email", "integra@servicehub.com");
        body.put("phone", "11999999999");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        // Act: criar provider via POST
        ResponseEntity<Provider> criarResponse = restTemplate.postForEntity(
                "/providers", request, Provider.class);

        // Assert: criação retornou 201
        assert criarResponse.getStatusCode() == HttpStatus.CREATED;
        assert criarResponse.getBody() != null;
        Long id = criarResponse.getBody().getId();

        // Act: buscar provider criado via GET
        ResponseEntity<Provider> buscarResponse = restTemplate.getForEntity(
                "/providers/" + id, Provider.class);

        // Assert: busca retornou 200 e dados consistentes
        assert buscarResponse.getStatusCode() == HttpStatus.OK;
        assert buscarResponse.getBody() != null;
        assert buscarResponse.getBody().getName().equals("Integração Provider");

        // Verifica persistência real no banco
        assert providerRepository.findById(id).isPresent();
    }
}
