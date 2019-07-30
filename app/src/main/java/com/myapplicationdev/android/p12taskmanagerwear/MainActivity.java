package com.myapplicationdev.android.p12taskmanagerwear;

import android.content.Intent;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Button btnAdd;
    ArrayList<Tasks> al;
    ArrayAdapter<Tasks> aa;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lvTask);
        btnAdd = findViewById(R.id.btnAdd);
        db = new DBHelper(MainActivity.this);

        al = new ArrayList<Tasks>();
        al = db.getTasks();

        aa = new ArrayAdapter<Tasks>(getBaseContext(),
                android.R.layout.simple_list_item_1, al);

        lv.setAdapter(aa);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivityForResult(intent, 1);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tasks task = al.get(position);
                Intent intent = new Intent(getBaseContext(), EditTask.class);
                intent.putExtra("tasks", task);
                startActivityForResult(intent, 2);
            }
        });

        CharSequence reply = null;
        Intent intent = getIntent();

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null){
            reply = remoteInput.getCharSequence("status");
        }

        if (reply != null) {
            Intent ir = getIntent();
            int id = ir.getIntExtra("id", 0);
            db.deleteTask(id);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                al.clear();
                al.addAll(db.getTasks());
                aa = new ArrayAdapter<Tasks>(getBaseContext(),
                        android.R.layout.simple_list_item_1, al);
                lv.setAdapter(aa);

            } else if (requestCode == 2){
                al.clear();
                al.addAll(db.getTasks());
                aa = new ArrayAdapter<Tasks>(getBaseContext(),
                        android.R.layout.simple_list_item_1, al);
                lv.setAdapter(aa);

            }

        }
    }

}
