package com.example.mybtl_appbandienthoai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mybtl_appbandienthoai.Interface.ItemClickListener;
import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.activity.ChiTietSpActivity;
import com.example.mybtl_appbandienthoai.model.DatHang;
import com.example.mybtl_appbandienthoai.model.SanPhamMoi;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DecimalFormat;

public class ChiTietDonHangAdapter extends FirebaseRecyclerAdapter<DatHang, ChiTietDonHangAdapter.myViewHolder> {
    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChiTietDonHangAdapter(@NonNull FirebaseRecyclerOptions<DatHang> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull DatHang model) {
        holder.tennguoinhan.setText(model.getTennguoinhan());
        holder.sodt.setText(model.getSodienthoai());
        holder.diachi.setText(model.getDiachi());
        holder.tensp.setText(model.getTensp());
        holder.soluong.setText(String.valueOf(model.getSoluong()));
        holder.tongtien.setText(String.valueOf(model.getTongtien()));
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhangct, parent, false);
        return new ChiTietDonHangAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView tennguoinhan, sodt, diachi, tensp, soluong, tongtien;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tennguoinhan = (TextView) itemView.findViewById(R.id.itemdh_tennnhan);
            sodt = (TextView) itemView.findViewById(R.id.itemdh_sodt);
            diachi = (TextView) itemView.findViewById(R.id.itemdh_diachi);
            tensp = (TextView) itemView.findViewById(R.id.itemdh_tensp);
            soluong = (TextView) itemView.findViewById(R.id.itemdh_soluong);
            tongtien = (TextView) itemView.findViewById(R.id.itemdh_tongtien);
        }
    }
}
