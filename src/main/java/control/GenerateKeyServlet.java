package control;

import crypto.KeyManager;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GenerateKeyServlet", value = "/GenerateKeyServlet")
public class GenerateKeyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String keyPairJson = KeyManager.generateKeyPair();
            if (keyPairJson == null) {
                throw new Exception("Key generation failed");
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(keyPairJson);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Set lỗi 500
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
