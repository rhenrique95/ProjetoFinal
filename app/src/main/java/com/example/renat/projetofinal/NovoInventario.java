package com.example.renat.projetofinal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NovoInventario extends Activity {

    protected Button inserir;
    protected EditText produto;
    protected EditText quantidade;
    protected AdaptadorBaseDados adb;

    @Override
    protected void onStart() {
        super.onStart();
        adb = new AdaptadorBaseDados(this).open();
    }
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adb.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_inventario);

        produto = (EditText) findViewById(R.id.produto);
        quantidade = (EditText) findViewById(R.id.quantidade);
        inserir = (Button) findViewById(R.id.inserir);


        inserir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(produto.getText().toString().equals("") || quantidade.getText().toString().equals("") ){
                    Toast toast = Toast.makeText(NovoInventario.this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    Boolean resultado = adb.insereInventario(produto.getText().toString(), quantidade.getText().toString());

                    if (resultado) {
                        Toast toast = Toast.makeText(NovoInventario.this, "Fornecedor inserido com sucesso!", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent=new Intent (NovoInventario.this,Inventario.class);
                        startActivity (intent);


                    } else {
                        Toast toast = Toast.makeText(NovoInventario.this, "Ocorreu um erro. Tente novamente!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });
    }



    @Override
    public void onBackPressed() {
        Intent intent=new Intent (NovoInventario.this,Inventario.class);
        startActivity (intent);
        finish();
        super.onBackPressed();
    }
}
