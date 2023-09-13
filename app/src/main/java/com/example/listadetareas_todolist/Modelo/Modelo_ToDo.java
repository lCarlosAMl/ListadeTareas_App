package com.example.listadetareas_todolist.Modelo;

import com.example.listadetareas_todolist.Utils.DatabaseHandler;

public class Modelo_ToDo {
    private int id, status;
    private String tareas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTareas() {
        return tareas;
    }

    public void setTareas(String tareas) {
        this.tareas = tareas;
    }
}

