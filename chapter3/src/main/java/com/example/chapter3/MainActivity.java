package com.example.chapter3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private EditText et;
    private CalThread calThread;
    private final String UPPER_NUM = "upper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        et = findViewById(R.id.editText);

        calThread = new CalThread();
        calThread.start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = 0x123;
                Bundle bundle = new Bundle();
                bundle.putInt(UPPER_NUM, Integer.parseInt(et.getText().toString()));
                msg.setData(bundle);
                calThread.handler.sendMessage(msg);
            }
        });
    }

    public class CalThread extends Thread{
        public Handler handler;

        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if(msg.what == 0x123){
                        int upper = msg.getData().getInt(UPPER_NUM);
                        List<Integer> nums = new ArrayList<Integer>();
                        outer:
                        for(int i=2;i<=upper;i++){
                            for(int j=2;j<=Math.sqrt(i);j++){
                                if(i!=2&&i%j==0){
                                    continue outer;
                                }
                            }
                            nums.add(i);
                        }
                        Toast.makeText(MainActivity.this,nums.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            };
            Looper.loop();
        }
    }
}