/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import DAO.PetDAO;
import DAO.PetImageDAO;
import Models.Breed;
import Models.Pet;
import Models.PetImage;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
@MultipartConfig
public class SellerUpdatePetServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdatePetServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePetServlet at " + request.getContextPath() + "</h1>");
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
        PetDAO _daopet = new PetDAO();
        BreedDAO _daobreed = new BreedDAO();
        PetImageDAO _daoimage = new PetImageDAO();

        int petId = Integer.parseInt(request.getParameter("id"));

        int pendingOrderId = _daopet.getPendingOrderIdForPet(petId);

        if (pendingOrderId == 0) {
            Pet pet = _daopet.getPetById(petId);
            List<Breed> breedList = _daobreed.getAllBreeds();
            List<PetImage> imageList = _daoimage.getPetImagesById(petId);

            request.setAttribute("pet", pet);
            request.setAttribute("breedList", breedList);
            request.setAttribute("imageList", imageList);

            request.getRequestDispatcher("seller_pet_edit.jsp")
                    .forward(request, response);
        } else {
            HttpSession session = request.getSession(false);
            String referer = request.getHeader("referer");
            session.setAttribute("errMess", "Không thể chỉnh sửa thú cưng #" + petId + ". Thú cưng ở trong đơn hàng chờ xác nhận #" + pendingOrderId);

            if (referer != null) {
                response.sendRedirect(referer);
            } else {
                response.sendRedirect("displayallpet");
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

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        PetDAO _daopet = new PetDAO();

        try {
            int petId = Integer.parseInt(request.getParameter("petId"));
            String petName = request.getParameter("petName");
            String petDobStr = request.getParameter("petDob");
            String petOrigin = request.getParameter("petOrigin");
            String petGender = request.getParameter("petGender");
            int petAvailability = Integer.parseInt(request.getParameter("petAvailability"));
            String petColor = request.getParameter("petColor");
            int petVaccination = Integer.parseInt(request.getParameter("petVaccination"));
            int petStatus = Integer.parseInt(request.getParameter("petStatus"));
            String petDescription = request.getParameter("petDescription");
            double petPrice = Double.parseDouble(request.getParameter("petPrice"));
            int breedId = Integer.parseInt(request.getParameter("breedId"));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilPetDob = sdf.parse(petDobStr);
            java.sql.Date sqlPetDob = new java.sql.Date(utilPetDob.getTime());

            Pet petToUpdate = _daopet.getPetById(petId);
            petToUpdate.setPetId(petId);
            petToUpdate.setPetName(petName);
            petToUpdate.setPetDob(sqlPetDob);
            petToUpdate.setPetOrigin(petOrigin);
            petToUpdate.setPetGender(petGender);
            petToUpdate.setPetAvailability(petAvailability);
            petToUpdate.setPetColor(petColor);
            petToUpdate.setPetVaccination(petVaccination);
            petToUpdate.setPetStatus(petStatus);
            petToUpdate.setPetDescription(petDescription);
            petToUpdate.setPetPrice(petPrice);
            petToUpdate.setBreedId(breedId);

            String[] deleteImageIds = request.getParameterValues("deleteImageIds");

            List<byte[]> newImagesData = new ArrayList<>();
            for (Part part : request.getParts()) {
                if ("newImages".equals(part.getName()) && part.getSize() > 0) {
                    try (InputStream is = part.getInputStream()) {
                        newImagesData.add(is.readAllBytes());
                    }
                }
            }

//            boolean updateSuccess = _daopet.updatePet(petToUpdate);
//            boolean deleteSuccess = _daopet.deleteImages(deleteImageIds);
//            boolean addSuccess = _daopet.addImages(petId, newImagesData);
            if (_daopet.updatePetById(petId, petToUpdate)) {
                session.setAttribute("successMess", "Cập nhật thông tin thú cưng #" + petId + " thành công!");
            } else {
                session.setAttribute("errMess", "Đã có lỗi xảy ra trong quá trình cập nhật. Vui lòng thử lại.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errMess", "Lỗi: Dữ liệu không hợp lệ hoặc đã xảy ra sự cố máy chủ.");
        }

        response.sendRedirect("displayallpet");
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
