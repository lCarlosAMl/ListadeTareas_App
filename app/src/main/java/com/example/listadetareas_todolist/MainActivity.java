package com.example.listadetareas_todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.listadetareas_todolist.Adapter.ToDoAdapter;
import com.example.listadetareas_todolist.Modelo.Modelo_ToDo;
import com.example.listadetareas_todolist.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView TareasRecyclerView;
    private ToDoAdapter AdapterTareas;
    private List<Modelo_ToDo> ListaTareas;
    private DatabaseHandler db;
    private FloatingActionButton fab;

    TextView TareasTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        TareasTxt = findViewById(R.id.TareasTxt);

        //Ubicación
        String ubicacion = "font/ConcertOne-Regular.ttf";
        Typeface tf = Typeface.createFromAsset(MainActivity.this.getAssets(), ubicacion);



        TareasTxt.setTypeface(tf);


        db = new DatabaseHandler(this);
        db.openDatabase();

        ListaTareas = new ArrayList<>();

        TareasRecyclerView = findViewById(R.id.TareasRecyclerView);
        TareasRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterTareas = new ToDoAdapter(db, this);
        TareasRecyclerView.setAdapter(AdapterTareas);

        fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(AdapterTareas));
        itemTouchHelper.attachToRecyclerView(TareasRecyclerView);

        /*
        Modelo_ToDo Tarea = new Modelo_ToDo();
        Tarea.setTareas("Test de tarea");
        Tarea.setStatus(0);
        Tarea.setId(1);

        ListaTareas.add(Tarea);
        ListaTareas.add(Tarea);
        ListaTareas.add(Tarea);
        ListaTareas.add(Tarea);
        ListaTareas.add(Tarea);

        AdapterTareas.setTasks(ListaTareas);

         */

        ListaTareas = db.getTodasLasTareas();
        Collections.reverse(ListaTareas);
        AdapterTareas.setTasks(ListaTareas);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarNuevaTarea.newInstance().show(getSupportFragmentManager(),AgregarNuevaTarea.TAG);

            }
        });

    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        ListaTareas = db.getTodasLasTareas();
        Collections.reverse(ListaTareas);
        AdapterTareas.setTasks(ListaTareas);
        AdapterTareas.notifyDataSetChanged();
    }
}