/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class GioHoc81 {
    private String idGioHoc;
    private String khungGio;

    // Constructor
    public GioHoc81(String idGioHoc, String khungGio) {
        this.idGioHoc = idGioHoc;
        this.khungGio = khungGio;
    }

    // Getters and Setters
    public String getIdGioHoc() {
        return idGioHoc;
    }

    public void setIdGioHoc(String idGioHoc) {
        this.idGioHoc = idGioHoc;
    }

    public String getKhungGio() {
        return khungGio;
    }

    public void setKhungGio(String khungGio) {
        this.khungGio = khungGio;
    }
}

