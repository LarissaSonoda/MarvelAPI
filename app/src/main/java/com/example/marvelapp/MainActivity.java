package com.example.marvelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.marvelapp.DAO.UserDAO;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "usuarioLogado.json";
    private EditText edtTxtEmail, edtTxtPassword;
    private TextView txtcadastro;
    private Button btnLogin;
    private UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void home(View v){
       Intent homeActivity = new Intent(this, Home.class);
       startActivity(homeActivity);
    }
}