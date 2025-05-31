<%-- 
    Document   : recover_account_page
    Created on : 31 May 2025, 12:58:07 pm
    Author     : HuyHoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lấy lại mật khẩu</title>
        <link href="css/recover_account_page.css" rel="stylesheet" type="text/css"/>
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
            <form action="" method="post">
                <c:if test="${not empty sessionScope.successMessRecover}">
                    <div class="successMess">
                        <p>${sessionScope.successMessRecover}</p>
                    </div>
                </c:if>

                <div class="otp-header">
                    <h2>Bạn quên mật khẩu?</h2>
                    <p class="instruction">Nhập lại Email của bạn để lấy lại mật khẩu Email</p>
                </div>

                <div class="input-group">
                    <label for="email">Email của bạn*</label>
                    <input type="email" id="email" name="email" value="${email}">
                </div>

                <c:if test="${not empty errMess}">
                    <div class="errMess">
                        <p>${errMess}</p>
                    </div>
                </c:if>

                <button type="submit" class="btn">Lấy lại mật khẩu</button>
            </form>
            <p class="login-link">
                <a href="login"><i class="fas fa-arrow-left"></i> Quay lại đăng nhập</a>
            </p>
        </div>
    </body>
</html>
