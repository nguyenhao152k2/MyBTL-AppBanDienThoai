package com.example.mybtl_appbandienthoai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.model.GioHang;
import com.example.mybtl_appbandienthoai.model.SanPhamMoi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChiTietSpActivity extends AppCompatActivity {
    //Khai báo các thuộc tính
    Toolbar toolbar;
    TextView tensp, giasp, motasp;
    Button btnThemgh;
    ImageView hinhanhsp;
    Spinner spinner;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("giohang");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);
        //Gọi các hàm
        Anhxa();
        ActionToolBar();
        getSanPhamChiTiet();
        themSPGH();
    }
    // xử lý sự kiện nút thêm sản phảm vào giỏ hàng
    private void themSPGH() {
        btnThemgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { themGioHang(); }
        });
    }
    //
    private void themGioHang(){
        String idsp = sanPhamMoi.getIdsp();
        Query query = reference.orderByChild("idsp").equalTo(idsp);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //Sản phẩm đã có trong giỏ hang, tăng số lượng lên 1
                    for (DataSnapshot item : snapshot.getChildren()){
                        GioHang gioHang = item.getValue(GioHang.class);
                        int slm = gioHang.getSoluongsp() + 1;
                        Toast.makeText(ChiTietSpActivity.this, "Đã thêm sản phẩm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                        item.getRef().child("soluongsp").setValue(slm);
                    }
                } else {
                    //Sản phẩm chưa có trong giỏ hàng, thêm vào giỏ hàng
                    String idgh = reference.push().getKey();
                    String idsp = sanPhamMoi.getIdsp();
                    String ten = tensp.getText().toString();
                    String ha = hinhanhsp.getResources().toString();
                    long gia = Long.parseLong(giasp.getText().toString());
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    GioHang gioHang = new GioHang(idgh, idsp, ha, ten, gia, sl);
                    //Lưu vào database
                    reference.child(idgh).setValue(gioHang)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ChiTietSpActivity.this, "Đã thêm sản phẩm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChiTietSpActivity.this, "Không thể thêm sản phẩm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getSanPhamChiTiet() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTensp());
        motasp.setText(sanPhamMoi.getMotasp());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanhsp()).into(hinhanhsp);
        giasp.setText(String.valueOf(sanPhamMoi.getGiasp()));
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        giasp.setText(decimalFormat.format((sanPhamMoi.getGiasp())));
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(adapter);
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toobar);
        tensp = (TextView) findViewById(R.id.tvtenchitiet);
        giasp = (TextView) findViewById(R.id.tvgiachitiet);
        motasp = (TextView) findViewById(R.id.tvmtct);
        btnThemgh = (Button) findViewById(R.id.btnthemgh);
        hinhanhsp = (ImageView) findViewById(R.id.imgchitiet);
        spinner = (Spinner) findViewById(R.id.spinner);
        badge = (NotificationBadge) findViewById(R.id.menu_sl);
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent trangchu = new Intent(ChiTietSpActivity.this, MainActivity.class);
                startActivity(trangchu);
            }
        });
    }
}