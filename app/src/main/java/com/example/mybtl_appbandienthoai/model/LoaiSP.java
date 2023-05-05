package com.example.mybtl_appbandienthoai.model;

public class LoaiSP {
    //Khai báo biến
//    int id;
    String tendmsp;
    String hinhanh;

    //Khởi tạo contructor

    public LoaiSP() {
    }

    public LoaiSP(String tendmsp, String hinhanh) {
        this.tendmsp = tendmsp;
        this.hinhanh = hinhanh;
    }

    public String getTendmsp() {
        return tendmsp;
    }

    public void setTendmsp(String tendmsp) {
        this.tendmsp = tendmsp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public class myViewHolder {
    }
}
