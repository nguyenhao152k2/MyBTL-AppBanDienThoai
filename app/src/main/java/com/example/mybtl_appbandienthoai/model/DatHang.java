package com.example.mybtl_appbandienthoai.model;

public class DatHang {
    //Khai báo thuộc tính
    String idtk, iddh, tensp, tennguoinhan, sodienthoai, diachi;
    int soluong;
    Long tongtien;
    //Khởi tạo contructor

    public DatHang() {
    }

    public DatHang( String iddh, String idtk, String tensp, int soluong, String tennguoinhan, String sodienthoai, String diachi, Long tongtien) {
        this.iddh = iddh;
        this.idtk = idtk;
        this.tensp = tensp;
        this.soluong = soluong;
        this.tennguoinhan = tennguoinhan;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
        this.tongtien = tongtien;
    }
    // set - get

    public String getIddh() {
        return iddh;
    }

    public void setIddh(String iddh) {
        this.iddh = iddh;
    }

    public String getIdtk() {
        return idtk;
    }

    public void setIdtk(String idtk) {
        this.idtk = idtk;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getTennguoinhan() {
        return tennguoinhan;
    }

    public void setTennguoinhan(String tennguoinhan) {
        this.tennguoinhan = tennguoinhan;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Long getTongtien() {
        return tongtien;
    }

    public void setTongtien(Long tongtien) {
        this.tongtien = tongtien;
    }
}
