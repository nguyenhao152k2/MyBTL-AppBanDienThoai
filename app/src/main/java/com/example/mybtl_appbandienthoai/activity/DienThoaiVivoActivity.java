package com.example.mybtl_appbandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.adapter.DtVivoAdapter;
import com.example.mybtl_appbandienthoai.model.SanPhamMoi;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DienThoaiVivoActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvVivo;
    DtVivoAdapter vivoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai_vivo);
        //
        Anhxa();
        ActionToolBar();
        getDtVivo();
    }

    private void getDtVivo() {
        FirebaseRecyclerOptions<SanPhamMoi> options =
                new FirebaseRecyclerOptions.Builder<SanPhamMoi>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("sanpham").orderByChild("loaisp").equalTo(4), SanPhamMoi.class)
                        .build();
        vivoAdapter = new DtVivoAdapter(options, this);
        rcvVivo.setAdapter(vivoAdapter);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent trangchu = new Intent(DienThoaiVivoActivity.this, MainActivity.class);
                startActivity(trangchu);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toobar);
        rcvVivo = findViewById(R.id.rcvVivo);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcvVivo.setLayoutManager(layoutManager);
        rcvVivo.setHasFixedSize(true);
    }
    //

    @Override
    protected void onStart() {
        super.onStart();
        vivoAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vivoAdapter.stopListening();
    }
}