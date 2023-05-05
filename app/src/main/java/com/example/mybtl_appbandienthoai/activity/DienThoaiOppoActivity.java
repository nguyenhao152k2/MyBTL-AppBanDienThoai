package com.example.mybtl_appbandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.adapter.DtOppoAdapter;
import com.example.mybtl_appbandienthoai.model.SanPhamMoi;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DienThoaiOppoActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvOppo;
    DtOppoAdapter oppoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai_oppo);
        //Gọi các hàm
        Anhxa();
        ActionToolBar();
        getDtOppo();
    }

    private void getDtOppo() {
        FirebaseRecyclerOptions<SanPhamMoi> options =
                new FirebaseRecyclerOptions.Builder<SanPhamMoi>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("sanpham").orderByChild("loaisp").equalTo(3), SanPhamMoi.class)
                        .build();
        oppoAdapter = new DtOppoAdapter(options, this);
        rcvOppo.setAdapter(oppoAdapter);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent trangchu = new Intent(DienThoaiOppoActivity.this, MainActivity.class);
                startActivity(trangchu);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toobar);
        rcvOppo = findViewById(R.id.rcvOppo);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcvOppo.setLayoutManager(layoutManager);
        rcvOppo.setHasFixedSize(true);
    }
    //

    @Override
    protected void onStart() {
        super.onStart();
        oppoAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        oppoAdapter.stopListening();
    }
}