package com.example.mybtl_appbandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;

import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.adapter.DtIphoneAdapter;
import com.example.mybtl_appbandienthoai.adapter.SanPhamMoiAdapter;
import com.example.mybtl_appbandienthoai.model.SanPhamMoi;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TimKiemActivity extends AppCompatActivity {
    Toolbar toolbar;
    SearchView sVTimkiem;
    RecyclerView rcvTimkiem;
    SanPhamMoiAdapter sanPhamMoiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        Anhxa();
        ActionToolbar();
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toobar);
        sVTimkiem = (SearchView) findViewById(R.id.svTimkiem);
        sVTimkiem.clearFocus();
        rcvTimkiem = (RecyclerView) findViewById(R.id.rcvTimkiem);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcvTimkiem.setHasFixedSize(true);
        rcvTimkiem.setLayoutManager(layoutManager);
        //sanPhamMoiList = new ArrayList<>();
        //
        sVTimkiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });
        //
    }
    private void txtSearch(String str){
        FirebaseRecyclerOptions<SanPhamMoi> options =
                new FirebaseRecyclerOptions.Builder<SanPhamMoi>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("sanpham").orderByChild("tensp")
                                .startAt(str).endAt(str + "~"), SanPhamMoi.class)
                        .build();
        sanPhamMoiAdapter = new SanPhamMoiAdapter(options, this);
        sanPhamMoiAdapter.startListening();
        rcvTimkiem.setAdapter(sanPhamMoiAdapter);
    }
}