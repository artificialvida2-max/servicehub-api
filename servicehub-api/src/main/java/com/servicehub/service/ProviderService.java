package com.servicehub.service;

import com.servicehub.model.Provider;
import com.servicehub.repository.ProviderRepository;
import com.servicehub.util.SanitizerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository repo;

    @PreAuthorize("isAuthenticated()")
    public List<Provider> listar() {
        return repo.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROVIDER')")
    public Provider criar(Provider p) {
        p.setName(SanitizerUtil.sanitize(p.getName()));
        p.setEmail(SanitizerUtil.sanitize(p.getEmail()));
        p.setPhone(SanitizerUtil.sanitize(p.getPhone()));
        return repo.save(p);
    }

    @PreAuthorize("isAuthenticated()")
    public Provider buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROVIDER')")
    public Provider atualizar(Long id, Provider p) {
        p.setId(id);
        p.setName(SanitizerUtil.sanitize(p.getName()));
        p.setEmail(SanitizerUtil.sanitize(p.getEmail()));
        p.setPhone(SanitizerUtil.sanitize(p.getPhone()));
        return repo.save(p);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROVIDER')")
    public Provider atualizarParcial(Long id, Provider p) {
        Provider existente = buscar(id);
        if (existente == null) return null;
        if (p.getName() != null) existente.setName(SanitizerUtil.sanitize(p.getName()));
        if (p.getEmail() != null) existente.setEmail(SanitizerUtil.sanitize(p.getEmail()));
        if (p.getPhone() != null) existente.setPhone(SanitizerUtil.sanitize(p.getPhone()));
        return repo.save(existente);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public boolean remover(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
