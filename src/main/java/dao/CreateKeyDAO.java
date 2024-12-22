package dao;

import context.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import static entity.CreateKey.isUserIdExists;
import static entity.CreateKey.storePublicKeyInDatabase;

public class CreateKeyDAO {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    // tạo key
    public void create_key(String publicKey, int specificUserId) {
        create_key(publicKey, specificUserId, new Timestamp(System.currentTimeMillis()));
    }

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
                    if(checkExpiredKey(userId)) {
                        updatePublicKeyInDatabase(userId, userName, publicKey, createTime);
                        System.out.println("Updating public key cho User ID: " + userId + ", Name: " + userName);
                    } else{
                    // Lưu trữ public key cho người dùng cụ thể này với thời gian tạo được chỉ định
                        storePublicKeyInDatabase(userId, userName, publicKey, createTime);
                        System.out.println("Storing public key cho User ID: " + userId + ", Name: " + userName);
                    }
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

    private void updatePublicKeyInDatabase(int userId, String userName, String publicKey, Timestamp createTime) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int id = 0;

        try {
            connection = new DBConnect().getConnection();

            // Kiểm tra xem có key nào với status = 'Huy' hay không
            String selectExpiredKeyQuery = "SELECT id, end_at FROM public_keys WHERE user_id = ? AND status = 'Mat'";
            preparedStatement = connection.prepareStatement(selectExpiredKeyQuery);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Nếu có key bị hủy trước đó, lấy end_at của key đó làm thời gian tạo mới
                id = resultSet.getInt("id");
                createTime = resultSet.getTimestamp("end_at");
                System.out.println("Tạo key mới với thời gian tạo = end_at của key trước đó: " + createTime);
            }

            // Đóng PreparedStatement cũ để chuẩn bị cho câu lệnh chèn
            resultSet.close();
            preparedStatement.close();

            // Câu lệnh chèn dữ liệu với thời gian tạo được chỉ định
            String updateQuery = "UPDATE public_keys\n" +
                    "SET key_value = ?,  status = ?, created_at = ?, end_at = NULL WHERE id = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            
            preparedStatement.setString(1, publicKey);
            preparedStatement.setString(2, "Xac thuc");
            preparedStatement.setTimestamp(3, createTime);
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
            System.out.println("Public key đã được lưu cho người dùng " + userName + " (ID: " + userId + ")");
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

    private void storePublicKeyInDatabase(int userId, String userName, String publicKey, Timestamp createTime) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = new DBConnect().getConnection();
            // Câu lệnh chèn dữ liệu với thời gian tạo được chỉ định
            String insertQuery = "INSERT INTO public_keys (user_id, user_name, key_value, status, created_at, end_at) " +
                    "VALUES (?, ?, ?, ?, ?, NULL)";


            preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, publicKey);
            preparedStatement.setString(4, "Xac thuc");
            preparedStatement.setTimestamp(5, createTime);

            preparedStatement.executeUpdate();
            System.out.println("Public key đã được lưu cho người dùng " + userName + " (ID: " + userId + ")");
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

    public boolean checkKey(int uId) {
        try {
            conn = new DBConnect().getConnection();
            String sql = "Select * from public_keys where user_id = ? and status = 'Xac thuc'";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, uId);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public boolean checkExpiredKey(int uId) {
        try {
            conn = new DBConnect().getConnection();
            String sql = "Select * from public_keys where user_id = ? and status = 'Mat'";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, uId);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean checkDuplicatePublicKey(String publicKey) {
        String query = "SELECT COUNT(*) FROM public_keys WHERE key_value = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, publicKey);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu COUNT > 0 thì Public Key đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // báo key mất
    public Timestamp reportLostKey(int userId) {
        Connection connection = null;
        PreparedStatement ps = null;
        Timestamp lostTime = new Timestamp(System.currentTimeMillis());

        try {
            connection = new DBConnect().getConnection();

            // Cập nhật thời gian kết thúc (end_at) cho key hiện tại thành thời điểm báo mất
            String updateCurrentKey = "UPDATE public_keys SET end_at = ?, status = 'Mat' " +
                    "WHERE user_id = ? AND status = 'Xac thuc' AND end_at IS NULL";
            ps = connection.prepareStatement(updateCurrentKey);
            ps.setTimestamp(1, lostTime);
            ps.setInt(2, userId);
            ps.executeUpdate();

            return lostTime;
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

}
