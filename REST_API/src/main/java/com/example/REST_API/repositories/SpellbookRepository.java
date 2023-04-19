package com.example.REST_API.repositories;

import com.example.REST_API.entities.Spellbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpellbookRepository extends JpaRepository <Spellbook, UUID> {
    void deleteById(UUID id);
}
