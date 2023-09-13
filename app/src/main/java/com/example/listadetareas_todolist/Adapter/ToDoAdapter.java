package com.example.listadetareas_todolist.Adapter;

 import android.content.Context;
 import android.os.Bundle;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
 import android.widget.CompoundButton;

 import androidx.recyclerview.widget.RecyclerView;

 import com.example.listadetareas_todolist.AgregarNuevaTarea;
 import com.example.listadetareas_todolist.MainActivity;
import com.example.listadetareas_todolist.Modelo.Modelo_ToDo;
import com.example.listadetareas_todolist.R;
 import com.example.listadetareas_todolist.Utils.DatabaseHandler;

 import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<Modelo_ToDo> ToDoList;
    private MainActivity activity;
    private DatabaseHandler db;


    public ToDoAdapter(DatabaseHandler db, MainActivity activity){
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tareas_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {

        db.openDatabase();
        final Modelo_ToDo item = ToDoList.get(position);
        holder.task.setText(item.getTareas());
        holder.task.setChecked(toBoolean(item.getStatus()));
        /*
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });

         */
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.ActualizarStatus(item.getId(),1);
                }else{
                    db.ActualizarStatus(item.getId(),0);
                }
            }
        });
    }

    public int getItemCount(){
        return ToDoList.size();
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    public void setTasks(List<Modelo_ToDo> todoList) {
        this.ToDoList = todoList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        Modelo_ToDo item = ToDoList.get(position);
        db.EliminarTarea(item.getId());
        ToDoList.remove(position);
        notifyItemRemoved(position);

    }

    public void editItem(int position){
        Modelo_ToDo item = ToDoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("tareas", item.getTareas());
        AgregarNuevaTarea fragment = new AgregarNuevaTarea();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AgregarNuevaTarea.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }
}
