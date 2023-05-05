package com.example.mybtl_appbandienthoai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.example.mybtl_appbandienthoai.R;
import com.example.mybtl_appbandienthoai.model.LoaiSP;
import java.util.ArrayList;
import java.util.List;


public class LoaiSPAdapter extends ArrayAdapter<LoaiSP> {

    public LoaiSPAdapter(@NonNull Context context, @NonNull ArrayList<LoaiSP> loaiSPArrayList) {
        super(context, R.layout.item_sanpham, loaiSPArrayList);
    }

    private static class ViewHolder{
        ImageView hinhanh;
        TextView tendmsp;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LoaiSP loaiSP = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_sanpham, parent, false);
            viewHolder.hinhanh = (ImageView) convertView.findViewById(R.id.item_image);
            viewHolder.tendmsp = (TextView) convertView.findViewById(R.id.item_tendmsp);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(getContext()).load(loaiSP.getHinhanh()).into(viewHolder.hinhanh);
        viewHolder.tendmsp.setText(loaiSP.getTendmsp());

        return convertView;
    }

    //    public LoaiSPAdapter(@NonNull Context context, @NonNull ArrayList<LoaiSP> loaiSPArrayList) {
//        super(context, 0, loaiSPArrayList);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View listItemView = convertView;
//        if (listItemView == null){
//            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_sanpham, parent, false);
//        }
//
//        LoaiSP loaiSP = getItem(position);
//
//        ImageView hinhanh = (ImageView) listItemView.findViewById(R.id.item_image);
//        TextView tendmsp = (TextView) listItemView.findViewById(R.id.item_tendmsp);
//
//        Glide.with(getContext()).load(loaiSP.getHinhanh()).into(hinhanh);
//        tendmsp.setText(loaiSP.getTendmsp());
//
//        return listItemView;
//    }

}