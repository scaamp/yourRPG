package com.example.REST_API.services;

import com.example.REST_API.entities.Character;
import com.example.REST_API.dtos.CharacterRequest;
import com.example.REST_API.dtos.CharacterResponse;
import com.example.REST_API.repositories.CharacterRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;

    private final Logger LOG = LoggerFactory.getLogger(CharacterServiceImpl.class);

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }


    @Override
    public List<CharacterResponse> getAllCharacters()
    {
        /*return StreamSupport.stream(characterRepository.findAll().spliterator(), false)
                .map(entity -> new CharacterResponse(entity.getCharacter_id(), entity.getName(), entity.getAge(), entity.getMovie_id().getMovie_id()))
                .collect(Collectors.toList());*/
        List<Character> characters = characterRepository.findAll();
        List<CharacterResponse> characterResponses = new ArrayList<>(characters.size());

        for (Character u : characters) {
            CharacterResponse characterResponse = new CharacterResponse(u.getCharacterId(), u.getName(), u.getLevel(), u.getExp(), u.getStrength(), u.getAgility(), u.getSpellbooks());
            characterResponses.add(characterResponse);
        }

        LOG.info("getAll - number of characters: " + characterResponses.size());
        return characterResponses;
    }

    @Override
    public void addCharacter(CharacterRequest characterRequest)
    {
        Character character = new Character();
        character.setCharacterId(characterRequest.getCharacterId());
        character.setName(characterRequest.getName());
        character.setLevel(characterRequest.getLevel());
        character.setExp(characterRequest.getExp());
        character.setAgility(characterRequest.getAgility());
        character.setStrength(characterRequest.getStrength());
        characterRepository.save(character);
    }

    @Override
    public Character getCharacterById(UUID id) {
        return characterRepository.findById(id).get();
    }

    @Override
    public void updateCharacter(UUID id, Character characterArg) {
        Character character = characterRepository.findById(id).get();
        System.out.println(characterArg.toString());
        character.setName(characterArg.getName());
        character.setLevel(characterArg.getLevel());
        character.setExp(characterArg.getExp());
        character.setAgility(characterArg.getAgility());
        character.setStrength(characterArg.getStrength());
        characterRepository.save(character);
    }

    @Override
    public void deleteCharacter(UUID id) {
        characterRepository.deleteById(id);
    }
}
