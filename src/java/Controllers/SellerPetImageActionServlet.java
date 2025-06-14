/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.PetImagePathDAO;
import Utils.ImgBbUploader;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
        processRequest(request, response);
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
        PetImagePathDAO _dao = new PetImagePathDAO();
        int petId = Integer.parseInt(request.getParameter("petId"));
        int imageId = Integer.parseInt(request.getParameter("imageId"));
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String referer = request.getHeader("referer");

        switch (action) {
            case "change":
                Part filePart = request.getPart("newImageFile");

                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

                File tempFile = File.createTempFile("upload_", "_" + fileName);
                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                String imageURL = ImgBbUploader.uploadImage(tempFile);

                tempFile.delete();

                if (_dao.changeImageById(imageId, imageURL)) {
                    session.setAttribute("successMess", "Đã thay đổi ảnh thành công.");
                } else {
                    session.setAttribute("errMess", "Đã xảy ra lỗi khi thay đổi ảnh.");
                }
                break;
            case "delete":
                if (_dao.countImagesById(petId) == 1) {
                    _dao.changeImageById(imageId, "https://i.ibb.co/NggxZvb7/defaultcatdog.png");
                    session.setAttribute("successMess", "Chỉ còn 1 ảnh. Đã chuyển về ảnh mặc định.");
                } else {
                    if (_dao.deleteImageById(petId, imageId)) {
                        session.setAttribute("successMess", "Đã xóa ảnh thành công.");
                    } else {
                        session.setAttribute("errMess", "Đã xảy ra lỗi khi xóa ảnh.");
                    }
                }
                break;
            default:
                throw new AssertionError();
        }
        if (referer != null) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect("updatepet?petId=" + petId);
        }
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
