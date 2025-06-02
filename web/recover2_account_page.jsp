<%-- 
    Document   : recover2_account_page
    Created on : 2 June 2025, 11:57:27 pm
    Author     : HuyHoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cập nhật mật khẩu mới</title>
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
            <h2>Cập nhật mật khẩu</h2>
            <form action="" method="post">

                <c:if test="${not empty successMess}">
                    <div class="successMess">
                        <p>${successMess}</p>
                    </div>
                </c:if>

                <div class="input-group">
                    <label for="email">Mật khẩu mới*</label>
                    <input type="password" id="password" name="password" value="${password}">
                </div>

                <div class="input-group">
                    <label for="comfirm_password">Xác nhận mật khẩu *</label>
                    <input type="password" id="comfirm_password" name="comfirm_password" value="${comfirm_password}">
                </div>

                <!--                <div class="login-options">
                                    <div class="remember-password">
                                        <input type="checkbox" id="remember" name="remember" value="on">
                                        <label for="remember">Nhớ mật khẩu</label>
                                    </div>
                                    <a href="recover" class="forgot-password">Bạn quên mật khẩu?</a>
                                </div>-->

                <!--<p class="recover-link"><a href="recover">Bạn quên mật khẩu?</a></p>-->


                <c:if test="${not empty errMess}">
                    <div class="errMess">
                        <p>${errMess}</p>
                    </div>
                </c:if>

                <button type="submit" class="btn">Xác nhận</button>

            </form>
            <!--<p class="login-link">Bạn chưa có tài khoản? <a href="register">Tạo tài khoản</a></p>-->
            <p class="login-link">
                <a href="login"><i class="fas fa-arrow-left"></i> Quay lại đăng nhập</a>
            </p>
        </div>
    </body>
</html>
