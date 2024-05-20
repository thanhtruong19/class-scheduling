/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class LopHocPhan81 {
    private int maLop;
    private String maMon;
    private int soSinhVien;
    private String idPhongHoc;
    private String idGioHoc;
    public static int STTLHP = 1;

    // Constructor
    public LopHocPhan81(int maLop, String maMon, int soSinhVien, String idPhongHoc, String idGioHoc) {
        this.maLop = maLop;
        this.maMon = maMon;
        this.soSinhVien = soSinhVien;
        this.idPhongHoc = idPhongHoc;
        this.idGioHoc = idGioHoc;
    }

    // Getters and Setters
    public int getMaLop() {
        return maLop;
    }

    public void setMaLop(int maLop) {
        this.maLop = maLop;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public int getSoSinhVien() {
        return soSinhVien;
    }

    public void setSoSinhVien(int soSinhVien) {
        this.soSinhVien = soSinhVien;
    }

    public String getIdPhongHoc() {
        return idPhongHoc;
    }

    public void setIdPhongHoc(String idPhongHoc) {
        this.idPhongHoc = idPhongHoc;
    }

    public String getIdGioHoc() {
        return idGioHoc;
    }

    public void setIdGioHoc(String idGioHoc) {
        this.idGioHoc = idGioHoc;
    }
}
