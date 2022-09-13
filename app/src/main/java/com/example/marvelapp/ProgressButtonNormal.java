package com.example.marvelapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.content.res.ResourcesCompat;

public class ProgressButtonNormal extends AppCompatButton {
    Drawable mLocalButtonProgress;

    public ProgressButtonNormal(Context context){
        super(context);
        init();
    }

    public ProgressButtonNormal(Context context,
                                AttributeSet attrs,
                                int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mLocalButtonProgress = ResourcesCompat.getDrawable(getResources(), R.drawable.button_bg_normal, null);

        setOnTouchListener(new OnTouchListener() {
            @RequiresApi(api= Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }
}
