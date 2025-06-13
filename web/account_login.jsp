<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập - PETFPT Shop</title>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link rel="icon" type="png" href="images/logo_banner/logo1.png">
    
        <script src="js/scroll_chat.js" type="text/javascript"></script>
     
    </head>
    <body>
        
        </div>

        <div class="login-container">
            <h2>Đăng Nhập</h2>
            <form action="loginuser" method="post">
                <div class="input-group">
                    <label for="txtlogin">Tên đăng nhập *</label>
                    <input type="text" id="txtlogin" name="username" required>
                </div>

                <div class="input-group">
                    <label for="password">Mật Khẩu *</label>
                    <input type="password" id="password" name="password" required>
                    <label style="color: red">${errMessage}</label>
                    <label>${message}</label>
                </div>



                <div class="forgot-password">
                    <a href="#" id="forgotPasswordLink">Quên mật khẩu?</a>
                </div>

                <button type="submit" class="btn">Đăng Nhập</button>
            </form>
            <p class="register-link">Chưa có tài khoản? <a href="account_register.jsp">Tạo tài khoản</a></p>
        </div>

        <div class="popup-container" id="popupForgot">
            <div class="popup-content">
                <span class="close-btn" id="closePopup">&times;</span>
                <h3>Quên mật khẩu?</h3>
                <p>Vui lòng liên hệ đội ngũ hỗ trợ PETFPT qua Zalo để được cấp lại mật khẩu nhanh nhất.</p>
                <a href="https://zalo.me/your-zalo-link" target="_blank" class="btn-zalo">
                    Liên hệ qua Zalo ngay
                </a>
            </div>
        </div>

       
    </body>
</html>
