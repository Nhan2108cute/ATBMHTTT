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
            ps.setString(10, bill.getStatus());



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
                "hd.tongtien, hd.ghichu, hd.hash, hd.signature, hd.status " + // Thêm hd.signature
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
                    bill.setStatus(rs.getString("status"));
                    billList.add(bill);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billList;
    }
    public void cancelOrder(String bID) {
        String query = "UPDATE hoadon SET status = 'Huy' where id = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, bID);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<Bill> getAllBills() {
        List<Bill> list = new ArrayList<>();
        String query =
                "SELECT DISTINCT h.id as bill_id, h.*, n.hoten, " +
                        "( " +
                        "    SELECT pk.status " +
                        "    FROM public_keys pk " +
                        "    WHERE pk.user_id = h.id_ngdung " +
                        "    AND pk.created_at <= h.ngaylap_hd " +
                        "    ORDER BY pk.created_at DESC " +
                        "    LIMIT 1 " +
                        ") as key_status, " +
                        "( " +
                        "    SELECT pk.created_at " +
                        "    FROM public_keys pk " +
                        "    WHERE pk.user_id = h.id_ngdung " +
                        "    AND pk.created_at <= h.ngaylap_hd " +
                        "    ORDER BY pk.created_at DESC " +
                        "    LIMIT 1 " +
                        ") as key_created, " +
                        "( " +
                        "    SELECT pk.end_at " +
                        "    FROM public_keys pk " +
                        "    WHERE pk.user_id = h.id_ngdung " +
                        "    AND pk.created_at <= h.ngaylap_hd " +
                        "    ORDER BY pk.created_at DESC " +
                        "    LIMIT 1 " +
                        ") as key_end_at, " +
                        "( " +
                        "    SELECT pk.key_value " +
                        "    FROM public_keys pk " +
                        "    WHERE pk.user_id = h.id_ngdung " +
                        "    AND pk.created_at <= h.ngaylap_hd " +
                        "    ORDER BY pk.created_at DESC " +
                        "    LIMIT 1 " +
                        ") as public_key " +
                        "FROM hoadon h " +
                        "JOIN nguoidung n ON h.id_ngdung = n.id " +
                        "ORDER BY h.ngaylap_hd DESC";

        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bill bill = createBill(rs);

                String keyStatus = rs.getString("key_status");
                Timestamp keyCreated = rs.getTimestamp("key_created");
                Timestamp keyEndAt = rs.getTimestamp("key_end_at");
                Timestamp orderDate = rs.getTimestamp("ngaylap_hd");
                String signature = rs.getString("signature");
                String publicKey = rs.getString("public_key");
                String currentStatus = rs.getString("status"); // Lấy trạng thái hiện tại

                // Chỉ cập nhật trạng thái nếu cần thiết
                if (signature != null && !signature.isEmpty() && publicKey != null) {
                    String newStatus = determineStatus(keyStatus, keyEndAt, keyCreated, orderDate,
                            publicKey, signature, bill.getHash());
                    if (!newStatus.equals(currentStatus)) {
                        updateBillStatus(bill.getId(), newStatus);
                        bill.setStatus(newStatus);
                    } else {
                        bill.setStatus(currentStatus);
                    }
                }

                list.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private String determineStatus(String keyStatus, Timestamp keyEndAt, Timestamp keyCreated,
                                   Timestamp orderDate, String publicKey, String signature, String hash) {
        if (keyStatus == null || signature == null || signature.isEmpty()) {
            return "Chua xac thuc";
        }

        if ("Xac thuc".equals(keyStatus)) {
            // Key đang active
            if (keyEndAt == null && orderDate.after(keyCreated)) {
                return verifySignature(hash, publicKey, signature) ? "Da xac thuc" : "Chua xac thuc";
            }
        } else if ("Mat".equals(keyStatus) && keyEndAt != null) {
            // Key đã bị báo mất
            if (orderDate.before(keyEndAt)) {
                return verifySignature(hash, publicKey, signature) ? "Da xac thuc" : "Chua xac thuc";
            } else {
                return "Huy"; // Hủy đơn hàng sau thời điểm mất key
            }
        }

        return "Chua xac thuc";
    }

    private Bill createBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setId(rs.getInt("bill_id"));
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
        nguoiDung.setId(rs.getString("id_ngdung"));
        nguoiDung.setFullName(rs.getString("hoten"));
        bill.setNguoiDung(nguoiDung);

        return bill;
    }
    private void updateBillStatus(int billId, String status) {
        String query = "UPDATE hoadon SET status = ? WHERE id = ?";

        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, billId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean verifySignature(String hash, String publicKeyStr, String signatureStr) {
        try {
            // Giải mã khóa
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            // Xác thực chữ ký
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(hash.getBytes(StandardCharsets.UTF_8));

            byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
