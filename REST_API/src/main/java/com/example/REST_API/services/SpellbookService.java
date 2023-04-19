package com.example.REST_API.services;

import com.example.REST_API.dtos.SpellbookRequest;
import com.example.REST_API.dtos.SpellbookResponse;
import com.example.REST_API.entities.Spellbook;

import java.util.List;
import java.util.UUID;

public interface SpellbookService {
    List<SpellbookResponse> getAllSpellbooks();

    void addSpellbook(SpellbookRequest spellbookRequest);

    Spellbook getSpellbookById(UUID id);

    void updateSpellbook(UUID id, Spellbook spellbook);

    void deleteSpellbook(UUID id);
}
