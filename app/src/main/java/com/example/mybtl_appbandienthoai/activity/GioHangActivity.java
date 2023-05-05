package com.example.mybtl_appbandienthoai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.example.mybtl_appbandienthoai.R;

import com.example.mybtl_appbandienthoai.adapter.GioHangAdapter;
import com.example.mybtl_appbandienthoai.model.GioHang;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    //
    Toolbar toolbar;
    static TextView giohangtrong, tongtien;
    Button btndathang, btnttmuahang;
    GioHangAdapter gioHangAdapter;
    RecyclerView rcvGiohang;
    static long tongtiensp;
    static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("giohang");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        //Gọi các hàm
        Anhxa();
        ActionToolBar();
        getGioHang();
        tinhTongtien();
    }
    // Hàm tính tổng tiền trong giỏ hàng
    public static void tinhTongtien() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tongtiensp = 0;
                for (DataSnapshot item : snapshot.getChildren()){
                    GioHang gioHang = item.getValue(GioHang.class);
                    // Lấy giá tiền của sản phẩm
                    long gia = gioHang.getGiasp();
                    //Lấy số lượng sản phẩm trong giỏ hàng
                    int sl = gioHang.getSoluongsp();
                    // Tính tổng số tiền của sản phẩm và tổng các sản phẩm
                    long tiensp = gia * sl;
                    tongtiensp += tiensp;
                }
                //Hiển thị tổng tiền ra giỏ hàng
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                tongtien.setText(decimalFormat.format(tongtiensp));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getGioHang() {
        FirebaseRecyclerOptions<GioHang> options =
                new FirebaseRecyclerOptions.Builder<GioHang>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("giohang"), GioHang.class)
                        .build();
        gioHangAdapter = new GioHangAdapter(options,this);
        rcvGiohang.setAdapter(gioHangAdapter);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent trangchu = new Intent(GioHangActivity.this, MainActivity.class);
                startActivity(trangchu);
            }
        });
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toobar);
        giohangtrong = (TextView) findViewById(R.id.tvgiohangtrong);
        tongtien = (TextView) findViewById(R.id.tvTongtien);
        btndathang = (Button) findViewById(R.id.btndathang);
        btnttmuahang = (Button) findViewById(R.id.btnttmuahang);
        rcvGiohang = (RecyclerView) findViewById(R.id.rcvgiohang);
        rcvGiohang.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvGiohang.setLayoutManager(layoutManager);
        //
        btnttmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent trangchu = new Intent(GioHangActivity.this, MainActivity.class);
                startActivity(trangchu);
            }
        });
        //
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            //Giỏ hàng không rỗng, cho phép đặt hàng
                            for (DataSnapshot item : snapshot.getChildren()){
                                GioHang gioHang = item.getValue(GioHang.class);
                                String ten = gioHang.getTensp();
                                int sl = gioHang.getSoluongsp();
                                Intent intent = new Intent(GioHangActivity.this, DatHangActivity.class);
                                intent.putExtra("tensp", ten);
                                intent.putExtra("sl", sl);
                                intent.putExtra("tongtien", tongtiensp);
                                startActivity(intent);
                            }

                        } else {
                            //Giỏ hàng rỗng, thông báo cho người dùng
                            Toast.makeText(GioHangActivity.this, "Giỏ hàng của bạn đang trống, vui lòng chọn sản phẩm!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        gioHangAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gioHangAdapter.stopListening();
    }
}