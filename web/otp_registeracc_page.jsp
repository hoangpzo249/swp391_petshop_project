<%-- 
    Document   : otp_registeracc_page
    Created on : 30 May 2025, 11:33:29 pm
    Author     : HuyHoang
--%>
<%
    String otp = (String) session.getAttribute("otp");
    if (otp == null) {
        response.sendRedirect("register");
        return;
    }
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xác thực OTP</title>
        <link href="css/otp_registeraccount_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>

    <body>
        <div class="floating-particle particle1"></div>
        <div class="floating-particle particle2"></div>
        <div class="floating-particle particle3"></div>
        <div class="floating-particle particle4"></div>
        <div class="floating-particle particle5"></div>

        <div class="register-container">
            <div class="otp-header">
                <h2>Xác thực OTP</h2>
                <p>Gửi mã OTP thành công</p>
                <p class="email-display">${email}</p>
                <p class="instruction">Bạn cần nhập mã OTP để hoàn tất tạo tài khoản</p>
            </div>
            <c:if test="${not empty errMess}">
                <div class="errMess">
                    <p>${errMess}</p>
                </div>
            </c:if>
                
            <c:if test="${not empty successMess}">
                <div class="successMess">
                    <p>${successMess}</p>
                </div>
            </c:if>
                
                
            <form action="" method="post">
                <div class="input-group">
                    <label for="otp">Mã OTP (6 số) *</label>
                    <input type="text" id="otp" name="inputotp" value="" maxlength="6" placeholder="Nhập mã OTP 6 số">
                </div>

                <button type="submit" class="btn">Xác thực</button>
                <p></p>
            </form>

            <div class="action-links">
                <p class="resend-link">
                    Không nhận được mã? 
                    <a href="verify-otp?action=resend-otp">Gửi lại OTP</a>
                </p>
                <p class="login-link">
                    <a href="register"><i class="fas fa-arrow-left"></i> Quay lại đăng ký</a>
                </p>
            </div>
        </div>
    </body>
</html>
