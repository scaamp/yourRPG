package com.example.yourrpg.model;

import com.example.yourrpg.questlogAdapter.QuestlogViewHolderAdaptable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class Questlog implements Serializable, QuestlogViewHolderAdaptable {
    @JsonProperty("questId")
    private long questId;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("stat")
    private String stat;

    @JsonProperty("statPoints")
    private int statPoints;

    @JsonProperty("isDone")
    private boolean isDone;

    @JsonProperty("date")
    private Date date;

    public Questlog(long questId, String desc, String stat, int statPoints, boolean isDone, Date date) {
        this.questId = questId;
        this.desc = desc;
        this.stat = stat;
        this.statPoints = statPoints;
        this.isDone = isDone;
        this.date = date;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatPoints(int statPoints) {
        this.statPoints = statPoints;
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
    public int getStatPoints() {
        return statPoints;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}














































