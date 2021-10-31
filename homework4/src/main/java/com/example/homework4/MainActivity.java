package com.example.homework4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.homework4.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnBlankFragment, btnListFragment, btnLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBlankFragment = findViewById(R.id.btn_blank_fragment);
        btnBlankFragment.setOnClickListener(this);
        btnListFragment = findViewById(R.id.btn_list_fragment);
        btnListFragment.setOnClickListener(this);
        btnLoginActivity = findViewById(R.id.btn_login_activity);
        btnLoginActivity.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_blank_fragment:
                Toast.makeText(this,"blank fragment",Toast.LENGTH_SHORT).show();
                replaceFragment(new BlankFragment());
                break;
            case R.id.btn_list_fragment:
                Toast.makeText(this,"list fragment",Toast.LENGTH_SHORT).show();
                replaceFragment(new ItemFragment());
                break;
            case R.id.btn_login_activity:
                Toast.makeText(this,"login activity",Toast.LENGTH_SHORT).show();
                replaceActivity();
                break;
        }
    }

    private void replaceActivity() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}