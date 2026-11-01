package com.servicehub.graphql;

import com.servicehub.model.Provider;
import com.servicehub.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProviderGraphQLController {

    private final ProviderService providerService;

    @QueryMapping
    public Provider provider(@Argument Long id) {
        return providerService.buscar(id);
    }

    @QueryMapping
    public List<Provider> providers(@Argument String specialty, @Argument Boolean available) {
        return providerService.listar();
    }

    @MutationMapping
    public Provider createProvider(@Argument("input") ProviderInput input) {
        Provider p = new Provider();
        p.setName(input.getName());
        p.setEmail(input.getEmail());
        p.setPhone(input.getPhone());
        return providerService.criar(p);
    }

    @SchemaMapping(typeName = "Provider", field = "services")
    public List<Object> services(Provider provider) {
        return java.util.Collections.emptyList();
    }

    public static class ProviderInput {
        private String name;
        private String email;
        private String phone;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
    }
}
