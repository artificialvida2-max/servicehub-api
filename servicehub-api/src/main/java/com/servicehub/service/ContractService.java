package com.servicehub.service;

import com.servicehub.model.Contract;
import com.servicehub.repository.ContractRepository;
import com.servicehub.util.SanitizerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository repo;

    @PreAuthorize("isAuthenticated()")
    public List<Contract> listar() {
        return repo.findAll();
    }

    @PreAuthorize("hasRole('CLIENT')")
    public Contract criar(Contract c) {
        if (c.getDescription() != null) {
            c.setDescription(SanitizerUtil.sanitize(c.getDescription()));
        }
        return repo.save(c);
    }

    @PreAuthorize("isAuthenticated()")
    public Contract buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public boolean remover(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
