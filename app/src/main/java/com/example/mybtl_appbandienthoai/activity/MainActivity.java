package com.example.mybtl_appbandienthoai.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.bumptech.glide.Glide;
import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.adapter.LoaiSPAdapter;
import com.example.mybtl_appbandienthoai.adapter.SanPhamMoiAdapter;
import com.example.mybtl_appbandienthoai.model.GioHang;
import com.example.mybtl_appbandienthoai.model.LoaiSP;
import com.example.mybtl_appbandienthoai.model.SanPhamMoi;
import com.example.mybtl_appbandienthoai.model.Taikhoan;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView rcvsanpham;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ListView lvmenu;
    LoaiSPAdapter loaiSPAdapter;
    ArrayList<LoaiSP> listLoaiSP;
    DatabaseReference databaseReference;
    SanPhamMoiAdapter spmAdapter;
    NotificationBadge badge;
    FrameLayout flGioHang, flTaikhoan;
    ImageView imgTimkiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listLoaiSP = new ArrayList<>();
        Anhxa();
        ActionBar();
        getDanhMucSanPham();
        getSanPhamMoi();
        ActionViewFlipper();
        getEventClickMenu();
        slspGH();
        iconClick();
    }
    //Hàm kiểm tra đăng nhập tài khoản
    private boolean checkDNTK(){
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan1", MODE_PRIVATE);
        String id = sharedPreferences.getString("id","");
        //
        return !id.equals("");
    }
    //
    private void iconClick() {
        //tạo sự kiện cho tài khoản
        flTaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkDNTK() == true){
                    Intent intent = new Intent(getApplicationContext(), ThongTinTaiKhoanActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                    startActivity(intent);
                }
            }
        });
        //Tạo sự kiện cho giỏ hàng
        flGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });

        imgTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimKiemActivity.class);
                startActivity(intent);
            }
        });
    }

    private void slspGH() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("giohang");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int tongsp = 0;
                for (DataSnapshot item : snapshot.getChildren()){
                    GioHang gioHang = item.getValue(GioHang.class);
                    tongsp += gioHang.getSoluongsp();
                }
                //
                badge.setText(String.valueOf(tongsp));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getEventClickMenu() {
        lvmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent iphone = new Intent(getApplicationContext(), DienThoaiIphoneActivity.class);
                        startActivity(iphone);
                        Toast.makeText(getApplicationContext(),"Điện thoại Iphone", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Intent samsung = new Intent(getApplicationContext(), DienThoaiSamSungActivity.class);
                        startActivity(samsung);
                        Toast.makeText(getApplicationContext(),"Điện thoại Samsung", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Intent oppo = new Intent(getApplicationContext(), DienThoaiOppoActivity.class);
                        startActivity(oppo);
                        Toast.makeText(getApplicationContext(),"Điện thoại Oppo", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        Intent vivo = new Intent(getApplicationContext(), DienThoaiVivoActivity.class);
                        startActivity(vivo);
                        Toast.makeText(getApplicationContext(),"Điện thoại Vivo", Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        Intent xiaomi = new Intent(getApplicationContext(), DienThoaiXiaomiActivity.class);
                        startActivity(xiaomi);
                        Toast.makeText(getApplicationContext(),"Điện thoại Xiaomi", Toast.LENGTH_LONG).show();
                        break;
                    case 6:
                        Intent realme = new Intent(getApplicationContext(), DienThoaiRealmeActivity.class);
                        startActivity(realme);
                        Toast.makeText(getApplicationContext(),"Điện thoại Realme", Toast.LENGTH_LONG).show();
                        break;
                    case 10:
                        Intent ctdh = new Intent(getApplicationContext(), ChiTietDonHangActivity.class);
                        startActivity(ctdh);
                        Toast.makeText(getApplicationContext(),"Chi tiết đơn hàng", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void getSanPhamMoi(){
        FirebaseRecyclerOptions<SanPhamMoi> options =
                new FirebaseRecyclerOptions.Builder<SanPhamMoi>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("sanpham"), SanPhamMoi.class)
                        .build();
        spmAdapter = new SanPhamMoiAdapter(options, this);
        rcvsanpham.setAdapter(spmAdapter);
    }

    private void getDanhMucSanPham() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("danhmucsanpham");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                LoaiSP loaiSP = snapshot.getValue(LoaiSP.class);
                listLoaiSP.add(loaiSP);
                loaiSPAdapter = new LoaiSPAdapter(MainActivity.this, listLoaiSP);
                lvmenu.setAdapter(loaiSPAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ActionViewFlipper() {
        //10:20
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        for (int i = 0; i < mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa(){
        toolbar = (Toolbar) findViewById(R.id.tbManHinhChinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.vfManHinhChinh);
        rcvsanpham = (RecyclerView) findViewById(R.id.rcvsanpham);
        //rcvsanpham.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcvsanpham.setLayoutManager(layoutManager);
        rcvsanpham.setHasFixedSize(true);
        lvmenu = (ListView) findViewById(R.id.lvmenu);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        drawerLayout = (DrawerLayout) findViewById(R.id.dlManHinhChinh);
        badge = (NotificationBadge) findViewById(R.id.menu_sl);
        flGioHang = (FrameLayout) findViewById(R.id.flGioHang);
        flTaikhoan = (FrameLayout) findViewById(R.id.flTaikhoan);
        imgTimkiem = (ImageView) findViewById(R.id.imgTimkiem);
    }

    @Override
    protected void onStart() {
        super.onStart();
        spmAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        spmAdapter.stopListening();
    }
}