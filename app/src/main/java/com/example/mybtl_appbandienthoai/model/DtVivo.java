package com.example.mybtl_appbandienthoai.model;

public class DtVivo {
    //Khai báo thuộc tính
    String hinhanhsp, tensp;
    Long giasp;
    //Khởi tạo contructor

    public DtVivo() {
    }

    public DtVivo(String hinhanhsp, String tensp, Long giasp) {
        this.hinhanhsp = hinhanhsp;
        this.tensp = tensp;
        this.giasp = giasp;
    }

    //set-get

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
}
