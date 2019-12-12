package com.example.bindingtest;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


import com.example.bindingtest.database.DBHelper;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    //List<Task> list;
    ArrayList<Task> list;
    DBHelper dbHelper;
    SQLiteDatabase db;
    Task task;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        /////DB
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        listView = findViewById(R.id.listView);


        list = new ArrayList<>();
        /*
        for (int i = 0; i < 5; i++) {
            task = new Task("title" + i, "descripcion" + i, 0);
            dbHelper.insertTask(task, db);
        }*/
//            list.add(new Task("Titulo " + i, "Descripcion " + i, i));



        //adapter = new CustomAdapter(this, list);
        //listView.setAdapter(adapter);


        list = dbHelper.getAllRows(db);
        if(list.size() > 0){
            inflateTask(list);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir actividad: Nueva tarea
                Intent intent = new Intent(getApplicationContext(), AddTask.class);
                //startActivity(intent);
                startActivityForResult(intent,100);
            }
        });

    }

    private void inflateTask(ArrayList<Task> task) {
        adapter = new CustomAdapter(this, task);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode) {
            case (Activity.RESULT_OK) : {
                list = dbHelper.getAllRows(db);
                inflateTask(list);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                break;
            }

        }
    }

}
