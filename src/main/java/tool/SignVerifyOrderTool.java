package tool;

import javax.swing.*;
import context.DBConnect;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.FileReader;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;


public class SignVerifyOrderTool extends JFrame {
    private Font labelFont;
    private Color backgroundColor;
    private Color buttonColor;

    public static void main(String[] args) {
        // Đảm bảo GUI được tạo và hiển thị trong Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Thiết lập giao diện người dùng cho giống với hệ điều hành
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    // Nếu không thiết lập được, sử dụng giao diện mặc định
                    e.printStackTrace();
                }

                // Tạo cửa sổ chính của ứng dụng
                SignVerifyOrderTool mainWindow = new SignVerifyOrderTool();

                // Căn giữa cửa sổ trên màn hình
                mainWindow.setLocationRelativeTo(null);

                // Hiển thị cửa sổ
                mainWindow.setVisible(true);
            }
        });
    }

    public SignVerifyOrderTool() {
        super("Tool Ký và Xác Thực Đơn Hàng");
        initializeUI();
    }

    // Khởi tạo giao diện người dùng
    private void initializeUI() {
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        labelFont = new Font("Arial", Font.BOLD, 14);
        backgroundColor = new Color(230, 240, 250);
        buttonColor = new Color(70, 130, 180);

        // Tạo thanh tab
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));

        // Thêm các tab
        tabbedPane.addTab("Ký Đơn Hàng", createSignPanel());
        tabbedPane.addTab("Xác Thực Chữ Ký", createVerifyPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
    // Tạo panel ký đơn hàng
    private JScrollPane createSignPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Trường nhập mã hash
        addComponent(mainPanel, new JLabel("Mã Hash:"), gbc, 0, 0);
        JTextField hashField = new JTextField(30);
        addComponent(mainPanel, hashField, gbc, 1, 0);

        // Khóa công khai
        addComponent(mainPanel, new JLabel("Public Key:"), gbc, 0, 1);
        JTextArea publicKeyArea = createTextArea(5, 30);
        JScrollPane publicKeyScroll = new JScrollPane(publicKeyArea);
        addComponent(mainPanel, publicKeyScroll, gbc, 1, 1);

        JButton uploadPublicKeyBtn = createStyledButton("Tải File Public Key");
        addComponent(mainPanel, uploadPublicKeyBtn, gbc, 1, 2);

        // Khóa bí mật
        addComponent(mainPanel, new JLabel("Private Key:"), gbc, 0, 3);
        JTextArea privateKeyArea = createTextArea(5, 30);
        JScrollPane privateKeyScroll = new JScrollPane(privateKeyArea);
        addComponent(mainPanel, privateKeyScroll, gbc, 1, 3);

        JButton uploadPrivateKeyBtn = createStyledButton("Tải File Private Key");
        addComponent(mainPanel, uploadPrivateKeyBtn, gbc, 1, 4);

        // Nút ký
        JButton signButton = createStyledButton("Ký Mã Hash");
        addComponent(mainPanel, signButton, gbc, 1, 5);

        // Kết quả chữ ký
        addComponent(mainPanel, new JLabel("Chữ Ký:"), gbc, 0, 6);
        JTextArea resultArea = createTextArea(3, 30);
        resultArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(resultArea);
        addComponent(mainPanel, resultScroll, gbc, 1, 6);

        // Thêm các xử lý sự kiện
        setupSignPanelListeners(uploadPublicKeyBtn, uploadPrivateKeyBtn, signButton,
                hashField, publicKeyArea, privateKeyArea, resultArea);

        return wrapInScrollPane(mainPanel);
    }

    // Tạo panel xác thực chữ ký
    private JScrollPane createVerifyPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Trường nhập mã hash
        addComponent(mainPanel, new JLabel("Mã Hash:"), gbc, 0, 0);
        JTextField hashField = new JTextField(30);
        addComponent(mainPanel, hashField, gbc, 1, 0);

        // Khóa công khai
        addComponent(mainPanel, new JLabel("Public Key:"), gbc, 0, 1);
        JTextArea publicKeyArea = createTextArea(5, 30);
        JScrollPane publicKeyScroll = new JScrollPane(publicKeyArea);
        addComponent(mainPanel, publicKeyScroll, gbc, 1, 1);

        JButton uploadPublicKeyBtn = createStyledButton("Tải File Public Key");
        addComponent(mainPanel, uploadPublicKeyBtn, gbc, 1, 2);

        // Chữ ký
        addComponent(mainPanel, new JLabel("Chữ Ký:"), gbc, 0, 3);
        JTextArea signatureArea = createTextArea(5, 30);
        JScrollPane signatureScroll = new JScrollPane(signatureArea);
        addComponent(mainPanel, signatureScroll, gbc, 1, 3);

        // Nút xác thực
        JButton verifyButton = createStyledButton("Xác Thực Chữ Ký");
        addComponent(mainPanel, verifyButton, gbc, 1, 4);

        // Ket quả
        JLabel resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        addComponent(mainPanel, resultLabel, gbc, 1, 5);

        // Thêm các xử lý sự kiện
        setupVerifyPanelListeners(uploadPublicKeyBtn, verifyButton,
                hashField, publicKeyArea, signatureArea, resultLabel);

        return wrapInScrollPane(mainPanel);
    }

    // Bọc panel trong một thanh cuộn
    private JScrollPane wrapInScrollPane(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    // Thêm thành phần vào panel với các ràng buộc định vị
    private void addComponent(JPanel panel, JComponent component,
                              GridBagConstraints gbc, int x, int y) {
        if (component instanceof JLabel) {
            ((JLabel) component).setFont(labelFont);
        }
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(component, gbc);
    }

    // Tạo vùng văn bản
    private JTextArea createTextArea(int rows, int cols) {
        JTextArea textArea = new JTextArea(rows, cols);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(new LineBorder(Color.GRAY));
        return textArea;
    }
    // Tạo nút
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(51, 122, 183));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(null);
        return button;
    }
    // Thiết lập các xử lý sự kiện cho panel ký
    private void setupSignPanelListeners(JButton uploadPublicKeyBtn, JButton uploadPrivateKeyBtn,
                                         JButton signButton, JTextField hashField,
                                         JTextArea publicKeyArea, JTextArea privateKeyArea,
                                         JTextArea resultArea) {
        // Xử lý sự kiện tải file
        uploadPublicKeyBtn.addActionListener(e -> uploadKeyFile(publicKeyArea));
        uploadPrivateKeyBtn.addActionListener(e -> uploadKeyFile(privateKeyArea));

        // Xử lý sự kiện nút ký
        signButton.addActionListener(e -> {
            try {
                handleSignAction(hashField.getText(), publicKeyArea.getText(),
                        privateKeyArea.getText(), resultArea);
            } catch (Exception ex) {
                showError("Lỗi khi ký mã Hash: " + ex.getMessage());
            }
        });
    }

    // Thiết lập các xử lý sự kiện cho panel xác thực
    private void setupVerifyPanelListeners(JButton uploadPublicKeyBtn, JButton verifyButton,
                                           JTextField hashField, JTextArea publicKeyArea,
                                           JTextArea signatureArea, JLabel resultLabel) {
        uploadPublicKeyBtn.addActionListener(e -> uploadKeyFile(publicKeyArea));

        verifyButton.addActionListener(e -> {
            try {
                boolean isValid = verifySignature(hashField.getText(),
                        publicKeyArea.getText(),
                        signatureArea.getText());
                updateVerificationResult(resultLabel, isValid);
            } catch (Exception ex) {
                showError("Lỗi khi xác thực chữ ký: " + ex.getMessage());
            }
        });
    }
    // Tải file khóa
    private void uploadKeyFile(JTextArea targetArea) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileReader reader = new FileReader(fileChooser.getSelectedFile())) {
                char[] buffer = new char[(int) fileChooser.getSelectedFile().length()];
                reader.read(buffer);
                targetArea.setText(new String(buffer));
            } catch (Exception ex) {
                showError("Không thể đọc file!");
            }
        }
    }

    // Xử lý hành động ký
    private void handleSignAction(String hash, String publicKeyStr,
                                  String privateKeyStr, JTextArea resultArea) throws Exception {
        if (hash.isEmpty() || publicKeyStr.isEmpty() || privateKeyStr.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin!");
        }

        if (isAlreadySigned(hash)) {
            throw new IllegalStateException("Đơn hàng đã được ký trước đó!");
        }

        if (!isPublicKeyValid(hash, publicKeyStr)) {
            throw new IllegalArgumentException("Public Key không hợp lệ hoặc không thuộc mã Hash này!");
        }

        PrivateKey privateKey = getPrivateKeyFromInput(privateKeyStr);
        String signature = signHashWithPrivateKey(hash, privateKey);
        saveSignatureToDatabase(hash, signature);
        resultArea.setText(signature);
        showSuccess("Ký thành công!");
    }


    // Xác thực chữ ký
    private boolean verifySignature(String hash, String publicKeyStr,
                                    String signatureStr) throws Exception {
        if (hash.isEmpty() || publicKeyStr.isEmpty() || signatureStr.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin!");
        }

        PublicKey publicKey = getPublicKeyFromInput(publicKeyStr);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(hash.getBytes("UTF-8"));

        byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);
        return signature.verify(signatureBytes);
    }


    // Cập nhật kết quả xác thực
    private void updateVerificationResult(JLabel resultLabel, boolean isValid) {
        if (isValid) {
            resultLabel.setText("Xác thực thành công ✓");
            resultLabel.setForeground(new Color(0, 150, 0));
        } else {
            resultLabel.setText("Xác thực thất bại ✗");
            resultLabel.setForeground(new Color(150, 0, 0));
        }
    }

    // Hiển thị thông báo lỗi
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    // Hiển thị thông báo thành công
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }


    // Kiểm tra xem mã hash đã được ký chưa
    private static boolean isAlreadySigned(String hash) {
        String query = "SELECT signature FROM hoadon WHERE hash = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, hash);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getString("signature") != null
                        && !rs.getString("signature").isEmpty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static PublicKey getPublicKeyFromInput(String publicKeyContent) throws Exception {
        String cleanPublicKey = cleanKey(publicKeyContent);
        byte[] keyBytes = Base64.getDecoder().decode(cleanPublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    private static PrivateKey getPrivateKeyFromInput(String privateKeyContent) throws Exception {
        String cleanPrivateKey = cleanKey(privateKeyContent);
        byte[] keyBytes = Base64.getDecoder().decode(cleanPrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private static String cleanKey(String keyContent) {
        return keyContent.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
    }

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
