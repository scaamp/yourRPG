package com.example.REST_API.services;

import com.example.REST_API.entities.Character;
import com.example.REST_API.dtos.CharacterRequest;
import com.example.REST_API.dtos.CharacterResponse;

import java.util.List;
import java.util.UUID;

public interface CharacterService {
    List<CharacterResponse> getAllCharacters();

    void addCharacter(CharacterRequest characterRequest);

    Character getCharacterById(UUID id);

    void updateCharacter(UUID id, Character character);

    void deleteCharacter(UUID id);
}
