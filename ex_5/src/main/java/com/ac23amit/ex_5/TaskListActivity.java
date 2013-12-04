package com.ac23amit.ex_5;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class TaskListActivity extends Activity

    {

        /**
         * Called when the activity is first created.
         */
        @Override
        public void onCreate (Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);
            final ListView listView = (ListView) findViewById(R.id.listV_main);
            Connect_DB connectorDB = Connect_DB.getInstance(this);
            if (connectorDB.getSize() == 0) connectorDB.populateItemsArr();

            listView.setAdapter(new ItemListBaseAdapter(this));
            Button addBtn = (Button) findViewById(R.id.add_Btn);
            addBtn.setOnClickListener(new View.OnClickListener()
            {
                public void onClick (View v)
                {

                    startActivity(new Intent(TaskListActivity.this, CreateTaskActivity.class));
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
    }
