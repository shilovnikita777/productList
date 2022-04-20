package com.example.creativeproject.ui.makinglist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MakingListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MakingListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is making list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}