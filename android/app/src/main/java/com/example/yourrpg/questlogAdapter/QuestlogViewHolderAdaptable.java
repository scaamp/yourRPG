package com.example.yourrpg.questlogAdapter;

public interface QuestlogViewHolderAdaptable {
    String getDesc();
    String getDeadline();
    String getStat();
    int getStatPoints();
    boolean isDone();
    void setDone(boolean isDone);


}
