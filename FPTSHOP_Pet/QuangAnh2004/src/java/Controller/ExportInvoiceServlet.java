/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.InvoiceDAO;
import DAO.OrderContentDAO;
import Model.Invoice;
import Model.OrderContent;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.Font;

/**
 *
 * @author QuangAnh
 */
public class ExportInvoiceServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet ExportInvoiceServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportInvoiceServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String idRaw = request.getParameter("invoiceId");

    try {
        int invoiceId = Integer.parseInt(idRaw);
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        OrderContentDAO orderContentDAO = new OrderContentDAO();

        Invoice invoice = (Invoice) invoiceDAO.getInvoiceById(invoiceId);
        if (invoice == null) {
            request.setAttribute("error", "Không tìm thấy hóa đơn");
            request.getRequestDispatcher("invoice.jsp").forward(request, response);
            return;
        }

        List<OrderContent> contents = orderContentDAO.getByOrderId(invoice.getOrderId());

        // Tạo PDF (gợi ý)
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + invoiceId + ".pdf");

        OutputStream out = response.getOutputStream();
        Document document = new Document() {};
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("HÓA ĐƠN #" + invoiceId));
        document.add(new Paragraph("Ngày xuất: " + invoice.getIssuaDate()));
        document.add(new Paragraph("Tổng tiền: " + invoice.getTotalAmount()));
        document.add(new Paragraph("Thuế: " + invoice.getTaxAmount()));
        document.add(new Paragraph("Hình thức thanh toán: " + invoice.getPaymentMethod()));
        document.add(new Paragraph("Danh sách thú cưng:"));

        for (OrderContent oc : contents) {
            document.add(new Paragraph("- Pet ID: " + oc.getPetId() + ", Giá: " + oc.getPriceAtOrder()));
        }

        document.close();
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "Lỗi khi tạo hóa đơn PDF.");
        request.getRequestDispatcher("invoice.jsp").forward(request, response);
    }
}


    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
