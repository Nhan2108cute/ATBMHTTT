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
    public void updateOrder(int id, String ten, String diachi, String ghichu, double tongtien, String pt_thanhtoan, String status) {
        String query = "UPDATE hoadon SET ten = ?, dia_chi_giao_hang = ?, ghichu = ?, tongtien = ?, pt_thanhtoan = ?, status = ? WHERE id = ?";
        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ten);             // Tên khách hàng
            ps.setString(2, diachi);          // Địa chỉ giao hàng
            ps.setString(3, ghichu);          // Ghi chú
            ps.setDouble(4, tongtien);        // Tổng tiền
            ps.setString(5, pt_thanhtoan);    // Phương thức thanh toán
            ps.setString(6, status);          // Trạng thái
            ps.setInt(7, id);                 // ID hóa đơn
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bill getBillById(int id) {
        String query = "SELECT * FROM hoadon WHERE id = ?";
        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Bill(
                        rs.getInt("id"),
                        null, // Thay bằng User nếu cần thông tin
                        rs.getString("ten"),
                        rs.getTimestamp("ngaylap_hd"),
                        rs.getTimestamp("lancuoithaydoi_hd"),
                        rs.getString("dia_chi_giao_hang"),
                        rs.getString("pt_thanhtoan"),
                        rs.getString("ghichu"),
                        rs.getDouble("tongtien"),
                        rs.getString("hash"),
                        rs.getString("signature"),
                        rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Bill> getAllBills() {
        List<Bill> list = new ArrayList<>();
        // Query đơn hàng đã xác thực
        String query = "SELECT hd.id AS hoadon_id, hd.ten, hd.dia_chi_giao_hang, hd.ngaylap_hd, " +
                "hd.tongtien, hd.ghichu,hd.pt_thanhtoan, hd.hash, hd.signature, hd.status, hd.lancuoithaydoi_hd " + // Thêm hd.signature
                "FROM hoadon hd";

        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setId(rs.getInt("hoadon_id"));
                    bill.setTen(rs.getString("ten"));
                    bill.setDiachi(rs.getString("dia_chi_giao_hang"));
                    bill.setNgayLap_hoaDon(rs.getTimestamp("ngaylap_hd"));
                    bill.setTongTien(rs.getDouble("tongtien"));
                    bill.setGhiChu(rs.getString("ghichu"));
                    bill.setPt_thanhToan(rs.getString("pt_thanhtoan"));
                    bill.setHash(rs.getString("hash"));
                    bill.setSignature(rs.getString("signature")); // Gán giá trị signature
                    bill.setStatus(rs.getString("status"));
                    bill.setLancuoithaydoi_hoaDon(rs.getTimestamp("lancuoithaydoi_hd"));
                    list.add(bill);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }





    private Bill createBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setId(rs.getInt("id"));
        bill.setTen(rs.getString("hoten"));
        bill.setDiachi(rs.getString("dia_chi_giao_hang"));
        bill.setStatus(rs.getString("status")); // Trạng thái từ database
        bill.setTongTien(rs.getDouble("tongtien"));
        bill.setPt_thanhToan(rs.getString("pt_thanhtoan"));
        bill.setGhiChu(rs.getString("ghichu"));
        bill.setNgayLap_hoaDon(rs.getTimestamp("ngaylap_hd"));
        System.out.println("Database ID: " + bill.getId() + ", Status: " + bill.getStatus());
        return bill;
    }

    private Bill createUnverifiedBill(ResultSet rs) throws SQLException {
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
        bill.setStatus("Da xac thuc");

        User nguoiDung = new User();
        nguoiDung.setId(rs.getString("id_ngdung"));
        nguoiDung.setFullName(rs.getString("hoten"));
        bill.setNguoiDung(nguoiDung);

        return bill;
    }
    public String getBillStatusById(int id) {
        String status = null;
        String query = "SELECT status FROM hoadon WHERE id = ?";
        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getString("status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    public void updateBillStatus(int billId, String newStatus) {
        String query = "UPDATE hoadon SET status = ? WHERE id = ? AND status != 'Huy'";
        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Truy vấn trạng thái hiện tại từ database
            String currentStatusQuery = "SELECT status FROM hoadon WHERE id = ?";
            try (PreparedStatement checkPs = conn.prepareStatement(currentStatusQuery)) {
                checkPs.setInt(1, billId);
                ResultSet rs = checkPs.executeQuery();
                if (rs.next()) {
                    String currentStatus = rs.getString("status");

                    // Không cho phép thay đổi trạng thái nếu trạng thái hiện tại là Hủy
                    if ("Huy".equals(currentStatus)) {
                        System.out.println("Không thể thay đổi trạng thái đơn hàng ID: " + billId + " vì trạng thái hiện tại là Hủy.");
                        return; // Không thực hiện cập nhật
                    }
                }
            }

            // Thực hiện cập nhật nếu trạng thái không phải Hủy
            ps.setString(1, newStatus);
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
