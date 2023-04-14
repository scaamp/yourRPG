package com.example.REST_API.dtos;

import com.example.REST_API.entities.Spellbook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CharacterRequest
{
    private long characterId;
    private String name;
    private int level;
    private int exp;
    private int strength;
    private int agility;
    private List<Spellbook> spellbooks = new ArrayList<Spellbook>();
}
