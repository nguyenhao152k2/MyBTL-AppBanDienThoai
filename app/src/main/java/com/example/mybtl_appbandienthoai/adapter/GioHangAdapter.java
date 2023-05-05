package com.example.mybtl_appbandienthoai.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.mybtl_appbandienthoai.Interface.ImageClickListener;
import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.activity.GioHangActivity;
import com.example.mybtl_appbandienthoai.model.GioHang;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.DecimalFormat;

public class GioHangAdapter extends FirebaseRecyclerAdapter<GioHang, GioHangAdapter.myViewHolder> {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("giohang");
    private Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public GioHangAdapter(@NonNull FirebaseRecyclerOptions<GioHang> options, Context context) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull GioHang model) {
        holder.tensp.setText(model.getTensp());
        //holder.motasp.setText(model.getMotasp());
        holder.soluongsp.setText(model.getSoluongsp() + "");
        //holder.giasp.setText(String.valueOf(model.getGiasp()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giasp.setText(decimalFormat.format(model.getGiasp()) + " VNĐ");
        Glide.with(holder.hinhanhsp).load(model.getHinhanhsp()).placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.hinhanhsp);
        String idgh = model.getIdgh();
        // Nút tăng giảm số sản phẩm trong giỏ hàng
        holder.setListener(new ImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1){
                    reference.child(idgh).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Lấy thông tin sản phẩm trong giỏ hàng
                            GioHang gioHang = snapshot.getValue(GioHang.class);
                            //Lấy số lượng sản phẩm cũ
                            int sl = gioHang.getSoluongsp();
                            // Cập nhật số lượng mới
                            if (sl > 1){
                                int slm = sl - 1;
                                reference.child(idgh).child("soluongsp").setValue(slm);
                            }
                            //Cập nhật lại tổng tiền sản phẩm giỏ hàng
                            GioHangActivity.tinhTongtien();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (giatri == 2) {
                    // được sử dụng để lắng nghe dữ liệu một lần duy nhất trong khi `setValue()` được sử dụng để cập nhật giỏ hàng của khách hàng.
                    reference.child(idgh).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Lấy thông tin sản phẩm trong giỏ hàng
                            GioHang gioHang = snapshot.getValue(GioHang.class);
                            //Lấy số lượng sản phẩm cũ
                            int sl = gioHang.getSoluongsp();
                            //Cập nhật số lượng sản phẩm mới
                            if (sl < 10){
                                int slm = sl + 1;
                                reference.child(idgh).child("soluongsp").setValue(slm);
                            }
                            //Cập nhật lại tổng tiền sản phẩm giỏ hàng
                            GioHangActivity.tinhTongtien();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if (giatri == 3) {
                    //Hiển thị ra thông báo người dùng muốn xóa sản phẩm hay không
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.tensp.getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm khỏi giỏ hàng?");
                    //
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Tham chiếu đến key sản phẩm trong CSDL để xóa
                            reference.child(getRef(pos).getKey()).removeValue();
                            //Đưa ra thông báo sản phẩm xóa thành công
                            Toast.makeText(context, "Đã xóa khỏi giỏ hàng!", Toast.LENGTH_SHORT).show();
                            //Cập nhật lại tổng tiền sản phẩm
                            GioHangActivity.tinhTongtien();
                        }
                    });
                    //
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, "Đã hủy", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView hinhanhsp, imgcong, imgtru, imgxoa;
        TextView tensp, giasp, soluongsp, tvTongtien;
        ImageClickListener listener;
        CheckBox checkBox;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhanhsp = (ImageView) itemView.findViewById(R.id.imggh_image);
            imgcong = (ImageView) itemView.findViewById(R.id.imggh_cong);
            imgtru = (ImageView) itemView.findViewById(R.id.imggh_tru);
            imgxoa = (ImageView) itemView.findViewById(R.id.imgXoa);
            tensp = (TextView) itemView.findViewById(R.id.tvgh_tensp);
            giasp = (TextView) itemView.findViewById(R.id.tvgh_giasp);
            tvTongtien = (TextView) itemView.findViewById(R.id.tvTongtien);
            soluongsp = (TextView) itemView.findViewById(R.id.tvgh_soluong);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox1);
            //Xử lý sự kiện cộng trừ số luongj sản phẩm
            imgtru.setOnClickListener(this);
            imgcong.setOnClickListener(this);
            //
            imgxoa.setOnClickListener(this);
        }
        public void setListener(ImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if (view == imgtru) {
                listener.onImageClick(view, getAdapterPosition(), 1);
                // 1 trù
            } else if (view == imgcong) {
                // 2 cộng
                listener.onImageClick(view, getAdapterPosition(), 2);
            } else if (view == imgxoa) {
                // 3 xóa
                listener.onImageClick(view, getAdapterPosition(), 3);
            }
        }
    }
}
