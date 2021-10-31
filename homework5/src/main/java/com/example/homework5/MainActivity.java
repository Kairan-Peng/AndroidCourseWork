package com.example.homework5;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.ll);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.start();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout,"alpha",0f,1f);
        objectAnimator.setDuration(5000);
        objectAnimator.start();
        imageView = findViewById(R.id.iv);
        Button btnAlpha = findViewById(R.id.btn_alpha);
        btnAlpha.setOnClickListener(this);
        Button btnRotate = findViewById(R.id.btn_rotate);
        btnRotate.setOnClickListener(this);
        Button btnScale = findViewById(R.id.btn_scale);
        btnScale.setOnClickListener(this);
        Button btnTranslate = findViewById(R.id.btn_translate);
        btnTranslate.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_alpha:
                animationStart(R.anim.alpha);
                break;
            case R.id.btn_rotate:
                animationStart(R.anim.rotate);
                break;
            case R.id.btn_scale:
                animationStart(R.anim.scale);
                break;
            case R.id.btn_translate:
                animationStart(R.anim.translate);
                break;
        }
    }



    private void animationStart(int ResAnimId) {
        Animation animation = AnimationUtils.loadAnimation(this, ResAnimId);
        imageView.startAnimation(animation);
    }

}