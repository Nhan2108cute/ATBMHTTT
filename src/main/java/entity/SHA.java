package entity;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class SHA {
    public static final String SHA_256 = "SHA-256";

    // Phương thức băm chuỗi
    public static String hash(String mess) {
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_256); // Sử dụng SHA-256
            byte[] messageDigest = md.digest(mess.getBytes("UTF-8"));
            BigInteger number = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(number.toString(16));

            // Bổ sung số 0 vào đầu chuỗi nếu cần
            while (hashText.length() < 64) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // Phương thức băm file
    public static String hashFile(File file, String algorithms) {
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance(algorithms);
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            byte[] messageDigest = md.digest();
            BigInteger number = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(number.toString(16));

            // Bổ sung số 0 vào đầu chuỗi nếu cần
            while (hashText.length() < 64) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // Chạy thử
    public static void main(String[] args) {
        String valueTest = SHA.hash("thanh");
        System.out.println("Hash của chuỗi: " + valueTest);

        File file = new File("C:\\Users\\DELL\\Documents\\qltt8.txt");
        String fileHash = SHA.hashFile(file, SHA.SHA_256);
        System.out.println("Hash của file: " + fileHash);
    }
}
