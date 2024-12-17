package dao;

import context.DBConnect;
import entity.Bill;
import entity.BillDetails;
import entity.Product;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // Thêm hóa đơn vào bảng 'hoadon'
    public int addBill(Bill bill) throws SQLException {
        ResultSet resultSet = null;
        int generatedId = -1;

        String query = "INSERT INTO hoadon (ngaylap_hd, id_ngdung, ten, dia_chi_giao_hang, tongtien, pt_thanhtoan, ghichu, hash, signature) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setTimestamp(1, bill.getNgayLap_hoaDon());
            ps.setString(2, bill.getNguoiDung().getId());
            ps.setString(3, bill.getTen());
            ps.setString(4, bill.getDiachi());
            ps.setDouble(5, bill.getTongTien());
            ps.setString(6, bill.getPt_thanhToan());
            ps.setString(7, bill.getGhiChu());
            ps.setString(8, bill.getHash());
            ps.setString(9, ""); // Signature để trống

            ps.executeUpdate();

            // Lấy ID của hóa đơn vừa thêm
            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);
            }
            System.out.println("Hóa đơn đã được thêm. ID: " + generatedId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) resultSet.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
        return generatedId;
    }

    // Thêm chi tiết hóa đơn vào bảng 'ct_hoadon'
    public void addBillDetails(BillDetails billDetails) {
        String query = "INSERT INTO ct_hoadon (id_hoadon, id_sanpham, soluong, dongia) VALUES (?, ?, ?, ?)";

        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);

            System.out.println("Thêm chi tiết hóa đơn: id_hoadon = " + billDetails.getId_hd() +
                    ", id_sanpham = " + billDetails.getProduct().getId() +
                    ", soluong = " + billDetails.getSoLuong() +
                    ", dongia = " + billDetails.getDongia());

            ps.setInt(1, billDetails.getId_hd());
            ps.setString(2, billDetails.getProduct().getId());
            ps.setInt(3, billDetails.getSoLuong());
            ps.setDouble(4, billDetails.getDongia());

            int result = ps.executeUpdate();
            System.out.println("Kết quả: " + result + " dòng đã được thêm vào ct_hoadon.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void deleteBill(int billId) {
        String deleteBillDetailsQuery = "DELETE FROM ct_hoadon WHERE id_hoadon = ?";
        String deleteBillQuery = "DELETE FROM hoadon WHERE id = ?";

        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement psDetails = conn.prepareStatement(deleteBillDetailsQuery);
             PreparedStatement psBill = conn.prepareStatement(deleteBillQuery)) {

            // Xóa các chi tiết hóa đơn trước
            psDetails.setInt(1, billId);
            int detailsDeleted = psDetails.executeUpdate();
            System.out.println("Chi tiết hóa đơn đã xóa: " + detailsDeleted);

            // Xóa hóa đơn
            psBill.setInt(1, billId);
            int billDeleted = psBill.executeUpdate();
            System.out.println("Hóa đơn đã xóa: " + billDeleted);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách hóa đơn của một người dùng
    public List<Bill> getBillDetails(String userId) {
        List<Bill> billList = new ArrayList<>();
        String query = "SELECT hd.id AS hoadon_id, hd.ten, hd.dia_chi_giao_hang, hd.ngaylap_hd, " +
                "hd.tongtien, hd.ghichu, hd.hash, hd.signature " + // Thêm hd.signature
                "FROM hoadon hd " +
                "WHERE hd.id_ngdung = ?";

        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setId(rs.getInt("hoadon_id"));
                    bill.setTen(rs.getString("ten"));
                    bill.setDiachi(rs.getString("dia_chi_giao_hang"));
                    bill.setNgayLap_hoaDon(rs.getTimestamp("ngaylap_hd"));
                    bill.setTongTien(rs.getDouble("tongtien"));
                    bill.setGhiChu(rs.getString("ghichu"));
                    bill.setHash(rs.getString("hash"));
                    bill.setSignature(rs.getString("signature")); // Gán giá trị signature

                    billList.add(bill);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billList;
    }

}
