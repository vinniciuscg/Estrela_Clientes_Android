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

public class ListAdapterCliente extends ArrayAdapter<Cliente> {

    private Context context;
    private ArrayList<Cliente> lista;

    public ListAdapterCliente(Context context, ArrayList<Cliente> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cliente itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.view_cliente, null);

        TextView nome = (TextView) convertView.findViewById(R.id.itemNomeTelaPizza);
        nome.setText(itemPosicao.getNome());

        TextView telefone= (TextView) convertView.findViewById(R.id.itemTelefone);
        telefone.setText(itemPosicao.getTelefone());

        TextView id = (TextView) convertView.findViewById(R.id.itemId);
        id.setText("Id: "+String.valueOf(itemPosicao.getChave()));

        return convertView;
    }
}
