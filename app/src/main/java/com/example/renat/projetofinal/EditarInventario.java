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

public class EditarInventario extends Activity {

    protected Intent oIntent;
    protected Button editar;
    protected EditText produto;
    protected EditText quantidade;
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
        setContentView(R.layout.activity_editar_inventario);

        produto = (EditText) findViewById(R.id.produto);
        quantidade = (EditText) findViewById(R.id.quantidade);

        editar = (Button) findViewById(R.id.inserir);

        adb = new AdaptadorBaseDados(this).open();

        oIntent = getIntent();
        _id = oIntent.getExtras().getInt("_id");

        cursor = adb.obterInventarioId(_id);

        if(cursor.moveToFirst()){

            produto.setText(cursor.getString(1));
            quantidade.setText(cursor.getString(2));

        }


        editar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(produto.getText().toString().equals("") || quantidade.getText().toString().equals("")){
                    Toast toast = Toast.makeText(EditarInventario.this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    Boolean res = adb.alteraInventario(_id, produto.getText().toString(), quantidade.getText().toString());

                    if (res) {
                        Toast toast = Toast.makeText(EditarInventario.this, "Inventario guardado com sucesso!", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent=new Intent (EditarInventario.this, VerInventario.class);
                        intent.putExtra("_id", _id);
                        startActivity (intent);


                    } else {
                        Toast toast = Toast.makeText(EditarInventario.this, "Ocorreu um erro. Tente novamente!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent (EditarInventario.this,VerInventario.class);
        intent.putExtra("_id", _id);
        startActivity (intent);
        finish();
        super.onBackPressed();
    }
}
