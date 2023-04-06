package com.example.yourrpg.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Character implements Serializable {
    @JsonProperty("characterId")
    private long characterId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("strength")
    private int strength;

    public Character(long characterId, String name, int strength) {
        this.characterId = characterId;
        this.name = name;
        this.strength = strength;
    }

    public Character getCharacter()
    {
        return this;
    }

    public long getUserId() {
        return characterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setUserId(long characterId) {
        this.characterId = characterId;
    }

    @Override
    public String toString()
    {
        return characterId + " " + name + " " + strength;
    }
}
