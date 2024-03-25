package com.example.linkgenerator.repo;

import com.example.linkgenerator.model.InvoiceLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepo extends JpaRepository<InvoiceLink, Long> {

    Optional<InvoiceLink> findByLink(String uuid);
}
