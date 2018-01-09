package com.example.renat.projetofinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarFornecedor extends Activity {

    protected Intent oIntent;
    protected Button editar;
    protected EditText nome;
    protected EditText morada;
    protected EditText contacto;
    protected EditText email;
    protected EditText empresa;
    protected EditText notas;
    protected AdaptadorBaseDados adb;
    protected int _id;
    protected Cursor cursor;

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
        setContentView(R.layout.activity_editar_fornecedor);

        nome = (EditText) findViewById(R.id.nome);
        morada = (EditText) findViewById(R.id.morada);
        contacto = (EditText) findViewById(R.id.contacto);
        email = (EditText)findViewById(R.id.email);
        empresa = (EditText) findViewById(R.id.empresa);
        notas = (EditText) findViewById(R.id.notas);
        editar = (Button) findViewById(R.id.editar);

        adb = new AdaptadorBaseDados(this).open();

        oIntent = getIntent();
        _id = oIntent.getExtras().getInt("_id");

        cursor = adb.obterFornecedorId(_id);

        if(cursor.moveToFirst()){

            nome.setText(cursor.getString(1));
            morada.setText(cursor.getString(2));
            contacto.setText(cursor.getString(3));
            email.setText(cursor.getString(4));
            empresa.setText(cursor.getString(5));
            notas.setText(cursor.getString(6));
        }



        editar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(nome.getText().toString().equals("") || morada.getText().toString().equals("") || contacto.getText().toString().equals("") || email.getText().toString().equals("") || empresa.getText().toString().equals("")){
                    Toast toast = Toast.makeText(EditarFornecedor.this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    Boolean res = adb.alteraFornecedor(_id, nome.getText().toString(), morada.getText().toString(), contacto.getText().toString(), email.getText().toString(), empresa.getText().toString(), notas.getText().toString());

                    if (res) {
                        Toast toast = Toast.makeText(EditarFornecedor.this, "Fornecedor guardado com sucesso!", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent=new Intent (EditarFornecedor.this, VerFornecedor.class);
                        intent.putExtra("_id", _id);
                        startActivity (intent);


                    } else {
                        Toast toast = Toast.makeText(EditarFornecedor.this, "Ocorreu um erro. Tente novamente!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent (EditarFornecedor.this,VerFornecedor.class);
        intent.putExtra("_id", _id);
        startActivity (intent);
        finish();
        super.onBackPressed();
    }
}
