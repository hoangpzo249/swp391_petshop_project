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
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("userAccount");
        if (account == null || !account.getAccRole().equals("Seller")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }
        BreedDAO _daobreed = new BreedDAO();
        PetDAO _daopet = new PetDAO();

        List<Breed> breedList = _daobreed.getAllBreeds();
        List<String> colorList = _daopet.getAllColors();
        List<String> originList = _daopet.getAllOrigins();

        request.setAttribute("breedList", breedList);
        request.setAttribute("colorList", colorList);
        request.setAttribute("originList", originList);

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
        PetImageDAO _daoimage = new PetImageDAO();
        StringBuilder errMess = new StringBuilder();
        BreedDAO _daobreed = new BreedDAO();
        List<Breed> breedList = _daobreed.getAllBreeds();
        request.setAttribute("breedList", breedList);

        try {
            String petDobStr = request.getParameter("petDob");
            String dateValidation = validatePetDob(petDobStr);

            String petName = request.getParameter("petName").trim();
            String petOrigin = request.getParameter("petOrigin").trim();
            String petGender = request.getParameter("petGender");
            int petAvailability = Integer.parseInt(request.getParameter("petAvailability"));
            String petColor = request.getParameter("petColor").trim();
            int petVaccination = Integer.parseInt(request.getParameter("petVaccination"));
            int petStatus = Integer.parseInt(request.getParameter("petStatus"));
            double petPrice = Double.parseDouble(request.getParameter("petPrice"));
            String petDescription = request.getParameter("petDescription").trim();
            String petVaccineInfo = request.getParameter("petVaccineInfo").trim();
            int breedId = Integer.parseInt(request.getParameter("breedId"));
            int creatorid = ((Account) session.getAttribute("userAccount")).getAccId();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilPetDob = sdf.parse(petDobStr);
            java.sql.Date sqlPetDob = new java.sql.Date(utilPetDob.getTime());

            request.setAttribute("petName", petName);
            request.setAttribute("petOrigin", petOrigin);
            request.setAttribute("petGender", petGender);
            request.setAttribute("petAvailability", petAvailability);
            request.setAttribute("petColor", petColor);
            request.setAttribute("petVaccination", petVaccination);
            request.setAttribute("petStatus", petStatus);
            request.setAttribute("petDescription", petDescription);
            request.setAttribute("petVaccineInfo", petVaccineInfo);
            request.setAttribute("petPrice", new BigDecimal(request.getParameter("petPrice")));
            request.setAttribute("breedId", breedId);
            request.setAttribute("petDob", petDobStr);

            if (petVaccination == 0) {
                petVaccineInfo = "Chưa được tiêm";
            }
            
            String infoValidation = validatePetInput(petName, petColor, petOrigin, petDescription, petVaccineInfo);

            if (infoValidation.length() != 0) {
                errMess.append(infoValidation);
            }

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
                request.getRequestDispatcher("seller_pet_add.jsp")
                        .forward(request, response);
                return;
            }

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
            pet.setPetVaccineInfo(petVaccineInfo);
            pet.setPetPrice(petPrice);
            pet.setBreedId(breedId);
            pet.setCreatedBy(creatorid);

            int newPetId = _daopet.addPet(pet);

            if (newPetId != -1) {
                List<byte[]> imageDatas = new ArrayList<>();
                List<Part> imageParts = request.getParts().stream()
                        .filter(part -> "images".equals(part.getName()) && part.getSize() > 0)
                        .limit(5)
                        .collect(Collectors.toList());

                for (Part part : imageParts) {
                    try (InputStream is = part.getInputStream()) {
                        imageDatas.add(is.readAllBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (imageDatas.isEmpty()) {
                    try {
                        String defaultImagePath = getServletContext().getRealPath("/images/defaultcatdog.png");
                        File defaultImageFile = new File(defaultImagePath);

                        if (defaultImageFile.exists()) {
                            byte[] defaultImageData = Files.readAllBytes(defaultImageFile.toPath());
                            imageDatas.add(defaultImageData);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                boolean imageSuccess = true;
                if (!imageDatas.isEmpty()) {
                    imageSuccess = _daoimage.addImage(newPetId, imageDatas);
                }

                if (imageSuccess) {
                    session.setAttribute("successMess", "Đăng bán thú cưng thành công!");
                    response.sendRedirect("displaypet?id=" + newPetId);
                } else {
                    session.setAttribute("errMess", "Thú cưng đã được tạo nhưng có lỗi khi lưu hình ảnh. Vui lòng thử lại.");
                    response.sendRedirect("updatepet?id=" + newPetId);
                }
            } else {
                session.setAttribute("errMess", "Đã có lỗi xảy ra trong quá trình đăng bán. Vui lòng thử lại.");
                request.getRequestDispatcher("seller_pet_add.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errMess", "Lỗi: Dữ liệu không hợp lệ hoặc đã xảy ra sự cố máy chủ.");
            request.getRequestDispatcher("seller_pet_add.jsp")
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
