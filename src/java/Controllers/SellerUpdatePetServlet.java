/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import DAO.PetDAO;
import DAO.PetImageDAO;
import Models.Account;
import Models.Breed;
import Models.Pet;
import Models.PetImage;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("userAccount");
        if (account == null || !account.getAccRole().equals("Seller")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }

        int petId = Integer.parseInt(request.getParameter("id"));

        boolean petInOrder = _daopet.isPetInActiveOrder(petId);

        if (!petInOrder) {
            Pet pet = _daopet.getPetById(petId);
            List<Breed> breedList = _daobreed.getAllBreedsSeller();
            List<PetImage> imageList = _daoimage.getPetImagesById(petId);
            List<String> colorList = _daopet.getAllColors();
            List<String> originList = _daopet.getAllOrigins();

            if (request.getParameter("done") == null && pet.getPetStatus() == 1) {
                _daopet.updatePetStatusById(petId, 0);
                session.setAttribute("successMess", "Thú cưng đã được ẩn cho quá trình chỉnh sửa thông tin.");
            }
            int existingImageCount = pet.getImages().size();
            int maxNewImages = 5 - existingImageCount;
            request.setAttribute("existingImageCount", existingImageCount);
            request.setAttribute("maxNewImages", maxNewImages);
            request.setAttribute("pet", pet);
            request.setAttribute("breedList", breedList);
            request.setAttribute("imageList", imageList);
            request.setAttribute("colorList", colorList);
            request.setAttribute("originList", originList);

            System.out.println("=====Debug: " + pet.getPetPrice());

            request.getRequestDispatcher("seller_pet_edit.jsp")
                    .forward(request, response);
        } else {
            String referer = request.getHeader("referer");
            int pendingOrderId = _daopet.getOrderIdForPet(petId);
            session.setAttribute("errMess", "Không thể chỉnh sửa thú cưng #" + petId + ". Thú cưng ở trong đơn hàng #" + pendingOrderId);

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
        Account account = (Account) session.getAttribute("userAccount");
        if (account == null || !account.getAccRole().equals("Seller")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }
        PetDAO _daopet = new PetDAO();
        BreedDAO _daobreed = new BreedDAO();
        PetImageDAO _daoimage = new PetImageDAO();
        StringBuilder errMess = new StringBuilder();

        try {
            String petDobStr = request.getParameter("petDob");
            String dateValidation = validatePetDob(petDobStr);

            int petId = Integer.parseInt(request.getParameter("petId"));
            String petName = request.getParameter("petName").trim();
            String petOrigin = request.getParameter("petOrigin").trim();
            String petGender = request.getParameter("petGender");
            int petAvailability = Integer.parseInt(request.getParameter("petAvailability"));
            String petColor = request.getParameter("petColor").trim();
            int petVaccination = Integer.parseInt(request.getParameter("petVaccination"));
            int petStatus = Integer.parseInt(request.getParameter("petStatus"));
            String petDescription = request.getParameter("petDescription").trim();
            String petVaccineInfo = request.getParameter("petVaccineInfo").trim();
            if (petVaccination == 0) {
                petVaccineInfo = "Chưa được tiêm";
            }
            String petPriceStr = request.getParameter("petPrice");
            double petPrice = Double.parseDouble(petPriceStr);
            int breedId = Integer.parseInt(request.getParameter("breedId"));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilPetDob = sdf.parse(petDobStr);
            java.sql.Date sqlPetDob = new java.sql.Date(utilPetDob.getTime());

            List<Breed> breedList = _daobreed.getAllBreedsSeller();
            List<PetImage> imageList = _daoimage.getPetImagesById(petId);

            request.setAttribute("breedList", breedList);
            request.setAttribute("imageList", imageList);
            request.setAttribute("done", "done");

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
            petToUpdate.setPetVaccineInfo(petVaccineInfo);
            petToUpdate.setPetPrice(petPrice);
            petToUpdate.setBreedId(breedId);

            request.setAttribute("pet", petToUpdate);

            String infoValidation = validatePetInput(petName, petColor, petOrigin, petDescription, petVaccineInfo);
            if (infoValidation.length() != 0) {
                errMess.append(infoValidation);
            }
            System.out.println("====DEBUG: " + petPrice);
            if (petPrice < 0) {
                if (errMess.length() != 0) {
                    errMess.append("<br>");
                }
                errMess.append("Giá thú cưng không được dưới 0₫");
            }

            if (petPrice > 99999000) {
                if (errMess.length() != 0) {
                    errMess.append("<br>");
                }
                errMess.append("Giá thú cưng không được vươt quá 99.999.000₫");
            }

            if (petPrice > 0 && petPrice % 1000 != 0) {
                if (errMess.length() != 0) {
                    errMess.append("<br>");
                }
                errMess.append("Giá thú cưng phải là một số chẵn nghìn (kết thúc bằng 000).");
            }

            if (dateValidation != null) {
                if (errMess.length() != 0) {
                    errMess.append("<br>");
                }
                errMess.append(dateValidation);
            }
            if (errMess.length() != 0) {
                session.setAttribute("errMess", errMess.toString());
                request.getRequestDispatcher("seller_pet_edit.jsp")
                        .forward(request, response);
                return;
            }

            List<byte[]> imageDatas = new ArrayList<>();
            int availableSlots = 5 - _daoimage.countImagesById(petId);

            if (availableSlots > 0) {
                List<Part> imageParts = new ArrayList<>();
                for (Part part : request.getParts()) {
                    if ("newImages".equals(part.getName()) && part.getSize() > 0) {
                        imageParts.add(part);
                    }
                }

                imageParts.stream()
                        .limit(availableSlots)
                        .forEach(part -> {
                            try (InputStream is = part.getInputStream()) {
                                imageDatas.add(is.readAllBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }

            if (_daopet.updatePetById(petId, petToUpdate)) {
                if (!imageDatas.isEmpty()) {
                    _daoimage.addImage(petId, imageDatas);
                }
                session.setAttribute("successMess", "Cập nhật thông tin thú cưng #" + petId + " thành công!");
                response.sendRedirect("updatepet?id=" + petId + "&done=done");
            } else {
                session.setAttribute("errMess", "Đã có lỗi xảy ra trong quá trình cập nhật. Vui lòng thử lại.");
                request.getRequestDispatcher("seller_pet_edit.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errMess", "Lỗi: Dữ liệu không hợp lệ hoặc đã xảy ra sự cố máy chủ.");
            request.getRequestDispatcher("seller_pet_edit.jsp")
                    .forward(request, response);
        }
    }

    public static String validatePetInput(String name, String color, String origin, String description, String vaccine) {
        StringBuilder stringCheck = new StringBuilder();

        if (name.isEmpty() || name.length() > 100 || !name.matches("^[\\p{L}\\s\\-']+$")) {
            stringCheck.append("tên, ");
        }

        if (color.isEmpty() || color.length() > 50 || !color.matches("^[\\p{L}\\s\\-]+$")) {
            stringCheck.append("màu sắc, ");
        }

        if (origin.isEmpty() || origin.length() > 100 || !origin.matches("^[\\p{L}\\s,.'-]+$")) {
            stringCheck.append("nguồn gốc, ");
        }

        if (description.isEmpty() || description.length() > 2000 || description.contains("\0")) {
            stringCheck.append("mô tả, ");
        }
        if (vaccine.isEmpty() || vaccine.length() > 2000 || vaccine.contains("\0")) {
            stringCheck.append("thông tin vắc-xin, ");
        }

        if (stringCheck.length() > 0) {
            stringCheck.setLength(stringCheck.length() - 2);
            stringCheck.append(" của thú cưng không hợp lệ");

            String result = stringCheck.toString();
            return result.substring(0, 1).toUpperCase() + result.substring(1);
        }

        return "";
    }

    private String validatePetDob(String petDobStr) {
        if (petDobStr == null || petDobStr.trim().isEmpty()) {
            return "Ngày sinh không được để trống.";
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            java.util.Date parsedUtilDate = sdf.parse(petDobStr);

            LocalDate parsedDate = parsedUtilDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate today = LocalDate.now();
            LocalDate fiftyYearsAgo = today.minusYears(50);

            if (parsedDate.isAfter(today)) {
                return "Ngày sinh không được ở tương lai.";
            }

            if (parsedDate.isBefore(fiftyYearsAgo)) {
                return "Ngày sinh không được quá 50 năm.";
            }

        } catch (ParseException e) {
            return "Định dạng ngày sinh không hợp lệ. Vui lòng sử dụng yyyy-MM-dd.";
        }

        return null;
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
