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
import com.example.mybtl_appbandienthoai.model.SanPhamMoi;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DecimalFormat;

public class DtSamsungAdapter extends FirebaseRecyclerAdapter<SanPhamMoi, DtSamsungAdapter.myViewHolder> {
    //
    private Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DtSamsungAdapter(@NonNull FirebaseRecyclerOptions<SanPhamMoi> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull SanPhamMoi model) {
        holder.tensp.setText(model.getTensp());
        holder.motasp.setText(model.getMotasp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giasp.setText("Giá:" + decimalFormat.format(Long.valueOf(model.getGiasp()))+ " VNĐ");
        Glide.with(holder.hinhanhsp).load(model.getHinhanhsp()).placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.hinhanhsp);
        //
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick){
                    Intent intent = new Intent(context, ChiTietSpActivity.class);
                    intent.putExtra("chitiet", model);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_moi, parent, false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView hinhanhsp;
        TextView tensp, giasp, motasp;
        private ItemClickListener itemClickListener;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhanhsp = (ImageView) itemView.findViewById(R.id.itemsp_image);
            tensp = (TextView) itemView.findViewById(R.id.itemsp_ten);
            giasp = (TextView) itemView.findViewById(R.id.itemsp_gia);
            motasp = (TextView) itemView.findViewById(R.id.itemsp_mota);
            motasp.setVisibility(View.GONE);//Ẩn mô tả
            itemView.setOnClickListener(this);
        }
        //

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
