package com.example.marvelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class Home extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public EditText medtBusca;
    String name;
    TextView txtErrorMessage;
    View cardCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        txtErrorMessage = findViewById(R.id.txterrorMessage);
        cardCharacter = findViewById(R.id.character);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
        // Acessando a tela
        medtBusca = (EditText) findViewById(R.id.edtBusca);
    }

    public void buscarInvocador(View view) {
        // Verifica o status da conexão de rede
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        name = medtBusca.getText().toString();

        // Se a rede estiver disponivel e o campo de busca não estiver vazio, iniciar o Loader CarregaInvocador
        if (networkInfo != null && networkInfo.isConnected()
                && name.length() != 0) {

            Bundle queryBundle = new Bundle();
            queryBundle.putString("name", name);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            Toast.makeText(getApplicationContext(), "Procurando pelo personagem...", Toast.LENGTH_SHORT).show();
        }
        // atualiza a textview para informar que não há conexão ou termo de busca
        else {
            if (name.length() == 0) {
                txtErrorMessage.setVisibility(View.VISIBLE);
                txtErrorMessage.setText("⚠  Informe um personagem");
                //Toast.makeText(getApplicationContext(), "⚠  Informe um personagem", Toast.LENGTH_SHORT).show();
            } else {
                txtErrorMessage.setVisibility(View.VISIBLE);
                txtErrorMessage.setText("⚠  Verifique sua conexão!");
               // Toast.makeText(getApplicationContext(), "⚠  Verifique sua conexão!", Toast.LENGTH_SHORT).show();
            }
        }

    }
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String name = "";
        if (args != null) {
            name = args.getString("name");
        }
        return new CarregaPersonagem(this, name);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try{
            // Converter a resposta em JSON
            JSONObject jsonObject = new JSONObject(data);

            // Inicializar contador e itens a serem buscados
            String name = null;
            String thumbnail = null;
            Integer series = null;
            Integer comics = null;


            // Procurando pelos itens
            try {
                name = jsonObject.getString("name");
                thumbnail = jsonObject.getString("thumbnail");
                series = jsonObject.getInt("series");
                comics = jsonObject.getInt("comics");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Verificando dados retornados
            if (name != null &&
                    thumbnail != null &&
                    series != null &&
                    comics != null){
                // Abrir próxima activity
                abrirPerfilActivity(summonerNickname,
                        encryptedSummonerId,
                        profileIconId,
                        summonerLevel,
                        tier,
                        rank,
                        leaguePoints,
                        wins,
                        losses,
                        hotStreak,
                        inactive,
                        topChampionsMastery);

                // Destruir loader para fazer próxima busca
                getSupportLoaderManager().destroyLoader(0);
            }

        } catch (Exception e) {
            // Se não receber um JSON válido, informa ao usuário
            Toast.makeText(getApplicationContext(), "Não encontramos nada sobre esse invocador :/", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}