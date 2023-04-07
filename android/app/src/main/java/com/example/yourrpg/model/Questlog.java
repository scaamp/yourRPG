package com.example.yourrpg.model;

import com.example.yourrpg.questlogAdapter.QuestlogViewHolderAdaptable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Questlog implements Serializable, QuestlogViewHolderAdaptable {
    @JsonProperty("questId")
    private long questId;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("stat")
    private String stat;

    @JsonProperty("statPoints")
    private String statPoints;

    @JsonProperty("isDone")
    private boolean isDone;

    public Questlog(long questId, String desc, String stat, String statPoints, boolean isDone) {
        this.questId = questId;
        this.desc = desc;
        this.stat = stat;
        this.statPoints = statPoints;
        this.isDone = isDone;
    }

    public long getQuestId() {
        return questId;
    }

    public void setQuestId(long questId) {
        this.questId = questId;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getDeadline() {
        return null;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public String getStatPoints() {
        return statPoints;
    }

    public void setStatPoints(String statPoints) {
        this.statPoints = statPoints;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}














































