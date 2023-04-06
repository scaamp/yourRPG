package com.example.yourrpg.adapter;

import androidx.annotation.DrawableRes;

import java.util.Date;
public interface ViewHolderAdaptable {

    String getText();
    String getRank();
    String getTrainer();
    @DrawableRes
    Integer getCategoryDrawable();
}
