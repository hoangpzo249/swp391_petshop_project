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
        <link href="css/ai_chat.css" rel="stylesheet" type="text/css"/>
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

                <form action="listshoppet" method="get">

                    <input type="hidden" name="species" value="${param.species}" />
                    <input type="hidden" name="breed" value="${param.breed}" />
                    <input type="hidden" name="gender" value="${param.gender}" />
                    <input type="hidden" name="color" value="${param.color}" />
                    <input type="hidden" name="origin" value="${param.origin}" />
                    <input type="hidden" name="dobFrom" value="${param.dobFrom}" />
                    <input type="hidden" name="dobTo" value="${param.dobTo}" />
                    <input type="hidden" name="priceRange" value="${param.priceRange}" />
                    <input type="hidden" name="vaccination" value="${param.vaccination}" />
                    <div class="search">
                        <input type="text" name="search" value="${param.search}" placeholder="Tìm kiếm thú cưng ..." />
                        <button type="submit" class="search-button">
                            <img src="images/support button/search.png" width="20" height="20" alt="search" />
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
                                            <a href="displaysalesstatistic" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Seller</span>
                                            </a>
                                        </c:when>

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Manager'}">
                                            <a href="displayrevenuestatistic" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Manager</span>
                                            </a>
                                        </c:when>

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Shipper'}">
                                            <a href="shipper_panel" class="dropdown-item">
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
                                    <c:choose>
                                        <c:when test="${sessionScope.userAccount.accRole eq 'Customer'}">
                                            <a href="orders?status=Pending" class="dropdown-item">
                                                <i class="fas fa-shopping-bag"></i> 
                                                <span>Đơn hàng đã mua</span>
                                            </a>
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
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

                    <c:if test="${empty sessionScope.userAccount or sessionScope.userAccount.accRole eq 'Customer'}">

                        <div class="card">
                            <a href="displaycart">
                                <img src="images/card.png" width="50" height="50" alt="card"/>
                                <p class="cardtext">
                                    Giỏ Hàng
                                    <span id="cart-count" class="cartcount">${sessionScope.cartcount}</span>
                                </p>
                            </a>
                        </div>
                    </c:if>
                </div>
            </div>
            <div>
                <nav>
                    <ul class="menu">
                        <li><a href="homepage">Trang Chủ</a></li>
                        <li><a href="listshoppet">Thú Cưng</a></li>
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
        <c:if test="${not empty successMess}">
            <div class="alert-message">${successMess}</div>
            <c:remove var="successMess" scope="session" />
        </c:if>
        <c:if test="${not empty errMess}">
            <div class="alert-message error">${errMess}</div>
            <c:remove var="errMess" scope="session" />
        </c:if>

        <!-- banner -->
        <div class="banner">
            <a href="homepage">
                <img src="images/logo_banner/banner.png" alt=""/>
            </a>
        </div>

        <div class="box">

            <!-- dog-product -->
            <h2 class="text-item">Thú Cưng Nổi Bật</h2>
            <div class="view-all-item">
                <a href="listshoppet?species=&sort=popular">Xem Tất Cả</a>
            </div>

            <div class="detail-item-product">
                <c:forEach items="${listBreed}" var="b">
                    <div class="detail-item">
                        <a href="listshoppet?species=${b.breedSpecies}&breed=${b.breedId}&gender=&color=&origin=&priceRange=&dobFrom=&dobTo=&vaccination=">
                            <img src="${b.displayBreedImage()}" width="800" height="800" alt="${b.breedName}"/>
                            <p class="item-name">${b.breedName}</p>
                        </a>
                    </div>
                </c:forEach>
            </div>

            <!-- cat-product -->

            <h2 class="text-item">Thành Viên Mới</h2>
            <div class="view-all-item">
                <a href="listshoppet">Xem Tất Cả</a>
            </div>

            <div class="detail-item-product">
                <c:forEach items="${listPet}" var="x">
                    <div class="detail-item">
                        <a href="displaypet?id=${x.petId}">
                            <img src="${x.getFirstImage()}" alt="${x.petName}" class="main-image" />
                            <p class="item-name">${x.petName}</p>
                        </a>
                    </div>
                </c:forEach>
            </div>

            <h2 class="text-favo-item">Boss nào hợp với bạn?</h2>
            <div class="pet-match-section">
                <div class="pet-options-container">
                    <a href="listshoppet?species=Mèo&sort=popular" class="pet-option">
                        <div class="pet-image">
                            <img src="images/cat.png" alt="Mèo" class="pet-img">
                        </div>
                        <span class="pet-name">Mèo</span>
                    </a>

                    <a href="listshoppet?species=Chó&sort=popular" class="pet-option">
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

        <c:choose>
            <c:when test="${sessionScope.userAccount.accRole eq 'Admin' or sessionScope.userAccount.accRole eq 'Shipper' or sessionScope.userAccount.accRole eq 'Manager' or sessionScope.userAccount.accRole eq 'Seller'}">

            </c:when>
            <c:otherwise>
                <button class="chat-toggle-button" id="chatToggleButton" aria-label="Toggle AI Assistant Chat">
                    <i class="fas fa-comments"></i>
                </button>

                <div class="chatbox-container" id="chatboxContainer">
                    <div class="chatbox-header">
                        <h3>PetFPT</h3>
                        <button class="chatbox-close-button" id="chatboxCloseButton" aria-label="Close Chat">×</button>
                    </div>
                    <div class="chatbox-messages" id="chatboxMessages">
                    </div>
                    <div class="chatbox-input-area">
                        <form id="chatForm">
                            <input type="text" id="chatInput" placeholder="Ask me anything..." autocomplete="off" required>
                            <button type="submit" id="chatSendButton" aria-label="Send Message"><i class="fas fa-paper-plane"></i></button>
                        </form>
                    </div>
                </div>
                <script src="js/ai_chat.js"></script>
            </c:otherwise>
        </c:choose>
    </body>
</html>