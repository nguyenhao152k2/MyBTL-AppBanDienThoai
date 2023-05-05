package com.example.mybtl_appbandienthoai.model;

public class Taikhoan {
    //Khai báo thuộc tính
    String ho, ten, tendn, email, idtk;
    String matkhau;
    //Khởi tạo contructor

    public Taikhoan() {
    }

    public Taikhoan(String idtk, String ho, String ten, String tendn, String email, String matkhau) {
        this.idtk = idtk;
        this.ho = ho;
        this.ten = ten;
        this.tendn = tendn;
        this.email = email;
        this.matkhau = matkhau;
    }

    public Taikhoan(String ho, String ten, String tendn, String email){
        this.ho = ho;
        this.ten = ten;
        this.tendn = tendn;
        this.email = email;
    }
    //set-get

    public String getIdtk() { return idtk; }

    public void setIdtk(String idtk) { this.idtk = idtk; }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTendn() {
        return tendn;
    }

    public void setTendn(String tendn) {
        this.tendn = tendn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getMatkhau() {
//        return matkhau;
//    }
//
//    public void setMatkhau(String matkhau) {
//        this.matkhau = matkhau;
//    }
}
