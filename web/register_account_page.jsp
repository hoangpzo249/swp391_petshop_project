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

            <c:if test="${empty sendOtpSuccessRegister}">
                <h2>Tạo tài khoản</h2>
                <form action="register" method="post">
                    <div class="input-group">
                        <label>Tên *</label>
                        <input type="text" id="fNameRegister" name="fNameRegister" value="${fNameRegister}" required>
                    </div>

                    <div class="input-group">
                        <label for="lastname">Họ *</label>
                        <input type="text" id="lNameRegister" name="lNameRegister" value="${lNameRegister}" required>
                    </div>

                    <div class="input-group">
                        <label for="txtlogin">Địa chỉ Email *</label>
                        <input type="email" id="emailRegister" name="emailRegister" value="${emailRegister}" required>
                    </div>

                    <div class="input-group">
                        <label for="password">Mật Khẩu *</label>
                        <div style="position: relative;">
                            <input type="password" id="passRegister" name="passRegister" value="${passRegister}" required>
                        </div>
                    </div>

                    <div class="input-group">
                        <label for="password">Xác nhận mật khẩu *</label>
                        <div style="position: relative;">
                            <input type="password" id="confirm_passwordRegister" name="confirm_passwordRegister" value="${confirm_passwordRegister}" required>
                        </div>
                    </div>

                    <c:if test="${not empty errMessRegister}">
                        <div class="errMess">
                            <p>${errMessRegister}</p>
                        </div>
                    </c:if>

                    <button type="submit" class="btn">Tạo tài khoản</button>
                    <div class="social-login">
                        <a href="https://accounts.google.com/o/oauth2/v2/auth?scope=email%20profile&access_type=online&response_type=code&redirect_uri=http://localhost:8080/PetShopFPT/login-google&client_id=272169085029-oucd0u94q8i5bcnfrlm4qq2k1r5jdu76.apps.googleusercontent.com" class="custom-google-btn">
                            <img src="https://developers.google.com/identity/images/g-logo.png" alt="Google logo">
                            <span>Đăng nhập bằng Google</span>
                        </a>
                    </div>
                </form>
                <p class="login-link">Đã có tài khoản? <a href="login">Đăng nhập</a></p>
                <p class="login-link">
                    <a href="homepage"><i class="fas fa-arrow-left"></i> Quay lại trang chủ</a>
                </p>
            </c:if>

            <c:if test="${not empty sendOtpSuccessRegister}">
                <form action="register?action=otp" method="post">

                    <div class="otp-header1">
                        <c:if test="${not empty emailRegister}">
                            <p>Gửi mã OTP thành công tới:</p>
                            <p class="email-display1">${emailRegister}</p>
                        </c:if>

                    </div>
                    <c:if test="${not empty successMessRegis}">
                        <div class="successMess">
                            <p>${successMessRegis}</p>
                        </div>
                    </c:if>

                    <div class="input-group1">
                        <label for="otp">Mã OTP (6 số) *</label>
                        <input type="text" name="inputotpRegis" maxlength="6" placeholder="Nhập mã OTP 6 số" required>
                    </div>

                    <button type="submit" class="btnotp">Xác thực</button>

                    <c:if test="${not empty errMessRegisOtp}">
                        <div class="errMess">
                            <p>${errMessRegisOtp}</p>
                        </div>
                    </c:if>

                    <div class="action-links1">
                        <p class="resend-link1">
                            Không nhận được mã? 
                            <a href="register?action=resend-otp">Gửi lại OTP</a>
                        </p>
                    </div>
                    <p class="login-link">Đã có tài khoản? <a href="login">Đăng nhập</a></p>
                </form>
            </c:if>


        </div>

    </body>
</html>
