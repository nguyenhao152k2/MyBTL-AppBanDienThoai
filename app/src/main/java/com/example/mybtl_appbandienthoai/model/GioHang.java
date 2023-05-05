package com.example.mybtl_appbandienthoai.model;

import java.io.Serializable;

public class GioHang {
    //Khai báo thuộc tính
    String idgh, idsp, hinhanhsp, tensp;
    Long giasp;
    int soluongsp;

    //Khởi tạo contructor
    public GioHang() {
    }

    public GioHang(String idgh, String idsp, String hinhanhsp, String tensp, Long giasp, int soluongsp) {
        this.idgh = idgh;
        this.idsp = idsp;
        this.hinhanhsp = hinhanhsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.soluongsp = soluongsp;
    }

    //set-get

    public String getIdgh() {
        return idgh;
    }

    public void setIdgh(String idgh) {
        this.idgh = idgh;
    }

    public String getIdsp() {
        return idsp;
    }

    public void setIdsp(String idsp) {
        this.idsp = idsp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Long getGiasp() {
        return giasp;
    }

    public void setGiasp(Long giasp) {
        this.giasp = giasp;
    }

    public int getSoluongsp() { return soluongsp; }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }

}
