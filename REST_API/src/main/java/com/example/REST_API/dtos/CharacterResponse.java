package com.example.REST_API.dtos;
import com.example.REST_API.entities.Spellbook;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterResponse
{
    private UUID characterId;
    private String name;
    private int level;
    private int exp;
    private int strength;
    private int agility;
    private List<Spellbook> spellbooks = new ArrayList<Spellbook>();

    public CharacterResponse(long id) {
    }
}

