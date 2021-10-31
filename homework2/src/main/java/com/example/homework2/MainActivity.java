package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView listView;
    private List<String> list;
    private AlertDialog.Builder dialog;
    private Timer timer;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        progressBar = findViewById(R.id.pb);
        listView = findViewById(R.id.lv);
        listView.setVisibility(View.VISIBLE);

//        timer = new Timer(true);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                int i = progressBar.getProgress();
//                i++;
//                progressBar.setProgress(i);
//                if (i == progressBar.getMax()) {
////                    progressBar.setVisibility(View.GONE);
////                    listView.setVisibility(View.VISIBLE);
//                }
//            }
//        }, 500, 500);

        mProgressDialog = new ProgressDialog(this);
        // 设置水平进度条
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setIcon(R.mipmap.ic_launcher);
        mProgressDialog.setTitle("提示");

//        mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // TODO Auto-generated method stub
//                        Toast.makeText(ProgressBarActivity.this, "确定", Toast.LENGTH_SHORT).show();
//                    }
//                });

        mProgressDialog.setMessage("这是一个水平进度条");
        mProgressDialog.show();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                int i = 0;
                while (i < 100 && mProgressDialog.getProgress() != 100) {
                    try {
                        Thread.sleep(15);
                        // 更新进度条的进度,可以在子线程中更新进度条进度
                        mProgressDialog.incrementProgressBy(1);
                        i++;
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                // 在进度条走完时删除Dialog
                mProgressDialog.dismiss();
            }
        }).start();

        list = new ArrayList<String>();
        for (int i = 1; i <= 20; i++) {
            list.add("第" + i + "个item");
        }
        MyAdapter adapter = new MyAdapter(list, this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog = getDialog(i + 1, MainActivity.this);
                dialog.show();
            }
        });


    }

    public AlertDialog.Builder getDialog(int i, Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("这是一个Dialog")
                .setMessage("你点击了第" + i + "个item")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "你点击了确定", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "你点击了取消", Toast.LENGTH_SHORT).show();
                    }
                });
        return dialog;
    }
}