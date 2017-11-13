package com.example.vinicius.estrelaclientes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Cardapio extends AppCompatActivity {

    private ListView listaCardapio = null;
    private ArrayList<Pizza> pizzas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        String[][] linhas = {
                {"1 - À Moda"},
                {"2 - Moda da Casa"},
                {"3 - Atum"},
                {"4 - Mussarela"},
                {"5 - Calabresa"},
                {"6 - Frango"},
                {"7 - Frango Cat."},
                {"8 - Marguerita"},
                {"9 - Romana"},
                {"10 - Italiana"},
                {"11 - Mexicana"},
                {"12 - Siciliana"},
                {"13 - Napolitana"},
                {"14 - Portuguesa"},
                {"15 - Toscana"},
                {"16 - 4 Estações"},
                {"17 - 4 Queijos"},
                {"18 - Vegetariana"},
                {"19 - Carne Seca"},
                {"20 - Camarão"},
                {"21 - Banana"},
                {"22 - Chocolate"},
                {"23 - Prestígio"},
                {"24 - Romeu e Julieta"},
                {"25 - Refrigerantes"}
        };

        String saborNovo;
        for(String[] line: linhas){
            saborNovo = line[0].substring(4);
            if(saborNovo.startsWith(" ")){
                saborNovo = saborNovo.substring(1);
            }
            pizzas.add(new Pizza(saborNovo));
        }

        pizzas.get(0).setIngredientes("Molho Especial\nMussarela\nPresunto\nCalabresa\nBacon\nCebola\nTomate\nCatupiry");
        pizzas.get(1).setIngredientes("Molho Especial\nMussarela\nPresunto\nBacon\nOvo Cozido\nPimentão\nCebola\nTomate\nCheddar");
        pizzas.get(2).setIngredientes("Molho Especial\nMussarela\nAtum\nCebola\nOrégano");
        pizzas.get(3).setIngredientes("Molho Especial\nMussarela\nTomate\nAzeitona");
        pizzas.get(4).setIngredientes("Molho Especial\nMussarela\nCalabresa\nCebola\nAzeitona\nOrégano");
        pizzas.get(5).setIngredientes("Molho Especial\nMussarela\nFrango Desfiado\nMilho Verde\nOrégano");
        pizzas.get(6).setIngredientes("Molho Especial\nMussarela\nFrango Desfiado\nCatupiry\nMilho Verde\nOrégano");
        pizzas.get(7).setIngredientes("Molho Especial\nMussarela\nParmesão\nTomate\nManjericão");
        pizzas.get(8).setIngredientes("Molho Especial\nMussarela\nCalabresa\nBacon\nCebola\nOrégano");
        pizzas.get(9).setIngredientes("Molho Especial\nMussarela\nLombo\nCalabresa\nomate\nAzeitona\nOvo Cozido");
        pizzas.get(10).setIngredientes("Molho Especial\nMussarela\nCalabresa Moída\nBacon\nAzeitona\nCebola\nOrégano");
        pizzas.get(11).setIngredientes("Molho Especial\nMussarela\nBacon\nChampignon\nAzeitona");
        pizzas.get(12).setIngredientes("Molho Especial\nMussarela\nTomate\nManjericão\nPresunto\nOrégano");
        pizzas.get(13).setIngredientes("Molho Especial\nMussarela\nPresunto\nPimentão\nTomate\nCebola\nOvo cozido\nAzeitona\nOrégano");
        pizzas.get(14).setIngredientes("Molho Especial\nMussarela\nToscana Picada\nCebola\nPimentão\nAzeitona\nOrégano");
        pizzas.get(15).setIngredientes("Molho Especial\nMussarela\nCalabresa\nPortuguesa\nFrango\nOrégano");
        pizzas.get(16).setIngredientes("Molho Especial\nMussarela\nParmesão\nCatupiry\nCheddar");
        pizzas.get(17).setIngredientes("Molho Especial\nMussarela\nAzeitona\nPimentão\nTomate\nCebola\nPalmito\nMilho Verde\nOvo Cozido\nOrégano");
        pizzas.get(18).setIngredientes("Molho Especial\nMussarela\nCarne de Sol Desfiada\nCatupiry\nCebola\nOrégano");
        pizzas.get(19).setIngredientes("Molho Especial\nMussarela\nCamarão\nAzeitona\nTomate\nOrégano");
        pizzas.get(20).setIngredientes("Mel\nMussarela\nBanana\nCanela\nAçúcar");
        pizzas.get(21).setIngredientes("Mel\nMussarela\nChocolate\nAçúcar");
        pizzas.get(22).setIngredientes("Mel\nMussarela\nChocolate\nCoco ralado");
        pizzas.get(23).setIngredientes("Mel\nMussarela\nCreme de Leite\nGoiabada");
        pizzas.get(24).setIngredientes("Coca Cola\nJesus\nFanta Laranja\nGuaraná Antártica\nKuat");

        ListAdapterPizza listaAdapterItem = new ListAdapterPizza(this, pizzas);

        listaCardapio = (ListView) findViewById(R.id.listaCardapio);
        listaCardapio.setAdapter(listaAdapterItem);

        listaCardapio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrar(pizzas.get(position));
            }
        });
    }

    public void mostrar(Pizza pizza){
        Intent it = new Intent(Cardapio.this, MostrarItemCardapio.class);
        it.putExtra("pizza", pizza);
        startActivity(it);
    }
}
