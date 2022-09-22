package com.example.marvelapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class User extends AppCompatActivity implements FetchAddressTask.OnTaskCompleted {
    // Arquivo shared preferences
    public static final String PREFERENCIAS_NAME = "com.example.android.geolocalizacao";
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    // Constantes
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String LASTADRESS_KEY = "adress";
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";

    // classes Location
    private boolean mTrackingLocation;


    // Shared preferences
    private SharedPreferences mPreferences;
    private String lastLatitude = "";
    private String lastLongitude = "";
    private String lastAdress = "";

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private TextView mLocationTextView;
    private Button mLocationBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);
        mLocationTextView = (TextView) findViewById(R.id.txtEndereco);
        mLocationBtn = (Button) findViewById(R.id.btnLocal);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);

        // Recupera o estado da aplicação quando é recriado
        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }


// Inicializa os callbacks da locations.
        mLocationCallback = new LocationCallback() {
            /**
             * This is the callback that is triggered when the
             * FusedLocationClient atualiza a localização.
             * @param locationResult The result containing the device location.
             */
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // If tracking is turned on, reverse geocode into an address
                if (mTrackingLocation) {
                    new FetchAddressTask(User.this, User.this)
                            .execute(locationResult.getLastLocation());
                }
            }
        };


        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Toggle the tracking state.
             * @param v The track location button.
             */
            @Override
            public void onClick(View v) {
                if (!mTrackingLocation) {
                    iniciarLocal();

                } else {
                    pararLocal();
                }
            }
        });

        //inicializa as preferências do usuário
        mPreferences = getSharedPreferences(PREFERENCIAS_NAME, MODE_PRIVATE);
        //recupera as preferencias
        recuperar();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // Permissão garantida
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    iniciarLocal();
                } else {
                    Toast.makeText(this,
                            R.string.permissao_negada,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

   private LocationRequest getLocationRequest() {
       LocationRequest locationRequest = new LocationRequest();
       locationRequest.setInterval(10000);
      locationRequest.setFastestInterval(5000);
      locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        return locationRequest;
   }

    private void iniciarLocal(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }else{
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(),
                            mLocationCallback,
                            null );

            mFusedLocationClient.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location= task.getResult();
                            if(location != null) {
                                Geocoder geocoder = new Geocoder(User.this,
                                        Locale.getDefault());
                                try {
                                    List<Address> adresses = geocoder.getFromLocation(
                                            location.getLatitude(), location.getLongitude(), 1);
                                    adresses.get(0).getCountryName();
                                    SharedPreferences preferences = getSharedPreferences(PREFERENCIAS_NAME, 0);

                                }
                                catch(IOException e){
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
            );

            mLocationTextView.setText(getString(R.string.endereco_text,
                    getString(R.string.loading), null, null));
        }
    }



    public void back(View view){
       Intent voltar = new Intent(this, Home.class);
      startActivity(voltar);
    }

    private void pararLocal() {
        if (mTrackingLocation) {
            mTrackingLocation = false;
        }
    }
    @Override
    public void onTaskCompleted(String[] result) {
        if (mTrackingLocation) {
            // Update the UI
            lastAdress = result[0];
            lastLatitude = result[1];
            lastLongitude = result[2];
            mLocationTextView.setText(getString(R.string.endereco_text,
                    lastAdress, lastLatitude, lastLongitude));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TRACKING_LOCATION_KEY, mTrackingLocation);
        super.onSaveInstanceState(outState);
    }

    //Armazena as preferencias do usuário
    //na aplicação será armazenada a última localização

    private void armazenar(String latitude, String longitude, String lastAdress) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(LASTADRESS_KEY, lastAdress);
        preferencesEditor.putString(LATITUDE_KEY, latitude);
        preferencesEditor.putString(LONGITUDE_KEY, longitude);
        preferencesEditor.apply();
    }


    @Override
    protected void onPause() {
        if (mTrackingLocation) {
            pararLocal();
            mTrackingLocation = true;
            armazenar(lastLatitude, lastLongitude, lastAdress);
        }
        super.onPause();
    }
    @Override
    protected void onResume() {
        if (mTrackingLocation) {
            iniciarLocal();
        }
        recuperar();
        super.onResume();
    }

    @SuppressLint("StringFormatMatches")
    private void recuperar() {
        SharedPreferences mPreferences = getSharedPreferences(PREFERENCIAS_NAME, 0);
        lastAdress = mPreferences.getString(LASTADRESS_KEY, "");
        lastLatitude = mPreferences.getString(LATITUDE_KEY, "");
        lastLongitude = mPreferences.getString(LONGITUDE_KEY, "");
        Toast.makeText(this, getString(R.string.endereco_text, lastAdress, lastLongitude, lastLatitude), Toast.LENGTH_SHORT).show();
    }
}