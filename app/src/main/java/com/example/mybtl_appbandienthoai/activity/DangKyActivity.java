package com.example.mybtl_appbandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.model.Taikhoan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DangKyActivity extends AppCompatActivity {
    EditText txtHo,txtTen, txtTendn, txtEmail, txtMatkhau, txtXacnhanmk;
    Button btnDangky;
    TextView tvDangnhap;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        Anhxa();
    }

    private void Anhxa() {
        txtHo = (EditText) findViewById(R.id.txtHo);
        txtTen = (EditText) findViewById(R.id.txtTen);
        txtTendn = (EditText) findViewById(R.id.txtTendn) ;
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtMatkhau = (EditText) findViewById(R.id.txtMatkhau);
        txtXacnhanmk = (EditText) findViewById(R.id.txtXacnhanmk);
        btnDangky = (Button) findViewById(R.id.btnDangky);
        tvDangnhap = (TextView) findViewById(R.id.tvDangnhap);
        //Xử lý sự kiện chuyển trang đăng nhaapj nếu như có tài khoản
        tvDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
        //Xử lý nút đăng ký
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tham chiếu đến node "giohang" trong database
                database = FirebaseDatabase.getInstance();
                reference = database.getReference().child("taikhoan");
                //
                String idtk = reference.push().getKey(); //tạo một ID ngẫu nhiên cho tài khoản
                String ho = txtHo.getText().toString();
                String ten = txtTen.getText().toString();
                String tendn = txtTendn.getText().toString();
                String email = txtEmail.getText().toString();
                String matkhau = txtMatkhau.getText().toString();
                String xnmk = txtXacnhanmk.getText().toString();
                //Khởi tạo đối tượng tài khoản idtk,
                Taikhoan taikhoan = new Taikhoan(idtk, ho, ten, tendn, email, matkhau);
                //Xử lý dữ liệu nhập vào
                if (ho.isEmpty()){
                    txtHo.setError("");
                    Toast.makeText(DangKyActivity.this, "Họ của bạn đang bỏ trống!", Toast.LENGTH_SHORT).show();
                } else if (ten.isEmpty()) {
                    txtTen.setError("");
                    Toast.makeText(DangKyActivity.this, "Tên của bạn đang bỏ trống", Toast.LENGTH_SHORT).show();
                } else if (tendn.isEmpty()) {
                    txtTendn.setError("");
                    Toast.makeText(DangKyActivity.this, "Tên đăng nhập bạn đang bỏ trống!", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    txtEmail.setError("");
                    Toast.makeText(DangKyActivity.this, "Email của bạn đang bỏ trống!", Toast.LENGTH_SHORT).show();
                } else if (matkhau.isEmpty()) {
                    txtMatkhau.setError("");
                    Toast.makeText(DangKyActivity.this, "Mật khẩu bạn đang bỏ trống!", Toast.LENGTH_SHORT).show();
                } else if (xnmk.isEmpty()) {
                    txtXacnhanmk.setError("Bạn chưa xác nhận mật khẩu!");
                } else if (matkhau.length() != 8) {
                    Toast.makeText(DangKyActivity.this, "Mật khẩu chưa đủ 8 ký tự!", Toast.LENGTH_SHORT).show();
                } else if (txtMatkhau != txtMatkhau) {
                    txtMatkhau.setError("");
                    txtXacnhanmk.setError("");
                    Toast.makeText(DangKyActivity.this, "Mật khẩu chưa trùng khớp!", Toast.LENGTH_SHORT).show();
                } else {
                    reference.child(idtk).setValue(taikhoan);
                    //
                    Toast.makeText(DangKyActivity.this, "Bạn đã tạo tài khoản mới thành công!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}