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
        <link href="css/head_about.css" rel="stylesheet" type="text/css"/>
        <script src="js/scroll_chat.js" type="text/javascript"></script>
        <link href="css/account_login.css" rel="stylesheet" type="text/css"/>
        <script src="js/account_fogotpass.js" type="text/javascript"></script>
    </head>
    <body>
        <!-- header -->
        <div class="header">
            <div class="header1">
                <div>
                    <a href="listmainmenu">
                        <img src="images/logo_banner/logo.png" alt=""/>
                    </a>
                </div>
                <form action="listshoppet">
                    <div class="search">
                        <input type="text" name="search" value="${sessionScope.search}" placeholder="Tìm kiếm thú cưng ..."/>
                        <button type="submit" class="search-button">
                            <img src="images/search.png" width="512" height="512" alt="search" />
                        </button>
                    </div>
                </form>

                <div class="accountcard">
    <div class="account">
        <a href="${not empty sessionScope.account ? 'account_profile_user.jsp' : 'account_login.jsp'}">
            <img src="images/account.png" width="800" height="800" alt="account"/>
            <p class="logintext">Tài Khoản</p>
        </a>
    </div>
    <div class="card">
        <a href=displaycart>
            <img src="images/card.png" width="1859" height="1573" alt="card"/>
            <p class="cardtext">
                Giỏ Hàng
                <span id="cart-count" class="cartcount">${sessionScope.cartcount}</span>
            </p>
        </a>
    </div>
</div>

            </div>
            <div>
                <nav>
                    <ul class="menu">
                        <li><a href="listmainmenu">Trang Chủ</a></li>
                        <li><a href="listshoppet?species=Chó&sort=popular&giapet1=&giapet2=&breed=&search=">Chó Cưng</a></li>
                        <li><a href="listshoppet?species=Mèo&sort=popular&giapet1=&giapet2=&breed=&search=">Mèo Cưng</a></li>
                        <li><a href="listshop?category=%search=&gia1=&gia2=&sort=popular">Phụ Kiện</a></li>
                        <li><a href="menu_about.jsp">Giới Thiệu</a></li>
                        <li><a href="menu_contact.jsp">Liên Hệ</a></li>
                            <c:choose>
                                <c:when test="${not empty sessionScope.account and sessionScope.account.accRole eq 'Admin'}">
                                <li><a href="admin.jsp">Admin Panel</a></li>
                                </c:when>
                                <c:otherwise>
                                </c:otherwise>
                            </c:choose>

                    </ul>
                </nav>
            </div>

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

        <!-- about -->
        <div class="about-section">
            <div class="about-column">
                <h3>Shop</h3>
                <ul>
                    <li><a href="listshoppet?species=Chó&sort=popular&giapet1=&giapet2=&breed=&search=">Dành Cho Chó</a></li>
                    <li><a href="listshoppet?species=Mèo&sort=popular&giapet1=&giapet2=&breed=&search=">Dành Cho Mèo</a></li>
                    <li><a href="listshop?category=%search=&gia1=&gia2=&sort=popular">Phụ kiện cho Chó & Mèo</a></li>
                </ul>
            </div>

            <div class="about-column">
                <h3>PETFPT Shop</h3>
                <ul>
                    <li><a href="menu_about.jsp">Giới Thiệu</a></li>
                    <li><a href="footer_policy.jsp">Chính sách</a></li>
                    <li><a href="footer_paymentmethod.jsp">Phương Thức Thanh Toán</a></li>
                    <li><a href="footer_termofuse.jsp">Điều Khoản Sử Dụng</a></li>                    

                </ul>
            </div>

            <div class="about-column">
                <h3>Liên Hệ</h3>
                <p class="contactpet">PETFPT Shop</p>
                <p><i class="fas fa-phone"></i>0767676770</p>
                <p><i class="fas fa-map-marker-alt"></i>Khu Công nghệ cao Hòa Lạc <br>
                    Km29 Đại lộ Thăng Long, H. Thạch Thất, TP. Hà Nội</p>
                <p><i class="fas fa-envelope email"></i>
                    <a href="mailto:hoangnhhe181051@fpt.edu.vn">hoangnhhe181051@fpt.edu.vn</a>
                </p>

                <div class="social-container">
                    <a href="https://www.facebook.com/petfptshop" target="_blank" class="social-icon">
                        <i class="fab fa-facebook fa-2x"></i>
                    </a>
                </div>
            </div>
        </div>
        <footer>
            © 2025 PETFPT - Đồng hành cùng bạn và thú cưng mỗi ngày!
        </footer>
    </body>
</html>
