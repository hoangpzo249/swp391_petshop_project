<%-- 
    Document   : home_page
    Created on : 19 May 2025, 11:13:48 pm
    Author     : HuyHoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/header_footer.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

        <title>JSP Page</title>
    </head>
    <!-- header -->
    <div class="header">
        <div class="header1">
            <div>
                <a href="">
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
                    <a href="">
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
            <h3>PETFPT Shop</h3>
            <ul>
                <li><a href="">Giới Thiệu</a></li>
                <li><a href="">Chính sách</a></li>
                <li><a href="">Phương Thức Thanh Toán</a></li>
                <li><a href="">Điều Khoản Sử Dụng</a></li>                    

            </ul>
        </div>

        <div class="about-column">
            <h3>Liên Hệ</h3>
            <p class="contactpet">PETFPT Shop</p>
            <p><i class="fas fa-phone"></i>0767676770</p>
            <p><i class="fas fa-map-marker-alt"></i>Khu Công nghệ cao Hòa Lạc <br>
                Km29 Đại lộ Thăng Long, H. Thạch Thất, TP. Hà Nội</p>
            <p><i class="fas fa-envelope email"></i>
                <a href="mailto:hoangnhhe181051@fpt.edu.vn">petfpt@gmail.com</a>
            </p>

            <div class="social-container">
                <a href="https://www.facebook.com/petfptshop" target="_blank" class="social-icon">
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
