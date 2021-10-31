package com.example.homework7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText etA,etB,etResult;
    private CompareService.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (CompareService.MyBinder)service;
            System.out.println("---> Service Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("---> Service Disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBeforeCreate();
    }

    private void initBeforeCreate() {
        initView();
        initService();
    }

    private void initView() {
        etA = findViewById(R.id.et_a);
        etB = findViewById(R.id.et_b);
        etResult = findViewById(R.id.et_result);
    }

    private void initService() {
        Intent intent = new Intent(this, CompareService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        int a = Integer.parseInt(etA.getText().toString());
                        int b = Integer.parseInt(etB.getText().toString());
                        int result = binder.getResult(a, b);
                        etResult.setText(""+result);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}