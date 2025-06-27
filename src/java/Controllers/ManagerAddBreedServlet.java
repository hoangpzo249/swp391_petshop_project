/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import Models.Breed;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Lenovo
 */
@MultipartConfig
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
        HttpSession session = request.getSession();
        StringBuilder errMess = new StringBuilder();

        String breedName = request.getParameter("breedName");
        String breedSpecies = request.getParameter("breedSpecies");
        String breedStatus = request.getParameter("breedStatus");

        List<String> speciesList = _dao.getAllSpecies();
        request.setAttribute("speciesList", speciesList);
        request.setAttribute("breedName", breedName);
        request.setAttribute("breedSpecies", breedSpecies);
        request.setAttribute("breedStatus", breedStatus);

        String infoValidation = validateBreedInput(breedName, breedSpecies, breedStatus);

        if (infoValidation.length() != 0) {
            errMess.append(infoValidation);
        }

        if (_dao.breedNameExists(breedName)) {
            if (errMess.length() > 0) {
                errMess.append("<br>");
            }
            errMess.append(breedName + " đã tồn tại.");
        }

        if (errMess.length() > 0) {
            session.setAttribute("errMess", errMess.toString());
            request.getRequestDispatcher("manager_breed_add.jsp")
                    .forward(request, response);
            return;
        }

        Part image = null;
        byte[] imageData;
        for (Part part : request.getParts()) {
            if ("breedImage".equals(part.getName()) && part.getSize() > 0) {
                image = part;
            }
        }
        if (image != null) {
            try (InputStream is = image.getInputStream()) {
                imageData = is.readAllBytes();
            }
        } else {
            String defaultImagePath = getServletContext().getRealPath("/images/defaultcatdog.png");
            try (InputStream is = new FileInputStream(defaultImagePath)) {
                imageData = is.readAllBytes();
            }
        }

        Breed newBreed = new Breed();
        newBreed.setBreedName(breedName);
        newBreed.setBreedSpecies(breedSpecies);
        newBreed.setBreedStatus(Integer.parseInt(breedStatus));
        newBreed.setBreedImage(imageData);
        if (_dao.addBreed(newBreed)) {
            session.setAttribute("successMess", "Đã tạo giống thú cưng thành công.");
            response.sendRedirect("displaybreed");
        } else {
            session.setAttribute("errMess", "Đã có lỗi xảy ra trong quá trình tạo giống thú cưng. Vui lòng thử lại.");
            request.getRequestDispatcher("manager_breed_add.jsp")
                    .forward(request, response);
        }
    }

    public static String validateBreedInput(String name, String species, String status) {
        StringBuilder stringCheck = new StringBuilder();

        if (name==null || name.isEmpty() || name.length() > 100 || !name.matches("^[\\p{L}\\s\\-']+$")) {
            stringCheck.append("tên, ");
        }
        if (species==null || species.isEmpty() || species.length() > 50 || !species.matches("^[\\p{L}\\s\\-']+$")) {
            stringCheck.append("loài, ");
        }
        if (status==null || status.isEmpty() || !status.equals("1") && !status.equals("0")) {
            stringCheck.append("trạng thái, ");
        }

        if (stringCheck.length() > 0) {
            stringCheck.setLength(stringCheck.length() - 2);
            stringCheck.append(" của giống thú cưng không hợp lệ.");

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
