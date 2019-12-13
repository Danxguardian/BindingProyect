package com.example.bindingtest;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.bindingtest.database.DBHelper;
import com.example.bindingtest.databinding.ActivityMainBinding;
import com.facebook.stetho.Stetho;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Task> list;
    DBHelper dbHelper;
    SQLiteDatabase db;

    ActivityMainBinding binding;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main); //Con binding podemos acceder a todos los elementos
        Stetho.initializeWithDefaults(this);//Libreria para ver BD sqlite en chrome
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        listView = binding.lvTask;
        list = new ArrayList<>();
        inflateTaskList();

        //Boton flotante
        binding.fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTask.class);
                intent.putExtra("taskObject", "");
                startActivityForResult(intent,100);

            }
        });

        //Acciones de la listview
        binding.lvTask.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.dialog_message_delete)
                        .setTitle(R.string.dialog_title);

                builder.setPositiveButton(R.string.dialog_btn_borrar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper.deleteRowById(db, list.get(pos).get_id());
                        inflateTaskList();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


                return true;
            }
        });

        binding.lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gson gson = new Gson();
                Intent intent = new Intent(getApplicationContext(), AddTask.class);
                intent.putExtra("taskObject", gson.toJson(list.get(position)));
                startActivityForResult(intent,100);
            }
        });


    }

    //Funcion para llenar y/o actualizar la lista
    private void inflateTaskList() {
        list = dbHelper.getAllRows(db);
        if(list.size() > 0) {
            adapter = new CustomAdapter(this, list);
            adapter.notifyDataSetChanged();
            binding.lvTask.setAdapter(adapter);
        }
    }



    //Funcion callback de las actividades con intents
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode) {
            case (Activity.RESULT_OK) : {
                inflateTaskList();
                break;
            }

        }
    }

}
