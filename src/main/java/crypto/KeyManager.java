package crypto;

import dao.DAO;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

// Class để quản lý KeyPair
public class KeyManager {
    private static DAO dao = new DAO(); // Tạo một đối tượng DAO để truy cập database

    public static String generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);

            String publicKeyBase64;
            String privateKeyBase64;

            do {
                // Phát sinh cặp khóa RSA
                KeyPair keyPair = keyGen.generateKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();

                // Chuyển Public Key và Private Key sang Base64
                publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
                privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

                // Kiểm tra trùng lặp Public Key trong database
            } while (dao.checkDuplicatePublicKey(publicKeyBase64)); // Nếu trùng, phát sinh khóa mới

            // Trả về khóa dưới dạng JSON
            return String.format("{\"publicKey\": \"%s\", \n \"privateKey\": \"%s\"}", publicKeyBase64, privateKeyBase64);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(generateKeyPair());
    }
}


