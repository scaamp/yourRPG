package com.example.yourrpg.model;

import com.example.yourrpg.questlogAdapter.QuestlogViewHolderAdaptable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Questlog implements Serializable, QuestlogViewHolderAdaptable {
    @JsonProperty("questId")
    private long questId;

    @JsonProperty("desc")
    private String desc;

    public Questlog(long questId, String desc, boolean isDone) {
        this.questId = questId;
        this.desc = desc;
        this.isDone = isDone;
    }

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

    @Override
    public String getText() {
        return null;
    }

    @Override
    public String getDeadline() {
        return null;
    }

    @Override
    public String getStat() {
        return null;
    }

    @Override
    public String getStatPoints() {
        return null;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
