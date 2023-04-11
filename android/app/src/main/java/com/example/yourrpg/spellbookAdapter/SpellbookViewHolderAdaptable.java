package com.example.yourrpg.spellbookAdapter;

import androidx.annotation.DrawableRes;

import java.util.Date;

public interface SpellbookViewHolderAdaptable {

    String getText();
    String getRank();
    String getTrainer();
    Date getDate();
    @DrawableRes
    Integer getCategoryDrawable();
}
