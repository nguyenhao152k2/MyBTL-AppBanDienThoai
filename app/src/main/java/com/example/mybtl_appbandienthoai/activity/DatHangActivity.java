package com.example.mybtl_appbandienthoai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.model.DatHang;
import com.example.mybtl_appbandienthoai.model.GioHang;
import com.example.mybtl_appbandienthoai.model.Taikhoan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DatHangActivity extends AppCompatActivity {
    //
    Toolbar toolbar;
    TextView tvTongtien, tvTensp, tvSoluong;
    EditText txtTennguoinhan, txtSodienthoai, txtDiachi;
    Button btnDathang;
    DatabaseReference reference;
    Taikhoan taikhoan = new Taikhoan();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);
        //
        Anhxa();
        ActionToolbar();
        DatHang();
    }

    //
    private void DatHang() {
        //Lấy dữ liệu tổng tiền của đơn hàng
//        gioHang = (GioHang) getIntent().getSerializableExtra("giohang1");
        String tensp = getIntent().getStringExtra("tensp");
        int soluong = getIntent().getIntExtra("sl", 0);
        tvTensp.setText(tensp);
        tvSoluong.setText(String.valueOf(soluong));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long tongtien = getIntent().getLongExtra("tongtien", 0);
        tvTongtien.setText(decimalFormat.format(tongtien));
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("dondathang");
                String idtk = taikhoan.getIdtk(); //lấy id người dùng
                String iddh = reference.push().getKey(); //tạo mã đơn hàng tự động

                String ten = txtTennguoinhan.getText().toString();
                String sdt = txtSodienthoai.getText().toString();
                String dc = txtDiachi.getText().toString().trim();
                if (ten.isEmpty()) {
                    txtTennguoinhan.setError("Bạn chưa nhập tên người nhận!");
                    Toast.makeText(DatHangActivity.this, "Bạn chưa nhập tên người nhận!", Toast.LENGTH_SHORT).show();
                } else if (sdt.isEmpty()) {
                    txtSodienthoai.setError("Bạn chưa nhập số điện thoại!");
                    Toast.makeText(DatHangActivity.this, "Bạn chưa nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                } else if (dc.isEmpty()){
                    txtDiachi.setError("Địa chỉ bạn không được bỏ trống!");
                    Toast.makeText(DatHangActivity.this, "Bạn chưa nhập địa chỉ nhận hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    DatHang datHang = new DatHang(iddh, idtk, tensp, soluong, ten, sdt, dc, tongtien);
                    reference.child(iddh).setValue(datHang);
                    //Đưa ra thông báo nếu bạn đặt hàng thành công
                    Intent intent = new Intent(getApplicationContext(), ThanhCongActivity.class);
                    startActivity(intent);
                    cnhatGH();
                    Toast.makeText(DatHangActivity.this, "Bạn đã đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Xóa sản phẩm khi đặt hàng và cập nhật lại giỏ hàng
    private void cnhatGH(){

        //Tạo tham chiếu đến node giỏ hàng
        reference = FirebaseDatabase.getInstance().getReference().child("giohang");
        //Truy vấn dữ liệu giỏ hàng và lấy ra danh sách sản phẩm trong giỏ hàng
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<GioHang> gioHangs = new ArrayList<>();
                //String idsp1 = gioHang.getIdsp();
                for (DataSnapshot item : snapshot.getChildren()){
                    GioHang gioHang = item.getValue(GioHang.class);
                    gioHangs.add(gioHang);
                }
                //Xóa bỏ sản phẩm đang chọn khỏi danh sách sản phẩm trong giỏ hàng
                for (int i = gioHangs.size() - 1; i>=0; i--){
                    GioHang gioHang = gioHangs.get(i);
                    gioHangs.remove(i);
                }
                reference.setValue(gioHangs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent trangchu = new Intent(DatHangActivity.this, GioHangActivity.class);
                startActivity(trangchu);
            }
        });
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toobar);
        tvTongtien = (TextView) findViewById(R.id.tvTongtien);
        tvTensp = (TextView) findViewById(R.id.tvTensp);
        tvSoluong = (TextView) findViewById(R.id.tvSoluong);
        txtTennguoinhan = (EditText) findViewById(R.id.txtTennguoinhan);
        txtSodienthoai = (EditText) findViewById(R.id.txtSosienthoai);
        txtDiachi = (EditText) findViewById(R.id.txtDiachinhan);
        btnDathang = (Button) findViewById(R.id.btnDathang);
    }
}