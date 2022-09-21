package com.example.marvelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(onClickLogin);
    }
    private View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // TODO Auto-generated method stub

            Intent i = new Intent(MainActivity.this , Home.class);
            //startActivityForResult(i, ADD);
            startActivity(i);
        }

    };


    // public void home(View v){
     //  Intent homeActivity = new Intent(this, Home.class);
       //startActivity(homeActivity);
    //}
}