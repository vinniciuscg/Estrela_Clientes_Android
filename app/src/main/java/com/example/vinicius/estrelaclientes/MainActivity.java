package com.example.vinicius.estrelaclientes;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends Activity {

    public static final int REQUEST_PERMISSIONS_CODE = 128;
    private MaterialDialog mMaterialDialog;

    private EditText caixaBusca = null;
    private ArrayList<Cliente> clientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caixaBusca = (EditText) findViewById(R.id.caixaBusca1);

        iniciarArquivo();

    }

    private void iniciarArquivo(){
        if( ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){

            if( ActivityCompat.shouldShowRequestPermissionRationale( this, Manifest.permission.READ_EXTERNAL_STORAGE ) ){
                callDialog( "É preciso permitir que aplicativo acesse o arquivo de clientes na memória", new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} );
            }
            else{
                ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_CODE );
            }
        }
        else{
            carregarArquivo();
        }
    }

    private void callDialog( String message, final String[] permissions ){
        mMaterialDialog = new MaterialDialog(this)
                .setTitle("Permissão")
                .setMessage( message )
                .setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_PERMISSIONS_CODE);
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch( requestCode ){
            case REQUEST_PERMISSIONS_CODE:
                for( int i = 0; i < permissions.length; i++ ){

                    if( permissions[i].equalsIgnoreCase( Manifest.permission.READ_EXTERNAL_STORAGE )
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED ){

                        carregarArquivo();
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void carregarArquivo() {

        File arq;
        String str;

        try {
            arq = new File(Environment.getExternalStorageDirectory().getPath()+"//Download//arq.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arq.getAbsolutePath()), "ISO-8859-1"));

            try {
                while((str = br.readLine()) != null){
                    Cliente temp = new Cliente();
                    temp.setChave(Integer.parseInt(str));
                    temp.setNome(br.readLine());
                    temp.setTelefone(br.readLine());
                    temp.setEndereco(br.readLine());
                    temp.setReferencia(br.readLine());
                    clientes.add(temp);
                }
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Arquivo não encontrado na pasta Download", Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Codificação não suportada", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscar(View v){

        String strBusca = caixaBusca.getText().toString();
        Intent it = new Intent(MainActivity.this, ResultBusca.class);
        it.putExtra("clientes", clientes);
        it.putExtra("busca", strBusca);
        startActivity(it);
    }

    public void cadastrarCliente(View v){

        Intent it = new Intent(MainActivity.this, TelaCadastro.class);
        it.putExtra("clientes", clientes);
        startActivity(it);
    }

    public void mostraCardapio(View v){

        Intent it = new Intent(MainActivity.this, Cardapio.class);
        startActivity(it);
    }
}
