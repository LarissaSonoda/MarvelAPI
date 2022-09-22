package com.example.marvelapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.marvelapp.DAO.UserDAO;

public class Register extends AppCompatActivity {
    EditText edtTxtName, edtTxtEmail, edtTxtPassword;
    String userName, userEmail, userPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);

        edtTxtName = findViewById(R.id.edtTxtLogin);
        edtTxtPassword = findViewById(R.id.edtTxtPsswd);
        btnRegister.setOnClickListener(v-> {
            userName = edtTxtName.getText().toString();
            userPassword = edtTxtPassword.getText().toString();

            Usuario user = new Usuario(null, userName, userPassword);
            UserDAO userDAO = new UserDAO(Register.this);

            try{
                userDAO.insertUser(user);
                Toast.makeText(getApplicationContext(), "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // SAVED INSTANCE
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        userName = edtTxtName.getText().toString();
        userPassword = edtTxtPassword.getText().toString();

        outState.putString("UserName", userName);
        outState.putString("UserPassword", userPassword);
    }

}