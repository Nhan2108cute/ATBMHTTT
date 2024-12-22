package dao;

import context.DBConnect;
import entity.Bill;
import entity.BillDetails;
import entity.Product;
import entity.User;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class BillDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // Thêm hóa đơn vào bảng 'hoadon'
    public int addBill(Bill bill) throws SQLException {
        ResultSet resultSet = null;
        int generatedId = -1;

        String query = "INSERT INTO hoadon (ngaylap_hd, id_ngdung, ten, dia_chi_giao_hang, tongtien, pt_thanhtoan, ghichu, hash, signature,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            ps.setString(10,bill.getStatus());

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

    // Phương thức kiểm tra và cập nhật trạng thái đơn hàng
    public void updateOrderVerificationStatus(int billId) {
        String query = "SELECT h.*, pk.status as key_status, pk.end_at, h.ngaylap_hd " +
                "FROM hoadon h " +
                "JOIN public_keys pk ON h.id_ngdung = pk.user_id " +
                "WHERE h.id = ? " +
                "ORDER BY pk.created_at DESC LIMIT 1";

        String updateQuery = "UPDATE hoadon SET status = ? WHERE id = ?";

        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             PreparedStatement updatePs = conn.prepareStatement(updateQuery)) {

            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String keyStatus = rs.getString("key_status");
                Timestamp endAt = rs.getTimestamp("end_at");
                Timestamp orderDate = rs.getTimestamp("ngaylap_hd");
                String newStatus;

                if ("Xac thuc".equals(keyStatus) && endAt == null) {
                    newStatus = "Da xac thuc";
                } else if ("Mat".equals(keyStatus) && endAt != null) {
                    if (orderDate.before(endAt)) {
                        newStatus = "Da xac thuc";
                    } else {
                        newStatus = "Chua xac thuc";
                    }
                } else {
                    newStatus = "Chua xac thuc";
                }

                // Cập nhật trạng thái mới
                updatePs.setString(1, newStatus);
                updatePs.setInt(2, billId);
                updatePs.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Bill> getAllBills() {
        List<Bill> list = new ArrayList<>();
        // Query join 3 bảng: hoadon, nguoidung và public_keys để lấy thông tin xác thực
        String query = "SELECT h.*, n.hoten, pk.status as key_status, pk.key_value as public_key, " +
                "pk.end_at, CASE " +
                "   WHEN pk.status = 'Xac thuc' AND h.signature IS NOT NULL AND h.signature != '' THEN 'Da xac thuc' " +
                "   ELSE 'Chua xac thuc' " +
                "END as verification_status " +
                "FROM hoadon h " +
                "JOIN nguoidung n ON h.id_ngdung = n.id " +
                "LEFT JOIN (SELECT * FROM public_keys WHERE status = 'Xac thuc' " +
                "          ORDER BY created_at DESC LIMIT 1) pk ON h.id_ngdung = pk.user_id " +
                "ORDER BY h.ngaylap_hd DESC";

        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setTen(rs.getString("ten"));
                bill.setNgayLap_hoaDon(rs.getTimestamp("ngaylap_hd"));
                bill.setDiachi(rs.getString("dia_chi_giao_hang"));
                bill.setTongTien(rs.getDouble("tongtien"));
                bill.setPt_thanhToan(rs.getString("pt_thanhtoan"));
                bill.setGhiChu(rs.getString("ghichu"));
                bill.setHash(rs.getString("hash"));
                bill.setSignature(rs.getString("signature"));
                bill.setStatus(rs.getString("status"));

                User nguoiDung = new User();
                nguoiDung.setId(rs.getString("id"));
                bill.setNguoiDung(nguoiDung);

                // Cập nhật trạng thái vào database
                updateBillStatus(bill.getId(), bill.getStatus());

                list.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void updateBillStatus(int billId, String status) {
        String updateQuery = "UPDATE hoadon SET status = ? WHERE id = ?";

        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(updateQuery)) {

            ps.setString(1, status);
            ps.setInt(2, billId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private String verifyAndGetOrderStatus(Bill bill) {
//        String query = "SELECT pk.status as key_status, pk.key_value as public_key, " +
//                "pk.end_at " +
//                "FROM public_keys pk " +
//                "WHERE pk.user_id = ? AND pk.status = 'Xac thuc' " +
//                "ORDER BY pk.created_at DESC LIMIT 1";
//
//        try (Connection conn = new DBConnect().getConnection();
//             PreparedStatement ps = conn.prepareStatement(query)) {
//
//            ps.setString(1, bill.getNguoiDung().getId());
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    String publicKeyStr = rs.getString("public_key");
//                    Timestamp endAt = rs.getTimestamp("end_at");
//
////                    // Kiểm tra thời hạn khóa
////                    if (endAt != null && bill.getNgayLap_hoaDon().after(endAt)) {
////                        return "Khoa het han";
////                    }
//
//                    // Xác thực chữ ký
//                    boolean isSignatureValid = verifySignature(
//                            bill.getHash(),
//                            publicKeyStr,
//                            bill.getSignature()
//                    );
//
//                    return isSignatureValid ? "Da xac thuc" : "Chua xac thuc";
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "Chua xac thuc";
//    }
//
//    private boolean verifySignature(String hash, String publicKeyStr, String signatureStr) {
//        try {
//            // Làm sạch khóa công khai
//            String cleanPublicKey = publicKeyStr
//                    .replace("-----BEGIN PUBLIC KEY-----", "")
//                    .replace("-----END PUBLIC KEY-----", "")
//                    .replaceAll("\\s", "");
//
//            // Giải mã khóa
//            byte[] keyBytes = Base64.getDecoder().decode(cleanPublicKey);
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PublicKey publicKey = keyFactory.generatePublic(keySpec);
//
//            // Xác thực chữ ký
//            Signature signature = Signature.getInstance("SHA256withRSA");
//            signature.initVerify(publicKey);
//            signature.update(hash.getBytes(StandardCharsets.UTF_8));
//
//            byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);
//            return signature.verify(signatureBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
