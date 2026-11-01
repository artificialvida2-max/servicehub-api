package com.servicehub.repository;
import com.servicehub.model.Contract; import org.springframework.data.jpa.repository.JpaRepository;
public interface ContractRepository extends JpaRepository<Contract, Long> {}
