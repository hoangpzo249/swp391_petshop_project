
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PETFPT Shop</title>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link href="css/head_about.css" rel="stylesheet" type="text/css"/>
        <script src="js/scroll_chat.js" type="text/javascript"></script>

        <link href="css/main_product.css?v=17" rel="stylesheet" type="text/css"/>
        <script src="js/readmore_product.js" type="text/javascript"></script>

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
                        <li><a href="listshoppet?species=Dog&sort=popular&giapet1=&giapet2=&breed=&search=">Chó Cưng</a></li>
                        <li><a href="listshoppet?species=Cat&sort=popular&giapet1=&giapet2=&breed=&search=">Mèo Cưng</a></li>

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
        <c:set var="pet" value="${pet}" />
        <div class="container">
            <div class="product-wrapper">
                <!-- Left Column -->
                <div class="left-column">
                    <div class="main-image-container">
                        <div class="image-box">
                            <img src="${pet.getFirstImage()}" alt="${pet.petName}" class="main-image" />
                        </div>
                    </div>

                    <div class="thumbnails-container">
                        <button class="thumbnail-nav"><i class="fas fa-chevron-left"></i></button>

                        <div class="thumbnails">
                            <div class="thumbnail-box">
                                <img src="${pet.getFirstImage()}" alt="Thumbnail 1" class="thumbnail-img" />
                            </div>
                            <div class="thumbnail-box">
                                <img src="${pet.getFirstImage()}" alt="Thumbnail 2" class="thumbnail-img" />
                            </div>
                            <div class="thumbnail-box">
                                <img src="${pet.getFirstImage()}" alt="Thumbnail 3" class="thumbnail-img" />
                            </div>
                        </div>

                        <button class="thumbnail-nav"><i class="fas fa-chevron-right"></i></button>
                    </div>
                </div>


                <!-- Right Column -->
                <div class="right-column">
                    <h1 class="product-title">${pet.petName}</h1>
                    <div class="price-section">
                        <span class="price">
                            <fmt:formatNumber value="${pet.petPrice}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                        </span>

                    </div>


                    <form action="addtocart" method="GET" class="cartForm">




                        <div class="action-buttons">
                            <input type="hidden" name="id" value="${pet.petId}">

                            <button type="submit" class="add-to-cart">Thêm vào giỏ hàng</button>


                        </div>
                    </form>
                    <div class="pet-info">
                        <p><strong>Giống:</strong> ${pet.breedName}</p>
                        <p><strong>Ngày sinh:</strong> <fmt:formatDate value="${pet.petDob}" pattern="dd/MM/yyyy"/></p>
                        <p><strong>Xuất xứ:</strong> ${pet.petOrigin}</p>
                        <p><strong>Màu sắc:</strong> ${pet.petColor}</p>
                        <p><strong>Tiêm vắc xin:</strong> 
                            <c:choose>
                                <c:when test="${pet.petVaccination == 1}">Đã tiêm</c:when>
                                <c:otherwise>Chưa tiêm</c:otherwise>
                            </c:choose>
                        </p>
                    </div>


                    <div class="status">
                        <span>Tình trạng: Còn hàng</span>
                    </div>


                    <div class="shipping-info">
                        <i class="fas fa-truck"></i>
                        <span>Miễn Phí Vận Chuyển</span>
                    </div>
                </div>
            </div>

            <div class="description-section">
                <h2>Thông Tin Thú Cưng</h2>
                <div class="description-content collapsed" id="description-content">
                    <p>

                        ${pet.petDescription} 

                    </p>
                </div>
                <button class="toggle-description-btn" id="toggle-description-btn">Đọc thêm</button>
            </div>


            <h2 class="text-favo-item">Thú Cưng Tương Tự</h2>

            <div class="detail-favo-item-product">
                <c:forEach items="${similarPets}" var="x">

                    <div class="detail-favo-item">
                        <a href="displaypet?id=${x.getPetId()}">
                            <div class="image-favo-container">
                                <span class="sale-badge">Sale</span>
                                <img src="${x.getFirstImage()}" width="800" height="800" alt="${x.petName}"/>
                            </div>
                            <div class="item-favo-name">${x.petName}

                            </div>
                        </a>
                        <div class="tym-item">
                            <fmt:formatNumber value="${x.petPrice}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                        </div>
                    </div>
                </c:forEach>

            </div>


            <c:if test="${not empty sessionScope.cartMessage}">
                <c:set var="messageText" value="${sessionScope.cartMessage}" />
                <c:set var="isWarning" value="${fn:containsIgnoreCase(messageText, 'đã tồn tại') || fn:containsIgnoreCase(messageText, 'tồn tại')}" />

                <div class="toast ${isWarning ? 'toast-warning' : ''}">
                    <i class="fas ${isWarning ? 'fa-exclamation-triangle' : 'fa-check-circle'}"></i>
                    ${messageText}
                </div>

                <c:remove var="cartMessage" scope="session" />
            </c:if>








            <!--about-->
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

            <!-- scroll_chat -->
            <div class="chat-scroll-container">
                <button onclick="scroll()" id="scroll">
                    <i class="fas fa-chevron-up"></i>
                </button>

                <div class="chat-container">
                    <div id="chat-button" class="chat-button">
                        <img src="images/scroll_chat/chat.png" alt=""/>
                    </div>

                    <div id="zalo-button" class="zalo-button">
                        <a href="https://zalo.me/your-zalo-link" target="_blank">
                            <img src="images/scroll_chat/zalo.jpg" alt=""/>
                        </a>
                    </div>
                </div>
            </div>
            <footer>
                © 2025 PETFPT - Đồng hành cùng bạn và thú cưng mỗi ngày!
            </footer>
            <script src="js/scroll_chat.js"></script>


    </body>
</html>
