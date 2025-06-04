<%-- 
    Document   : account_register_page
    Created on : 29 May 2025, 8:37:52 am
    Author     : HuyHoang
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tạo tài khoản</title>
        <link href="css/account_register_page.css" rel="stylesheet" type="text/css"/>
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

            <c:if test="${empty sendOtpSuccess}">
                <h2>Tạo tài khoản</h2>
                <form action="register" method="post">
                    <div class="input-group">
                        <label>Tên *</label>
                        <input type="text" id="firstname" name="firstname" value="${firstname}" >
                    </div>

                    <div class="input-group">
                        <label for="lastname">Họ *</label>
                        <input type="text" id="lastname" name="lastname" value="${lastname}" >
                    </div>

                    <div class="input-group">
                        <label for="txtlogin">Địa chỉ Email *</label>
                        <input type="email" id="txtemail" name="email" value="${email}" >
                    </div>

                    <div class="input-group">
                        <label for="password">Mật Khẩu *</label>
                        <input type="password" id="password" name="password" value="${password}" >
                    </div>

                    <div class="input-group">
                        <label for="password">Xác nhận mật Khẩu *</label>
                        <input type="password" id="confirm_password" name="confirm_password" value="${confirm_password}" >
                    </div>

                    <c:if test="${not empty errMess}">
                        <div class="errMess">
                            <p>${errMess}</p>
                        </div>
                    </c:if>

                    <button type="submit" class="btn">Tạo tài khoản</button>
                    <div class="social-login">
                        <a href="https://accounts.google.com/o/oauth2/v2/auth?scope=email%20profile&access_type=online&response_type=code&redirect_uri=http://localhost:8080/swp391_petshop_project/login-google&client_id=406838536003-3dvgsajsk4p6a500vlmaokr7u5q3nsa5.apps.googleusercontent.com" class="custom-google-btn">
                            <img src="https://developers.google.com/identity/images/g-logo.png" alt="Google logo">
                            <span>Đăng nhập bằng Google</span>
                        </a>
                    </div>
                </form>
            </c:if>

            <c:if test="${not empty sendOtpSuccess}">
                <form action="register?action=otp" method="post">

                    <div class="otp-header1">
                        <c:if test="${not empty email}">
                            <p>Gửi mã OTP thành công tới:</p>
                            <p class="email-display1">${email}</p>
                        </c:if>

                    </div>
                    <c:if test="${not empty successMess}">
                        <div class="successMess">
                            <p>${successMess}</p>
                        </div>
                    </c:if>

                    <div class="input-group1">
                        <label for="otp">Mã OTP (6 số) *</label>
                        <input type="text" id="otp" name="inputotp" maxlength="6" placeholder="Nhập mã OTP 6 số">
                    </div>

                    <button type="submit" class="btnotp">Xác thực</button>

                    <c:if test="${not empty errMess}">
                        <div class="errMess">
                            <p>${errMess}</p>
                        </div>
                    </c:if>

                    <div class="action-links1">
                        <p class="resend-link1">
                            Không nhận được mã? 
                            <a href="register?action=resend-otp">Gửi lại OTP</a>
                        </p>
                    </div>
                </form>
            </c:if>

            <p class="login-link">Đã có tài khoản? <a href="login">Đăng nhập</a></p>
        </div>

    </body>
</html>
