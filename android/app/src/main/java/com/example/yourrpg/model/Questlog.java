package com.example.yourrpg.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Questlog implements Serializable {
    @JsonProperty("questId")
    private long questId;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("isDone")
    private boolean isDone;

    public long getQuestId() {
        return questId;
    }

    public void setQuestId(long questId) {
        this.questId = questId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
