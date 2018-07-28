package com.example.vinicius.estrelaclientes;

import android.content.Context;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultBusca extends AppCompatActivity {

    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Cliente> lista;
    private EditText caixaBusca = null;
    private ListView listView = null;
    private ConstraintLayout progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_busca);

        setTitle("Estrela Clientes");

        Intent it = getIntent();
        String strBusca = it.getStringExtra("busca");
        clientes = it.getExtras().getParcelableArrayList("clientes");

        caixaBusca = (EditText) findViewById(R.id.caixaBusca2);
        listView = (ListView) findViewById(R.id.listaResul);
        progressBar = (ConstraintLayout) findViewById(R.id.progressBar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrar(lista.get(position));
            }
        });

        buscar(strBusca);
    }

    public void buscar(String strBusca){
        NovaThread novaThread = new NovaThread(this, strBusca, clientes);
        novaThread.execute();
    }

    public void procurar(View v){
        String strBusca = caixaBusca.getText().toString();
        buscar(strBusca);
    }

    public void mostrar(Cliente cliente){
        Intent it = new Intent(ResultBusca.this, MostrarCliente.class);
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

    private class NovaThread extends AsyncTask<String, Void, ArrayList<Cliente>> {

        private ArrayList<Cliente> clientes;
        private Context c;
        private int count;
        private String busca;

        public NovaThread(Context c, String busca, ArrayList<Cliente> clientes){
            this.c = c;
            this.busca = busca;
            this.clientes = clientes;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            caixaBusca.setText(busca);
            progressBar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected ArrayList<Cliente> doInBackground(String... strings) {

            lista = new ArrayList<>();

            count = 0;
            boolean stat = false;

            String strTest;
            busca = removeAcentos(busca.toLowerCase());

            if(busca.length() > 0){
                for(Cliente temp: clientes){
                    strTest = removeAcentos(temp.getNome().toLowerCase());
                    if(strTest.contains(busca) ||
                            temp.getTelefone().replace("-","").contains(busca.replace("-",""))){

                        lista.add(temp);
                        count++;
                        stat = true;
                    }
                }

                if(stat == false){
                    char[] c = busca.toCharArray();
                    if(!Character.isDigit(c[0])){
                        if(busca.length() >= 4){
                            String key1 = busca.substring(1);
                            for(Cliente temp : clientes) {
                                strTest = removeAcentos(temp.getNome().toLowerCase());
                                if(strTest.contains(key1)){
                                    lista.add(temp);
                                    count++;
                                }
                            }
                        }
                    }
                }

            }else{
                for(Cliente temp: clientes){
                    lista.add(temp);
                    count++;
                }
            }

            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<Cliente> lista) {
            super.onPostExecute(lista);

            ListAdapterCliente listaAdapterItem = new ListAdapterCliente(c, lista);

            listView.setAdapter(listaAdapterItem);

            progressBar.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);

            hideKeyboard(c, caixaBusca);

            if(busca.length() > 0){
                Toast.makeText(c, String.valueOf(count)+" ocorrências", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(c, "Exibindo Todos - " + String.valueOf(count), Toast.LENGTH_SHORT).show();
            }

        }
    }

    protected static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
