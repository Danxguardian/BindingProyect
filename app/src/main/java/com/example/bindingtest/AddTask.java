package com.example.bindingtest;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bindingtest.database.DBHelper;
import com.example.bindingtest.databinding.ActivityAddTaskBinding;
import com.google.gson.Gson;

public class AddTask extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    Gson gson;
    Task task;
    Boolean newTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_task);
        final ActivityAddTaskBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        newTask = true;
        String taskObject = getIntent().getStringExtra("taskObject");

        //Para usar la misma vista usamos intent con parametros para validar si es nueva tarea o editar tarea
        if(!taskObject.equals("")){
            newTask = false;
            gson = new Gson();
            task = gson.fromJson(taskObject, Task.class);
            binding.setTask(task);
        }

        //Boton Guardar
        binding.btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newTask) {
                    task = new Task(binding.etTitle.getText().toString(),binding.etDescription.getText().toString(),0);
                    dbHelper.insertTask(task,db);
                } else {
                    dbHelper.updateRowById(task, db, task.get_id());
                }


                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });
    }
}
