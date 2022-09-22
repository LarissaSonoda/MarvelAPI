package com.example.marvelapp;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.marvelapp.DAO.UserDAO;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String FILE_NAME = "usuarioLogado.json";
    private EditText edtTxtLogin, edtTxtPassword;
    private TextView txtcadastro;
    private Button btnLogin;
    private UserDAO userDAO;

    SensorManager sensorManager;
    Sensor sensor;
    ConstraintLayout activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtTxtLogin = findViewById(R.id.edtTxtLogin);
        edtTxtPassword = findViewById(R.id.edtTxtPsswd);
        txtcadastro = (TextView) findViewById(R.id.txtCadastro);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin= (Button) findViewById(R.id.btnLogin);

        //SENSOR
        sensorManager= (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        activity = (ConstraintLayout) findViewById(R.id.acLogin);

        btnLogin.setOnClickListener(v -> {
            String nameLogin = String.valueOf(edtTxtLogin.getText());
            String passwordLogin = String.valueOf(edtTxtPassword.getText());

            checkField();

            userDAO = new UserDAO(getApplicationContext());

            if(userDAO.checkLogin(nameLogin, passwordLogin)){
                Usuario user = userDAO.selectUserByName(nameLogin);

                Gson gson = new Gson();
                String json = gson.toJson(user);
                printUser(json);

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(MainActivity.this, "Usuário ou senha não correspondem", Toast.LENGTH_SHORT).show();
            }
        });

        txtcadastro.setOnClickListener(v -> {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });
    }
    // VALIDAR CAMPOS
    private void checkField(){
        boolean verification = false;

        String email = edtTxtLogin.getText().toString();
        String senha = edtTxtPassword.getText().toString();

        if (verification = nullField(email)) {
            edtTxtLogin.requestFocus();
            Toast.makeText(this, "Preencha o campo e-mail.", Toast.LENGTH_SHORT).show();
        } else if (verification = nullField(senha)) {
            edtTxtPassword.requestFocus();
            Toast.makeText(this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean nullField (String field){
        boolean verification = (TextUtils.isEmpty(field) || field.trim().isEmpty());
        return verification;
    }

    // SAVED INSTANCE
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String name = edtTxtLogin.getText().toString();
        outState.putString("UserName", name);
    }

    // ARMAZENAR DADOS
    private void printUser(String json) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(json.getBytes());
            Toast.makeText(this, "Usuário logado.", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // SENSOR

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            if(event.values[0] < 20000){
                edtTxtLogin.setTextColor(getResources().getColor(R.color.background));
                edtTxtPassword.setTextColor(getResources().getColor(R.color.background));
                activity.setBackgroundResource(R.color.black);
                btnLogin.setBackgroundColor(getResources().getColor(R.color.light_blue_50));
                btnLogin.setTextColor(getResources().getColor(R.color.black));
            }
            else{
                edtTxtLogin.setTextColor(getResources().getColor(R.color.black));
                edtTxtPassword.setTextColor(getResources().getColor(R.color.black));
                activity.setBackgroundResource(R.color.background);
                btnLogin.setBackgroundColor(getResources().getColor(R.color.black));
                btnLogin.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}