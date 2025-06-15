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
public class AddDiscount extends HttpServlet {

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
            out.println("<title>Servlet AddDiscount</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddDiscount at " + request.getContextPath() + "</h1>");
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
        DiscountDAO discountDao = new DiscountDAO();
        // this part- validation should be place on filter  to validate data. 
        // 1. InternalValidation - this will check the condition of the data been sended from JSP-View 
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

        // 1.1. Check single validation
        // check discountCode
        if (discountCode == null || discountCode.isBlank()) {
            errors.add("Discount code must not be blank");
        } else{
            if(discountCode.length()>50){
                errors.add("tge length of discount code must equal or smaller than 50");
            }
        }
        // check discountType
        if (discountType == null || discountType.isBlank()) {
            errors.add("Discount type must not be blank");
        } else {
            //  this can be affect by jsp value
            if (!(discountType.equalsIgnoreCase("Fixed") || discountType.equalsIgnoreCase("Percent"))) {
                errors.add("the discount type must be one of Fixed or Percent");
            }
        }
        // check discountValue
        if (discountValueString == null || discountValueString.isBlank()) {
            errors.add("Discount value must not be blank/empty");
        } else {
            try {
                discountValue = Float.parseFloat(discountValueString);
                if (discountValue <= 0) {
                    errors.add("the value must greatter than 0");
                }
                if(discountValue%(float)0.01!=0){
                    errors.add("the discount value must devidable for 0,01");
                }
            } catch (Exception e) {
                errors.add("discount value must be a number");
            }
        }
        // check description
        if (description == null || description.isBlank()) {
            errors.add("Description must not be blank");
        }

        // check valid From
        if (validFromString == null || validFromString.isBlank()) {
            errors.add("valid from must not be blank");
        } else {
            try {
                validFrom = dateFormatter.parse(validFromString); // valid from would receive only the day, not daytime
                Date now = new Date();
                Date today = dateFormatter.parse(dateFormatter.format(now));
                // We compare valid form with this day start from 00h00m
                if (validFrom.compareTo(today) < 0) {
                    errors.add("The from Date must equal or after today !");
                }
            } catch (Exception e) {
                errors.add("the valid from date must be the correct format");
            }
        }

        // check valid To   
        if (validToString == null || validToString.isBlank()) {
            errors.add("valid to must not be blank");
        } else {
            try {
                validTo = dateFormatter.parse(validToString);
            } catch (Exception e) {
                errors.add("the valid to date must be the correct format");
                errors.add(validFromString);
            }
        }

//                    check for minOrderAmount
        if (minOrderAmountString == null || minOrderAmountString.isBlank()) {
            errors.add("min order amount must not be blank");
        } else {
            try {
                minOrderAmount = Integer.parseInt(minOrderAmountString);
                if (minOrderAmount < 0) {
                    errors.add("the min order must not below 0");
                }
            } catch (Exception e) {
                errors.add("the min order amoun must be number");
            }
        }

        if (maxUsageString == null || maxUsageString.isBlank()) {
            errors.add("max usage must not be blank");
        } else {
            try {
                maxUsage = Integer.parseInt(minOrderAmountString);
                if (maxUsage <= 0) {
                    errors.add("the min order must greatter than 0");
                }
            } catch (Exception e) {
                errors.add("the min order amoun must be number");
            }
        }

        if (isActiveString == null || isActiveString.isBlank()) {
            errors.add("status must not be blank");
        } else {
            //  this can be affect by jsp value
            if (!(isActiveString.equalsIgnoreCase("true") || isActiveString.equalsIgnoreCase("false"))) {
                errors.add("the discount type must be one of Fixed or Percent");
            } else {
                isActive = (isActiveString.equalsIgnoreCase("true"));
            }
        }

        // 2. Check combination condition
        // 2.1 betweeen
        if (discountType != null && discountType.equals("Percent") && discountValue >= 100) {
            errors.add("The discount Value must smaller than 100 and larger than 0 because type is percent"); //ném thêm vào BR
        }
        //check condition of validFrom and ValidTo
        if (validFrom != null && validTo != null && (!validTo.after(validFrom))) {
            errors.add("valid To must after validFrom");
        }

        //if the request have problem we return the old data and error code
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("managerpages/adddiscount.jsp").forward(request, response);
        } else {
//            this else part should be the part of service
            if (discountDao.isExistDiscountCode(discountCode.trim())) {
                errors.add("Discount code đã tồn tại trong databse");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("managerpages/adddiscount.jsp").forward(request, response);
            } else {
                Discount added = new Discount();
                added.setDiscountCode(discountCode.trim());
                added.setDiscountType(discountType.trim());
                added.setDisCountValue(discountValue);
                added.setDescription(description.trim());
                added.setValidFrom(validFrom);
                added.setValidTo(validTo);
                added.setMinOrderAmount(minOrderAmount);
                added.setMaxUsage(maxUsage);
                added.setIsActive(isActive);
                if (discountDao.createDiscountToDatabase(added)) {
                    request.setAttribute("addStatus", "added successfull");
                } else {
                    request.setAttribute("addStatus", "added failed");
                }
                request.getRequestDispatcher("managerpages/adddiscount.jsp").forward(request, response);
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

}
