package com.example.vinicius.estrelaclientes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MostrarCliente extends AppCompatActivity {

    TextView nome = null;
    TextView telefone = null;
    EditText endereco = null;
    EditText referencia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cliente);

        setTitle("Estrela Clientes");

        Intent it = getIntent();
        Cliente cliente = (Cliente) it.getExtras().getSerializable("view_cliente");

        nome = (TextView) findViewById(R.id.mostraNome);
        telefone = (TextView) findViewById(R.id.mostraTel);
        endereco = (EditText) findViewById(R.id.mostrarEnd);
        referencia = (EditText) findViewById(R.id.mostrarRef);

        nome.setText(cliente.getNome());
        telefone.setText(cliente.getTelefone());
        endereco.setText(cliente.getEndereco());
        referencia.setText(cliente.getReferencia());

    }
}
