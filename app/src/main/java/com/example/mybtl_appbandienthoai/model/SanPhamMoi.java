package com.example.mybtl_appbandienthoai.model;

import java.io.Serializable;

public class SanPhamMoi implements Serializable {
    String idsp, tensp, hinhanhsp, motasp;
    Long giasp;
    int loaisp;

    // Khởi tạo contructor
    public SanPhamMoi() {
    }

    public SanPhamMoi(String idsp, String tensp, String hinhanhsp, Long giasp, String motasp, int loaisp) {//
        this.idsp = idsp;
        this.tensp = tensp;
        this.hinhanhsp = hinhanhsp;
        this.giasp = giasp;
        this.motasp = motasp;
        this.loaisp = loaisp;
    }
    //set - get

    public String getIdsp() {
        return idsp;
    }

    public void setId(String idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public Long getGiasp() {
        return giasp;
    }

    public void setGiasp(Long giasp) {
        this.giasp = giasp;
    }

    public String getMotasp() { return motasp; }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public int getLoaisp() {
        return loaisp;
    }

    public void setLoaisp(int loaisp) {
        this.loaisp = loaisp;
    }
}
