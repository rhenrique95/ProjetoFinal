package com.example.renat.projetofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by renat on 02/01/2018.
 */

public class AdaptadorBaseDados {
    private AjudaUsoBaseDados dbHelper;
    private SQLiteDatabase database;

    public AdaptadorBaseDados(Context context) {
        dbHelper = new AjudaUsoBaseDados(context);
    }

    public AdaptadorBaseDados open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor obterFornecedores(){
        Cursor cursor = database.rawQuery(
                "select _id, nome, morada, contacto, email, nomeEmpresa, notas from fornecedores", null);
        return cursor;
    }

    public Cursor obterFornecedorId(Integer id){
        Cursor cursor = database.rawQuery(
                "select _id, nome, morada, contacto, email, nomeEmpresa, notas from fornecedores where _id=?", new String[] { id.toString() });
        return cursor;
    }

    public boolean insereFornecedor(String nome, String morada, String contacto, String email, String nomeEmpresa, String notas){
        ContentValues valores;
        long resultado;

        database = dbHelper.getWritableDatabase();
        valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("morada", morada);
        valores.put("contacto", contacto);
        valores.put("email", email);
        valores.put("nomeEmpresa", nomeEmpresa);
        valores.put("notas", notas);

        resultado = database.insert("fornecedores", null, valores);
        database.close();

        if (resultado ==-1) {
            return false;
        }
        else {
            return true;
        }

    }

    public boolean alteraFornecedor(Integer id, String nome, String morada, String contacto, String email, String nomeEmpresa, String notas) {
        long resultado;
        String whereClause = "_id = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = new Integer(id).toString();
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("morada", morada);
        values.put("contacto", contacto);
        values.put("email", email);
        values.put("nomeEmpresa", nomeEmpresa);
        values.put("notas", notas);

        resultado =  database.update("fornecedores", values, whereClause, whereArgs);

        if (resultado ==-1) {
            return false;
        }
        else {
            return true;
        }
    }

    public int apagarFornecedor(Integer id) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = ""+id;
        return database.delete("fornecedores", whereClause, whereArgs);
    }
}

