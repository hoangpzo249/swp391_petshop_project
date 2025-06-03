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
    
request.setAttribute("email", email);
//request.setAttribute("email", email);
request.setAttribute("password", password);

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
            <form action="login" method="post">
                <div class="input-group">
                    <label for="email">Địa chỉ Email*</label>
                    <input type="text" id="email" name="email" value="${email}">
                </div>

                <div class="input-group">
                    <label for="password">Mật Khẩu *</label>
                    <input type="password" id="password" name="password" value="${password}">
                </div>

                <div class="login-options">
                    <div class="remember-password">
                        <input type="checkbox" id="remember" name="remember" value="on">
                        <label for="remember">Nhớ mật khẩu</label>
                    </div>
                    <a href="recover" class="forgot-password">Bạn quên mật khẩu?</a>
                </div>

                <!--<p class="recover-link"><a href="recover">Bạn quên mật khẩu?</a></p>-->

                <c:if test="${not empty errMess}">
                    <div class="errMess">
                        <p>${errMess}</p>
                    </div>
                </c:if>

                <button type="submit" class="btn">Đăng nhập</button>

                <div class="social-login">
                    <a href="https://accounts.google.com/o/oauth2/v2/auth?scope=email%20profile&access_type=online&response_type=code&redirect_uri=http://localhost:8080/swp391_petshop_project/login-google&client_id=406838536003-3dvgsajsk4p6a500vlmaokr7u5q3nsa5.apps.googleusercontent.com" class="custom-google-btn">
                        <img src="https://developers.google.com/identity/images/g-logo.png" alt="Google logo">
                        <span>Đăng nhập bằng Google</span>
                    </a>
                </div>

            </form>
            <p class="login-link">Bạn chưa có tài khoản? <a href="register">Tạo tài khoản</a></p>
        </div>
    </body>
</html>

