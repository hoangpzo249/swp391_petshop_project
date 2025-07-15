/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.DiscountDAO;
import DAO.PetDAO;
import Models.Account;
import Models.Discount;
import Models.Pet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author AD
 */
public class DiscountCheckServlet extends HttpServlet {

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
            out.println("<title>Servlet DiscountCheckServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DiscountCheckServlet at " + request.getContextPath() + "</h1>");
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

        String action = request.getParameter("action");
        String agreedTerms = request.getParameter("agreedTerms");
        request.setAttribute("agreedTerms", agreedTerms);
        String paymentMethod = request.getParameter("payment-method");
        request.setAttribute("paymentMethod", paymentMethod);

        if ("apply-discount".equals(action)) {

            String[] petIds = request.getParameterValues("selectedPets");
            List<Pet> selectedPetList = new ArrayList<>();
            PetDAO petDAO = new PetDAO();
            double total = 0.0;

            if (petIds != null) {
                for (String idStr : petIds) {
                    try {
                        int id = Integer.parseInt(idStr);
                        Pet pet = petDAO.getPetById(id);
                        if (pet != null) {
                            selectedPetList.add(pet);
                            total += pet.getPetPrice();
                        }
                    } catch (NumberFormatException e) {

                    }
                }
            }

            String code = request.getParameter("discountCode");

            DiscountDAO dao = new DiscountDAO();
            Discount d = dao.getActiveDiscountByCode(code);

            double discountAmount = 0.0;
            boolean isValid = false;
            String message;

            if (d == null || !d.isActive()) {
                message = "Mã giảm giá không tồn tại hoặc đã bị khóa.";
            } else if (!d.isValidNow()) {
                message = "Mã giảm giá đã hết hạn hoặc chưa đến thời gian sử dụng.\n";
                message += "Thời gian áp dụng: từ " + d.getValidFrom() + " đến " + d.getValidTo();
            } else if (total < d.getMinOrderAmount()) {
                message = "Đơn hàng chưa đạt mức tối thiểu để dùng mã này.\n";
                message += "Bạn cần mua từ " + String.format("%,.0f", d.getMinOrderAmount()) + "₫ để áp dụng mã.";

            } else if (!d.isUsageAvailable()) {
                message = "Mã đã hết lượt sử dụng";
            } else {
                if (d.getDiscountType().equalsIgnoreCase("percent")) {
                    discountAmount = total * d.getDiscountValue() / 100.0;
                    if (d.getMaxValue() != 0 && discountAmount > d.getMaxValue()) {
                        discountAmount = d.getMaxValue();
                    }
                } else if (d.getDiscountType().equalsIgnoreCase("fixed")) {
                    discountAmount = d.getDiscountValue();
                    if (d.getMaxValue() != 0 && discountAmount > d.getMaxValue()) {
                        discountAmount = d.getMaxValue();
                    }
                }
                isValid = true;
                message = "Áp dụng mã thành công!";
                request.setAttribute("discountCode", d.getDiscountCode());
                Locale localeVN = new Locale("vi", "VN");
                if ("Percent".equalsIgnoreCase(d.getDiscountType())){
                    message=message +" "+ "Giảm "+d.getDiscountValue()+"%. ";
                    if (d.getMaxValue()!=null){
                         message += " Giảm tối đa " + String.format(localeVN, "%,.0f", d.getMaxValue()) + "đ";
                    }
                }else{
                     message += "Giảm " + String.format(localeVN, "%,.0f", d.getDiscountValue()) + "đ";
                }
            }

            double finalTotal = total - discountAmount;
            request.setAttribute("discountMessage", message);
            request.setAttribute("discountValid", isValid);
            request.setAttribute("discountAmount", discountAmount);
            request.setAttribute("total", total);
            request.setAttribute("finalTotal", finalTotal);
            request.setAttribute("selectedPets", selectedPetList);
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("userAccount");
            if (account != null) {
                request.setAttribute("name", account.getAccFname() + " " + account.getAccLname());
                request.setAttribute("phone", account.getAccPhoneNumber());
                request.setAttribute("address", account.getAccAddress());
                request.setAttribute("email", account.getAccEmail());
            }
            request.setAttribute("guestName", request.getParameter("guestName"));
            request.setAttribute("guestPhone", request.getParameter("guestPhone"));
            request.setAttribute("guestAddress", request.getParameter("guestAddress"));
            request.setAttribute("email", request.getParameter("email"));

        } else if ("checkout".equals(action)) {
            HttpSession session = request.getSession();
            session.setAttribute("guestName", request.getParameter("guestName"));
            session.setAttribute("guestPhone", request.getParameter("guestPhone"));
            session.setAttribute("guestAddress", request.getParameter("guestAddress"));
            session.setAttribute("email", request.getParameter("email"));
            session.setAttribute("paymentMethod", paymentMethod);
            session.setAttribute("selectedPets", request.getParameterValues("selectedPets"));
            session.setAttribute("discountAmount", request.getParameter("discountAmount"));
            session.setAttribute("amount", request.getParameter("amount"));
            session.setAttribute("discountCode", request.getParameter("discountCode"));
            response.sendRedirect("purchase");
            return;

        }

        request.getRequestDispatcher("checkout.jsp").forward(request, response);
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
