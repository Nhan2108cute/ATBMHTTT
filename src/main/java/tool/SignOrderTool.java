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
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Base64;

public class SignOrderTool {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tool Ký Đơn Hàng");
        frame.setSize(600, 500);
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

        // Private Key Input
        JLabel keyLabel = new JLabel("Private Key:");
        keyLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(keyLabel, gbc);

        JTextArea keyArea = new JTextArea(5, 25);
        keyArea.setLineWrap(true);
        keyArea.setWrapStyleWord(true);
        keyArea.setBorder(new LineBorder(Color.GRAY));
        JScrollPane keyScrollPane = new JScrollPane(keyArea);
        gbc.gridx = 1;
        mainPanel.add(keyScrollPane, gbc);

        // Nút tải file Private Key
        JButton uploadButton = new JButton("Tải File Private Key");
        uploadButton.setBackground(buttonColor);
        uploadButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(uploadButton, gbc);

        // Nút Ký Hash
        JButton signButton = new JButton("Ký Mã Hash");
        signButton.setBackground(buttonColor);
        signButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(signButton, gbc);

        // Kết quả chữ ký
        JLabel resultLabel = new JLabel("Chữ Ký:");
        resultLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
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

        // Sự kiện tải file Private Key
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try (FileReader reader = new FileReader(selectedFile)) {
                        char[] buffer = new char[(int) selectedFile.length()];
                        reader.read(buffer);
                        keyArea.setText(new String(buffer));
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
                String privateKeyContent = keyArea.getText();

                if (hash.isEmpty() || privateKeyContent.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập mã Hash và Private Key!");
                    return;
                }

                try {
                    PrivateKey privateKey = getPrivateKeyFromInput(privateKeyContent);
                    String signature = signHashWithPrivateKey(hash, privateKey);
                    resultArea.setText(signature);
                    saveSignatureToDatabase(hash, signature);
                    JOptionPane.showMessageDialog(frame, "Chữ ký đã được lưu vào database!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Có lỗi khi ký mã Hash!");
                }
            }
        });
    }

    // Chuyển Private Key từ input thành PrivateKey
    private static PrivateKey getPrivateKeyFromInput(String privateKeyContent) throws Exception {
        String privateKeyPEM = privateKeyContent
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    // Ký mã Hash bằng Private Key
    private static String signHashWithPrivateKey(String hash, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(hash.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    // Lưu chữ ký vào database

    // Kiểm tra nếu mã Hash đã có chữ ký
    private static boolean isAlreadySigned(String hash) {
        String query = "SELECT signature FROM hoadon WHERE hash = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, hash);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
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

    // Lưu chữ ký vào database (chỉ khi chưa ký)
    private static void saveSignatureToDatabase(String hash, String signature) {
        if (isAlreadySigned(hash)) {
            JOptionPane.showMessageDialog(null, "Đơn hàng đã được ký. Không thể ký lại!");
            return;
        }

        String query = "UPDATE hoadon SET signature = ? WHERE hash = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, signature);
            ps.setString(2, hash);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Chữ ký đã được lưu thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy đơn hàng với mã Hash này.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Có lỗi khi lưu chữ ký vào database.");
        }
    }

}
