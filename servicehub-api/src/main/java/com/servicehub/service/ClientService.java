package com.servicehub.service;

import com.servicehub.model.Client;
import com.servicehub.repository.ClientRepository;
import com.servicehub.util.SanitizerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repo;

    @PreAuthorize("isAuthenticated()")
    public List<Client> listar() {
        return repo.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    public Client criar(Client c) {
        c.setName(SanitizerUtil.sanitize(c.getName()));
        c.setEmail(SanitizerUtil.sanitize(c.getEmail()));
        c.setPhone(SanitizerUtil.sanitize(c.getPhone()));
        return repo.save(c);
    }

    @PreAuthorize("isAuthenticated()")
    public Client buscar(Long id) {
        return repo.findById(id).orElse(null);
    }
}
