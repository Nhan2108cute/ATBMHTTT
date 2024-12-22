package dao;

import context.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class CreateKeyDAO {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    // Tạo key với thời gian hiện tại
    public void create_key(String publicKey, int specificUserId) {
        create_key(publicKey, specificUserId, new Timestamp(System.currentTimeMillis()));
    }

    // Tạo key với thời gian được chỉ định
    public void create_key(String publicKey, int specificUserId, Timestamp createTime) {
        try {
            // Kiểm tra xem public key đã tồn tại hay chưa
            if (checkDuplicatePublicKey(publicKey)) {
                System.out.println("Public key đã tồn tại trong cơ sở dữ liệu.");
                return;
            }

            // Kết nối tới cơ sở dữ liệu
            Connection connection = new DBConnect().getConnection();

            // Lấy thông tin người dùng từ bảng nguoidung
            String selectQuery = "SELECT id, hoten FROM nguoidung WHERE id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, specificUserId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String userName = resultSet.getString("hoten");

                // Kiểm tra xem người dùng đã có khóa đang hoạt động chưa
                if (!checkKey(userId)) {
                    // Lấy thời gian end_at của key bị mất gần nhất (nếu có)
                    Timestamp effectiveCreateTime = getLastReportedKeyEndTime(userId);
                    if (effectiveCreateTime == null) {
                        effectiveCreateTime = createTime;
                    }

                    // Luôn tạo bản ghi mới thay vì update bản ghi cũ
                    storeNewPublicKey(userId, userName, publicKey, effectiveCreateTime);
                    System.out.println("Đã tạo key mới cho User ID: " + userId + ", Name: " + userName);
                } else {
                    System.out.println("Người dùng " + userName + " đã có khóa đang hoạt động.");
                }
            }

            // Đóng các tài nguyên
            resultSet.close();
            selectStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy thời gian kết thúc của key bị báo mất gần nhất
    private Timestamp getLastReportedKeyEndTime(int userId) {
        try {
            Connection connection = new DBConnect().getConnection();
            String query = "SELECT end_at FROM public_keys WHERE user_id = ? AND status = 'Mat' " +
                    "ORDER BY end_at DESC LIMIT 1";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getTimestamp("end_at");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Lưu key mới vào database
    private void storeNewPublicKey(int userId, String userName, String publicKey, Timestamp createTime) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = new DBConnect().getConnection();
            String insertQuery = "INSERT INTO public_keys (user_id, user_name, key_value, status, created_at, end_at) " +
                    "VALUES (?, ?, ?, 'Xac thuc', ?, NULL)";

            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, publicKey);
            preparedStatement.setTimestamp(4, createTime);

            preparedStatement.executeUpdate();
            System.out.println("Public key mới đã được lưu cho người dùng " + userName + " (ID: " + userId + ")");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Kiểm tra key đang hoạt động
    public boolean checkKey(int uId) {
        try {
            conn = new DBConnect().getConnection();
            String sql = "SELECT * FROM public_keys WHERE user_id = ? AND status = 'Xac thuc'";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, uId);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // Kiểm tra key trùng lặp
    public boolean checkDuplicatePublicKey(String publicKey) {
        String query = "SELECT COUNT(*) FROM public_keys WHERE key_value = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, publicKey);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Báo mất key
    public Timestamp reportLostKey(Timestamp endTime ,int userId) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = new DBConnect().getConnection();
            String updateCurrentKey = "UPDATE public_keys SET end_at = ?, status = 'Mat' " +
                    "WHERE user_id = ? AND status = 'Xac thuc' AND end_at IS NULL";
            ps = connection.prepareStatement(updateCurrentKey);
            ps.setTimestamp(1, endTime);
            ps.setInt(2, userId);
            ps.executeUpdate();

            return endTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelOrdersAfterLostKey(int userId, Timestamp reportTime) {
        try (Connection conn = new DBConnect().getConnection()) {
            String sql = "UPDATE hoadon SET status = 'Huy' WHERE id_ngdung = ? AND lancuoithaydoi_hd > ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setTimestamp(2, reportTime);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}