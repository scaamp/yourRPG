package com.example.REST_API.repositories;

import com.example.REST_API.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    //List<Character> findById(long id);
}
