package com.example.mybtl_appbandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.adapter.DtXiaomiAdapter;
import com.example.mybtl_appbandienthoai.model.DtXiaomi;
import com.example.mybtl_appbandienthoai.model.SanPhamMoi;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DienThoaiXiaomiActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvXiaomi;
    DtXiaomiAdapter xiaomiAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai_xiaomi);
        //
        Anhxa();
        ActionToolBar();
        getDtXiaomi();
    }

    private void getDtXiaomi() {
        FirebaseRecyclerOptions<SanPhamMoi> options =
                new FirebaseRecyclerOptions.Builder<SanPhamMoi>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("sanpham").orderByChild("loaisp").equalTo(5), SanPhamMoi.class)
                        .build();
        xiaomiAdapter = new DtXiaomiAdapter(options, this);
        rcvXiaomi.setAdapter(xiaomiAdapter);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent trangchu = new Intent(DienThoaiXiaomiActivity.this, MainActivity.class);
                startActivity(trangchu);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toobar);
        rcvXiaomi = findViewById(R.id.rcvXiaomi);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcvXiaomi.setLayoutManager(layoutManager);
        rcvXiaomi.setHasFixedSize(true);
    }
    //

    @Override
    protected void onStart() {
        super.onStart();
        xiaomiAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        xiaomiAdapter.stopListening();
    }
}