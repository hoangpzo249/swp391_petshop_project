/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import DAO.PetDAO;
import DAO.PetImagePathDAO;
import Models.Account;
import Models.Breed;
import Models.Pet;
import Utils.ImgBbUploader;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
@MultipartConfig
public class SellerAddPetServlet extends HttpServlet {

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
            out.println("<title>Servlet AddPetServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddPetServlet at " + request.getContextPath() + "</h1>");
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
        BreedDAO _daobreed = new BreedDAO();

        List<Breed> breedList = _daobreed.getAllBreeds();

        request.setAttribute("breedList", breedList);

        request.getRequestDispatcher("seller_pet_add.jsp")
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

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        PetDAO _daopet = new PetDAO();
        PetImagePathDAO _daoimage = new PetImagePathDAO();

        try {
            List<Part> imageParts = new ArrayList<>();
            for (Part part : request.getParts()) {
                if ("images".equals(part.getName()) && part.getSize() > 0) {
                    imageParts.add(part);
                }
            }

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
            int creatorid = ((Account) session.getAttribute("account")).getAccId();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilPetDob = sdf.parse(petDobStr);
            java.sql.Date sqlPetDob = new java.sql.Date(utilPetDob.getTime());

            Pet pet = new Pet();
            pet.setPetName(petName);
            pet.setPetDob(sqlPetDob);
            pet.setPetOrigin(petOrigin);
            pet.setPetGender(petGender);
            pet.setPetAvailability(petAvailability);
            pet.setPetColor(petColor);
            pet.setPetVaccination(petVaccination);
            pet.setPetStatus(petStatus);
            pet.setPetDescription(petDescription);
            pet.setPetPrice(petPrice);
            pet.setBreedId(breedId);
            pet.setCreatedBy(creatorid);

            int newPetId = _daopet.addPet(pet);

            if (newPetId != -1) {
                List<String> imageURLs = new ArrayList<>();

                imageParts.stream()
                        .limit(5)
                        .forEach(part -> {
                            try {
                                File tempFile = File.createTempFile("upload-", ".tmp");
                                try (InputStream is = part.getInputStream()) {
                                    Files.copy(is, tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                                }
                                String url = ImgBbUploader.uploadImage(tempFile);
                                if (url != null) {
                                    imageURLs.add(url);
                                }
                                tempFile.delete();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                if (imageParts.size() > 5) {
                    session.setAttribute("infoMess", "Chỉ 5 ảnh đầu tiên được tải lên vì đã đạt giới hạn.");
                }

                if (imageURLs.isEmpty()) {
                    imageURLs.add("https://i.ibb.co/NggxZvb7/defaultcatdog.png");
                }

                if (_daoimage.addImage(newPetId, imageURLs)) {
                    session.setAttribute("successMess", "Đăng bán thú cưng thành công!");
                } else {
                    session.setAttribute("errMess", "Thú cưng đã được tạo nhưng có lỗi khi lưu hình ảnh. Vui lòng kiểm tra lại.");
                }
            } else {
                session.setAttribute("errMess", "Đã có lỗi xảy ra trong quá trình đăng bán. Vui lòng thử lại.");
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
