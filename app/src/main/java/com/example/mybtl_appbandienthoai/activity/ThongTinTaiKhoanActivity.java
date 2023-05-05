package com.example.mybtl_appbandienthoai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.model.Taikhoan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThongTinTaiKhoanActivity extends AppCompatActivity {
    TextView tttkHo, tttkTen, tttkEmail, tttkTendn, tttk, tv1, tv2, tv3, tv4;
    Button btnDangxuat, btnQuaylai;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tai_khoan);
        Anhxa();
        getThongtin();
    }
    //Hàm check đăng nhập sau đó show thông tin người dùng
    protected boolean checkDN() {
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan1", MODE_PRIVATE);
        String idtk = sharedPreferences.getString("id", "");
        return !idtk.equals("");
    }
    //
    private void getThongtin() {
        //Lấy thông tin tài khoản từ Activity đăng nhập
        if (checkDN()){
            SharedPreferences sharedPreferences = getSharedPreferences("taikhoan1", MODE_PRIVATE);
            String ho = sharedPreferences.getString("ho", "");
            String ten = sharedPreferences.getString("ten", "");
            String email = sharedPreferences.getString("email", "");
            String username = sharedPreferences.getString("tendn", "");
            //
            tttkHo.setText(ho);
            tttkTen.setText(ten);
            tttkEmail.setText(email);
            tttkTendn.setText(username);
        }
//        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan1", MODE_PRIVATE);
//        String ho = sharedPreferences.getString("ho", "");
//        String ten = sharedPreferences.getString("ten", "");
//        String email = sharedPreferences.getString("email", "");
//        String username = sharedPreferences.getString("tendn", "");
//        //
//        tttkHo.setText(ho);
//        tttkTen.setText(ten);
//        tttkEmail.setText(email);
//        tttkTendn.setText(username);
//        //Intent intent = getIntent();
//        String ho = getIntent().getStringExtra("ho");
//        String ten = getIntent().getStringExtra("ten");
//        String tendn = getIntent().getStringExtra("tendn");
//        String email = getIntent().getStringExtra("email");
//        //
//        Taikhoan taikhoan = new Taikhoan(ho, ten, tendn, email);
//        tttkHo.setText(taikhoan.getHo());
//        tttkTen.setText(taikhoan.getTen());
//        tttkEmail.setText(taikhoan.getEmail());
//        tttkTendn.setText(taikhoan.getTendn());
//        //
//        tttkHo.setText(ho);
//        tttkTen.setText(ten);
//        tttkEmail.setText(email);
//        tttkTendn.setText(tendn);
    }

    private void Anhxa() {
        tttkHo = (TextView) findViewById(R.id.tttk_ho);
        tttkTen = (TextView) findViewById(R.id.tttk_ten);
        tttkEmail = (TextView) findViewById(R.id.tttk_email);
        tttkTendn = (TextView) findViewById(R.id.tttk_tendn);
        btnDangxuat = (Button) findViewById(R.id.btnDangxuat);
        btnQuaylai = (Button) findViewById(R.id.btnQuaylai);
        //
        tttk = (TextView) findViewById(R.id.tttk);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        //
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        //
        btnDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Xóa thông tin đăng nhập trong SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("taikhoan1", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("id");
                editor.remove("ho");
                editor.remove("ten");
                editor.remove("email");
                editor.remove("tendn");
                editor.remove("mathkhau");
                //editor.clear();
                editor.apply();
                //mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(intent);
                Toast.makeText(ThongTinTaiKhoanActivity.this, "Bạn đã đăng xuất khỏi tài khoản!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}