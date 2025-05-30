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
            <h2>Tạo tài khoản</h2>
            <form action="" method="post">
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
                    <label for="txtlogin">Số điện thoại *</label>
                    <input type="number" id="txtphone" name="phone" value="${phone}" >
                </div>

                <div class="input-group">
                    <label for="txtlogin">Tên đăng nhập *</label>
                    <input type="text" id="txtlogin" name="username" value="${username}" >
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

                <button type="submit" class="btn">Tạo Tài Khoản</button>
                <p></p>
            </form>
            <p class="login-link">Đã có tài khoản? <a href="login">Đăng nhập</a></p>
        </div>

    </body>
</html>
