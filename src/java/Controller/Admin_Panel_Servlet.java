/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import DAO.Account_LogDAO;
import Model.Account;
import Model.Account_Log;
import Utils.EmailSender;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author HuyHoang
 */
public class Admin_Panel_Servlet extends HttpServlet {

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
            out.println("<title>Servlet Admin_Panel_Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Admin_Panel_Servlet at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        String act = request.getParameter("act");
        String check = request.getParameter("check");
        String key = request.getParameter("key");

        AccountDAO accDao = new AccountDAO();

        int total = accDao.countAllAcc();
        request.setAttribute("totalAcc", total);

        int totalActive = accDao.countAllAccActive();
        request.setAttribute("totalActive", totalActive);

        int totalInactive = accDao.countAllAccInactive();
        request.setAttribute("totalInactive", totalInactive);

        List<Account> accListNew = accDao.get10AccountNew();
        request.setAttribute("accListNew", accListNew);

        if ("account".equals(action) && type != null && "view".equals(act) && id != null) {
            int accId = Integer.parseInt(id);

            Account acc = accDao.getAccId(accId, type);

            request.setAttribute("accDetail", acc);

            request.getRequestDispatcher("admin_accDetail_page.jsp").forward(request, response);

        } else if ("account".equals(action) && type != null && "reset-pass".equals(act) && id != null) {
            int accId = Integer.parseInt(id);

            Account acc = accDao.getAccId(accId, type);

            request.setAttribute("resetpass", acc);
            System.out.println("ID:" + acc.getAccId());
            request.getRequestDispatcher("admin_accResetPass_Page.jsp").forward(request, response);

        } else if ("account".equals(action) && type != null && "update-role".equals(act) && id != null) {
            int accId = Integer.parseInt(id);

            Account acc = accDao.getAccId(accId, type);

            request.setAttribute("updateRole", acc);

            System.out.println("ID:" + acc.getAccId());
            request.getRequestDispatcher("admin_accRole_page.jsp").forward(request, response);

        } else if ("account".equals(action) && type != null && "ban-acc".equals(act) && id != null && check != null) {
            int accId = Integer.parseInt(id);

            Account acc = accDao.getAccIdStatus(accId, type, check);

            request.setAttribute("banacc", acc);

//            System.out.println("ID:" + acc.getAccId());
            request.getRequestDispatcher("admin_accBan_page.jsp").forward(request, response);

        } else if ("account".equals(action) && "all".equals(type)) {
            List<Account> accList;

            if (key != null && !key.trim().isEmpty()) {
                accList= accDao.searchAcc(key.trim());
                request.setAttribute("key", key);
            } else {
                accList = accDao.getAllAccount();
            }
            request.setAttribute("accList", accList);
            request.getRequestDispatcher("admin_accList_page.jsp").forward(request, response);

        } else if ("create-account".equals(action) && "customer".equals(type)) {
            request.getRequestDispatcher("admin_accCreate_page.jsp").forward(request, response);

        } else if ("create-account".equals(action) && "staff".equals(type)) {
            request.getRequestDispatcher("admin_accCreate_page.jsp").forward(request, response);

        } else if ("change-password".equals(action)) {
            request.getRequestDispatcher("admin_accChangePass_page.jsp").forward(request, response);

        } else {
            request.getRequestDispatcher("admin_account_page.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        String act = request.getParameter("act");
        String check = request.getParameter("check");

        HttpSession session = request.getSession();

        AccountDAO accDao = new AccountDAO();

        if ("account".equals(action) && type != null && "reset-pass".equals(act) && id != null) {

            int accId = Integer.parseInt(id);

            Account acc = accDao.getAccId(accId, type);

//            request.setAttribute("resetpass", acc);
//            System.out.println("ID:" + acc.getAccId());
            String password = request.getParameter("password");
            String confirm_Password = request.getParameter("confirm_Password");
            String adminPass = request.getParameter("adminPassword");

            if (password == null || password.trim().isEmpty()
                    || confirm_Password == null || confirm_Password.trim().isEmpty()
                    || adminPass == null || adminPass.trim().isEmpty()) {
                request.setAttribute("resetpass", acc);
                session.setAttribute("errMess", "Bạn cần điền đủ thông tin");
                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=reset-pass&id=" + acc.getAccId();
                response.sendRedirect(url);
                return;
            }

            if (!password.equals(confirm_Password)) {
                session.setAttribute("errMess", "Mật khẩu không khớp");
                request.setAttribute("resetpass", acc);
                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=reset-pass&id=" + acc.getAccId();
                response.sendRedirect(url);
                return;
            }

            boolean checkPassStrong = isValidPassword(password);
            if (!checkPassStrong) {
                request.setAttribute("resetpass", acc);
                session.setAttribute("errMess", "Mật khẩu phải nhiều hơn 8 kí tự bao gồm chữ thường, chữ hoa, số và kí tự đặc biệt");
                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=reset-pass&id=" + acc.getAccId();
                response.sendRedirect(url);
                return;
            }

            boolean checkPassAdmin = accDao.checkPassAdmin(adminPass);
            if (!checkPassAdmin) {
                request.setAttribute("resetpass", acc);
                session.setAttribute("errMess", "Mật khẩu Admin không hợp lệ");

                session.setAttribute("password", password);
                session.setAttribute("confirm_Password", confirm_Password);

                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=reset-pass&id=" + acc.getAccId();
                response.sendRedirect(url);
                return;
            }

            String email = acc.getAccEmail();
            boolean checkPass = accDao.checkPass(email, password);
            if (!checkPass) {
                String hashPass = BCrypt.hashpw(password, BCrypt.gensalt());

                boolean updateaAccPass = accDao.updatePass(email, hashPass);
                if (updateaAccPass) {
                    request.setAttribute("resetpass", acc);
                    acc.setAccPassword(password);
                    session.setAttribute("successMess", "Mật khẩu cập nhật thành công");
                } else {
                    session.setAttribute("errMess", "Đổi mật khẩu không thành công, bạn cần thử lại");
                }
                session.removeAttribute("errMess");
                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=reset-pass&id=" + acc.getAccId();
                response.sendRedirect(url);

            } else {
                session.setAttribute("errMess", "Mật khẩu không được trùng với mật khẩu cũ");

                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=reset-pass&id=" + acc.getAccId();
                response.sendRedirect(url);
            }

        } else if ("account".equals(action) && type != null && "update-role".equals(act) && id != null) {
            int accId = Integer.parseInt(id);

            Account acc = accDao.getAccId(accId, type);

            String newRole = request.getParameter("newRole");
            String adminPass = request.getParameter("adminPassword");
            String confirmAction = request.getParameter("confirmAction");

            if (newRole == null || newRole.trim().isEmpty()) {
                request.setAttribute("updateRole", acc);

                session.setAttribute("errMess", "Bạn cần chọn vai trò mới");

                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=update-role&id=" + acc.getAccId();
                response.sendRedirect(url);
                return;
            }

            if (adminPass == null || adminPass.trim().isEmpty()) {
                request.setAttribute("updateRole", acc);

                session.setAttribute("errMess", "Bạn cần điền đủ thông tin");

                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=update-role&id=" + acc.getAccId();
                response.sendRedirect(url);
                return;
            }

            boolean checkPassAdmin = accDao.checkPassAdmin(adminPass);
            if (!checkPassAdmin) {
                request.setAttribute("updateRole", acc);
                session.setAttribute("errMess", "Mật khẩu Admin không hợp lệ");

                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=update-role&id=" + acc.getAccId();
                response.sendRedirect(url);
                return;
            }

//            if (!"Saler".equals(acc.getAccRole()) || !"Manager".equals(acc.getAccRole())) {
//                request.setAttribute("updateRole", acc);
//                session.setAttribute("errMess", "Không thể thực hiện");
//
//                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=update-role&id=" + acc.getAccId();
//                response.sendRedirect(url);
//                return;
//            }
            if (confirmAction == null || confirmAction.trim().isEmpty()) {
                request.setAttribute("updateRole", acc);

                session.setAttribute("errMess", "Bạn cần xác nhận đồng ý thay đổi vai trò");

                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=update-role&id=" + acc.getAccId();
                response.sendRedirect(url);
                return;
            }

//            String role = acc.getAccRole();
            String email = acc.getAccEmail();
            String fullname = acc.getAccFname() + acc.getAccLname();
            String sendEmail = request.getParameter("sendEmail");

            boolean updateRole = accDao.updateRole(newRole, accId);
            if (updateRole) {

                acc.setAccRole(newRole);

                if ("on".equals(sendEmail)) {
                    try {
                        request.setAttribute("updateRole", acc);
                        EmailSender.sendUpdateRole(email, fullname);
                        session.setAttribute("successMess", "Cập nhật vai trò thành công và thông báo tới người dùng này");

                    } catch (Exception e) {
                        session.setAttribute("successMess", "Cập nhật vai trò thành công nhưng lỗi Email");
                    }

                } else {
                    request.setAttribute("updateRole", acc);

                    session.setAttribute("successMess", "Cập nhật vai trò thành công");
                }
                session.removeAttribute("errMess");
                String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=update-role&id=" + acc.getAccId();
                response.sendRedirect(url);
            }
        } else if ("account".equals(action) && type != null && "ban-acc".equals(act) && id != null && check != null) {

            int accId = Integer.parseInt(id);
            Account acc = accDao.getAccIdStatus(accId, type, check);
            Account accAdmin = (Account) session.getAttribute("userAccount");

            if ("Active".equals(check)) {
                String banReason = request.getParameter("banReason");
                String sendEmail = request.getParameter("sendEmail");
                String adminPass = request.getParameter("adminPassword");
                String confirmAction = request.getParameter("confirmAction");

                if (banReason == null || banReason.trim().isEmpty() || adminPass == null || adminPass.trim().isEmpty()) {
                    request.setAttribute("banacc", acc);
                    session.setAttribute("errMess", "Bạn cần điền đủ thông tin.");
                    String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=ban-acc&id=" + acc.getAccId() + "&check=" + acc.getAccStatus();
                    response.sendRedirect(url);
                    return;
                }

                boolean checkPassAdmin = accDao.checkPassAdmin(adminPass);
                if (!checkPassAdmin) {
                    request.setAttribute("banacc", acc);
                    session.setAttribute("errMess", "Mật khẩu Admin không khớp");
                    String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=ban-acc&id=" + acc.getAccId() + "&check=" + acc.getAccStatus();
                    response.sendRedirect(url);
                    return;
                }

                if (confirmAction == null || confirmAction.trim().isEmpty()) {
                    request.setAttribute("banacc", acc);
                    session.setAttribute("errMess", "Bạn cần xác nhận khóa tài khoản");
                    String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=ban-acc&id=" + acc.getAccId() + "&check=" + acc.getAccStatus();
                    response.sendRedirect(url);
                    return;
                }
                String email = acc.getAccEmail();
                String fullName = acc.getAccFname() + acc.getAccLname();
                boolean banAcc = accDao.banAcc(accId);
                if (banAcc) {
                    acc.setAccStatus("Inactive");

                    Account_Log log = new Account_Log();

                    log.setAccId(accId);
                    log.setDeactivatedBy(accAdmin.getAccId());
                    log.setDeactivationReason(banReason);
                    Account_LogDAO logDao = new Account_LogDAO();
                    try {
                        logDao.banAcc(log);
                        if ("on".equals(sendEmail)) {
                            try {
                                request.setAttribute("banacc", acc);
                                EmailSender.sendBanAcc(email, fullName, banReason);
                                session.setAttribute("successMess", "Khóa acc thành công và thông báo tới người dùng này");

                            } catch (Exception e) {
                                session.setAttribute("successMess", "Khóa acc thành công nhưng lỗi Email");
                            }

                        } else {
                            request.setAttribute("banacc", acc);

                            session.setAttribute("successMess", "Khóa acc thành công");
                        }
                        session.removeAttribute("errMess");
                        String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=ban-acc&id=" + acc.getAccId() + "&check=" + acc.getAccStatus();
                        response.sendRedirect(url);
                    } catch (Exception e) {
                        session.setAttribute("errMess", "Không lưu được LOG");
                        String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=ban-acc&id=" + acc.getAccId() + "&check=" + acc.getAccStatus();
                        response.sendRedirect(url);
                    }
                }
            } else if ("Inactive".equals(check)) {
                String sendEmail = request.getParameter("sendEmailUnBan");
                String adminPass = request.getParameter("adminPasswordUnBan");
                String confirmAction = request.getParameter("confirmActionUnBan");

                if (adminPass == null || adminPass.trim().isEmpty()) {
                    request.setAttribute("banacc", acc);
                    session.setAttribute("errMess", "Bạn cần điền đủ thông tin.");
                    String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=ban-acc&id=" + acc.getAccId() + "&check=" + acc.getAccStatus();
                    response.sendRedirect(url);
                    return;
                }

                boolean checkPassAdmin = accDao.checkPassAdmin(adminPass);
                if (!checkPassAdmin) {
                    request.setAttribute("banacc", acc);
                    session.setAttribute("errMess", "Mật khẩu Admin không khớp");
                    String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=ban-acc&id=" + acc.getAccId() + "&check=" + acc.getAccStatus();
                    response.sendRedirect(url);
                    return;
                }

                if (confirmAction == null || confirmAction.trim().isEmpty()) {
                    request.setAttribute("banacc", acc);
                    session.setAttribute("errMess", "Bạn cần xác nhận mở khóa tài khoản");
                    String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=ban-acc&id=" + acc.getAccId() + "&check=" + acc.getAccStatus();
                    response.sendRedirect(url);
                    return;
                }

                String email = acc.getAccEmail();
                String fullName = acc.getAccFname() + acc.getAccLname();

                boolean UnbanAcc = accDao.unBanAcc(accId);
                if (UnbanAcc) {
                    acc.setAccStatus("Active");

                    if ("on".equals(sendEmail)) {
                        try {
                            EmailSender.sendUnBanAcc(email, fullName);

                            session.setAttribute("successMess", "Mở khóa acc thành công và thông báo tới người dùng này");

                        } catch (Exception e) {
                            session.setAttribute("successMess", "Mở khoác acc thành công nhưng lỗi Email");
                        }

                    } else {
                        session.setAttribute("successMess", "Mở acc thành công");
                    }
                    session.removeAttribute("errMess");
                    request.setAttribute("banacc", acc);
                    String url = "admin-panel?action=account&type=" + acc.getAccRole() + "&act=ban-acc&id=" + acc.getAccId() + "&check=" + acc.getAccStatus();
                    response.sendRedirect(url);
                }
            }
        } else if ("create-account".equals(action) && type != null) {
            if ("customer".equals(type)) {
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String pass = request.getParameter("password");
                String comfirmPass = request.getParameter("confirmPassword");
                String fName = request.getParameter("firstName");
                String lName = request.getParameter("lastName");
                String phone = request.getParameter("phone");
                String dob = request.getParameter("dob");
                Date date = java.sql.Date.valueOf(dob);
                String address = request.getParameter("address");

                String checkName = "^[a-zA-ZÀ-ỹ\\s]+$";
                if (!fName.matches(checkName)) {

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    session.setAttribute("errMess", "Tên của bạn không được chứa kí tự đặc biệt và số");
                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }
                if (!lName.matches(checkName)) {
                    session.setAttribute("errMess", "Tên của bạn không được chứa kí tự đặc biệt");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkEmail = accDao.isEmailExist(email);
                if (checkEmail) {
                    session.setAttribute("errMess", "Email đã tồn tại, bạn cần nhập email khác.");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                if (!pass.equals(comfirmPass)) {
                    session.setAttribute("errMess", "Mật khẩu không khớp!");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkPass = isValidPassword(pass);
                if (!checkPass) {
                    session.setAttribute("errMess", "Mật khẩu phải nhiều hơn 8 kí tự bao gồm chữ thường, chữ hoa, số và kí tự đặc biệt");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                if (address == null || address.trim().isEmpty()) {
                    session.setAttribute("errMess", "Địa chỉ không được để trống");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkUsername = accDao.isUsernameExist(username);
                if (checkUsername) {
                    session.setAttribute("errMess", "Tên tài khoản đã tồn tại.");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkPhone = isValidPhone(phone);
                if (!checkPhone) {
                    session.setAttribute("errMess", "Số điện thoại không hợp lệ");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt());

                Account acc = new Account();
                acc.setAccUsername(username);
                acc.setAccEmail(email);
                acc.setAccPassword(hashPass);
                acc.setAccFname(fName);
                acc.setAccLname(lName);
                acc.setAccPhoneNumber(phone);
                acc.setAccDob(date);
                acc.setAccAddress(address);
                accDao.registerAccCusByAdmin(acc);
                try {
                    session.setAttribute("successMess", "Tạo tài khoản cho khách hàng thành công");

                    session.removeAttribute("username");
                    session.removeAttribute("email");
                    session.removeAttribute("pass");
                    session.removeAttribute("comfirmPass");
                    session.removeAttribute("fName");
                    session.removeAttribute("lName");
                    session.removeAttribute("phone");
                    session.removeAttribute("dob");

                } catch (Exception e) {
                    session.setAttribute("errMess", "Tạo tài khoản cho khách hàng không thành công");

                    session.removeAttribute("username");
                    session.removeAttribute("email");
                    session.removeAttribute("pass");
                    session.removeAttribute("comfirmPass");
                    session.removeAttribute("fName");
                    session.removeAttribute("lName");
                    session.removeAttribute("phone");
                    session.removeAttribute("dob");

                }
                String url = "admin-panel?action=create-account&type=customer";
                response.sendRedirect(url);

            } else if ("staff".equals(type)) {
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String pass = request.getParameter("password");
                String comfirmPass = request.getParameter("confirmPassword");
                String role = request.getParameter("role");
                String status = request.getParameter("status");

                String fName = request.getParameter("firstName");
                String lName = request.getParameter("lastName");
                String phone = request.getParameter("phone");
                String dob = request.getParameter("dob");
                Date date = java.sql.Date.valueOf(dob);
                String address = request.getParameter("address");

                String checkName = "^[a-zA-ZÀ-ỹ\\s]+$";
                if (!fName.matches(checkName)) {
                    session.setAttribute("errMess", "Tên của bạn không được chứa kí tự đặc biệt và số");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }
                if (!lName.matches(checkName)) {
                    session.setAttribute("errMess", "Tên của bạn không được chứa kí tự đặc biệt");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkEmail = accDao.isEmailExist(email);
                if (checkEmail) {
                    session.setAttribute("errMess", "Email đã tồn tại, bạn cần nhập email khác.");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                if (role == null || role.trim().isEmpty() || status == null || status.trim().isEmpty()) {
                    session.setAttribute("errMess", "Bạn cần chọn đầy đủ thông tin");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                if (!pass.equals(comfirmPass)) {
                    session.setAttribute("errMess", "Mật khẩu không khớp!");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                if (address == null || address.trim().isEmpty()) {
                    session.setAttribute("errMess", "Địa chỉ không được để trống");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkPass = isValidPassword(pass);
                if (!checkPass) {
                    session.setAttribute("errMess", "Mật khẩu phải nhiều hơn 8 kí tự bao gồm chữ thường, chữ hoa, số và kí tự đặc biệt");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkUsername = accDao.isUsernameExist(username);
                if (checkUsername) {
                    session.setAttribute("errMess", "Tên tài khoản đã tồn tại.");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkPhone = isValidPhone(phone);
                if (!checkPhone) {
                    session.setAttribute("errMess", "Số điện thoại không hợp lệ");

                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                    session.setAttribute("comfirmPass", comfirmPass);
                    session.setAttribute("fName", fName);
                    session.setAttribute("lName", lName);
                    session.setAttribute("phone", phone);
                    session.setAttribute("dob", dob);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt());

                Account acc = new Account();
                acc.setAccUsername(username);
                acc.setAccEmail(email);
                acc.setAccPassword(hashPass);
                acc.setAccRole(role);
                acc.setAccStatus(status);

                acc.setAccFname(fName);
                acc.setAccLname(lName);
                acc.setAccPhoneNumber(phone);
                acc.setAccDob(date);
                acc.setAccAddress(address);
                accDao.registerAccStaffByAdmin(acc);
                try {
                    session.setAttribute("successMess", "Tạo tài khoản cho nhân viên thành công");

                    session.removeAttribute("username");
                    session.removeAttribute("email");
                    session.removeAttribute("pass");
                    session.removeAttribute("comfirmPass");
                    session.removeAttribute("fName");
                    session.removeAttribute("lName");
                    session.removeAttribute("phone");
                    session.removeAttribute("dob");

                } catch (Exception e) {

                    session.removeAttribute("username");
                    session.removeAttribute("email");
                    session.removeAttribute("pass");
                    session.removeAttribute("comfirmPass");
                    session.removeAttribute("fName");
                    session.removeAttribute("lName");
                    session.removeAttribute("phone");
                    session.removeAttribute("dob");

                    session.setAttribute("errMess", "Tạo tài khoản cho nhân viên không thành công");
                }
                String url = "admin-panel?action=create-account&type=staff";
                response.sendRedirect(url);
            }
        } else if ("change-password".equals(action)) {
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            boolean checkPass = isValidPassword(newPassword);
            if (!checkPass) {
                session.setAttribute("errMess", "Mật khẩu phải nhiều hơn 8 kí tự bao gồm chữ thường, chữ hoa, số và kí tự đặc biệt");

                String url = "admin-panel?action=change-password";
                response.sendRedirect(url);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                session.setAttribute("errMess", "Mật khẩu không khớp!");

                String url = "admin-panel?action=change-password";
                response.sendRedirect(url);
                return;
            }

            boolean checkPassAdmin = accDao.checkPassAdmin(currentPassword);
            if (!checkPassAdmin) {
                session.setAttribute("errMess", "Mật khẩu của Admin không hợp lệ");

                String url = "admin-panel?action=change-password";
                response.sendRedirect(url);
                return;
            }
            Account accAdmin = (Account) session.getAttribute("userAccount");
            String emailADmin = accAdmin.getAccEmail();

            boolean checkPassExit = accDao.checkPass(emailADmin, newPassword);
            if (!checkPassExit) {
                session.setAttribute("errMess", "Mật khẩu không trùng mật khẩu cũ");

                String url = "admin-panel?action=change-password";
                response.sendRedirect(url);
                return;
            }

            String hashPass = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            boolean updatePass = accDao.updatePass(emailADmin, hashPass);
            if (updatePass) {
                session.setAttribute("successMessRecover", "Thay đổi mật khẩu thành công, bạn cần đăng nhập lại để tiếp tục");
                response.sendRedirect("login");
            } else {
                session.setAttribute("errMess", "Thay đổi mật khẩu không thành công");
                String url = "admin-panel?action=change-password";
                response.sendRedirect(url);
            }
        } else {
            request.getRequestDispatcher("admin_account_page.jsp").forward(request, response);
        }
    }

    public boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        return phone.matches("^(03|05|07|08|09)\\d{8}$");
    }

    public boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isWhitespace(c)) {
                return false;
            }
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if ("!@#$%^&*()_+-=[]{}|;:'\",.<>?/`~".indexOf(c) >= 0) {
                hasSpecial = true;
            }
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
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
