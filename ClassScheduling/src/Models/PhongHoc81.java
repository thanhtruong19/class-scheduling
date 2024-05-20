/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class PhongHoc81 {
    private String idPhongHoc;
    private String tenPhongHoc;

    // Constructor
    public PhongHoc81(String idPhongHoc, String tenPhongHoc) {
        this.idPhongHoc = idPhongHoc;
        this.tenPhongHoc = tenPhongHoc;
    }

    // Getters and Setters
    public String getIdPhongHoc() {
        return idPhongHoc;
    }

    public void setIdPhongHoc(String idPhongHoc) {
        this.idPhongHoc = idPhongHoc;
    }

    public String getTenPhongHoc() {
        return tenPhongHoc;
    }

    public void setTenPhongHoc(String tenPhongHoc) {
        this.tenPhongHoc = tenPhongHoc;
    }
}

