public class khach_hang {
    private String TenKH;
    private Double So_du;
    private String MaKH;
    private String SDT;

    public khach_hang() {
    }

    public khach_hang(String tenKH, Double so_du, String maKH, String SDT) {
        TenKH = tenKH;
        So_du = so_du;
        MaKH = maKH;
        this.SDT = SDT;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        this.TenKH = TenKH;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public Double getSo_du() {
        return So_du;
    }

    public void setSo_du(Double so_du) {
        So_du = So_du;
    }
    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        SDT = SDT;
    }
}
