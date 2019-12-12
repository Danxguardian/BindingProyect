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

public class AddTask extends AppCompatActivity {
    Button btnAddTask;
    DBHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_task);
        final ActivityAddTaskBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        binding.btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("binding", binding.etTitle.getText().toString());
                Log.d("binding", binding.etDescription.getText().toString());

                Task task = new Task(binding.etTitle.getText().toString(),binding.etDescription.getText().toString(),0);
                dbHelper.insertTask(task,db);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });

    }


}
