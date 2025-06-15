<%-- 
    Document   : home_page
    Created on : 19 May 2025, 11:13:48 pm
    Author     : HuyHoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/header_footer.css" rel="stylesheet" type="text/css"/>
        <link href="css/home_page.css" rel="stylesheet" type="text/css"/>
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
                    <c:choose>
                        <c:when test="${sessionScope.userAccount != null}">
                            <div class="account-dropdown">
                                <a href="#" class="account-trigger">
                                    <img src="images/support button/account.png" width="50" height="50" alt="account"/>
                                    <p class="username">Tài khoản</p>
                                </a>
                                <div class="dropdown-content">
                                    <c:choose>

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Admin'}">
                                            <a href="admin-panel" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Admin</span>
                                            </a>
                                        </c:when>
                                        <c:when test="${sessionScope.userAccount.accRole eq 'Seller'}">
                                            <a href="displayorder" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Seller</span>
                                            </a>
                                        </c:when>

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Manager'}">
                                            <a href="profile" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Manager</span>
                                            </a>
                                        </c:when>

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Saler'}">
                                            <a href="displayorder" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Saler</span>
                                            </a>
                                        </c:when>

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Shipper'}">
                                            <a href="profile" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Shipper</span>
                                            </a>
                                        </c:when>

                                        <c:otherwise>
                                        </c:otherwise>
                                    </c:choose>

                                    <a href="profile" class="dropdown-item">
                                        <i class="fas fa-user"></i> 
                                        <span>Thông tin cá nhân</span>
                                    </a>
                                    <a href="orders" class="dropdown-item">
                                        <i class="fas fa-shopping-bag"></i> 
                                        <span>Đơn hàng đã mua</span>
                                    </a>
                                    <a href="logout" class="dropdown-item logout">
                                        <i class="fas fa-sign-out-alt"></i> 
                                        <span>Đăng xuất</span>
                                    </a>
                                </div>
                            </div>
                        </c:when>

                        <c:otherwise>
                            <div class="account">
                                <a href="login">
                                    <img src="images/support button/account.png" width="50" height="50" alt="account"/>
                                    <p class="logintext">Đăng nhập</p>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <div class="card">
                        <a href="displaycart">
                            <img src="images/support button/card.png" width="50" height="50" alt="card"/>
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
                        <li><a href="homepage">Trang Chủ</a></li>
                        <li><a href="listshoppet?species=Dog&sort=popular">Chó Cưng</a></li>
                        <li><a href="listshoppet?species=Cat&sort=popular">Mèo Cưng</a></li>
                        <li><a href="menu_about.jsp">Giới Thiệu</a></li>
                        <li><a href="menu_contact.jsp">Liên Hệ</a></li>
                    </ul>
                </nav>
            </div>
        </div>

        <c:if test="${not empty loginSuccess}">
            <div class="login-success-alert">
                ${loginSuccess}
            </div>
            <c:remove var="loginSuccess" scope="session" />
        </c:if>

        <!-- banner -->
        <div class="banner">
            <a href="index.html">
                <img src="images/logo_banner/banner.png" alt=""/>
            </a>
        </div>

        <div class="box">

            <!-- dog-product -->
            <h2 class="text-item">Thú Cưng Nổi Bật</h2>
            <div class="view-all-item">
                <a href="">Xem Tất Cả</a>
            </div>

            <div class="detail-item-product">
                <c:forEach items="${listDogBreed}" var="x">
                    <div class="detail-item">
                        <a href="listshoppet?species=Chó&sort=popular&giapet1=&giapet2=&breed=${x.breedId}&search=">
                            <img src="${x.breedImageBase64}" width="800" height="800" alt="${x.breedName}"/>
                            <p class="item-name">${x.breedName}</p>
                        </a>
                    </div>
                </c:forEach>
            </div>


            <!-- cat-product -->

            <h2 class="text-item">Thành Viên Mới</h2>
            <div class="view-all-item">
                <a href="">Xem Tất Cả</a>
            </div>

            <div class="detail-item-product">
                <c:forEach items="${listCatBreed}" var="x">
                    <div class="detail-item">
                        <a href="listshoppet?species=Mèo&sort=popular&giapet1=&giapet2=&breed=${x.breedId}&search=">
                            <img src="${x.breedImageBase64}" width="800" height="800" alt="${x.breedName}"/>
                            <p class="item-name">${x.breedName}</p>
                        </a>
                    </div>
                </c:forEach>
            </div>

            <h2 class="text-favo-item">Chăm BOSS cùng FPTPet</h2>
            <div class="blog-container">
                <div class="blog-card">
                    <div class="blog-image">
                        <img src="https://cdnjs.cloudflare.com/ajax/libs/simple-icons/3.0.1/placeholder.svg" alt="Chăm sóc thú cưng mùa hè">
                        <span class="blog-category">Chăm Sóc</span>
                    </div>
                    <div class="blog-content">
                        <h3>Cách chăm sóc thú cưng mùa hè nóng bức</h3>
                        <p class="blog-excerpt">Những lưu ý quan trọng giúp Boss vượt qua mùa hè một cách khỏe mạnh và thoải mái...</p>
                        <div class="blog-meta">
                            <span><i class="fas fa-calendar"></i> 10/06/2025</span>
                            <a href="blog/cham-soc-thu-cung-mua-he" class="read-more">Đọc tiếp <i class="fas fa-arrow-right"></i></a>
                        </div>
                    </div>
                </div>

                <div class="blog-card">
                    <div class="blog-image">
                        <img src="https://cdnjs.cloudflare.com/ajax/libs/simple-icons/3.0.1/placeholder.svg" alt="Dinh dưỡng cho chó con">
                        <span class="blog-category">Dinh Dưỡng</span>
                    </div>
                    <div class="blog-content">
                        <h3>Dinh dưỡng cho chó con dưới 6 tháng tuổi</h3>
                        <p class="blog-excerpt">Chế độ ăn uống khoa học giúp cún cưng phát triển toàn diện trong giai đoạn đầu đời...</p>
                        <div class="blog-meta">
                            <span><i class="fas fa-calendar"></i> 05/06/2025</span>
                            <a href="blog/dinh-duong-cho-con" class="read-more">Đọc tiếp <i class="fas fa-arrow-right"></i></a>
                        </div>
                    </div>
                </div>

                <div class="blog-card">
                    <div class="blog-image">
                        <img src="https://cdnjs.cloudflare.com/ajax/libs/simple-icons/3.0.1/placeholder.svg" alt="Huấn luyện mèo">
                        <span class="blog-category">Huấn Luyện</span>
                    </div>
                    <div class="blog-content">
                        <h3>5 bước huấn luyện mèo đi vệ sinh đúng chỗ</h3>
                        <p class="blog-excerpt">Phương pháp hiệu quả giúp Boss mèo nhanh chóng hình thành thói quen tốt...</p>
                        <div class="blog-meta">
                            <span><i class="fas fa-calendar"></i> 01/06/2025</span>
                            <a href="blog/huan-luyen-meo-di-ve-sinh" class="read-more">Đọc tiếp <i class="fas fa-arrow-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <!--favorite-product-->

            <h2 class="text-favo-item">Bạn Đồng hành tiềm Năng</h2>
            <div class="view-all-item">
                <a href="listshop?category=%search=&gia1=&gia2=&sort=popular">Xem Tất Cả</a>
            </div>

            <c:forEach items="${listProduct}" var="group">
                <div class="detail-favo-item-product">
                    <c:forEach items="${group}" var="x">
                        <div class="detail-favo-item">
                            <a href="displayproduct?id=${x.getProductId()}">
                                <div class="image-favo-container">
                                    <span class="sale-badge">Sale</span>
                                    <img src="${x.getProductImageBase64()}" width="800" height="800" alt="${x.productName}"/>
                                </div>
                                <p class="item-favo-name">${x.productName}</p>
                            </a>
                            <div class="tym-item">
                                <p class="price">${x.productPrice}đ</p>
                                <c:choose>
                                    <c:when test="${sessionScope.account == null}">
                                        <a href="account_login.jsp" class="favorite-icon">&#10084;</a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="favoriteUrl" value="favorite">
                                            <c:param name="location" value="homepage" />
                                            <c:param name="id" value="${x.productId}" />
                                        </c:url>
                                        <a href="${favoriteUrl}" class="favorite-icon"
                                           style="color: ${sessionScope.favoriteProducts.contains(x.productId) ? 'orangered' : 'orange'}">
                                            &#10084;
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:forEach>

            <h2 class="text-favo-item">Boss nào hợp với bạn?</h2>
            <div class="pet-match-section">
                <div class="pet-options-container">
                    <a href="" class="pet-option">
                        <div class="pet-image">
                            <img src="images/cat.png" alt="Mèo" class="pet-img">
                        </div>
                        <span class="pet-name">Mèo</span>
                    </a>

                    <a href="" class="pet-option">
                        <div class="pet-image">
                            <img src="images/hamster.png" alt="Chuột" class="pet-img">
                        </div>
                        <span class="pet-name">Chuột</span>
                    </a>

                    <a href="" class="pet-option">
                        <div class="pet-image">
                            <img src="images/dog.png" alt="Chó" class="pet-img">
                        </div>
                        <span class="pet-name">Chó</span>
                    </a>
                </div>
            </div>
        </div>



        <div class="about-section">
            <div class="about-column newsletter-column">
                <h3>Shop</h3>
                <ul>
                    <li><a href="">Dành Cho Chó</a></li>
                    <li><a href="">Dành Cho Mèo</a></li>
                    <li><a href="">Dành Cho Chuột</a></li>
                </ul>

                <!-- Phần đăng ký email được gộp vào đây -->
                <div class="newsletter-block">
                    <h3>Đăng Ký Nhận Tin</h3>
                    <p>Cập nhật thông tin về thú cưng và ưu đãi hấp dẫn</p>
                    <div class="newsletter-form">
                        <form action="" method="post">
                            <div class="newsletter-input-container">
                                <input type="email" name="email" placeholder="Email của bạn" required>
                                <button type="submit"><i class="fas fa-paper-plane"></i></button>
                            </div>
                        </form>
                    </div>
<!--                    <div class="newsletter-note">
                        <p>Bằng việc đăng ký, bạn đồng ý với <a href="">chính sách bảo mật</a> của chúng tôi</p>
                    </div>-->
                </div>
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