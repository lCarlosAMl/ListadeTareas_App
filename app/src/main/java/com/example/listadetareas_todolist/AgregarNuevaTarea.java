package com.example.listadetareas_todolist;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.listadetareas_todolist.Modelo.Modelo_ToDo;
import com.example.listadetareas_todolist.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class AgregarNuevaTarea extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText NuevaTarea;
    private Button BotonNuevaTarea;

    private DatabaseHandler db;

    public static AgregarNuevaTarea newInstance(){
        return new AgregarNuevaTarea();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);




    }

     @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.nueva_tarea, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        NuevaTarea = getView().findViewById(R.id.NuevaTarea);
        BotonNuevaTarea = getView().findViewById(R.id.BotonNuevaTarea);

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("tareas");
            NuevaTarea.setText(task);
            assert task != null;
            if(task.length()>0)
                BotonNuevaTarea.setTextColor(ContextCompat.getColor(getContext(), R.color.design_default_color_background));
        }

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        NuevaTarea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    BotonNuevaTarea.setEnabled(false);
                    BotonNuevaTarea.setTextColor(Color.GRAY);
                }
                else{
                    BotonNuevaTarea.setEnabled(true);
                    BotonNuevaTarea.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.holo_green_light));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        BotonNuevaTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = NuevaTarea.getText().toString();
                if(finalIsUpdate){
                    db.ActualizarTarea(bundle.getInt("id"), text);
                }
                else {
                    Modelo_ToDo task = new Modelo_ToDo();
                    task.setTareas(text);
                    task.setStatus(0);
                    db.InsertarTarea(task);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
    }


}
