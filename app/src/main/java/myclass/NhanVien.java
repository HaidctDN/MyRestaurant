package myclass;

public class NhanVien {
    private int idNV;
    private String tenNV, sdt, cmnd, pass;
    private int vitri;

    public NhanVien() {
    }

    public NhanVien(int idNV, String tenNV, String sdt, String cmnd, int vitri,
                    String pass) {
        this.idNV = idNV;
        this.tenNV = tenNV;
        this.sdt = sdt;
        this.cmnd = cmnd;
        this.vitri = vitri;
        this.pass = pass;
    }

    public int getIdNV() {
        return idNV;
    }

    public void setIdNV(int idNV) {
        this.idNV = idNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public int getVitri() {
        return vitri;
    }

    public void setVitri(int vitri) {
        this.vitri = vitri;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "ID:" + idNV + " - TenNV:" + tenNV + " - SDT:" + sdt
                + " - CMND:" + cmnd + " - Vi tri:" + vitri;
    }

}
