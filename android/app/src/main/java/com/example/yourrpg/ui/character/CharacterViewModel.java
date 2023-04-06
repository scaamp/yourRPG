package com.example.yourrpg.ui.character;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CharacterViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CharacterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}