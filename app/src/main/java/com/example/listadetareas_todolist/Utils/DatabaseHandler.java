package com.example.listadetareas_todolist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.listadetareas_todolist.Modelo.Modelo_ToDo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int VERSION = 2;

    private static final String NAME = "DB_ListadeTareas";
    private static final String TABLA_TODOTAREAS = "todo";
    private static final String ID = "id";
    private static final String TAREAS = "tareas";
    private static final String STATUS = "status";
    private static final String CREATE_TABLE_TODO = "CREATE TABLE " + TABLA_TODOTAREAS + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TAREAS + " TEXT, "
            + STATUS + " INTEGER)";


    private SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TODO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Elimina las tablas anteriores.
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_TODOTAREAS);
        //Crea una nueva tabla
        onCreate(db);
    }

    public void openDatabase(){
        db=this.getWritableDatabase();
    }

    public void InsertarTarea(Modelo_ToDo Tarea){
        ContentValues cv = new ContentValues();
        cv.put(TAREAS, Tarea.getTareas());
        cv.put(STATUS, 0);
        db.insert(TABLA_TODOTAREAS, null, cv);
    }

    public List<Modelo_ToDo> getTodasLasTareas(){
        List<Modelo_ToDo> ListaTareas = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TABLA_TODOTAREAS, null, null, null, null, null, null, null);
            if(cur !=null){
                if(cur.moveToFirst()){
                    do{
                        Modelo_ToDo Tarea = new Modelo_ToDo();
                        Tarea.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        Tarea.setTareas(cur.getString(cur.getColumnIndexOrThrow(TAREAS)));
                        Tarea.setStatus(cur.getInt(cur.getColumnIndexOrThrow(STATUS)));
                        ListaTareas.add(Tarea);
                    }while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cur.close();
        }
        return ListaTareas;
    }


    public void ActualizarStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TABLA_TODOTAREAS, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void ActualizarTarea(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TAREAS, task);
        db.update(TABLA_TODOTAREAS, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void EliminarTarea(int id){
        db.delete(TABLA_TODOTAREAS, ID + "= ?", new String[] {String.valueOf(id)});
    }

}
