package com.example.REST_API.dtos;

import com.example.REST_API.entities.Character;
import com.example.REST_API.entities.Spellbook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpellbookRequest {
    private long spellbookId;
    private String text;
    private String trainer;
    private String rank;
    private Character character;
}
