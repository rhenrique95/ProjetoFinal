package com.example.renat.projetofinal;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VerFornecedor extends Activity {

    protected Intent oIntent;
    protected Button editar;
    protected Button apagar;
    protected TextView nome;
    protected TextView morada;
    protected TextView contacto;
    protected TextView email;
    protected TextView empresa;
    protected TextView notas;
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
        setContentView(R.layout.activity_ver_fornecedor);

        nome = (TextView) findViewById(R.id.nome);
        morada = (TextView) findViewById(R.id.morada);
        contacto = (TextView) findViewById(R.id.contacto);
        email = (TextView) findViewById(R.id.email);
        empresa = (TextView) findViewById(R.id.empresa);
        notas = (TextView) findViewById(R.id.notas);

        adb = new AdaptadorBaseDados(this).open();
        oIntent = getIntent();
        _id = oIntent.getExtras().getInt("_id");
        cursor = adb.obterFornecedorId(_id);


        morada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("geo:0,0?q=" + morada.getText().toString() + ""));
                startActivity(i);
            }
        });
        if (cursor.moveToFirst()) {
            nome.setText(cursor.getString(1));
            morada.setText(cursor.getString(2));
            contacto.setText(cursor.getString(3));
            email.setText(cursor.getString(4));
            empresa.setText(cursor.getString(5));
            notas.setText(cursor.getString(6));
        }


        editar = (Button) findViewById(R.id.editar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerFornecedor.this, EditarFornecedor.class);
                intent.putExtra("_id", _id);
                startActivity(intent);
            }
        });

        apagar = (Button) findViewById(R.id.apagar);
        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adb.apagarFornecedor(_id);
                Toast toast = Toast.makeText(VerFornecedor.this, "Fornecedor apagado com sucesso!", Toast.LENGTH_LONG);
                toast.show();

                Intent i = new Intent(VerFornecedor.this, MainActivity.class);
                startActivity(i);
            }
        });


        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(contacto.getText().toString());
            }
        });


    }

    protected void makeCall(String contacto) {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE}, 351);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+contacto)));
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent (VerFornecedor.this,MainActivity.class);
        startActivity (intent);
        finish();
        super.onBackPressed();
    }
}

