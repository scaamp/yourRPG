package com.example.yourrpg.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Character implements Serializable {
    @JsonProperty("userId")
    private long userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("strength")
    private int strength;

    public Character(long userId, String name, int strength) {
        this.userId = userId;
        this.name = name;
        this.strength = strength;
    }

    public long getUserId() {
        return userId;
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

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return userId + " " + name + " " + strength;
    }
}
