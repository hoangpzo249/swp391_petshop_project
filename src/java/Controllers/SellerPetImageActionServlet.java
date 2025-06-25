/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.PetImageDAO;
import Models.Account;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lenovo
 */
@MultipartConfig
public class SellerPetImageActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SellerPetImageActionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SellerPetImageActionServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("userAccount");
        if (account == null || !account.getAccRole().equals("Seller")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PetImageDAO _dao = new PetImageDAO();
        Map<String, Object> jsonResponse = new HashMap<>();
        Gson gson = new Gson();

        try {
            int petId = Integer.parseInt(request.getParameter("petId"));
            int imageId = Integer.parseInt(request.getParameter("imageId"));
            String action = request.getParameter("action");

            switch (action) {
                case "change":
                    Part filePart = request.getPart("newImageFile");
                    if (filePart == null || filePart.getSize() == 0) {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không có ảnh nào được tải lên.");
                        break;
                    }

                    byte[] imageData;
                    try (InputStream fileContent = filePart.getInputStream()) {
                        imageData = fileContent.readAllBytes();
                    }

                    if (_dao.changeImageById(imageId, imageData)) {
                        String base64Image = Base64.getEncoder().encodeToString(imageData);
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Đã thay đổi ảnh thành công.");
                        jsonResponse.put("newImageData", base64Image); // <-- ADD THIS LINE BACK
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Đã xảy ra lỗi khi thay đổi ảnh.");
                    }
                    break;
                case "delete":
                    if (_dao.countImagesById(petId) <= 1) {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể xóa ảnh cuối cùng. Thú cưng phải có ít nhất một ảnh.");
                    } else {
                        if (_dao.deleteImageById(petId, imageId)) {
                            jsonResponse.put("success", true);
                            jsonResponse.put("message", "Đã xóa ảnh thành công.");
                        } else {
                            jsonResponse.put("success", false);
                            jsonResponse.put("message", "Đã xảy ra lỗi khi xóa ảnh.");
                        }
                    }
                    break;
                default:
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Hành động không hợp lệ.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Đã xảy ra lỗi hệ thống: " + e.getMessage());
        }

        response.getWriter().write(gson.toJson(jsonResponse));
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
