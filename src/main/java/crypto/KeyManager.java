package crypto;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

// Class để quản lý KeyPair
public class KeyManager {
    public static String generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Chuyển khóa thành Base64
            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

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

