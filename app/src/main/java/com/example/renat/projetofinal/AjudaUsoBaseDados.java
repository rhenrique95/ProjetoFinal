package com.example.renat.projetofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by renat on 02/01/2018.
 */

public class AjudaUsoBaseDados extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "base-dados.db";
    private static final int VERSION = 1;

    public AjudaUsoBaseDados(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String fornecedores = "CREATE TABLE fornecedores(_id integer primary key autoincrement, nome varchar(40), morada varchar(40), contacto varchar(40), email varchar(40),  nomeEmpresa varchar(40),  notas text)";
        db.execSQL(fornecedores);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fornecedores");
        onCreate(db);
    }
}
