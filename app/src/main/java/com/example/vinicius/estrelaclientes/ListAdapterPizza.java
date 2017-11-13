package com.example.vinicius.estrelaclientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vinicius on 13/10/2017.
 */

public class ListAdapterPizza extends ArrayAdapter<Pizza> {

    private Context context;
    private ArrayList<Pizza> lista;

    public ListAdapterPizza(Context context, ArrayList<Pizza> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pizza itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.view_pizza, null);

        TextView sabor = (TextView) convertView.findViewById(R.id.pizzaSabor);
        sabor.setText(itemPosicao.getSabor());

        return convertView;
    }
}
