package com.example.renat.projetofinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Inventario extends Activity {

    protected Button novo;
    protected Button menu;
    protected AdaptadorBaseDados adb;
    protected Cursor cursor;
    protected ListView listView;
    protected List<String> inventario;

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
        setContentView(R.layout.activity_inventario);

        cursor = null;
        inventario = new ArrayList<>();
        adb = new AdaptadorBaseDados(this).open();

        cursor = adb.obterInventario();

        if (cursor.moveToFirst()) {
            do {
                inventario.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }



        listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, inventario);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                cursor.moveToPosition(position);
                int _id = cursor.getInt(0);

                Intent intent = new Intent (Inventario.this, VerInventario.class);
                intent.putExtra("_id", _id);
                startActivity (intent);
            }

        });


        novo = (Button) findViewById(R.id.novo);
        novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent (Inventario.this, NovoInventario.class);
                startActivity (i);
            }
        });

        menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent (Inventario.this, MainActivity.class);
                startActivity (i);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                moveTaskToBack(true);
                return true;
        }
        return false;
    }
}
