package com.example.vinicius.estrelaclientes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MostrarItemCardapio extends AppCompatActivity {

    TextView itemNome = null;// (TextView) findViewById(R.id.itemNomeTelaPizza);
    TableLayout precosPizza = null;// (TableLayout) findViewById(R.id.precosPizza);
    TableLayout precosRefri = null;// (TableLayout) findViewById(R.id.precosRefri);
    TextView ingredientesLabel = null;// (TextView) findViewById(R.id.ingredientes_);
    ListView ingredientesListView = null;//(ListView) findViewById(R.id.ingredientes);
    TextView mediaTam = null;//(TextView) findViewById(R.id.media);
    TextView grandeTam = null;//(TextView) findViewById(R.id.grande);
    TextView familiaTam = null;//(TextView) findViewById(R.id.familia);

    ArrayList<String> ingredientesArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_item_cardapio);

        itemNome = (TextView) findViewById(R.id.itemNomeTelaPizza);
        precosPizza = (TableLayout) findViewById(R.id.precosPizza);
        precosRefri = (TableLayout) findViewById(R.id.precosRefri);
        ingredientesLabel = (TextView) findViewById(R.id.ingredientes_);
        ingredientesListView = (ListView) findViewById(R.id.ingredientes);

        mediaTam = (TextView) findViewById(R.id.media);
        grandeTam = (TextView) findViewById(R.id.grande);
        familiaTam = (TextView) findViewById(R.id.familia);

        Intent it = getIntent();
        Pizza pizza = (Pizza) it.getExtras().getSerializable("pizza");

        String sabor = pizza.getSabor();
        itemNome.setText(sabor);

        if(sabor.contains("Refri")){
            precosPizza.setVisibility(View.INVISIBLE);
            ingredientesLabel.setText("Sabores:");
        }else {
            precosRefri.setVisibility(View.INVISIBLE);
        }

        if(sabor.contains("Carne Seca") || sabor.contains("Camarão")){
            mediaTam.setText("25");
            grandeTam.setText("30");
            familiaTam.setText("35");
        }

        String [] ingredientesVetor = pizza.getIngredientes().split("\n");

        for(String temp: ingredientesVetor){
            ingredientesArray.add(temp);
        }

        ListAdapterString listaAdapterItem = new ListAdapterString(this, ingredientesArray);
        ingredientesListView.setAdapter(listaAdapterItem);
    }
}
