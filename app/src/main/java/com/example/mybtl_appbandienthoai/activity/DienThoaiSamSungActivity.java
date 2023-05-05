package com.example.mybtl_appbandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.adapter.DtSamsungAdapter;
import com.example.mybtl_appbandienthoai.model.SanPhamMoi;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DienThoaiSamSungActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvSamsung;
    DtSamsungAdapter samsungAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai_samsung);
        //
        Anhxa();
        ActionToolBar();
        getDtSamsung();
    }

    private void getDtSamsung() {
        FirebaseRecyclerOptions<SanPhamMoi> options =
                new FirebaseRecyclerOptions.Builder<SanPhamMoi>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("sanpham").orderByChild("loaisp").equalTo(2), SanPhamMoi.class)
                        .build();
        samsungAdapter = new DtSamsungAdapter(options, this);
        rcvSamsung.setAdapter(samsungAdapter);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent trangchu = new Intent(DienThoaiSamSungActivity.this, MainActivity.class);
                startActivity(trangchu);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toobar);
        rcvSamsung = findViewById(R.id.rcvSamsung);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcvSamsung.setLayoutManager(layoutManager);
        rcvSamsung.setHasFixedSize(true);
    }
    //

    @Override
    protected void onStart() {
        super.onStart();
        samsungAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        samsungAdapter.stopListening();
    }
}