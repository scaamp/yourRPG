package com.example.REST_API.repositories;

import com.example.REST_API.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, UUID> {
    void deleteById(UUID id);

    Optional<Character> findById(UUID id);
}
