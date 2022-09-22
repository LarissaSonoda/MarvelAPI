package com.example.marvelapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public EditText medtBusca;
    String queryname;
    TextView txtErrorMessage,nameCharacter, txtseries, txtcomics;
    View cardCharacter, Intro;
    Button btn;
    ImageView thumbnailPerso;
    ImageButton btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        txtErrorMessage = findViewById(R.id.txterrorMessage);
        medtBusca = (EditText) findViewById(R.id.edtBusca);
        btn = (Button) findViewById(R.id.imgBtnUser);
        btn.setOnClickListener(onClickUser);
        btnSearch = (ImageButton) findViewById(R.id.imgBtnSrch);
        nameCharacter = (TextView) findViewById(R.id.txtNameCharacter);
        //OnClickListener do button de busca
        btnSearch.setOnClickListener(onClickSearch);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

    }
//Access User class
    private View.OnClickListener onClickUser = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            // TODO Auto-generated method stub
            Intent user = new Intent(Home.this, User.class);
            startActivity(user);
        }
    };
    //Busca
    private View.OnClickListener onClickSearch = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // TODO Auto-generated method stub
        // Verifica o status da conexão de rede
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connMgr != null) {
                networkInfo = connMgr.getActiveNetworkInfo();
            }

            // resgata o texto de busca
            queryname = medtBusca.getText().toString();

            // Se a rede estiver disponivel e o campo de busca não estiver vazio, iniciar o Loader CarregaInvocador
            if (networkInfo != null && networkInfo.isConnected()
                    && queryname.length() != 0) {

                Bundle queryBundle = new Bundle();
                queryBundle.putString("queryname", queryname);
                getSupportLoaderManager().restartLoader(0, queryBundle, Home.this);
                Toast.makeText(getApplicationContext(), "Procurando pelo personagem...", Toast.LENGTH_SHORT).show();
            }
            // atualiza a textview para informar que não há conexão ou termo de busca
            else {
                if (queryname.length() == 0) {
                    txtErrorMessage.setVisibility(View.VISIBLE);
                    Intro.setVisibility(View.INVISIBLE);
                    txtErrorMessage.setText("⚠  Informe um personagem");
                    //Toast.makeText(getApplicationContext(), "⚠  Informe um personagem", Toast.LENGTH_SHORT).show();
                } else {
                    txtErrorMessage.setVisibility(View.VISIBLE);
                    Intro.setVisibility(View.GONE);
                    txtErrorMessage.setText("⚠  Verifique sua conexão!");
                    // Toast.makeText(getApplicationContext(), "⚠  Verifique sua conexão!", Toast.LENGTH_SHORT).show();
                }
            }

        }

    };



    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryname = "";
        if (args != null) {
            queryname = args.getString("queryname");
        }
        return new CarregaPersonagem(this, queryname);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try{
            // Converter a resposta em JSON
            JSONObject jsonObject = new JSONObject(data);

            //ENTRA NO ARRAY DATA
            JSONArray dataPerso = jsonObject.getJSONArray("data");

            //Acessa o array de comics
           JSONArray dataComics = jsonObject.getJSONArray("comics");

           //acessa o array de series
           JSONArray dataSeries = jsonObject.getJSONArray("series");
            int i = 0;
            String name = null;
            String comics = null;
            String series = null;


            while(i < dataPerso.length() && (name==null && comics==null & series==null)){
                JSONObject personagem = dataPerso.getJSONObject(i);
                JSONObject results = personagem.getJSONObject("results");
                try {
                    //Busca o nome no array results
                    name = results.getString("name");
                }catch(JSONException e){
                    e.printStackTrace();
                }
                JSONObject comicsJSONObject = dataComics.getJSONObject(i);
                JSONObject itemsArray = comicsJSONObject.getJSONObject("items");
                try {
                    //Busca as comics
                    comics = itemsArray.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject seriesJSONObject = dataSeries.getJSONObject(i);
                JSONObject resultSeries = seriesJSONObject.getJSONObject("series");
                try{
                    series = resultSeries.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }

            if(name != null &&
            comics != null &&
            series!= null){

                // setando a visibilidade do card
               // cardCharacter = (View) findViewById(R.id.character);
                cardCharacter.setVisibility(View.VISIBLE);
                Intro.setVisibility(View.GONE);

                // adicionando nome na TextView
                nameCharacter.setText(name);

                // add thumbnail
               // thumbnailPerso = (ImageView) findViewById(R.id.thumbnail);
                //String Foto =  thumbnail + ".jpg";
                //Picasso.get().load(Foto).into(thumbnailPerso);

                //add texto series
                txtseries.setText(series);

                //add texto comics

                txtcomics.setText(comics);
            }

        } catch (Exception e) {
            txtErrorMessage.setText("Não encontramos seu personagem!");
            txtErrorMessage.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}