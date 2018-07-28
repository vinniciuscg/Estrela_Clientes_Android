package com.example.vinicius.estrelaclientes;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private AnswerCallBroadcastReceiver serviceReceiver;

    public static final int REQUEST_PERMISSIONS_CODE = 128;
    private MaterialDialog mMaterialDialog;

    public static EditText caixaBuscaPrincipal = null;
    public static Button botaoBuscar = null;

    private ArrayList<Cliente> clientes = new ArrayList<>();
    private Cliente cliente = null;

    private int qntdEnviados = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caixaBuscaPrincipal = (EditText) findViewById(R.id.caixaBusca1);
        botaoBuscar = (Button) findViewById(R.id.buttonBuscaPrincipal);

        serviceReceiver = new AnswerCallBroadcastReceiver();

        iniciarArquivo();

        Intent it = getIntent();
        if(it.getStringExtra("numero") != null){
            caixaBuscaPrincipal.setText(it.getStringExtra("numero"));
            botaoBuscar.callOnClick();
        }
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

        String strBusca = caixaBuscaPrincipal.getText().toString();
        Intent it = new Intent(MainActivity.this, ResultBusca.class);
        it.putParcelableArrayListExtra("clientes", clientes);
        it.putExtra("busca", strBusca);
        startActivity(it);
    }

    public void upar(View v){

        if(clientes.size() > 0) {

            qntdEnviados = 0;

            // Deletar entradas no banco
            /*db.collection("clientes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            db.collection("clientes").document(document.getId()).delete();
                        }
                    }
                }
            });*/

            for (Cliente temp : clientes) {
                db.collection("clientes").document(String.valueOf(temp.getChave()))
                        .set(temp)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                qntdEnviados++;
                            }
                        });

            }

            db.collection("clientes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int count = 0;
                                for (DocumentSnapshot document : task.getResult()) {
                                    count++;
                                }
                                Toast.makeText(MainActivity.this, "Enviados "+String.valueOf(count)+" clientes", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void cadastrarCliente(View v){

        Intent it = new Intent(MainActivity.this, TelaCadastro.class);
        it.putParcelableArrayListExtra("clientes", clientes);
        startActivity(it);
    }

    public void mostraCardapio(View v){

        Intent it = new Intent(MainActivity.this, Cardapio.class);
        startActivity(it);
    }
}
