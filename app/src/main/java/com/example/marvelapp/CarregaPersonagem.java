package com.example.marvelapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class CarregaPersonagem extends AsyncTaskLoader<String> {
    private String nameStartsWith;
    public CarregaPersonagem(Context context, String NAME) {
        super(context);
        nameStartsWith = NAME;

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.searchCharacter(nameStartsWith);
    }
}