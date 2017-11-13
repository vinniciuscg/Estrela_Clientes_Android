package com.example.vinicius.estrelaclientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vinicius on 21/10/2017.
 */

public class ListAdapterString extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> lista;

    public ListAdapterString(Context context, ArrayList<String> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.view_pizza, null);

        TextView sabor = (TextView) convertView.findViewById(R.id.pizzaSabor);
        sabor.setPadding(0,2,0,2);
        sabor.setTextSize(20F);
        sabor.setText(itemPosicao);

        return convertView;
    }
}
