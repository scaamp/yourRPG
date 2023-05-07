package com.example.yourrpg.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

public class Character implements Serializable {
    @JsonProperty("characterId")
    private UUID characterId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("level")
    private int level;

    @JsonProperty("exp")
    private int exp;

    @JsonProperty("strength")
    private int strength;

    @JsonProperty("agility")
    private int agility;

    @JsonProperty("stamina")
    private int stamina;

    @JsonProperty("power")
    private int power;

    @JsonProperty("cunning")
    private int cunning;

    @JsonProperty("endurance")
    private int endurance;

    @JsonProperty("intelligence")
    private int intelligence;

    @JsonProperty("creativity")
    private int creativity;

    @JsonProperty("wisdom")
    private int wisdom;

    @JsonProperty("productivity")
    private int productivity;

    @JsonProperty("influence")
    private int influence;

    @JsonProperty("determination")
    private int determination;

    private int iconPosition;

    public Character(UUID characterId, String name, int level, int exp, int strength, int agility, int stamina, int power, int cunning, int endurance, int intelligence, int creativity, int wisdom, int productivity, int influence, int determination) {
        this.characterId = characterId;
        this.name = name;
        this.level = level;
        this.exp = exp;
        this.strength = strength;
        this.agility = agility;
        this.stamina = stamina;
        this.power = power;
        this.cunning = cunning;
        this.endurance = endurance;
        this.intelligence = intelligence;
        this.creativity = creativity;
        this.wisdom = wisdom;
        this.productivity = productivity;
        this.influence = influence;
        this.determination = determination;
    }

    public Character(UUID characterId, String name, int level, int exp,  int strength, int agility, int iconPosition) {
        this.characterId = characterId;
        this.name = name;
        this.level = level;
        this.exp = exp;
        this.strength = strength;
        this.agility = agility;
        this.iconPosition = iconPosition;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public UUID getCharacterId() {
        return characterId;
    }

    public void setCharacterId(UUID characterId) {
        this.characterId = characterId;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCunning() {
        return cunning;
    }

    public void setCunning(int cunning) {
        this.cunning = cunning;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getCreativity() {
        return creativity;
    }

    public void setCreativity(int creativity) {
        this.creativity = creativity;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getProductivity() {
        return productivity;
    }

    public void setProductivity(int productivity) {
        this.productivity = productivity;
    }

    public int getInfluence() {
        return influence;
    }

    public void setInfluence(int influence) {
        this.influence = influence;
    }

    public int getDetermination() {
        return determination;
    }

    public void setDetermination(int determination) {
        this.determination = determination;
    }

    public Character getCharacter()
    {
        return this;
    }

    public UUID getUserId() {
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

    public void setUserId(UUID characterId) {
        this.characterId = characterId;
    }

    public int getIconPosition() {
        return iconPosition;
    }

    public void setIconPosition(int iconPosition) {
        this.iconPosition = iconPosition;
    }

    @Override
    public String toString()
    {
        return characterId + " " + name + " " + strength;
    }
}
