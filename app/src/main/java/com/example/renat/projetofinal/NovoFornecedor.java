package com.example.renat.projetofinal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NovoFornecedor extends Activity {

    protected Button inserir;
    protected EditText nome;
    protected EditText morada;
    protected EditText contacto;
    protected EditText email;
    protected EditText empresa;
    protected EditText notas;
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
        setContentView(R.layout.activity_novo_fornecedor);

        nome = (EditText) findViewById(R.id.nome);
        morada = (EditText) findViewById(R.id.morada);

        contacto = (EditText) findViewById(R.id.contacto);
        email = (EditText)findViewById(R.id.email);

        empresa = (EditText) findViewById(R.id.empresa);
        notas = (EditText) findViewById(R.id.notas);
        inserir = (Button) findViewById(R.id.inserir);


        inserir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(nome.getText().toString().equals("") || morada.getText().toString().equals("") || contacto.getText().toString().equals("") || email.getText().toString().equals("") || empresa.getText().toString().equals("")){
                    Toast toast = Toast.makeText(NovoFornecedor.this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    Boolean resultado = adb.insereFornecedor(nome.getText().toString(), morada.getText().toString(), contacto.getText().toString(), email.getText().toString(), empresa.getText().toString(), notas.getText().toString());

                    if (resultado) {
                        Toast toast = Toast.makeText(NovoFornecedor.this, "Fornecedor inserido com sucesso!", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent=new Intent (NovoFornecedor.this,MainActivity.class);
                        startActivity (intent);


                    } else {
                        Toast toast = Toast.makeText(NovoFornecedor.this, "Ocorreu um erro. Tente novamente!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent (NovoFornecedor.this,MainActivity.class);
        startActivity (intent);
        finish();
        super.onBackPressed();
    }
}

