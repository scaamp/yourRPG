package com.example.yourrpg.spellbookAdapter;

import androidx.annotation.DrawableRes;

import java.util.Date;
import java.util.UUID;

public interface SpellbookViewHolderAdaptable {
    long getSpellbookId();
    String getText();
    String getRank();
    String getTrainer();
    UUID getId();
    Date getDate();
    @DrawableRes
    Integer getCategoryDrawable();
}
