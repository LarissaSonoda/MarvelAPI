package com.example.marvelapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.content.res.ResourcesCompat;

import java.util.concurrent.atomic.AtomicBoolean;

public class ProgressButtonNormal extends AppCompatButton {
    Drawable mLocalButtonProgress;
    ProgressBar progressbar;

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
                if ((getCompoundDrawablesRelative()[2] != null)) {

                    //um boolean que pode ser atualizado dinamicamente
                    AtomicBoolean isProgressButtonClicked = new AtomicBoolean(false);

                        // verifica o clique do botão
                        if (isProgressButtonClicked.get()) {
                            // Verifica o ACTION_DOWN (sempre ocorre antes do ACTION_UP).
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                // troca a versão do botão
                                mLocalButtonProgress =
                                        ResourcesCompat.getDrawable(getResources(),
                                                R.drawable.button_bg_normal, null);
                                showProgressButton();
                            }
                            // Verifica o  ACTION_UP.
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                // Troca pela versão opaca
                                mLocalButtonProgress =
                                        ResourcesCompat.getDrawable(getResources(),
                                                R.drawable.button_bg_disabled, null);
                                progressbar.setVisibility(View.VISIBLE);

                                return true;
                            }
                        } else {
                            return false;
                        }
                    }
                return false;
            }
        });
      /*  // Se o texto muda mostra/oculta o botão
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start, int count, int after) {
                // Do nothing.
            }

            @Override
            public void onTextChanged(CharSequence s,
                                      int start, int before, int count) {
                showProgressButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing.
            }
        });*/
    }

    //exibição do botão
    private void showProgressButton() {
        // Define  aposição do drawable
        //exige versão minima do sdk
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelativeWithIntrinsicBounds
                    (null,                      // Inicio do texto
                            null,               // Topo do texto.
                            mLocalButtonProgress,  // Fim do Texto
                            null);              // Abaixo do texto
        }
    }

}
