/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ManagerAddBreedServlet extends HttpServlet {

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
            out.println("<title>Servlet ManagerAddBreedServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerAddBreedServlet at " + request.getContextPath() + "</h1>");
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
        BreedDAO _dao = new BreedDAO();
        List<String> speciesList = _dao.getAllSpecies();
        request.setAttribute("speciesList", speciesList);
        request.getRequestDispatcher("manager_breed_add.jsp")
                .forward(request, response);
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
        BreedDAO _dao = new BreedDAO();
        String breedName = request.getParameter("breedName");
        String breedSpecies=request.getParameter("breedSpecies");
        String breedStatus=request.getParameter("breedStatus");
        
        
        if (breedName != null && !_dao.breedNameExists(breedName)) {
            List<String> speciesList = _dao.getAllSpecies();
            request.setAttribute("speciesList", speciesList);
            
        }
    }
    
    public static String validateBreedInput(String name, String species, String status) {
        StringBuilder stringCheck = new StringBuilder();

        if (name.isEmpty() || name.length() > 100 || !name.matches("^[\\p{L}\\s\\-']+$")) {
            stringCheck.append("tên, ");
        }

//        if (color.isEmpty() || color.length() > 50 || !color.matches("^[\\p{L}\\s\\-]+$")) {
//            stringCheck.append("màu sắc, ");
//        }
//
//        if (origin.isEmpty() || origin.length() > 100 || !origin.matches("^[\\p{L}\\s,.'-]+$")) {
//            stringCheck.append("nguồn gốc, ");
//        }
//
//        if (description.isEmpty() || description.length() > 2000 || description.contains("\0")) {
//            stringCheck.append("mô tả, ");
//        }

        if (stringCheck.length() > 0) {
            stringCheck.setLength(stringCheck.length() - 2);
            stringCheck.append(" của giống thú cưng không hợp lệ");

            String result = stringCheck.toString();
            return result.substring(0, 1).toUpperCase() + result.substring(1);
        }

        return "";
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
