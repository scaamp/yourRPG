package com.example.REST_API.controllers;

import com.example.REST_API.dtos.SpellbookRequest;
import com.example.REST_API.dtos.SpellbookResponse;
import com.example.REST_API.entities.Spellbook;
import com.example.REST_API.services.SpellbookService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/spellbooks")
public class SpellbookController {
    private final SpellbookService SpellbookService;

    @Autowired
    public SpellbookController(SpellbookService SpellbookService) {
        this.SpellbookService = SpellbookService;
    }

    @GetMapping
    public ResponseEntity<List<SpellbookResponse>> getSpellbooks() {
        List<SpellbookResponse> Spellbooks = SpellbookService.getAllSpellbooks();

        return new ResponseEntity<>(Spellbooks, HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Spellbook> getSpellbook(@PathVariable("id") UUID id) {
        return new ResponseEntity (SpellbookService.getSpellbookById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addSpellbook(@RequestBody SpellbookRequest SpellbookRequest) throws URISyntaxException {
        SpellbookService.addSpellbook(SpellbookRequest);
        ResponseEntity.created(new URI("/spellbooks/" + SpellbookRequest.getSpellbookId())).body(SpellbookRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity <Spellbook> updateSpellbook(@PathVariable("id") UUID id, @RequestBody Spellbook Spellbook) {
        SpellbookService.updateSpellbook(id, Spellbook);
        return new ResponseEntity<>(SpellbookService.getSpellbookById(id), HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Spellbook> deleteSpellbook(@PathVariable("id") UUID id) {
        SpellbookService.deleteSpellbook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
