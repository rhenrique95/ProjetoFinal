package com.example.renat.projetofinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VerInventario extends Activity {

    protected Intent oIntent;
    protected Button editar;
    protected Button apagar;
    protected TextView produto;
    protected TextView quantidade;
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
        setContentView(R.layout.activity_ver_inventario);

        produto = (TextView) findViewById(R.id.produto);
        quantidade = (TextView) findViewById(R.id.quantidade);


        adb = new AdaptadorBaseDados(this).open();

        oIntent = getIntent();
        _id = oIntent.getExtras().getInt("_id");
        cursor = adb.obterInventarioId(_id);

        if (cursor.moveToFirst()) {
            produto.setText(cursor.getString(1));
            quantidade.setText(cursor.getString(2));
        }



        editar = (Button) findViewById(R.id.editar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (VerInventario.this, EditarInventario.class);
                intent.putExtra("_id", _id);
                startActivity (intent);
            }
        });

        apagar = (Button) findViewById(R.id.apagar);
        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adb.apagarInventario(_id);
                Toast toast = Toast.makeText(VerInventario.this, "Inventario apagado com sucesso!", Toast.LENGTH_LONG);
                toast.show();

                Intent i=new Intent (VerInventario.this,Inventario.class);
                startActivity (i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent (VerInventario.this,Inventario.class);
        startActivity (intent);
        finish();
        super.onBackPressed();
    }
}
