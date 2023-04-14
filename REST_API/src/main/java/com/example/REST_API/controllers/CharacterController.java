package com.example.REST_API.controllers;

import com.example.REST_API.entities.Character;
import com.example.REST_API.dtos.CharacterRequest;
import com.example.REST_API.dtos.CharacterResponse;
import com.example.REST_API.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {
    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public ResponseEntity<List<CharacterResponse>> getCharacters() {
        List<CharacterResponse> characters = characterService.getAllCharacters();

        return new ResponseEntity<>(characters, HttpStatus.OK);
    }
    
    @GetMapping({"/{id}"})
    public ResponseEntity<Character> getCharacter(@PathVariable("id") Long id) {
        return new ResponseEntity<>(characterService.getCharacterById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addCharacter(@RequestBody CharacterRequest characterRequest) throws URISyntaxException {
        characterService.addCharacter(characterRequest);
        ResponseEntity.created(new URI("/characters/" + characterRequest.getCharacterId())).body(characterRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @PutMapping({"/{id}"})
    public ResponseEntity<Character> updateCharacter(@PathVariable("id") Long id, @RequestBody Character character) {
        characterService.updateCharacter(id, character);
        return new ResponseEntity<>(characterService.getCharacterById(id), HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Character> deleteCharacter(@PathVariable("id") Long id) {
        characterService.deleteCharacter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/characters")
//    public ResponseEntity<List<Character>> getAllCharacters(@RequestParam(required = false) String title) {
//        try {
//            List<Character> characters = new ArrayList<Character>();
//
//            if (title == null)
//                characterRepository.findAll().forEach(characters::add);
//            else
//                characterRepository.findById(title).forEach(characters::add);
//
//            if (characters.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(characters, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping("/character/{id}")
//    public ResponseEntity<Character> getCharacterById(@PathVariable("id") long id) {
//        Optional<Character> characterData = characterRepository.findById(id);
//
//        if (characterData.isPresent()) {
//            return new ResponseEntity<>(characterData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//    @PostMapping("/character")
//    public ResponseEntity<Character> createCharacter(@RequestBody Character character) {
//        try {
//            Character _character = characterRepository
//                    .save(new Character(character.getId()));
//            return new ResponseEntity<>(_character, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}
