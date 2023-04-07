package com.example.yourrpg.spellbookAdapter;

import androidx.annotation.DrawableRes;

public interface SpellbookViewHolderAdaptable {

    String getText();
    String getRank();
    String getTrainer();
    @DrawableRes
    Integer getCategoryDrawable();
}
