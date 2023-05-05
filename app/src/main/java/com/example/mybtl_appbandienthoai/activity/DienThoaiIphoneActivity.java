package com.example.mybtl_appbandienthoai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.adapter.DtIphoneAdapter;
import com.example.mybtl_appbandienthoai.model.SanPhamMoi;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class DienThoaiIphoneActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvIphone;
    DtIphoneAdapter iphoneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai_iphone);

        Anhxa();
        ActionToolBar();
        getDtIphone();
    }
    private void getDtIphone() {
        FirebaseRecyclerOptions<SanPhamMoi> options =
                new FirebaseRecyclerOptions.Builder<SanPhamMoi>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("sanpham").orderByChild("loaisp").equalTo(1), SanPhamMoi.class)
                        .build();
        iphoneAdapter = new DtIphoneAdapter(options, this);
        rcvIphone.setAdapter(iphoneAdapter);
    }
    //
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent trangchu = new Intent(DienThoaiIphoneActivity.this, MainActivity.class);
                startActivity(trangchu);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toobar);
        rcvIphone = findViewById(R.id.rcvIphone);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcvIphone.setLayoutManager(layoutManager);
        rcvIphone.setHasFixedSize(true);
    }
//

    @Override
    protected void onStart() {
        super.onStart();
        iphoneAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        iphoneAdapter.stopListening();
    }
}