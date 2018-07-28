package com.example.vinicius.estrelaclientes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

import me.drakeet.materialdialog.MaterialDialog;

public class TelaCadastro extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Cliente> clientes = null;

    private EditText cxNome = null;
    private EditText cxTelefone = null;
    private EditText cxEndereco = null;
    private EditText cxReferencia = null;

    private String nome = null;
    private String telefone = null;
    private String endereco = null;
    private String referencia = null;

    private Cliente novo = null;
    private MaterialDialog mMaterialDialog = null;
    private String retorno = "nada";

    private ConstraintLayout constraintLayout = null;
    private ScrollView scrollView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        cxNome = (EditText) findViewById(R.id.caixaNomeCadastrar);
        cxTelefone = (EditText) findViewById(R.id.caixaTelCadastrar);
        cxEndereco = (EditText) findViewById(R.id.caixaEndCadastrar);
        cxReferencia = (EditText) findViewById(R.id.caixaRefCadastrar);

        scrollView = (ScrollView) findViewById(R.id.scrollViewCadastro);

        constraintLayout = (ConstraintLayout) findViewById(R.id.consLayoutCadastro);
        constraintLayout.setVisibility(View.INVISIBLE);

        Intent it = getIntent();
        clientes =it.getExtras().getParcelableArrayList("clientes");
        //clientes.addAll((ArrayList<Cliente>) it.getExtras().getSerializable("clientes2"));
    }

    public void cadastrar(View v) {

        nome = cxNome.getText().toString().toLowerCase();
        telefone = cxTelefone.getText().toString().replace("-", "");
        endereco = cxEndereco.getText().toString();
        referencia = cxReferencia.getText().toString();

        //se nenhum desses campos está vazio entra no if
        if ((!nome.equals("")) && (!telefone.equals("")) && (!endereco.equals(""))) {

            //se o telefone tem pelo menos 8 digitos entra no if
            if(telefone.length() >= 8){

                NovaThread novaThread = new NovaThread(this, clientes);
                novaThread.execute();
            }else{
                Toast.makeText(this, "O telefone deve ter pelo menos 8 dígitos", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Campo obrigatório vazio", Toast.LENGTH_SHORT).show();
        }

    }

    public void baixar (View v){
        clientes.clear();
        db.collection("clientes").orderBy("chave")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            clientes = (ArrayList<Cliente>) task.getResult().toObjects(Cliente.class);
                            salvarTodos();
                        } else {
                            Toast.makeText(TelaCadastro.this, "Erro ao carregar clientes do servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void mostrarCliente(Cliente cliente){
        cxNome.setText("");
        cxTelefone.setText("");
        cxEndereco.setText("");
        cxReferencia.setText("");

        Intent it = new Intent(TelaCadastro.this, MostrarCliente.class);
        it.putExtra("view_cliente", cliente);
        startActivity(it);
    }

    public String removeAcentos(String str){

        str = str.replaceAll("[éêë]","e");
        str = str.replaceAll("[úü]","u");
        str = str.replaceAll("[íïî]","i");
        str = str.replaceAll("[áàâã]","a");
        str = str.replaceAll("[óôöõ]","o");
        str = str.replaceAll("[ç]","c");

        str = str.replaceAll("[ÉÊË]","E");
        str = str.replaceAll("[ÚÜ]","U");
        str = str.replaceAll("[ÍÏÎ]","I");
        str = str.replaceAll("[ÁÀÂÃ]","A");
        str = str.replaceAll("[ÓÔÖÕ]","O");
        str = str.replaceAll("[Ç]","C");

        return str;
    }

    public void salvarTodos(){

        File arq;

        try {

            arq = new File(Environment.getExternalStorageDirectory().getPath()+"//Download//arq.txt");
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arq.getAbsolutePath()), "ISO-8859-1"));

            try {
                for(Cliente temp : clientes) {
                    br.write(String.valueOf(temp.getChave()));
                    br.newLine();
                    br.write(temp.getNome());
                    br.newLine();
                    br.write(temp.getTelefone());
                    br.newLine();
                    br.write(temp.getEndereco());
                    br.newLine();
                    br.write(temp.getReferencia());
                    br.newLine();
                }
                br.close();

            } catch (IOException ex) {
                Toast.makeText(this, "Erro ao escrever no arquivo", Toast.LENGTH_SHORT).show();
            }finally {
                Toast.makeText(this, "Clientes Armazenados", Toast.LENGTH_SHORT).show();
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Arquivo não encontrado na pasta Download", Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Erro ao escrever no arquivo", Toast.LENGTH_SHORT).show();
        }
    }

    public void salvarArquivo(){

        File arq;

        try {
            arq = new File(Environment.getExternalStorageDirectory().getPath()+"//Download//arq.txt");
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arq.getAbsolutePath(), true), "ISO-8859-1"));

            try {
                br.write(String.valueOf(clientes.size()));
                br.newLine();
                br.write(novo.getNome());
                br.newLine();
                br.write(novo.getTelefone());
                br.newLine();
                br.write(novo.getEndereco());
                br.newLine();
                br.write(novo.getReferencia());
                br.newLine();
                br.close();

            } catch (IOException ex) {
                Toast.makeText(this, "Erro ao escrever no arquivo", Toast.LENGTH_SHORT).show();
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Arquivo não encontrado na pasta Download", Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Erro ao escrever no arquivo", Toast.LENGTH_SHORT).show();
        }
    }

    public void callDialog(Cliente temp){

        mMaterialDialog = new MaterialDialog(this)
                .setTitle("Atenção!")
                .setMessage( "Já existe alguém com esse nome e número:" +
                        "\n\nNome: "+temp.getNome()+
                        "\nTelefone: "+temp.getTelefone()+
                        "\nEndereço: "+temp.getEndereco()+
                        "\n\nEndereço novo: " +endereco+
                        "\n\n>> Os endereços são diferentes? <<" )
                .setPositiveButton("Sim", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        novo = new Cliente(clientes.size()+1, nome+" (end 2)", telefone, endereco, referencia);

                        salvarArquivo();
                        Toast.makeText(getApplicationContext(), "Endereço novo cadastrado", Toast.LENGTH_SHORT).show();
                        mostrarCliente(novo);
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Não", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getApplicationContext(), "Exibindo endereço já cadastrado", Toast.LENGTH_SHORT).show();
                        mostrarCliente(novo);
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    private class NovaThread extends AsyncTask<String, Void, String> {

        private Context c;
        private ArrayList<Cliente> clientes;

        public NovaThread(Context c, ArrayList<Cliente> clientes){
            this.c = c;
            this.clientes = clientes;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            constraintLayout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            if(referencia.equals("")){
                //se referencia ta vazia coloca [vazio] no arquivo
                referencia = "[vazio]";
            }

            //Tratamento para o nome
            String[] partes = nome.split(" ");
            StringBuilder sb = new StringBuilder();
            for (String subStr: partes) {
                String word = subStr;
                word = word.substring(0, 1).toUpperCase() + word.substring(1);
                sb.append(" ").append(word);
            }
            nome = sb.toString();
            nome = nome.substring(1);

            //Tratamento para o telefone
            int tam = telefone.length();
            if(tam == 9){
                telefone = telefone.substring(0,5)+"-"+telefone.substring(5);
            }else if(tam == 8){
                if(!telefone.startsWith("3")){
                    telefone = "9"+telefone;
                }else{
                    telefone = " "+telefone;
                }
                telefone = telefone.substring(0,5)+"-"+telefone.substring(5);
            }
            telefone = telefone.replace(" ", "");

            //Tratamento para o endereço
            if(!endereco.toLowerCase().contains("cid. op")) {
                if (endereco.toLowerCase().contains("und")) {
                    endereco = endereco + ", Cid. Op";
                    endereco.replace("und", "Und");
                }
            }

            for(Cliente temp: clientes){
                novo = temp;
                String[] partes2 = temp.getNome().split(" ");
                if((removeAcentos(temp.getNome().toLowerCase()).contains(removeAcentos(partes[0].toLowerCase())) ||
                        removeAcentos(nome.toLowerCase()).contains(removeAcentos(partes2[0].toLowerCase()))) &&
                        (temp.getTelefone().replace("-","").contains(telefone.replace("-","")) ||
                                telefone.replace("-","").contains(temp.getTelefone().replace("-","")))){

                    retorno = "edit";
                    break;
                }
            }

            if(!retorno.equals("edit")){
                novo = new Cliente(clientes.size() + 1, nome, telefone, endereco, referencia);

                salvarArquivo();
                retorno = "cadNovo";
            }

            return retorno;
        }



        @Override
        protected void onPostExecute(String retorno) {
            super.onPostExecute(retorno);

            if (retorno.equals("edit")) {
                callDialog(novo);

            } else {
                mostrarCliente(novo);
                Toast.makeText(c, "Cliente novo cadastrado", Toast.LENGTH_SHORT).show();

            }

            constraintLayout.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }
}
