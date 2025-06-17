<%-- 
    Document   : account_login_page
    Created on : 29 May 2025, 8:37:43 am
    Author     : HuyHoang
--%>

<%
    String email = "";
    String password = "";
    
    Cookie[] cook = request.getCookies();
    if(cook != null) {
    for(Cookie c : cook){
        if("email".equals(c.getName())) email = c.getValue();
//        if("username".equals(c.getName())) email = c.getValue();
        if("password".equals(c.getName())) password = c.getValue();
    }
    }
    
request.setAttribute("emailLogin", email);
request.setAttribute("passwordLogin", password);

%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập</title>
        <link href="css/account_login_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>
    <body>
        <div class="floating-particle particle1"></div>
        <div class="floating-particle particle2"></div>
        <div class="floating-particle particle3"></div>
        <div class="floating-particle particle4"></div>
        <div class="floating-particle particle5"></div>

        <c:if test="${not empty successMessRecover}">
            <div class="login-success-alert">
                ${successMessRecover}
            </div>
            <c:remove var="successMessRecover" scope="session" />
        </c:if>

        <div class="register-container">
            <h2>Đăng nhập</h2>
            <c:if test="${empty sendOTP2FA}">
                <form action="login" method="post">
                    <div class="input-group">
                        <label for="email">Địa chỉ Email*</label>
                        <input type="text" id="emailLogin" name="emailLogin" value="${emailLogin}" required>
                    </div>

                    <div class="input-group">
                        <label for="password">Mật Khẩu *</label>
                        <div style="position: relative;">
                            <input type="password" id="passwordLogin" name="passwordLogin" value="${passwordLogin}" required>
                        </div>
                    </div>

                    <div class="login-options">
                        <div class="remember-password">
                            <input type="checkbox" id="remember" name="remember" value="on" checked>
                            <label for="remember">Nhớ mật khẩu</label>
                        </div>
                        <a href="recover" class="forgot-password">Bạn quên mật khẩu?</a>
                    </div>

                    <c:if test="${not empty errMessLogin}">
                        <div class="errMess">
                            <p>${errMessLogin}</p>
                        </div>
                    </c:if>

                    <button type="submit" class="btn">Đăng nhập</button>

                    <div class="social-login">
                        <a href="https://accounts.google.com/o/oauth2/v2/auth?scope=email%20profile&access_type=online&response_type=code&redirect_uri=http://localhost:8080/PetShopFPT/login-google&client_id=272169085029-oucd0u94q8i5bcnfrlm4qq2k1r5jdu76.apps.googleusercontent.com" class="custom-google-btn">
                            <img src="https://developers.google.com/identity/images/g-logo.png" alt="Google logo">
                            <span>Đăng nhập bằng Google</span>
                        </a>
                    </div>

                </form>
                <p class="login-link">Bạn chưa có tài khoản? <a href="register">Tạo tài khoản</a></p>
                <p class="login-link">
                    <a href="homepage"><i class="fas fa-arrow-left"></i> Quay lại trang chủ</a>
                </p>
            </c:if>

            <c:if test="${not empty sendOTP2FA}">
                <form action="login?action=otp" method="post">

                    <div class="otp-header1">
                        <c:if test="${not empty sendemailLogin}">
                            <p>Gửi mã OTP thành công tới:</p>
                            <p class="email-display1">${sendemailLogin}</p>
                            <p style="font-style: italic">Bạn cần xác minh OTP để truy cập vào hệ thống đúng với vai trò của bạn</p>
                        </c:if>

                    </div>
                    <c:if test="${not empty successMessLogin}">
                        <div class="successMess">
                            <p>${successMessLogin}</p>
                        </div>
                    </c:if>

                    <div class="input-group1">
                        <label for="otp">Mã OTP (6 số) *</label>
                        <input type="text" name="inputotpLogin" maxlength="6" placeholder="Nhập mã OTP 6 số" required>
                    </div>

                    <button type="submit" class="btnotp">Xác thực</button>

                    <c:if test="${not empty errMessLoginOtp}">
                        <div class="errMess">
                            <p>${errMessLoginOtp}</p>
                        </div>
                        <c:remove var="errMessLoginOtp" scope="session" />
                    </c:if>

                    <div class="action-links1">
                        <p class="resend-link1">
                            Không nhận được mã? 
                            <a href="login?action=resend-otp">Gửi lại OTP</a>
                        </p>
                    </div>
                </form>
                <p class="login-link">
                    <a href="login"><i class="fas fa-arrow-left"></i> Quay lại đăng nhập</a>
                </p>
            </c:if>
        </div>
    </body>
</html>