package myclass;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by PhucHai on 12/23/2016.
 */

public class HoaDon {
    private String mahd, tenThuNgan;
    private int idban, tongtien;
    private String ngaylap, trangthai;

    public HoaDon() {
    }

    public HoaDon(String mahd, String tenThuNgan, int idban) {
        this.mahd = mahd;
        this.tenThuNgan = tenThuNgan;
        this.idban = idban;
        //---
        Calendar a = Calendar.getInstance();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
//        Toast.makeText(getApplicationContext(), ft.format(a.getTime()), Toast.LENGTH_SHORT).show();
        this.ngaylap = ft.format(a.getTime());
        this.trangthai = "Chưa thanh toán";
    }

    public HoaDon(String mahd, String tenThuNgan, int idban, int tongtien, String ngaylap, String trangthai) {
        this.mahd = mahd;
        this.tenThuNgan = tenThuNgan;
        this.idban = idban;
        this.tongtien = tongtien;
        this.ngaylap = ngaylap;
        this.trangthai = trangthai;
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getTenThuNgan() {
        return tenThuNgan;
    }

    public void setTenThuNgan(String tenThuNgan) {
        this.tenThuNgan = tenThuNgan;
    }

    public int getIdban() {
        return idban;
    }

    public void setIdban(int idban) {
        this.idban = idban;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public String getNgaylap() {
        return ngaylap;
    }

    public void setNgaylap(String ngaylap) {
        this.ngaylap = ngaylap;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public void setHoaDon(HoaDon h) {
        this.mahd = h.mahd;
        this.tenThuNgan = h.tenThuNgan;
        this.idban = h.idban;
        this.tongtien = h.tongtien;
        this.ngaylap = h.ngaylap;
        this.trangthai = h.trangthai;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "mahd='" + mahd + '\'' +
                ", tenThuNgan='" + tenThuNgan + '\'' +
                ", idban=" + idban +
                ", tongtien=" + tongtien +
                ", ngaylap='" + ngaylap + '\'' +
                ", trangthai='" + trangthai + '\'' +
                '}';
    }
}
