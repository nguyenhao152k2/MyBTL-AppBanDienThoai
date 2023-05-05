package com.example.mybtl_appbandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.adapter.DtRealmeAdapter;
import com.example.mybtl_appbandienthoai.model.SanPhamMoi;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DienThoaiRealmeActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvRealme;
    DtRealmeAdapter realmeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai_realme);
        //
        Anhxa();
        ActionToolBar();
        getDtRealme();
    }

    private void getDtRealme() {
        FirebaseRecyclerOptions<SanPhamMoi> options =
                new FirebaseRecyclerOptions.Builder<SanPhamMoi>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("sanpham").orderByChild("loaisp").equalTo(6), SanPhamMoi.class)
                        .build();
        realmeAdapter = new DtRealmeAdapter(options, this);
        rcvRealme.setAdapter(realmeAdapter);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent trangchu = new Intent(DienThoaiRealmeActivity.this, MainActivity.class);
                startActivity(trangchu);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toobar);
        rcvRealme = findViewById(R.id.rcvRealme);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcvRealme.setLayoutManager(layoutManager);
        rcvRealme.setHasFixedSize(true);
    }
    //

    @Override
    protected void onStart() {
        super.onStart();
        realmeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        realmeAdapter.stopListening();
    }
}