package com.example.homework7;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class CompareService extends Service {
    private int result;
    private final MyBinder binder = new MyBinder();

    class MyBinder extends Binder {
        public int getResult(int a, int b) {
            CompareService.this.result = Math.max(a, b);
            return CompareService.this.result;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
