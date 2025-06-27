///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package Controllers;
//
//import DAO.AccountDAO;
//import Models.Account;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.file.Paths;
//import java.sql.Date;
//import java.time.LocalDate;
//import java.time.Period;
//import java.util.Base64;
//import java.util.Random;
//import org.mindrot.jbcrypt.BCrypt;
//
///**
// *
// * @author HuyHoang
// */
//@MultipartConfig
//public class Profile_Account_Servlet extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet Profile_Account_Servlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet Profile_Account_Servlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
////        String action = request.getParameter("action");
////        String taget = request.getParameter("taget");
////        String act = request.getParameter("act");
////        HttpSession session = request.getSession();
////        Account acc = (Account) session.getAttribute("userAccount");
////        if (acc == null) {
////            session.invalidate();
////            response.sendRedirect("login");
////            return;
////        }
////        if ("change-email".equals(action) && "email-otp".equals(act) && "verify-otp".equals(taget)) {
////
////            String email = (String) session.getAttribute("newEmail");
////            if (email != null || !email.trim().isEmpty()) {
////                String otp = otp();
////                try {
////                    EmailSender.sendOTPChangeEmail(email, otp);
////                    session.setAttribute("otp", otp);
////                    request.setAttribute("successMess", "Gửi lại mã OTP thành công");
////                    request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////                } catch (Exception e) {
////                    request.setAttribute("errMess", "Lỗi");
////                    request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////                }
////            } else {
////                request.setAttribute("successMess", "Lỗi");
////                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////            }
////        }
//        request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String action = request.getParameter("action");
////        String act = request.getParameter("act");
////        String taget = request.getParameter("taget");
//        HttpSession session = request.getSession();
//
//        Account acc = (Account) session.getAttribute("userAccount");
//
////        if (acc == null) {
////            session.invalidate();
////            response.sendRedirect("login");
////            return;
////        }
//        if ("update".equals(action)) {
//            String username = request.getParameter("username");
//            String fName = request.getParameter("firstname");
//            String lName = request.getParameter("lastname");
//            String dob = request.getParameter("dob");
//            Date date;
//            String phone = request.getParameter("phone");
//            String address = request.getParameter("address");
//            String description = request.getParameter("description");
//
////            int id = acc.getAccId();
////            System.out.println(id);
//            AccountDAO accDao = new AccountDAO();
//            boolean checkUsernameExist = accDao.isUsernameExist(username);
//            if (checkUsernameExist) {
//                request.setAttribute("errMess", "Tên đăng nhập đã tồn tại");
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                return;
//            }
//
//            if (username == null || username.trim().isEmpty()) {
//                username = acc.getAccUsername();
//            }
//            
//            if (fName == null || fName.trim().isEmpty()) {
//                fName = acc.getAccFname();
//            }
//            if (lName == null || lName.trim().isEmpty()) {
//                lName = acc.getAccLname();
//            }
//
//            String checkUsername = "^[a-z0-9_]{5,30}$";
//            if (!username.matches(checkUsername)) {
//                request.setAttribute("errMess", "Tên tài khoản chỉ cho phép chữ cái thường, số và dấu gạch dưới. Tối thiểu 5 kí tự và tối đa 30 kí tự");
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                return;
//            }
//
//            String checkName = "^[a-zA-ZÀ-Ỵà-ỵĐđ\\s]+$";
//            if (!fName.matches(checkName) || !lName.matches(checkName)) {
//                request.setAttribute("errMess", "Tên của bạn không được chứa kí tự đặc biệt và số");
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                return;
//            }
//
//            if (dob == null || dob.trim().isEmpty()) {
//                date = new java.sql.Date(acc.getAccDob().getTime());
//            } else {
//                try {
//                    LocalDate dateCheck = LocalDate.parse(dob);
//                    LocalDate now = LocalDate.now();
//
//                    if (dateCheck.isAfter(now) || Period.between(dateCheck, now).getYears() < 10 || Period.between(dateCheck, now).getYears() > 90) {
//                        request.setAttribute("errMess", "Ngày sinh không hợp lệ");
//                        request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                        return;
//                    }
//                    date = java.sql.Date.valueOf(dateCheck);
//
//                } catch (Exception e) {
//                    request.setAttribute("errMess", "Ngày sinh sai định dạng.");
//                    request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                    return;
//                }
//            }
//            
//            if (phone == null || phone.trim().isEmpty()) {
//                phone = acc.getAccPhoneNumber();
//            }
//
//            boolean checkPhone = isValidPhone(phone);
//            if (!checkPhone) {
//                request.setAttribute("errMess", "Số điện thoại không hợp lệ");
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                return;
//            }
//
//            if (address == null || address.trim().isEmpty()) {
//                address = acc.getAccAddress();
//            }
//            if (description == null || description.trim().isEmpty()) {
//                description = acc.getAccDescription();
//            }
//            
//            acc.setAccUsername(username);
//            acc.setAccFname(fName);
//            acc.setAccLname(lName);
//            acc.setAccDob(date);
//            acc.setAccPhoneNumber(phone);
//            acc.setAccAddress(address);
//            acc.setAccDescription(description);
//
////            AccountDAO accDao = new AccountDAO();
//            accDao.updateProfile(acc);
//
//            session.setAttribute("userAccount", acc);
//
//            request.setAttribute("updateSucess", "Cập nhật thông tin thành công");
//            request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//            
//        } else if ("change-password".equals(action)) {
//            String password = request.getParameter("password");
//            String comfirm_password = request.getParameter("comfirm_password");
//
//            boolean checkValidPass = isValidPassword(password);
//            if (!checkValidPass) {
//                request.setAttribute("errMess", "Mật khẩu phải nhiều hơn 8 kí tự bao gồm chữ thường, chữ hoa, số, kí tự đặc biệt và không chứa khoảng trắng.");
////                request.setAttribute("firstname", fName);
////                request.setAttribute("lastname", lName);
////                request.setAttribute("email", email);
////                request.setAttribute("password", pass);
//
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                return;
//            }
//
//            if (password == null || password.trim().isEmpty()
//                    || comfirm_password == null || comfirm_password.trim().isEmpty()) {
//                request.setAttribute("errMess", "Bạn cần điền đủ thông tin (không để trống hoặc chỉ chứa khoảng trắng)");
//                request.setAttribute("password", password);
//                request.setAttribute("comfirm_password", comfirm_password);
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                return;
//            }
//
//            if (!password.equals(comfirm_password)) {
//                request.setAttribute("errMess", "Mật khẩu không khớp");
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                return;
//            }
//
//            AccountDAO accDao = new AccountDAO();
//            String email = acc.getAccEmail();
//            boolean checkPass = accDao.checkPass(email, password);
//            if (!checkPass) {
//                String hashPass = BCrypt.hashpw(password, BCrypt.gensalt());
//
//                boolean success = accDao.updatePass(email, hashPass);
//                if (success) {
//
//                    acc.setAccPassword(hashPass);
////                session.setAttribute("userAccount", acc);
//
//                    request.setAttribute("updateSucess", "Đổi mật khẩu thành công");
//                    request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                    System.out.println(acc.getAccPassword());
//                } else {
//                    request.setAttribute("errMess", "Đổi mật khẩu không thành công, bạn hãy thử lại sau");
//                    request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                }
//            } else {
//                request.setAttribute("errMess", "Mật khẩu không được trùng với mật khẩu cũ");
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//            }
//
////        } else if ("change-email".equals(action) && act == null) {
////
////            String newEmail = request.getParameter("newEmail");
////
////            if (newEmail == null || newEmail.trim().isEmpty()) {
////                request.setAttribute("errMess", "Bạn cần điền đủ thông tin Email");
////                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////                return;
////            }
////
////            if (newEmail.equals((acc.getAccEmail()))) {
////                request.setAttribute("errMess", "Email khác với email hiện tại");
////                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////                return;
////            }
////
////            String otp = otp();
////            AccountDAO accDao = new AccountDAO();
////            boolean emailExist = accDao.isEmailExist(newEmail);
////            if (!emailExist) {
////                try {
////                    EmailSender.sendOTPChangeEmail(newEmail, otp);
////                    session.setAttribute("sendOtpSuccess", "true");
////
////                    session.setAttribute("otp", otp);
////                    session.setAttribute("newEmail", newEmail);
////
////                    request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////                } catch (Exception e) {
////                    request.setAttribute("errMess", "Lỗi không gửi được Email");
////                    request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////                }
////            } else {
////                request.setAttribute("errMess", "Email đã tồn tại trong hệ hống, bạn cần nhập Email khác");
////                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////            }
////
////        } else if ("change-email".equals(action) && "email-otp".equals(act)) {
////            String inputotp = request.getParameter("inputotp");
////            String otp = (String) session.getAttribute("otp");
////            String email = (String) session.getAttribute("newEmail");
////
////            if (inputotp == null || inputotp.trim().isEmpty()) {
////                request.setAttribute("errMess", "Bạn cần điền đủ thông tin");
////                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////                return;
////            }
////
////            if (otp == null || email == null) {
////                request.setAttribute("errMess", "Phiên hết hạn, vui lòng thử lại");
////                session.removeAttribute("sendOtpSuccess");
////                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////                return;
////            }
////
////            if (!inputotp.equals(otp)) {
////                request.setAttribute("errMess", "OTP không hợp lệ");
////                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////                return;
////            }
////
////            AccountDAO accDao = new AccountDAO();
////
////            int id = acc.getAccId();
////            boolean success = accDao.updateEmail(email, id);
////            if (success) {
////                acc.setAccEmail(email);
////                session.setAttribute("userAccount", acc);
////
////                session.removeAttribute("otp");
////                session.removeAttribute("email");
////                session.removeAttribute("sendOtpSuccess");
////
////                request.setAttribute("updateSucess", "Đổi tài khoản Email thành công");
////                request.getRequestDispatcher("profile?action=update").forward(request, response);
////            } else {
////                request.setAttribute("errMess", "Đổi tài khoản Email thành công, bạn hãy thử lại sau");
////                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
////            }
////            request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//        } else if ("upload-avatar".equals(action)) {
//            Part filePart = request.getPart("avatar");
//            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//            String contentType = filePart.getContentType();
//
//            if (!contentType.startsWith("image/")) {
//                request.setAttribute("errMess", "Chỉ được phép upload file ảnh (PNG, JPG)");
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                return;
//            }
//
//            long maxFileSize = 1024 * 1024;
//            if (filePart.getSize() > maxFileSize) {
//                request.setAttribute("errMess", "Ảnh tải lên vượt quá giới hạn 1MB");
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//                return;
//            }
//
//            InputStream fileContent = filePart.getInputStream();
//            byte[] imageBytes = fileContent.readAllBytes();
//            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
//
//            URL url = new URL("https://api.imgbb.com/1/upload?expiration=600&key=" + "9d883d96b50c81c2f4987106aa3e8054");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//
//            String data = "image=" + URLEncoder.encode(encodedImage, "UTF-8");
//            try (OutputStream os = conn.getOutputStream()) {
//                os.write(data.getBytes());
//            }
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            StringBuilder result = new StringBuilder();
//            String line;
//            while ((line = in.readLine()) != null) {
//                result.append(line);
//            }
//            in.close();
//
//            String json = result.toString();
//            String imageUrl = json.split("\"url\":\"")[1].split("\"")[0].replace("\\/", "/");
//
//            AccountDAO accDao = new AccountDAO();
//
//            boolean updateAvatar = accDao.uploadAvatar(acc.getAccId(), imageUrl);
//            if (updateAvatar) {
//                acc.setAccImage(imageUrl);
//                session.setAttribute("userAccount", acc);
//
//                request.setAttribute("updateSucess", "Cập nhật Avatar thành công");
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//            } else {
//                request.setAttribute("errMess", "Cập nhật Avatar không thành công");
//                request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//            }
//        } else {
//            request.getRequestDispatcher("profile_account_page.jsp").forward(request, response);
//        }
//
//    }
//
//    public boolean isValidPhone(String phone) {
//        if (phone == null || phone.trim().isEmpty()) {
//            return false;
//        }
//
//        return phone.matches("^(03|05|07|08|09)\\d{8}$");
//    }
//
//    public boolean isValidPassword(String password) {
//        if (password.length() < 8) {
//            return false;
//        }
//
//        boolean hasUpper = false;
//        boolean hasLower = false;
//        boolean hasDigit = false;
//        boolean hasSpecial = false;
//
//        for (char c : password.toCharArray()) {
//            if (Character.isWhitespace(c)) {
//                return false;
//            }
//            if (Character.isUpperCase(c)) {
//                hasUpper = true;
//            } else if (Character.isLowerCase(c)) {
//                hasLower = true;
//            } else if (Character.isDigit(c)) {
//                hasDigit = true;
//            } else if ("!@#$%^&*()_+-=[]{}|;:'\",.<>?/`~".indexOf(c) >= 0) {
//                hasSpecial = true;
//            }
//        }
//        return hasUpper && hasLower && hasDigit && hasSpecial;
//    }
//
//    public String otp() {
//        Random random = new Random();
//        int otp = random.nextInt(1000000);
//        return String.format("%06d", otp);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
