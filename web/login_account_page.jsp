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
        <link href="css/header_footer.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>
    <body>
        <!-- header -->
        <div class="header">
            <div class="header1">
                <div>
                    <a href="homepage">
                        <img src="images/logo_banner/logo2.png" alt=""/>
                    </a>
                </div>
                <form action="">
                    <div class="search">
                        <input type="text" name="search" value="" placeholder="Tìm kiếm thú cưng ..."/>
                        <button type="submit" class="search-button">
                            <img src="images/support button/search.png" width="512" height="512" alt="search" />
                        </button>
                    </div>
                </form>

                <div class="accountcard">
                    <div class="account">
                        <a href="login">
                            <img src="images/support button/account.png" width="800" height="800" alt="account"/>
                            <p class="logintext">Tài Khoản</p>
                        </a>
                    </div>
                    <div class="card">
                        <a href="">
                            <img src="images/support button/card.png" width="1859" height="1573" alt="card"/>
                            <p class="cardtext">
                                Giỏ Hàng
                            </p>
                        </a>
                    </div>
                </div>

            </div>
            <div>
                <nav>
                    <ul class="menu">
                        <li><a href="homepage">Trang Chủ</a></li>
                        <li><a href="">Thú Cưng</a></li>
                        <li><a href="">Giới Thiệu</a></li>
                        <li><a href="">Liên Hệ</a></li>
                    </ul>
                </nav>
            </div>
        </div>

        <div class="login-container">
            <h2>Đăng Nhập</h2>
            <form action="" method="post">

                <c:if test="${not empty sessionScope.successMess}">
                    <div class="successMess">
                        <p>${sessionScope.successMess}</p>
                    </div>
                </c:if>

                <div class="input-group">
                    <label for="txtlogin">Tên đăng nhập *</label>
                    <input type="text" id="txtlogin" name="username" required>
                </div>

                <div class="input-group">
                    <label for="password">Mật Khẩu *</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <div class="forgot-password">
                    <a href="#" id="">Quên mật khẩu?</a>
                </div>

                <c:if test="${not empty errMess}">
                    <div class="errMess">
                        <p>${errMess}</p>
                    </div>
                </c:if>

                <button type="submit" class="btn">Đăng Nhập</button>
            </form>
            <p class="register-link">Chưa có tài khoản? <a href="register">Tạo tài khoản</a></p>
        </div>

        <!-- about -->
        <div class="about-section">
            <div class="about-column">
                <h3>Shop</h3>
                <ul>
                    <li><a href="">Dành Cho Chó</a></li>
                    <li><a href="">Dành Cho Mèo</a></li>
                    <li><a href="">Dành Cho Chuột</a></li>
                </ul>
            </div>

            <div class="about-column">
                <h3>FPTPet Shop</h3>
                <ul>
                    <li><a href="menu_about.jsp">Giới Thiệu</a></li>
                    <li><a href="footer_policy.jsp">Chính sách</a></li>
                    <li><a href="footer_paymentmethod.jsp">Phương Thức Thanh Toán</a></li>
                    <li><a href="footer_termofuse.jsp">Điều Khoản Sử Dụng</a></li>                    

                </ul>
            </div>

            <div class="about-column">
                <h3>Liên Hệ</h3>
                <p class="contactpet">FPTPet Shop</p>
                <p><i class="fas fa-phone"></i>0767676770</p>
                <p><i class="fas fa-map-marker-alt"></i>Khu Công nghệ cao Hòa Lạc <br>
                    Km29 Đại lộ Thăng Long, H. Thạch Thất, TP. Hà Nội</p>
                <p><i class="fas fa-envelope email"></i>
                    <a href="mailto:fptpet@gmail.com">fptpet@gmail.com</a>
                </p>

                <div class="social-container">
                    <a href="https://www.facebook.com/FPTPetShop" target="_blank" class="social-icon">
                        <i class="fab fa-facebook fa-2x"></i>
                    </a>
                </div>
            </div>
        </div>
        <footer>
            © 2025 FPTPet - Đồng hành cùng bạn và thú cưng mỗi ngày!
        </footer>
    </body>
</html>

