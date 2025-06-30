/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import Models.Account;
import Models.Breed;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Lenovo
 */
@MultipartConfig
public class ManagerUpdateBreedServlet extends HttpServlet {

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
            out.println("<title>Servlet ManagerUpdateBreedServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerUpdateBreedServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("userAccount");
        if (account == null || !account.getAccRole().equals("Manager")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }
        String referer = request.getHeader("referer");
        StringBuilder errMess = new StringBuilder();

        String breedIdStr = request.getParameter("id");
        int breedId = 0;

        try {
            breedId = Integer.parseInt(breedIdStr);
        } catch (NumberFormatException e) {
            session.setAttribute("errMess", "ID giống thú cưng không hợp lệ.");
            response.sendRedirect(referer != null ? referer : "displaybreed");
            return;
        }

        Breed breed = _dao.getBreedById(breedId);

        if (breed == null) {
            session.setAttribute("errMess", "Giống thú cưng không tồn tại.");
            response.sendRedirect(referer != null ? referer : "displaybreed");
        }

        List<String> speciesList = _dao.getAllSpecies();

        request.setAttribute("speciesList", speciesList);
        request.setAttribute("breedId", breed.getBreedId());
        request.setAttribute("breedName", breed.getBreedName());
        request.setAttribute("breedSpecies", breed.getBreedSpecies());
        request.setAttribute("breedStatus", breed.getBreedStatus());
        request.setAttribute("breedImage", breed.displayBreedImage());
        request.getRequestDispatcher("manager_breed_edit.jsp")
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
        String referer = request.getHeader("referer");
        StringBuilder errMess = new StringBuilder();

        String breedIdStr = request.getParameter("breedId");
        int breedId = 0;
        String breedName = request.getParameter("breedName").trim().replaceAll("\\s+", " ");
        String breedSpecies = request.getParameter("breedSpecies").trim().replaceAll("\\s+", " ");
        String breedStatus = request.getParameter("breedStatus");

        try {
            breedId = Integer.parseInt(breedIdStr);
        } catch (NumberFormatException e) {
            session.setAttribute("errMess", "ID giống thú cưng không hợp lệ.");
            response.sendRedirect(referer != null ? referer : "displaybreed");
            return;
        }

        Breed breed = _dao.getBreedById(breedId);

        if (breed == null) {
            session.setAttribute("errMess", "Giống thú cưng không tồn tại.");
            response.sendRedirect(referer != null ? referer : "displaybreed");
        }

        List<String> speciesList = _dao.getAllSpecies();
        request.setAttribute("speciesList", speciesList);
        request.setAttribute("breedId", breedId);
        request.setAttribute("breedName", breedName);
        request.setAttribute("breedSpecies", breedSpecies);
        request.setAttribute("breedStatus", breedStatus);
        request.setAttribute("breedImage", breed.displayBreedImage());

        String infoValidation = validateBreedInput(breedName, breedSpecies, breedStatus);

        if (infoValidation.length() != 0) {
            errMess.append(infoValidation);
        }

        if (_dao.breedNameExists(breedName, breedId)) {
            if (errMess.length() > 0) {
                errMess.append("<br>");
            }
            errMess.append(breedName + " đã tồn tại.");
        }

        if (errMess.length() > 0) {
            session.setAttribute("errMess", errMess.toString());
            request.getRequestDispatcher("manager_breed_edit.jsp")
                    .forward(request, response);
            return;
        }

        Breed newBreed = new Breed();
        newBreed.setBreedId(breedId);
        newBreed.setBreedName(breedName);
        newBreed.setBreedSpecies(breedSpecies);
        newBreed.setBreedStatus(Integer.parseInt(breedStatus));
        if (_dao.updateBreedById(newBreed)) {
            session.setAttribute("successMess", "Đã chỉnh sửa giống thú cưng thành công.");
            request.getRequestDispatcher("manager_breed_edit.jsp")
                    .forward(request, response);
        } else {
            session.setAttribute("errMess", "Đã có lỗi xảy ra trong quá trình chỉnh sửa giống thú cưng. Vui lòng thử lại.");
            request.getRequestDispatcher("manager_breed_edit.jsp")
                    .forward(request, response);
        }
    }

    public static String validateBreedInput(String name, String species, String status) {
        StringBuilder stringCheck = new StringBuilder();

        if (name == null || name.isEmpty() || name.length() > 100 || !name.matches("^[\\p{L}\\s\\-']+$")) {
            stringCheck.append("tên, ");
        }
        if (species == null || species.isEmpty() || species.length() > 50 || !species.matches("^[\\p{L}\\s\\-']+$")) {
            stringCheck.append("loài, ");
        }
        if (status == null || status.isEmpty() || !status.equals("1") && !status.equals("0")) {
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
