package com.example.mybtl_appbandienthoai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.model.Taikhoan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DangNhapActivity extends AppCompatActivity {
    Toolbar toolbar;
    static EditText txtTendn, txtMatkhau;
    TextView tvDangky;
    Button btnDangnhap;
    FirebaseDatabase database;
    DatabaseReference reference;
    //Taikhoan taikhoan;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        Anhxa();
        ActionToolBar();
    }
    //
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent trangchu = new Intent(DangNhapActivity.this, MainActivity.class);
                startActivity(trangchu);
            }
        });
    }
    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toobar);
        txtMatkhau = (EditText) findViewById(R.id.txtTendn);
        txtTendn = (EditText) findViewById(R.id.txtTendn);
        tvDangky = (TextView) findViewById(R.id.tvDangky);
        btnDangnhap = (Button) findViewById(R.id.btnDangnhap);
        //Sử lý sự kiện chuyển sang đăng ký khi chưa có tài khoản
        tvDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
            }
        });
        //
        //Sử lý sự kiện nút đăng nhập
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                if(!checkTendn() || !checkMatkhau()){
                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                }else {
                    checkTaiKhoan();
                }
            }
        });
    }
    //Kiểm tra tên đăng nhập
    public Boolean checkTendn(){
        String check = txtTendn.getText().toString();
        if (check.isEmpty()){
            //txtTendn.setError("Tên đăng nhâp bạn không được để trống!");
            Toast.makeText(this, "Tên đăng nhâp bạn không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            txtTendn.setError(null);
            return true;
        }
    }
    //Kiểm tra mật khẩu
    public Boolean checkMatkhau(){
        String check = txtMatkhau.getText().toString();
//        String mkval = "^" +
//                "(?=.*[0-9])" +
//                "(?=.*[a-zA-Z])" +
//                "(?=.*[@#$%^&+=])";
        if (check.isEmpty()){
            //txtMatkhau.setError("Mật khẩu bạn không được để trống!");
            Toast.makeText(this, "Mật khẩu bạn không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
       // } //else if (!check.matches(mkval)) {
//            txtMatkhau.setError("Mật khẩu phải bao gồm chữ hoa, chữ thường, số, ký tự đặc biệt.");
//            return false;
        } else {
            txtMatkhau.setError(null);
            return true;
        }
    }
    //
    public void checkTaiKhoan(){
        //Lấy thông tin tài khoản người dùng từ editext
        String tendn = txtTendn.getText().toString().trim();
        String matkhau1 = txtMatkhau.getText().toString().trim();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("taikhoan");
        Query checkTK = reference.orderByChild("tendn").equalTo(tendn);

        checkTK.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    txtTendn.setError(null);

                    String matkhauDB = snapshot.child(tendn).child("matkhau").getValue(String.class);
//                    String hoDB = snapshot.child(tendn).child("ho").getValue(String.class);
//                    String tenDB = snapshot.child(tendn).child("ten").getValue(String.class);
//                    String tendnDB = snapshot.child(tendn).child("tendn").getValue(String.class);
//                    String emailDB = snapshot.child(tendn).child("email").getValue(String.class);
                    if (!Objects.equals(matkhauDB, matkhau1)){//matkhauDB.equals(matkhau1) == true
                        txtTendn.setError(null);
                        //Truyền dữ liệu intent
                        for (DataSnapshot item : snapshot.getChildren()){
                            Taikhoan taikhoan = item.getValue(Taikhoan.class);
                            String idtk = taikhoan.getTendn();
                            String hoDB = taikhoan.getHo();
                            String tenDB = taikhoan.getTen();
                            String emailDB = taikhoan.getEmail();
                            SharedPreferences sharedPreferences = getSharedPreferences("taikhoan1", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id", idtk);
                            editor.putString("ho", hoDB);
                            editor.putString("ten", tenDB);
                            editor.putString("email", emailDB);
                            editor.putString("tendn", tendn);
                            editor.apply();
                            Intent intent = new Intent(DangNhapActivity.this, ThongTinTaiKhoanActivity.class);
//                            intent.putExtra("ho", hoDB);
//                            intent.putExtra("ten", tenDB);
//                            intent.putExtra("tendn", tendnDB);
//                            intent.putExtra("email", emailDB);
//                            intent.putExtra("matkhau", matkhauDB);
                            startActivity(intent);
                        }
                        //String mkDB = snapshot.child(Tendn).child("matkhau").getValue(String.class);
                        //

                    }else {
                        //txtMatkhau.setError("Mật khẩu không hợp lệ!");
                        Toast.makeText(DangNhapActivity.this, "Mật khẩu không hợp lệ!", Toast.LENGTH_SHORT).show();
                        //Invalid credentials
                        txtMatkhau.requestFocus();
                    }
                } else {
                    Toast.makeText(DangNhapActivity.this, "Tên đăng nhập không tồn tại", Toast.LENGTH_SHORT).show();
                    txtTendn.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}