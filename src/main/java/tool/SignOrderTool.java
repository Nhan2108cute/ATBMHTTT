package tool;

import context.DBConnect;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

public class SignOrderTool {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tool Ký Đơn Hàng");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Tạo Font và màu sắc
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Color backgroundColor = new Color(230, 240, 250);
        Color buttonColor = new Color(70, 130, 180);

        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Mã Hash
        JLabel hashLabel = new JLabel("Mã Hash:");
        hashLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(hashLabel, gbc);

        JTextField hashField = new JTextField(25);
        gbc.gridx = 1;
        mainPanel.add(hashField, gbc);

        // Public Key Input
        JLabel publicKeyLabel = new JLabel("Public Key:");
        publicKeyLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(publicKeyLabel, gbc);

        JTextArea publicKeyArea = new JTextArea(5, 25);
        publicKeyArea.setLineWrap(true);
        publicKeyArea.setWrapStyleWord(true);
        publicKeyArea.setBorder(new LineBorder(Color.GRAY));
        JScrollPane publicKeyScrollPane = new JScrollPane(publicKeyArea);
        gbc.gridx = 1;
        mainPanel.add(publicKeyScrollPane, gbc);

        // Nút tải file Public Key
        JButton uploadPublicKeyButton = new JButton("Tải File Public Key");
        uploadPublicKeyButton.setBackground(buttonColor);
        uploadPublicKeyButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(uploadPublicKeyButton, gbc);

        // Private Key Input
        JLabel privateKeyLabel = new JLabel("Private Key:");
        privateKeyLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(privateKeyLabel, gbc);

        JTextArea privateKeyArea = new JTextArea(5, 25);
        privateKeyArea.setLineWrap(true);
        privateKeyArea.setWrapStyleWord(true);
        privateKeyArea.setBorder(new LineBorder(Color.GRAY));
        JScrollPane privateKeyScrollPane = new JScrollPane(privateKeyArea);
        gbc.gridx = 1;
        mainPanel.add(privateKeyScrollPane, gbc);

        // Nút tải file Private Key
        JButton uploadPrivateKeyButton = new JButton("Tải File Private Key");
        uploadPrivateKeyButton.setBackground(buttonColor);
        uploadPrivateKeyButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(uploadPrivateKeyButton, gbc);

        // Nút Ký Hash
        JButton signButton = new JButton("Ký Mã Hash");
        signButton.setBackground(buttonColor);
        signButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(signButton, gbc);

        // Kết quả chữ ký
        JLabel resultLabel = new JLabel("Chữ Ký:");
        resultLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(resultLabel, gbc);

        JTextArea resultArea = new JTextArea(3, 25);
        resultArea.setEditable(false);
        resultArea.setBorder(new LineBorder(Color.GRAY));
        resultArea.setBackground(Color.WHITE);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        gbc.gridx = 1;
        mainPanel.add(resultScrollPane, gbc);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Sự kiện tải file Public Key
        uploadPublicKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try (FileReader reader = new FileReader(selectedFile)) {
                        char[] buffer = new char[(int) selectedFile.length()];
                        reader.read(buffer);
                        publicKeyArea.setText(new String(buffer));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Không thể đọc file Public Key!");
                    }
                }
            }
        });

        // Sự kiện tải file Private Key
        uploadPrivateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try (FileReader reader = new FileReader(selectedFile)) {
                        char[] buffer = new char[(int) selectedFile.length()];
                        reader.read(buffer);
                        privateKeyArea.setText(new String(buffer));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Không thể đọc file Private Key!");
                    }
                }
            }
        });

        // Sự kiện ký mã Hash
        signButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hash = hashField.getText();
                String publicKeyContent = publicKeyArea.getText();
                String privateKeyContent = privateKeyArea.getText();

                if (hash.isEmpty() || publicKeyContent.isEmpty() || privateKeyContent.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập đủ Mã Hash, Public Key và Private Key!");
                    return;
                }

                try {
                    if (isAlreadySigned(hash)) {
                        JOptionPane.showMessageDialog(frame, "Đơn hàng đã được ký trước đó!");
                        return;
                    }
                    PublicKey publicKey = getPublicKeyFromInput(publicKeyContent);
                    PrivateKey privateKey = getPrivateKeyFromInput(privateKeyContent);

                    // Kiểm tra public key có hợp lệ không
                    if (!isPublicKeyValid(hash, publicKeyContent)) {
                        JOptionPane.showMessageDialog(frame, "Public Key không hợp lệ hoặc không thuộc mã Hash này!");
                        return;
                    }

                    // Ký mã Hash bằng private key
                    String signature = signHashWithPrivateKey(hash, privateKey);
                    resultArea.setText(signature);

                    // Lưu chữ ký vào database
                    saveSignatureToDatabase(hash, signature);
                    JOptionPane.showMessageDialog(frame, "Ký thành công!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Có lỗi khi ký mã Hash!");
                }
            }
        });
    }
    private static boolean isAlreadySigned(String hash) {
        String query = "SELECT signature FROM hoadon WHERE hash = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, hash);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String signature = rs.getString("signature");
                    return signature != null && !signature.isEmpty(); // Đã có chữ ký
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Chưa có chữ ký
    }

    // Lấy Public Key từ input
    private static PublicKey getPublicKeyFromInput(String publicKeyContent) throws Exception {
        String cleanPublicKey = cleanKey(publicKeyContent);
        byte[] keyBytes = Base64.getDecoder().decode(cleanPublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    // Lấy Private Key từ input
    private static PrivateKey getPrivateKeyFromInput(String privateKeyContent) throws Exception {
        String cleanPrivateKey = cleanKey(privateKeyContent);
        byte[] keyBytes = Base64.getDecoder().decode(cleanPrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    // Làm sạch khóa (bỏ BEGIN/END KEY)
    private static String cleanKey(String keyContent) {
        return keyContent.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
    }

    // Ký mã Hash bằng Private Key
    private static String signHashWithPrivateKey(String hash, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(hash.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    // Kiểm tra nếu Public Key hợp lệ
    private static boolean isPublicKeyValid(String hash, String publicKeyContent) {
        String cleanPublicKey = cleanKey(publicKeyContent);
        String query = "SELECT pk.key_value " +
                "FROM public_keys pk " +
                "JOIN hoadon hd ON pk.user_id = hd.id_ngdung " +
                "WHERE hd.hash = ? AND pk.key_value = ? AND pk.status = 'Xac thuc'";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, hash);
            ps.setString(2, cleanPublicKey);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lưu chữ ký vào database
    private static void saveSignatureToDatabase(String hash, String signature) {
        String query = "UPDATE hoadon SET signature = ? WHERE hash = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, signature);
            ps.setString(2, hash);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Chữ ký đã được lưu thành công!");
            } else {
                System.out.println("Không tìm thấy đơn hàng với mã Hash này.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
