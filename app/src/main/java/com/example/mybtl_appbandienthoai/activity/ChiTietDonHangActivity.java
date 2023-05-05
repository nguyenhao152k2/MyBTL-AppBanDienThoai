package com.example.mybtl_appbandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.adapter.ChiTietDonHangAdapter;
import com.example.mybtl_appbandienthoai.adapter.GioHangAdapter;
import com.example.mybtl_appbandienthoai.model.DatHang;
import com.example.mybtl_appbandienthoai.model.GioHang;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ChiTietDonHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvCTDH;
    ChiTietDonHangAdapter chiTietDonHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        Anhxa();
        ActionToolBar();
        getCTDH();
    }
    //
    private void getCTDH() {
        FirebaseRecyclerOptions<DatHang> options =
                new FirebaseRecyclerOptions.Builder<DatHang>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("dondathang"), DatHang.class)
                        .build();
        chiTietDonHangAdapter = new ChiTietDonHangAdapter(options);
        rcvCTDH.setAdapter(chiTietDonHangAdapter);
    }
    //
    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toobar);
        rcvCTDH = (RecyclerView) findViewById(R.id.rcvCTDH);
        rcvCTDH.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvCTDH.setLayoutManager(layoutManager);
    }
    //
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent trangchu = new Intent(ChiTietDonHangActivity.this, MainActivity.class);
                startActivity(trangchu);
            }
        });
    }
    //

    @Override
    protected void onStart() {
        super.onStart();
        chiTietDonHangAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chiTietDonHangAdapter.stopListening();
    }
}