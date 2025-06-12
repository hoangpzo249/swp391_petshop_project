/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager.managediscount;

import dal.DiscountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import model.Discount;

/**
 *
 * @author Admin
 */
public class ManageDiscount extends HttpServlet {

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
            out.println("<title>Servlet ManageDiscount</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageDiscount at " + request.getContextPath() + "</h1>");
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
        ArrayList<String> errors = new ArrayList<>();
        String userAcction = request.getParameter("userAcction");
        DiscountDAO discountDao = new DiscountDAO();

        if (userAcction == null || userAcction.isBlank()) {
            redirectToManageDiscountPage(request, response, discountDao);
        } else {
            switch (userAcction) {
                case "add": {
//                    redirect to another servlet
                    request.getRequestDispatcher("AddDiscount").forward(request, response);
                    break;
                }
                case "delete": {
                    String deletedIdString = request.getParameter("deleteId");
                    int deleteId = 0;
                    if (deletedIdString == null || deletedIdString.isBlank()) {
                        errors.add("the id is not indentified");
                    } else {
                        try {
                            deleteId = Integer.parseInt(deletedIdString);
                            if (deleteId < 0) {
                                errors.add("the id must greater than 0");
                            }
                        } catch (Exception e) {
                            errors.add("the delete id must be a number! ");
                        }
                    }
                    if (!errors.isEmpty()) {
                        request.setAttribute("errors", errors);
                        redirectToManageDiscountPage(request, response, discountDao);
                    } else {
                        if (discountDao.deleteDiscountInDataBase(deleteId)) {
                            request.setAttribute("actionStatus", "delete sucessfully");
                        } else {
                            request.setAttribute("actionStatus", "delete failed");
                        }
                        redirectToManageDiscountPage(request, response, discountDao);
                    }
                    break;
                }

                case "updateStatus": {
                    String updateStatusIdString = request.getParameter("updateStatusId");
                    int updateStatusId = 0;

                    String updateStatusString = request.getParameter("updateStatus");
                    boolean updateStatus = false;
                    // validate id 
                    if (updateStatusIdString == null || updateStatusIdString.isBlank()) {
                        errors.add("the id is not indentified");
                    } else {
                        try {
                            updateStatusId = Integer.parseInt(updateStatusIdString);
                            if (updateStatusId < 0) {
                                errors.add("the id must greater than 0");
                            }
                        } catch (Exception e) {
                            errors.add("the delete id must be a number! ");
                        }
                    }

                    // validate status
                    if (updateStatusString == null || updateStatusString.isBlank()) {
                        errors.add("the discount type must be one of Fixed or Percent");
                    } else {
                        if (!(updateStatusString.equalsIgnoreCase("true") || updateStatusString.equalsIgnoreCase("false"))) {
                            errors.add("the new status must be one of active or deactive");
                        } else {
                            updateStatus = (updateStatusString.equalsIgnoreCase("true"));
                        }
                    }

                    if (!errors.isEmpty()) {
                        request.setAttribute("errors", errors);
                        redirectToManageDiscountPage(request, response, discountDao);
                    } else {
                        if (discountDao.UpdateDiscountStatus(updateStatusId, updateStatus)) {
                            request.setAttribute("actionStatus", "delete sucessfully");
                        } else {
                            request.setAttribute("actionStatus", "delete failed");
                        }
                        redirectToManageDiscountPage(request, response, discountDao);
                    }
                    break;
                }

                case "update": {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                    String discountCode = request.getParameter("discountCode");
                    String discountType = request.getParameter("discountType");
                    String discountValueString = request.getParameter("discountValue");
                    float discountValue = 0;
                    String description = request.getParameter("description");
                    String validFromString = request.getParameter("validFrom");
                    String validToString = request.getParameter("validTo");
                    Date validFrom = null;
                    Date validTo = null;
                    String minOrderAmountString = request.getParameter("minOrderAmount");
                    String maxUsageString = request.getParameter("maxUsage");
                    int minOrderAmount = 0;
                    int maxUsage = 0;
                    // this must be convert to boolean
                    String isActiveString = request.getParameter("isActive");
                    boolean isActive = false;
                    break;
                }
            }
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

    protected void redirectToManageDiscountPage(HttpServletRequest request, HttpServletResponse response, DiscountDAO discountDao)
            throws ServletException, IOException {
        ArrayList<Discount> list = discountDao.getAllDiscountFromDatabase();
        request.setAttribute("discountList", list);
        request.getRequestDispatcher("managerpages/managediscount.jsp").forward(request, response);
    }
}
