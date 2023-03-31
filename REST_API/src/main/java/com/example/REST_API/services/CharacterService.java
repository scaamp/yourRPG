package com.example.REST_API.services;

import com.example.REST_API.entities.Character;
import com.example.REST_API.dtos.CharacterRequest;
import com.example.REST_API.dtos.CharacterResponse;

import java.util.List;

public interface CharacterService {
    List<CharacterResponse> getAllCharacters();

    void addCharacter(CharacterRequest characterRequest);

    Character getCharacterById(Long id);

    void updateCharacter(Long id, Character character);

    void deleteCharacter(Long id);
}
