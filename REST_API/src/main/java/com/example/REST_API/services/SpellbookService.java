package com.example.REST_API.services;

import com.example.REST_API.dtos.SpellbookRequest;
import com.example.REST_API.dtos.SpellbookResponse;
import com.example.REST_API.entities.Spellbook;

import java.util.List;

public interface SpellbookService {
    List<SpellbookResponse> getAllSpellbooks();

    void addSpellbook(SpellbookRequest spellbookRequest);

    Spellbook getSpellbookById(Long id);

    void updateSpellbook(Long id, Spellbook spellbook);

    void deleteSpellbook(Long id);
}
