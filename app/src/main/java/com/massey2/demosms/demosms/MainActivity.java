package com.massey2.demosms.demosms;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText msgEdit, numEdit;
    Button sendBtn, addMsgButton;

    ListView msgList, numList;

    ArrayList<String> messages = new ArrayList<>(), numbers = new ArrayList<>();

    BaseAdapter numberAdapter;
    BaseAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numEdit = (EditText) findViewById(R.id.numberText);
        msgEdit = (EditText) findViewById(R.id.messageText);
        sendBtn = (Button) findViewById(R.id.sendButton);
        addMsgButton = (Button) findViewById(R.id.addMsgButton);
        msgList = (ListView) findViewById(R.id.messageList);
        numList = (ListView) findViewById(R.id.numberList);
        numberAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, numbers);
        messageAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, messages);

        messages.add("Stuck in traffic, will be late.");
        messages.add("Picking up dinner.");
        messages.add("Can't talk right now.");
        messages.add("Will call back later.");
        messages.add("Massey Hacks II was lit!.");

        numbers.add("6477128595");
        numbers.add("6477700458");
        numbers.add("6476877805");


        msgList.setAdapter(messageAdapter);
        numList.setAdapter(numberAdapter);

        msgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                msgEdit.setText(msgList.getItemAtPosition(position).toString());
            }
        });

        numList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                numEdit.setText(numList.getItemAtPosition(position).toString());
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });

        addMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!msgEdit.getText().toString().trim().equals("")) {
                    messages.add(msgEdit.getText().toString().trim());
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void sendSMS() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numEdit.getText().toString(), null, msgEdit.getText().toString(), null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
