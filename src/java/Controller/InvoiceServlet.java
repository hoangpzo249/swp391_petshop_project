/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.InvoiceDAO;
import DAO.OrderContentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import Model.Invoice;
import Model.OrderContent;
import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author QuangAnh
 */
public class InvoiceServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 

        
        private InvoiceDAO invoiceDAO;
        
        @Override
        public void init() {
            invoiceDAO = new InvoiceDAO();
        }
        
        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet InvoiceServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet InvoiceServlet at " + request.getContextPath() + "</h1>");
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
            
            String orderIdStr = request.getParameter("orderId");
            
            if (orderIdStr == null || orderIdStr.isEmpty()) {
                request.setAttribute("error", "Thiếu mã đơn hàng.");
                request.getRequestDispatcher("Invoice.jsp").forward(request, response);
                return;
            }
            
            try {
                int orderId = Integer.parseInt(orderIdStr);

                // Lấy hóa đơn từ DAO
                InvoiceDAO invoiceDAO = new InvoiceDAO();
                Invoice invoice = invoiceDAO.getInvoiceByOrderId(orderId);  // sửa: không phải List

                if (invoice == null) {
                    request.setAttribute("error", "Không tìm thấy hóa đơn cho đơn hàng #" + orderId);
                    request.getRequestDispatcher("Invoice.jsp").forward(request, response);
                    return;
                }

                // Lấy danh sách sản phẩm (OrderContent)
                OrderContentDAO ocDAO = new OrderContentDAO();
                List<OrderContent> orderContents = ocDAO.getOrderContentByPetId(orderId); // sửa lại tên cho đúng

                // Đẩy dữ liệu sang JSP
                request.setAttribute("invoice", invoice);
                request.setAttribute("orderContents", orderContents);
                request.getRequestDispatcher("Invoice.jsp").forward(request, response);
                
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Mã đơn hàng không hợp lệ.");
                request.getRequestDispatcher("Invoice.jsp").forward(request, response);
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
            processRequest(request, response);
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

