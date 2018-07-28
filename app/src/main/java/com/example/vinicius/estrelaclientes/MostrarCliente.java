package com.example.vinicius.estrelaclientes;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import me.drakeet.materialdialog.MaterialDialog;

import static com.example.vinicius.estrelaclientes.MainActivity.REQUEST_PERMISSIONS_CODE;

public class MostrarCliente extends AppCompatActivity {

    TextView nome = null;
    TextView taxa = null;
    TextView telefone = null;
    EditText endereco = null;
    EditText referencia = null;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cliente);

        setTitle("Estrela Clientes");

        Intent it = getIntent();
        Cliente cliente = (Cliente) it.getParcelableExtra("view_cliente");

        nome = (TextView) findViewById(R.id.mostraNome);
        taxa = (TextView) findViewById(R.id.campoTaxa);
        telefone = (TextView) findViewById(R.id.mostraTel);
        endereco = (EditText) findViewById(R.id.mostrarEnd);
        referencia = (EditText) findViewById(R.id.mostrarRef);

        nome.setText(cliente.getNome());
        telefone.setText(cliente.getTelefone());
        endereco.setText(cliente.getEndereco());
        referencia.setText(cliente.getReferencia());


        if (isTaxavel(cliente)) {
            taxa.setVisibility(View.VISIBLE);
        }
    }

    public void ligarCliente(View v){

        Uri uri = Uri.parse("tel:"+telefone.getText());
        Intent intent = new Intent(Intent.ACTION_DIAL,uri);

        startActivity(intent);
    }

    public void abrirNoMapa(View v){
        callDialog("");
    }

    public void irParaMapa(){

        String endMapa = endereco.getText().toString().toLowerCase();

        if(removeAcentos(endMapa).contains("cidade operaria")){
            endMapa = endMapa.replace("cid. op", "");
        }

        endMapa = endMapa.replace("und.","unidade ")
                            .replace("und ", "unidade ")
                            .replace(" c ", " n ")
                            .replace(",c ", ",n ")
                            .replace("c-", "n ")
                            .replace("ap ", "apartamento ")
                            .replace("ap.", "apartamento")
                            .replace("apt", "apartamento")
                            .replace("bl ", "bloco ")
                            .replace("bl.", "bloco")
                            .replace("qd ", "quadra ")
                            .replace("qd.", "quadra")
                            .replace(" q ", " quadra ")
                            .replace(",q ", ",quadra ")
                            .replace(" r ", " rua ")
                            .replace(",r ", ",rua ")
                            .replace("alam.", "alameda")
                            .replace("alam ", "alameda ")
                            .replace("av.", "avenida")
                            .replace("av ", "avenida ")
                            .replace("trav ", "travessa ")
                            .replace("trav.", "travessa")
                            .replace("cond ", "condominio ")
                            .replace("cond.", "condominio")
                            .replace("resid ", "residencial ")
                            .replace("resid.", "residencial")
                            .replace("res.", "residencial")
                            .replace("cid ", "cidade ")
                            .replace("cid.", "cidade")
                            .replace("op ", "operaria ")
                            .replace("op.", "operaria")
                            .replace("s.", "sao ")
                            .replace("jd ","jardim ")
                            .replace("jd.", "jardim")
                            .replace("j. a", "jardim america")
                            .replace(";"," ");

        String url = String.format(Locale.getDefault(),  "geo:?q="+endMapa.replace(" ","+"));

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void callDialog(String message){

        mMaterialDialog = new MaterialDialog(this)
                .setTitle("Abrir no mapa?")
                .setMessage(message)
                .setPositiveButton("Sim", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        irParaMapa();
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Não", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    public boolean isTaxavel(Cliente cliente){
        String endereco = cliente.getEndereco();
        endereco = removeAcentos(endereco.toLowerCase());
        if(endereco.contains("riviera") || endereco.contains("geniparana") || endereco.contains("jeniparana") ||
                endereco.contains("tropical") || endereco.contains("olimpica") ||
                endereco.contains("janaina") || endereco.contains("riod") ||
                endereco.contains("vitoria") || endereco.contains("tiradente") ||
                ((endereco.contains("don") || endereco.contains("dom")) && endereco.contains("ricardo")) ||
                (endereco.contains("campo") && (endereco.contains("belo") || endereco.contains("bello"))) ||
                (endereco.contains("porto") && endereco.contains("duna")) ||
                (endereco.contains("vila") && endereco.contains("zeni")) ||
                (endereco.contains("del") && endereco.contains("este")) ||
                (endereco.contains("maiobinha") && !(endereco.contains("parque") || endereco.contains("do sol"))) ||
                ((endereco.contains("sao") || endereco.contains("s ")) && endereco.contains("jose")) ||
                ((endereco.contains("vilage") || endereco.contains("village")) && endereco.contains("bosque")) ||
                ((endereco.contains("s ") || endereco.contains("sao")) && (endereco.contains("bernardo") || endereco.contains("bernado"))) ||
                ((!(endereco.contains("jd") || endereco.contains("jardim") || endereco.contains("ipem") || endereco.contains("ipen"))) && endereco.contains("cristovao")) ||
                ((endereco.contains("sta") || endereco.contains("santa")) && endereco.contains("clara"))){
            return true;
        }
        return false;
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
}
