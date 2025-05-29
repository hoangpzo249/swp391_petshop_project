<%-- 
    Document   : account_login_page
    Created on : 29 May 2025, 8:37:43 am
    Author     : HuyHoang
--%>
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

        <div class="register-container">
            <h2>Đăng nhập</h2>
            <form action="" method="post">
                <c:if test="${not empty sessionScope.successMess}">
                    <div class="successMess">
                        <p>${sessionScope.successMess}</p>
                    </div>
                </c:if>
                <div class="input-group">
                    <label for="username">Tên đăng nhập hoặc Email*</label>
                    <input type="text" id="username" name="username" required>
                </div>

                <div class="input-group">
                    <label for="password">Mật Khẩu *</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <c:if test="${not empty errMess}">
                    <div class="errMess">
                        <p>${errMess}</p>
                    </div>
                </c:if>

                <button type="submit" class="btn">Đăng nhập</button>
            </form>
            <p class="login-link">Bạn chưa có tài khoản? <a href="register">Tạo tài khoản</a></p>
        </div>
    </body>
</html>

