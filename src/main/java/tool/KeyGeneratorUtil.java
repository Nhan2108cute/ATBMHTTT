package tool;

import java.security.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Base64;

import context.DBConnect;

public class KeyGeneratorUtil {

    // Tạo cặp khóa và lưu vào database
    public static void generateAndStoreKeys(String userId) {
        try {
            // 1. Tạo cặp khóa RSA 2048
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            // 2. Mã hóa khóa thành chuỗi Base64 để lưu vào database
            String privateKeyContent = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            String publicKeyContent = Base64.getEncoder().encodeToString(publicKey.getEncoded());

            // 3. Lưu vào database
            saveKeysToDatabase(userId, publicKeyContent, privateKeyContent);
            System.out.println("Tạo và lưu khóa thành công cho user: " + userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức lưu khóa vào bảng user_keys trong database
    private static void saveKeysToDatabase(String userId, String publicKey, String privateKey) {
        String query = "INSERT INTO user_keys (user_id, public_key, private_key) VALUES (?, ?, ?)"
                     + "ON DUPLICATE KEY UPDATE public_key = ?, private_key = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, userId);
            ps.setString(2, publicKey);
            ps.setString(3, privateKey);
            ps.setString(4, publicKey);
            ps.setString(5, privateKey);

            ps.executeUpdate();
            System.out.println("Khóa đã được lưu vào database thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main để tạo khóa cho một người dùng
    public static void main(String[] args) {
        String userId = "6"; // Thay userId tương ứng
        generateAndStoreKeys(userId);
    }
}
