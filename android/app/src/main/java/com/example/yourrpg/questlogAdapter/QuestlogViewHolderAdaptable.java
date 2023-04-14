package com.example.yourrpg.questlogAdapter;

import java.util.Date;

public interface QuestlogViewHolderAdaptable {
    String getDesc();
    String getDeadline();
    String getStat();
    Date getDate();
    int getStatPoints();
    boolean isDone();
    void setDone(boolean isDone);
}
