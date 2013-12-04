package com.ac23amit.ex_5;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
/**
 * Created by win7 on 05/12/13.
 */
public class CreateTaskActivity extends Activity
{
    Connect_DB connectorDB = Connect_DB.getInstance(this);
    public static String EXTRA_MESSAGE = "com.ac23amit.ex_5.MESSAGE";
    private String titleTask = "";

    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);
        Button createBtn = (Button) findViewById(R.id.create_Btn);
        createBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v)
            {

                EditText editText = (EditText) findViewById(R.id.edit_message);
                titleTask = editText.getText().toString();
                connectorDB.addItem(new ItemDetails(0, titleTask, "done", "50"));
                createNotification(v);
                startActivity(new Intent(CreateTaskActivity.this, TaskListActivity.class));

            }
        });
        Button close_Button = (Button) this.findViewById(R.id.close_Btn);
        close_Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                finish();
            }
        });

    }

    public void createNotification (View view)
    {
        Context context = getApplicationContext();
        Intent intent2 = new Intent("com.ac23amit.ex_5");
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
        long expireTime = calendar.getTimeInMillis() - System.currentTimeMillis();
        if (expireTime > 5000)
        {//only future notification for more than 1 minute
            intent2.putExtra(EXTRA_MESSAGE, titleTask);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);//
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + expireTime, pendingIntent2);
        }
    }
}
