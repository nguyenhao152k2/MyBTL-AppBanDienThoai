package com.example.mybtl_appbandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybtl_appbandienthoai.R;

public class ThanhCongActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_cong);
        Anhxa();
    }

    private void Anhxa() {
        imageView = findViewById(R.id.hinhanh);
        textView = findViewById(R.id.text);
        button = findViewById(R.id.ttmh);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}