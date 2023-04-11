package com.example.yourrpg.model;

import com.example.yourrpg.spellbookAdapter.SpellbookViewHolderAdaptable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Spellbook implements Serializable, SpellbookViewHolderAdaptable {

    @JsonProperty("spellbookId")
    private long spellbookId;

    @JsonProperty("text")
    @SerializedName("affirmation")
    private String text;

    @JsonProperty("trainer")
    private String trainer;

    @JsonProperty("rank")
    private String rank;

    public Spellbook(long spellbookId, String text, String trainer, String rank) {
        this.spellbookId = spellbookId;
        this.text = text;
        this.trainer = trainer;
        this.rank = rank;
    }

    public long getSpellbookId() {
        return spellbookId;
    }

    public void setSpellbookId(long spellbookId) {
        this.spellbookId = spellbookId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTrainer() {
        return trainer;
    }

    @Override
    public Integer getCategoryDrawable() {
        return null;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
