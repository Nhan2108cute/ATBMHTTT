package entity;

import java.sql.Timestamp;

public class Bill {
    private int id;
    private User nguoiDung;
    private String ten;
    private Timestamp ngayLap_hoaDon;
    private String diachi;
    private String pt_thanhToan;
    private String ghiChu;
    private double tongTien;
    private String signature;
    private String hash;
    private String status;


    public Bill(int id, User nguoiDung, String ten, Timestamp ngayLap_hoaDon, String diachi, String pt_thanhToan, String ghiChu, double tongTien, String hash, String signature,String status) {
        this.id = id;
        this.nguoiDung = nguoiDung;
        this.ten = ten;
        this.ngayLap_hoaDon = ngayLap_hoaDon;
        this.diachi = diachi;
        this.pt_thanhToan = pt_thanhToan;
        this.ghiChu = ghiChu;
        this.tongTien = tongTien;
        this.hash = hash;
        this.signature = signature;
        this.status = status;
    }

    public Bill() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(User nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public Timestamp getNgayLap_hoaDon() {
        return ngayLap_hoaDon;
    }

    public void setNgayLap_hoaDon(Timestamp ngayLap_hoaDon) {
        this.ngayLap_hoaDon = ngayLap_hoaDon;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getPt_thanhToan() {
        return pt_thanhToan;
    }

    public void setPt_thanhToan(String pt_thanhToan) {
        this.pt_thanhToan = pt_thanhToan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", nguoiDung=" + nguoiDung +
                ", ten='" + ten + '\'' +
                ", ngayLap_hoaDon=" + ngayLap_hoaDon +
                ", diachi='" + diachi + '\'' +
                ", pt_thanhToan='" + pt_thanhToan + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                ", tongTien=" + tongTien +
                ", signature='" + signature + '\'' +
                ", hash='" + hash + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

