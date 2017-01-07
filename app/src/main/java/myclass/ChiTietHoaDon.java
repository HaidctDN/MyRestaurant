package myclass;

/**
 * Created by PhucHai on 12/28/2016.
 */

public class ChiTietHoaDon {
    private String mahd;
    private String tenma;
    private int soluong;
    private int thanhtien;
    private String thoigian;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String mahd, String tenma, int soluong, int thanhtien, String thoigian) {
        this.mahd = mahd;
        this.tenma = tenma;
        this.soluong = soluong;
        this.thanhtien = thanhtien;
        this.thoigian = thoigian;
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getTenma() {
        return tenma;
    }

    public void setTenma(String tenma) {
        this.tenma = tenma;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(int thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "mahd='" + mahd + '\'' +
                ", tenma='" + tenma + '\'' +
                ", soluong=" + soluong +
                ", thanhtien=" + thanhtien +
                ", thoigian='" + thoigian + '\'' +
                '}';
    }
}
