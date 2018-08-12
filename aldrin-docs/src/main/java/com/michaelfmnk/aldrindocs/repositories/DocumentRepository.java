package com.michaelfmnk.aldrindocs.repositories;

import com.michaelfmnk.aldrindocs.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Optional<Document> findByFileIdAndAndDataIdIsNull(UUID fileId);
}
