package com.example.yourrpg.model;

import com.example.yourrpg.spellbookAdapter.SpellbookViewHolderAdaptable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

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

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy HH:mm:ss a")
    private Date date;

    public Spellbook(long spellbookId, String text, String trainer, String rank, Date date) {
        this.spellbookId = spellbookId;
        this.text = text;
        this.trainer = trainer;
        this.rank = rank;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
