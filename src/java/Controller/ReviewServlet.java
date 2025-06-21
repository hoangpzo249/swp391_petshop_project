/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ReviewDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import Model.Review;
import java.util.Date;
import java.util.List;
import utils.NotificationUtils;

/**
 *
 * @author QuangAnh
 */
public class ReviewServlet extends HttpServlet {

    private ReviewDAO reviewDAO;

    @Override
    public void init() {
        reviewDAO = new ReviewDAO();
    }

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
            out.println("<title>Servlet ReviewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReviewServlet at " + request.getContextPath() + "</h1>");
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

        int accId = Integer.parseInt(request.getParameter("accId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String reviewText = request.getParameter("comment");

// Tạo đánh giá
        Review r = new Review();
        r.setAccId(accId);
        r.setRating(rating);
        r.setReviewText(reviewText);
        r.setReviewDate(new java.util.Date());

// Thêm vào DB
        boolean success = reviewDAO.insertReview(r);

// Gửi thông báo nếu thành công
        if (success) {
            NotificationUtils.sendNotification(
                    accId, 0,
                    "Gửi đánh giá thành công",
                    "Cảm ơn bạn đã gửi đánh giá.",
                    "system"
            );
            request.setAttribute("message", "Gửi đánh giá thành công!");
        } else {
            request.setAttribute("error", "Gửi đánh giá thất bại!");
        }

// Hiển thị lại danh sách đánh giá của accId
        List<Review> reviewList = reviewDAO.getReviewsByAccId(accId);
        request.setAttribute("reviewList", reviewList);
        request.setAttribute("accId", accId);

// Hiển thị lại trang đánh giá với dữ liệu mới
        request.getRequestDispatcher("review.jsp").forward(request, response);

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
