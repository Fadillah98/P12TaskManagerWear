package com.myapplicationdev.android.p12taskmanagerwear;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {

    EditText etName, etDescription, etSeconds;
    Button btnAdd, btnCancel;
    DBHelper db;
    long result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etSeconds = findViewById(R.id.etSeconds);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        db = new DBHelper(AddTask.this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String desc = etDescription.getText().toString();
                String seconds = etSeconds.getText().toString();
                if (name.length() == 0 | desc.length() == 0) {
                    Toast.makeText(getBaseContext(), "Please insert the fields!", Toast.LENGTH_SHORT).show();
                } else {
                    result = db.insertTask(name, desc);
                    Toast.makeText(getBaseContext(), "Task Inserted!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK, i);

                    // Notification
                    if (seconds.length() != 0) {

                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.SECOND, Integer.parseInt(seconds));

                        Intent intent = new Intent(AddTask.this, MyReceiver.class);
                        intent.putExtra("id", result);
                        intent.putExtra("name", name);
                        intent.putExtra("desc", desc);
                        int reqCode = 12345;
                        PendingIntent pendingIntent =
                                PendingIntent.getBroadcast(AddTask.this,
                                        reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);

                        am.set(AlarmManager.RTC_WAKEUP,
                                cal.getTimeInMillis(), pendingIntent);
                    }

                    finish();

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
