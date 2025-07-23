/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.AccountDAO;
import DAO.Account_LogDAO;
import Models.Account;
import Models.Account_Log;
import Utils.EmailSender;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.Period;
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
        String role = request.getParameter("role");
        String status = request.getParameter("status");

        AccountDAO accDao = new AccountDAO();

        HttpSession session = request.getSession();
        Account accAdmin = (Account) session.getAttribute("userAccount");
        if (accAdmin == null || !accAdmin.getAccRole().equals("Admin")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }

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

        } else if ("account".equals(action) && key != null) {
            List<Account> accList = accDao.searchAcc(key);

            request.setAttribute("key", key);
            request.setAttribute("accList", accList);
            request.getRequestDispatcher("admin_accList_page.jsp").forward(request, response);

        } else if ("account".equals(action) && role != null) {
            List<Account> accList;
            if (role.trim().isEmpty()) {
                accList = accDao.getAllAccount();
            } else {
                accList = accDao.filterRoleAcc(role);
            }

//            request.setAttribute("key", key);
            request.setAttribute("accList", accList);
            request.getRequestDispatcher("admin_accList_page.jsp").forward(request, response);

        } else if ("account".equals(action) && status != null) {
            List<Account> accList;
            if (status.trim().isEmpty()) {
                accList = accDao.getAllAccount();
            } else {
                accList = accDao.filterStatusAcc(status);
            }
//            request.setAttribute("key", key);
            request.setAttribute("accList", accList);
            request.getRequestDispatcher("admin_accList_page.jsp").forward(request, response);

        } else if ("account".equals(action) && type != null) {
            List<Account> accList = accDao.getAllAccount();

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
                    try {
                        EmailSender.sendResetPassByAdmin(email, acc.getAccFname() + " " + acc.getAccLname(), email, password);
                        request.setAttribute("resetpass", acc);
                        acc.setAccPassword(password);
                        session.setAttribute("successMess", "Mật khẩu cập nhật thành công và thông báo tới người dùng");
                    } catch (Exception e) {
                        request.setAttribute("resetpass", acc);
                        acc.setAccPassword(password);
                        session.setAttribute("successMess", "Mật khẩu cập nhật thành công");
                    }
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

//            if (!"Seller".equals(acc.getAccRole()) || !"Manager".equals(acc.getAccRole())) {
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

            String oldRole = acc.getAccRole();
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

                        EmailSender.sendUpdateRole(email, fullname, oldRole, newRole);

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
                String usernameCus = request.getParameter("usernameCus");
                String emailCus = request.getParameter("emailCus");
                String passCus = request.getParameter("passCus");
                String comfirmPassCus = request.getParameter("comfirmPassCus");
                String fNameCus = request.getParameter("fNameCus");
                String lNameCus = request.getParameter("lNameCus");
                String phoneCus = request.getParameter("phoneCus");
                String dobCus = request.getParameter("dobCus");

                String addressCus = request.getParameter("addressCus");

                if (usernameCus == null || usernameCus.trim().isEmpty()
                        || emailCus == null || emailCus.trim().isEmpty()
                        || passCus == null || passCus.trim().isEmpty()
                        || comfirmPassCus == null || comfirmPassCus.trim().isEmpty()
                        || fNameCus == null || fNameCus.trim().isEmpty()
                        || lNameCus == null || lNameCus.trim().isEmpty()
                        || phoneCus == null || phoneCus.trim().isEmpty()
                        || dobCus == null || dobCus.trim().isEmpty()) {
                    session.setAttribute("errMess", "Bạn cần điền đủ thông tin (không để trống hoặc chỉ chứa khoảng trắng)");

                    session.setAttribute("usernameCus", usernameCus);
                    session.setAttribute("emailCus", emailCus);
                    session.setAttribute("passCus", passCus);
                    session.setAttribute("comfirmPassCus", comfirmPassCus);
                    session.setAttribute("fNameCus", fNameCus);
                    session.setAttribute("lNameCus", lNameCus);
                    session.setAttribute("phoneCus", phoneCus);
                    session.setAttribute("dobCus", dobCus);
                    session.setAttribute("addressCus", addressCus);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkUsernameCusExist = accDao.isUsernameExist(usernameCus);
                if (checkUsernameCusExist) {
                    session.setAttribute("errMess", "Tên tài khoản của bạn đã tồn tại.");

                    session.setAttribute("usernameCus", usernameCus);
                    session.setAttribute("emailCus", emailCus);
                    session.setAttribute("passCus", passCus);
                    session.setAttribute("comfirmPassCus", comfirmPassCus);
                    session.setAttribute("fNameCus", fNameCus);
                    session.setAttribute("lNameCus", lNameCus);
                    session.setAttribute("phoneCus", phoneCus);
                    session.setAttribute("dobCus", dobCus);
                    session.setAttribute("addressCus", addressCus);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                String checkUsernameCus = "^[a-z0-9_]{5,30}$";
                if (!usernameCus.matches(checkUsernameCus)) {
                    session.setAttribute("errMess", "Tên tài khoản chỉ cho phép chữ cái thường, số và dấu gạch dưới. Tối thiểu 5 kí tự và tối đa 30 kí tự");

                    session.setAttribute("usernameCus", usernameCus);
                    session.setAttribute("emailCus", emailCus);
                    session.setAttribute("passCus", passCus);
                    session.setAttribute("comfirmPassCus", comfirmPassCus);
                    session.setAttribute("fNameCus", fNameCus);
                    session.setAttribute("lNameCus", lNameCus);
                    session.setAttribute("phoneCus", phoneCus);
                    session.setAttribute("dobCus", dobCus);
                    session.setAttribute("addressCus", addressCus);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkEmailCus = accDao.isEmailExist(emailCus);
                if (checkEmailCus) {
                    session.setAttribute("errMess", "Email đã tồn tại, bạn cần nhập email khác.");

                    session.setAttribute("usernameCus", usernameCus);
                    session.setAttribute("emailCus", emailCus);
                    session.setAttribute("passCus", passCus);
                    session.setAttribute("comfirmPassCus", comfirmPassCus);
                    session.setAttribute("fNameCus", fNameCus);
                    session.setAttribute("lNameCus", lNameCus);
                    session.setAttribute("phoneCus", phoneCus);
                    session.setAttribute("dobCus", dobCus);
                    session.setAttribute("addressCus", addressCus);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkPassCus = isValidPassword(passCus);
                if (!checkPassCus) {
                    session.setAttribute("errMess", "Mật khẩu phải nhiều hơn 8 kí tự bao gồm chữ thường, chữ hoa, số và kí tự đặc biệt, không chứa khoảng trắng.");

                    session.setAttribute("usernameCus", usernameCus);
                    session.setAttribute("emailCus", emailCus);
                    session.setAttribute("passCus", passCus);
                    session.setAttribute("comfirmPassCus", comfirmPassCus);
                    session.setAttribute("fNameCus", fNameCus);
                    session.setAttribute("lNameCus", lNameCus);
                    session.setAttribute("phoneCus", phoneCus);
                    session.setAttribute("dobCus", dobCus);
                    session.setAttribute("addressCus", addressCus);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                if (!passCus.equals(comfirmPassCus)) {
                    session.setAttribute("errMess", "Mật khẩu không khớp!");

                    session.setAttribute("usernameCus", usernameCus);
                    session.setAttribute("emailCus", emailCus);
                    session.setAttribute("passCus", passCus);
                    session.setAttribute("comfirmPassCus", comfirmPassCus);
                    session.setAttribute("fNameCus", fNameCus);
                    session.setAttribute("lNameCus", lNameCus);
                    session.setAttribute("phoneCus", phoneCus);
                    session.setAttribute("dobCus", dobCus);
                    session.setAttribute("addressCus", addressCus);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                String checkName = "^[a-zA-ZÀ-Ỵà-ỵĐđ\\s]+$";
                if (!fNameCus.matches(checkName) || !lNameCus.matches(checkName)) {

                    session.setAttribute("usernameCus", usernameCus);
                    session.setAttribute("emailCus", emailCus);
                    session.setAttribute("passCus", passCus);
                    session.setAttribute("comfirmPassCus", comfirmPassCus);
                    session.setAttribute("fNameCus", fNameCus);
                    session.setAttribute("lNameCus", lNameCus);
                    session.setAttribute("phoneCus", phoneCus);
                    session.setAttribute("dobCus", dobCus);
                    session.setAttribute("addressCus", addressCus);

                    session.setAttribute("errMess", "Họ và Tên của bạn không được chứa kí tự đặc biệt và số");
                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                String fNameCheck = fNameCus.trim().replaceAll("\\s+", " ");
                String lNameCheck = lNameCus.trim().replaceAll("\\s+", " ");

                if (!fNameCus.matches(fNameCheck) || !lNameCus.matches(lNameCheck)) {
                    session.setAttribute("errMess", "Họ và Tên không được chứa nhiều khoảng trắng liên tiếp");

                    session.setAttribute("usernameCus", usernameCus);
                    session.setAttribute("emailCus", emailCus);
                    session.setAttribute("passCus", passCus);
                    session.setAttribute("comfirmPassCus", comfirmPassCus);
                    session.setAttribute("fNameCus", fNameCus);
                    session.setAttribute("lNameCus", lNameCus);
                    session.setAttribute("phoneCus", phoneCus);
                    session.setAttribute("dobCus", dobCus);
                    session.setAttribute("addressCus", addressCus);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkPhoneCus = isValidPhone(phoneCus);
                if (!checkPhoneCus) {
                    session.setAttribute("errMess", "Số điện thoại không hợp lệ");

                    session.setAttribute("usernameCus", usernameCus);
                    session.setAttribute("emailCus", emailCus);
                    session.setAttribute("passCus", passCus);
                    session.setAttribute("comfirmPassCus", comfirmPassCus);
                    session.setAttribute("fNameCus", fNameCus);
                    session.setAttribute("lNameCus", lNameCus);
                    session.setAttribute("phoneCus", phoneCus);
                    session.setAttribute("dobCus", dobCus);
                    session.setAttribute("addressCus", addressCus);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                String addressCheck = addressCus.trim().replaceAll("\\s+", " ");

                if (!addressCus.matches(addressCheck)) {
                    session.setAttribute("errMess", "Địa chỉ không được chứa nhiều khoảng trắng liên tiếp");
                    session.setAttribute("usernameCus", usernameCus);
                    session.setAttribute("emailCus", emailCus);
                    session.setAttribute("passCus", passCus);
                    session.setAttribute("comfirmPassCus", comfirmPassCus);
                    session.setAttribute("fNameCus", fNameCus);
                    session.setAttribute("lNameCus", lNameCus);
                    session.setAttribute("phoneCus", phoneCus);
                    session.setAttribute("dobCus", dobCus);
                    session.setAttribute("addressCus", addressCus);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                Date dateCus = null;
                try {
                    if (dobCus != null && !dobCus.trim().isEmpty()) {
                        LocalDate dateCheck = LocalDate.parse(dobCus);
                        LocalDate now = LocalDate.now();

                        if (dateCheck.isAfter(now) || Period.between(dateCheck, now).getYears() < 10 || Period.between(dateCheck, now).getYears() > 90) {
                            session.setAttribute("errMess", "Ngày sinh không hợp lệ.");

                            session.setAttribute("usernameCus", usernameCus);
                            session.setAttribute("emailCus", emailCus);
                            session.setAttribute("passCus", passCus);
                            session.setAttribute("comfirmPassCus", comfirmPassCus);
                            session.setAttribute("fNameCus", fNameCus);
                            session.setAttribute("lNameCus", lNameCus);
                            session.setAttribute("phoneCus", phoneCus);
                            session.setAttribute("dobCus", dobCus);
                            session.setAttribute("addressCus", addressCus);

                            String url = "admin-panel?action=create-account&type=customer";
                            response.sendRedirect(url);
                            return;
                        }

                        dateCus = java.sql.Date.valueOf(dateCheck);
                    } else {
                        session.setAttribute("errMess", "Vui lòng nhập ngày sinh.");
                        session.setAttribute("usernameCus", usernameCus);
                        session.setAttribute("emailCus", emailCus);
                        session.setAttribute("passCus", passCus);
                        session.setAttribute("comfirmPassCus", comfirmPassCus);
                        session.setAttribute("fNameCus", fNameCus);
                        session.setAttribute("lNameCus", lNameCus);
                        session.setAttribute("phoneCus", phoneCus);
                        session.setAttribute("dobCus", dobCus);
                        session.setAttribute("addressCus", addressCus);

                        String url = "admin-panel?action=create-account&type=customer";
                        response.sendRedirect(url);
                        return;
                    }
                } catch (Exception e) {
                    session.setAttribute("errMess", "Định dạng ngày sinh không hợp lệ.");

                    session.setAttribute("usernameCus", usernameCus);
                    session.setAttribute("emailCus", emailCus);
                    session.setAttribute("passCus", passCus);
                    session.setAttribute("comfirmPassCus", comfirmPassCus);
                    session.setAttribute("fNameCus", fNameCus);
                    session.setAttribute("lNameCus", lNameCus);
                    session.setAttribute("phoneCus", phoneCus);
                    session.setAttribute("dobCus", dobCus);
                    session.setAttribute("addressCus", addressCus);

                    String url = "admin-panel?action=create-account&type=customer";
                    response.sendRedirect(url);
                    return;
                }

                String hashPassCus = BCrypt.hashpw(passCus, BCrypt.gensalt());

                Account acc = new Account();
                acc.setAccUsername(usernameCus);
                acc.setAccEmail(emailCus);
                acc.setAccPassword(hashPassCus);
                acc.setAccFname(fNameCus);
                acc.setAccLname(lNameCus);
                acc.setAccPhoneNumber(phoneCus);
                acc.setAccDob(dateCus);
                acc.setAccAddress(addressCus);

                accDao.registerAccCusByAdmin(acc);
                try {
                    EmailSender.createAccCusSuccess(emailCus, fNameCus + " " + lNameCus, emailCus, passCus);
                    session.setAttribute("successMess", "Tạo tài khoản cho khách hàng thành công");

                    session.removeAttribute("usernameCus");
                    session.removeAttribute("emailCus");
                    session.removeAttribute("passCus");
                    session.removeAttribute("comfirmPassCus");
                    session.removeAttribute("fNameCus");
                    session.removeAttribute("lNameCus");
                    session.removeAttribute("phoneCus");
                    session.removeAttribute("dobCus");
                    session.removeAttribute("addressCus");

                    session.removeAttribute("errMess");

                } catch (Exception e) {
                    session.setAttribute("errMess", "Tạo tài khoản cho khách hàng không thành công");

                    session.removeAttribute("usernameCus");
                    session.removeAttribute("emailCus");
                    session.removeAttribute("passCus");
                    session.removeAttribute("comfirmPassCus");
                    session.removeAttribute("fNameCus");
                    session.removeAttribute("lNameCus");
                    session.removeAttribute("phoneCus");
                    session.removeAttribute("dobCus");
                    session.removeAttribute("addressCus");

                }
                String url = "admin-panel?action=create-account&type=customer";
                response.sendRedirect(url);

            } else if ("staff".equals(type)) {
                String usernameStaff = request.getParameter("usernameStaff");
                String emailStaff = request.getParameter("emailStaff");
                String passStaff = request.getParameter("passStaff");
                String comfirmPassStaff = request.getParameter("comfirmPassStaff");
                String roleStaff = request.getParameter("roleStaff");
                String statusStaff = request.getParameter("statusStaff");

                String fNameStaff = request.getParameter("fNameStaff");
                String lNameStaff = request.getParameter("lNameStaff");
                String phoneStaff = request.getParameter("phoneStaff");
                String dobStaff = request.getParameter("dobStaff");

                String addressStaff = request.getParameter("addressStaff");

                if (usernameStaff == null || usernameStaff.trim().isEmpty()
                        || emailStaff == null || emailStaff.trim().isEmpty()
                        || passStaff == null || passStaff.trim().isEmpty()
                        || comfirmPassStaff == null || comfirmPassStaff.trim().isEmpty()
                        || roleStaff == null || roleStaff.trim().isEmpty()
                        || statusStaff == null || statusStaff.trim().isEmpty()
                        || fNameStaff == null || fNameStaff.trim().isEmpty()
                        || lNameStaff == null || lNameStaff.trim().isEmpty()
                        || phoneStaff == null || phoneStaff.trim().isEmpty()
                        || dobStaff == null || dobStaff.trim().isEmpty()) {
                    session.setAttribute("errMess", "Bạn cần điền đủ thông tin (không để trống hoặc chỉ chứa khoảng trắng)");

                    session.setAttribute("usernameStaff", usernameStaff);
                    session.setAttribute("emailStaff", emailStaff);
                    session.setAttribute("passStaff", passStaff);
                    session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                    session.setAttribute("fNameStaff", fNameStaff);
                    session.setAttribute("lNameStaff", lNameStaff);
                    session.setAttribute("phoneStaff", phoneStaff);
                    session.setAttribute("dobStaff", dobStaff);
                    session.setAttribute("addressStaff", addressStaff);
                    session.setAttribute("roleStaff", roleStaff);
                    session.setAttribute("statusStaff", statusStaff);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkUsernameStaffExist = accDao.isUsernameExist(usernameStaff);
                if (checkUsernameStaffExist) {
                    session.setAttribute("errMess", "Tên tài khoản đã tồn tại.");

                    session.setAttribute("usernameStaff", usernameStaff);
                    session.setAttribute("emailStaff", emailStaff);
                    session.setAttribute("passStaff", passStaff);
                    session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                    session.setAttribute("fNameStaff", fNameStaff);
                    session.setAttribute("lNameStaff", lNameStaff);
                    session.setAttribute("phoneStaff", phoneStaff);
                    session.setAttribute("dobStaff", dobStaff);
                    session.setAttribute("addressStaff", addressStaff);
                    session.setAttribute("roleStaff", roleStaff);
                    session.setAttribute("statusStaff", statusStaff);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                String checkUsernameStaff = "^[a-z0-9_]{5,30}$";
                if (!usernameStaff.matches(checkUsernameStaff)) {
                    session.setAttribute("errMess", "Tên tài khoản chỉ cho phép chữ cái thường, số và dấu gạch dưới. Tối thiểu 5 kí tự và tối đa 30 kí tự");

                    session.setAttribute("usernameStaff", usernameStaff);
                    session.setAttribute("emailStaff", emailStaff);
                    session.setAttribute("passStaff", passStaff);
                    session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                    session.setAttribute("fNameStaff", fNameStaff);
                    session.setAttribute("lNameStaff", lNameStaff);
                    session.setAttribute("phoneStaff", phoneStaff);
                    session.setAttribute("dobStaff", dobStaff);
                    session.setAttribute("addressStaff", addressStaff);
                    session.setAttribute("roleStaff", roleStaff);
                    session.setAttribute("statusStaff", statusStaff);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkEmailStaff = accDao.isEmailExist(emailStaff);
                if (checkEmailStaff) {
                    session.setAttribute("errMess", "Email đã tồn tại, bạn cần nhập email khác.");

                    session.setAttribute("usernameStaff", usernameStaff);
                    session.setAttribute("emailStaff", emailStaff);
                    session.setAttribute("passStaff", passStaff);
                    session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                    session.setAttribute("fNameStaff", fNameStaff);
                    session.setAttribute("lNameStaff", lNameStaff);
                    session.setAttribute("phoneStaff", phoneStaff);
                    session.setAttribute("dobStaff", dobStaff);
                    session.setAttribute("addressStaff", addressStaff);
                    session.setAttribute("roleStaff", roleStaff);
                    session.setAttribute("statusStaff", statusStaff);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkPassStaff = isValidPassword(passStaff);
                if (!checkPassStaff) {
                    session.setAttribute("errMess", "Mật khẩu phải nhiều hơn 8 kí tự bao gồm chữ thường, chữ hoa, số và kí tự đặc biệt, không chứa khoảng trắng");

                    session.setAttribute("usernameStaff", usernameStaff);
                    session.setAttribute("emailStaff", emailStaff);
                    session.setAttribute("passStaff", passStaff);
                    session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                    session.setAttribute("fNameStaff", fNameStaff);
                    session.setAttribute("lNameStaff", lNameStaff);
                    session.setAttribute("phoneStaff", phoneStaff);
                    session.setAttribute("dobStaff", dobStaff);
                    session.setAttribute("addressStaff", addressStaff);
                    session.setAttribute("roleStaff", roleStaff);
                    session.setAttribute("statusStaff", statusStaff);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                if (!passStaff.equals(comfirmPassStaff)) {
                    session.setAttribute("errMess", "Mật khẩu không khớp!");

                    session.setAttribute("usernameStaff", usernameStaff);
                    session.setAttribute("emailStaff", emailStaff);
                    session.setAttribute("passStaff", passStaff);
                    session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                    session.setAttribute("fNameStaff", fNameStaff);
                    session.setAttribute("lNameStaff", lNameStaff);
                    session.setAttribute("phoneStaff", phoneStaff);
                    session.setAttribute("dobStaff", dobStaff);
                    session.setAttribute("addressStaff", addressStaff);
                    session.setAttribute("roleStaff", roleStaff);
                    session.setAttribute("statusStaff", statusStaff);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                String checkName = "^[a-zA-ZÀ-Ỵà-ỵĐđ\\s]+$";
                if (!fNameStaff.matches(checkName) || !lNameStaff.matches(checkName)) {

                    session.setAttribute("errMess", "Họ và Tên của bạn không được chứa kí tự đặc biệt và số");
                    session.setAttribute("usernameStaff", usernameStaff);
                    session.setAttribute("emailStaff", emailStaff);
                    session.setAttribute("passStaff", passStaff);
                    session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                    session.setAttribute("fNameStaff", fNameStaff);
                    session.setAttribute("lNameStaff", lNameStaff);
                    session.setAttribute("phoneStaff", phoneStaff);
                    session.setAttribute("dobStaff", dobStaff);
                    session.setAttribute("addressStaff", addressStaff);
                    session.setAttribute("roleStaff", roleStaff);
                    session.setAttribute("statusStaff", statusStaff);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                String fNameCheck = fNameStaff.trim().replaceAll("\\s+", " ");
                String lNameCheck = lNameStaff.trim().replaceAll("\\s+", " ");

                if (!fNameStaff.matches(fNameCheck) || !lNameStaff.matches(lNameCheck)) {
                    session.setAttribute("errMess", "Họ và Tên không được chứa nhiều khoảng trắng liên tiếp");
                    session.setAttribute("usernameStaff", usernameStaff);
                    session.setAttribute("emailStaff", emailStaff);
                    session.setAttribute("passStaff", passStaff);
                    session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                    session.setAttribute("fNameStaff", fNameStaff);
                    session.setAttribute("lNameStaff", lNameStaff);
                    session.setAttribute("phoneStaff", phoneStaff);
                    session.setAttribute("dobStaff", dobStaff);
                    session.setAttribute("addressStaff", addressStaff);
                    session.setAttribute("roleStaff", roleStaff);
                    session.setAttribute("statusStaff", statusStaff);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                String addressCheck = addressStaff.trim().replaceAll("\\s+", " ");

                if (!addressStaff.matches(addressCheck)) {
                    session.setAttribute("errMess", "Địa chỉ không được chứa nhiều khoảng trắng liên tiếp");
                    session.setAttribute("usernameStaff", usernameStaff);
                    session.setAttribute("emailStaff", emailStaff);
                    session.setAttribute("passStaff", passStaff);
                    session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                    session.setAttribute("fNameStaff", fNameStaff);
                    session.setAttribute("lNameStaff", lNameStaff);
                    session.setAttribute("phoneStaff", phoneStaff);
                    session.setAttribute("dobStaff", dobStaff);
                    session.setAttribute("addressStaff", addressStaff);
                    session.setAttribute("roleStaff", roleStaff);
                    session.setAttribute("statusStaff", statusStaff);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                boolean checkPhoneStaff = isValidPhone(phoneStaff);
                if (!checkPhoneStaff) {
                    session.setAttribute("errMess", "Số điện thoại không hợp lệ");

                    session.setAttribute("usernameStaff", usernameStaff);
                    session.setAttribute("emailStaff", emailStaff);
                    session.setAttribute("passStaff", passStaff);
                    session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                    session.setAttribute("fNameStaff", fNameStaff);
                    session.setAttribute("lNameStaff", lNameStaff);
                    session.setAttribute("phoneStaff", phoneStaff);
                    session.setAttribute("dobStaff", dobStaff);
                    session.setAttribute("addressStaff", addressStaff);
                    session.setAttribute("roleStaff", roleStaff);
                    session.setAttribute("statusStaff", statusStaff);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                Date dateStaff = null;
                try {
                    if (dobStaff != null && !dobStaff.trim().isEmpty()) {
                        LocalDate dateCheck = LocalDate.parse(dobStaff);
                        LocalDate now = LocalDate.now();

                        if (dateCheck.isAfter(now) || Period.between(dateCheck, now).getYears() < 18 || Period.between(dateCheck, now).getYears() > 65) {
                            session.setAttribute("errMess", "Ngày sinh không hợp lệ");

                            session.setAttribute("usernameStaff", usernameStaff);
                            session.setAttribute("emailStaff", emailStaff);
                            session.setAttribute("passStaff", passStaff);
                            session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                            session.setAttribute("fNameStaff", fNameStaff);
                            session.setAttribute("lNameStaff", lNameStaff);
                            session.setAttribute("phoneStaff", phoneStaff);
                            session.setAttribute("dobStaff", dobStaff);
                            session.setAttribute("addressStaff", addressStaff);
                            session.setAttribute("roleStaff", roleStaff);
                            session.setAttribute("statusStaff", statusStaff);

                            String url = "admin-panel?action=create-account&type=staff";
                            response.sendRedirect(url);
                            return;
                        }

                        dateStaff = java.sql.Date.valueOf(dateCheck);
                    } else {
                        session.setAttribute("errMess", "Vui lòng nhập ngày sinh.");

                        session.setAttribute("usernameStaff", usernameStaff);
                        session.setAttribute("emailStaff", emailStaff);
                        session.setAttribute("passStaff", passStaff);
                        session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                        session.setAttribute("fNameStaff", fNameStaff);
                        session.setAttribute("lNameStaff", lNameStaff);
                        session.setAttribute("phoneStaff", phoneStaff);
                        session.setAttribute("dobStaff", dobStaff);
                        session.setAttribute("addressStaff", addressStaff);
                        session.setAttribute("roleStaff", roleStaff);
                        session.setAttribute("statusStaff", statusStaff);

                        String url = "admin-panel?action=create-account&type=staff";
                        response.sendRedirect(url);
                        return;
                    }
                } catch (Exception e) {
                    session.setAttribute("errMess", "Định dạng ngày sinh không hợp lệ.");

                    session.setAttribute("usernameStaff", usernameStaff);
                    session.setAttribute("emailStaff", emailStaff);
                    session.setAttribute("passStaff", passStaff);
                    session.setAttribute("comfirmPassStaff", comfirmPassStaff);
                    session.setAttribute("fNameStaff", fNameStaff);
                    session.setAttribute("lNameStaff", lNameStaff);
                    session.setAttribute("phoneStaff", phoneStaff);
                    session.setAttribute("dobStaff", dobStaff);
                    session.setAttribute("addressStaff", addressStaff);
                    session.setAttribute("roleStaff", roleStaff);
                    session.setAttribute("statusStaff", statusStaff);

                    String url = "admin-panel?action=create-account&type=staff";
                    response.sendRedirect(url);
                    return;
                }

                String hashPassStaff = BCrypt.hashpw(passStaff, BCrypt.gensalt());

                Account acc = new Account();
                acc.setAccUsername(usernameStaff);
                acc.setAccEmail(emailStaff);
                acc.setAccPassword(hashPassStaff);
                acc.setAccRole(roleStaff);
                acc.setAccStatus(statusStaff);

                acc.setAccFname(fNameStaff);
                acc.setAccLname(lNameStaff);
                acc.setAccPhoneNumber(phoneStaff);
                acc.setAccDob(dateStaff);
                acc.setAccAddress(addressStaff);

                int newId = accDao.registerAccStaffByAdmin(acc);
                if (acc.getAccRole().equals("Shipper")) {
                    accDao.shipperAccount(newId);
                }
                try {
                    EmailSender.createAccStaffSuccess(emailStaff, fNameStaff + " " + lNameStaff, emailStaff, passStaff, roleStaff);
                    session.setAttribute("successMess", "Tạo tài khoản cho nhân viên thành công");

                    session.removeAttribute("usernameStaff");
                    session.removeAttribute("emailStaff");
                    session.removeAttribute("passStaff");
                    session.removeAttribute("comfirmPassStaff");
                    session.removeAttribute("fNameStaff");
                    session.removeAttribute("lNameStaff");
                    session.removeAttribute("phoneStaff");
                    session.removeAttribute("dobStaff");
                    session.removeAttribute("addressStaff");
                    session.removeAttribute("roleStaff");
                    session.removeAttribute("statusStaff");

                } catch (Exception e) {

                    session.removeAttribute("usernameStaff");
                    session.removeAttribute("emailStaff");
                    session.removeAttribute("passStaff");
                    session.removeAttribute("comfirmPassStaff");
                    session.removeAttribute("fNameStaff");
                    session.removeAttribute("lNameStaff");
                    session.removeAttribute("phoneStaff");
                    session.removeAttribute("dobStaff");
                    session.removeAttribute("addressStaff");
                    session.removeAttribute("roleStaff");
                    session.removeAttribute("statusStaff");

                    session.setAttribute("errMess", "Tạo tài khoản cho nhân viên không thành công");
                }
                String url = "admin-panel?action=create-account&type=staff";
                response.sendRedirect(url);
            }
        } else if ("change-password".equals(action)) {
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (currentPassword == null || currentPassword.trim().isEmpty()
                    || newPassword == null || newPassword.trim().isEmpty()
                    || confirmPassword == null || confirmPassword.trim().isEmpty()) {
                session.setAttribute("errMess", "Bạn cần điền đủ thông tin (không để trống hoặc chỉ chứa khoảng trắng)");
                String url = "admin-panel?action=change-password";
                response.sendRedirect(url);
                return;
            }

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
