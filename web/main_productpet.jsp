<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PETFPT Shop</title>
    <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="css/head_about.css" rel="stylesheet"/>
    <link href="css/header_footer.css" rel="stylesheet"/>
    <link href="css/main_product.css?v=32" rel="stylesheet"/>
    <script src="js/scroll_chat.js"></script>
    <script src="js/readmore_product.js"></script>
</head>
<body>
    <!-- Header (unchanged) -->
    <div class="header">…</div>

    <!-- Product Detail -->
    <div class="container">
        <div class="product-wrapper">
            <!-- Left Column -->
            <div class="left-column">
                <!-- Main image now uses getFirstImage() -->
                <div class="main-image-container">
                    <div class="image-box">
                        <img src="${pet.firstImage}" 
                             alt="${pet.petName}" 
                             class="main-image"/>
                    </div>
                </div>
                <!-- Thumbnail carousel uses pet.images list -->
                <div class="thumbnails-container">
                    <button class="thumbnail-nav"><i class="fas fa-chevron-left"></i></button>
                    <div class="thumbnails">
                        <c:forEach var="img" items="${pet.images}">
                            <div class="thumbnail-box">
                                <img src="${img}" 
                                     alt="Thumbnail" 
                                     class="thumbnail-img" 
                                     onclick="showImage('${img}')"/>
                            </div>
                        </c:forEach>
                    </div>
                    <button class="thumbnail-nav"><i class="fas fa-chevron-right"></i></button>
                </div>
            </div>

            <!-- Right Column -->
            <div class="right-column">
                <h1 class="product-title">${pet.petName}</h1>
                <div class="price-section">
                    <span class="price">
                        <fmt:formatNumber value="${pet.petPrice}"
                                          type="currency"
                                          currencySymbol="₫"
                                          groupingUsed="true"/>
                    </span>
                </div>
                <form action="addtocart" method="GET" class="cartForm">
                    <input type="hidden" name="id" value="${pet.petId}"/>
                    <c:if test="${empty sessionScope.account 
                                  or sessionScope.account.accRole eq 'Customer'}">
                        <button type="submit" class="add-to-cart">
                            Thêm vào giỏ hàng
                        </button>
                    </c:if>
                </form>
                <div class="pet-info">
                    <p><strong>Giống:</strong> ${pet.breedName}</p>
                    <p><strong>Ngày sinh:</strong> 
                       <fmt:formatDate value="${pet.petDob}" pattern="dd/MM/yyyy"/></p>
                    <p><strong>Xuất xứ:</strong> ${pet.petOrigin}</p>
                    <p><strong>Màu sắc:</strong> ${pet.petColor}</p>
                    <p><strong>Tiêm vắc xin:</strong> 
                        <c:choose>
                            <c:when test="${pet.petVaccination == 1}">Đã tiêm</c:when>
                            <c:otherwise>Chưa tiêm</c:otherwise>
                        </c:choose>
                    </p>
                </div>
                <div class="status"><span>Tình trạng: Còn hàng</span></div>
                <div class="shipping-info">
                    <i class="fas fa-truck"></i><span>Miễn Phí Vận Chuyển</span>
                </div>
            </div>
        </div>

        <!-- Description -->
        <div class="description-section">
            <h2>Thông Tin Thú Cưng</h2>
            <div class="description-content collapsed" id="description-content">
                <p>${pet.petDescription}</p>
            </div>
            <button id="toggle-description-btn" class="toggle-description-btn">
                Đọc thêm
            </button>
        </div>

        <!-- Similar Pets -->
        <h2 class="text-favo-item">Thú Cưng Tương Tự</h2>
        <div class="detail-favo-item-product">
            <c:forEach items="${similarPets}" var="x">
                <div class="detail-favo-item">
                    <a href="displaypet?id=${x.petId}">
                        <div class="image-favo-container">
                            <span class="sale-badge">Sale</span>
                            <img src="${x.firstImage}" 
                                 width="200" height="200" 
                                 alt="${x.petName}" 
                                 style="object-fit: cover; border-radius: 8px;"/>
                        </div>
                        <div class="item-favo-name">${x.petName}</div>
                    </a>
                    <div class="tym-item">
                        <fmt:formatNumber value="${x.petPrice}"
                                          type="currency"
                                          currencySymbol="₫"
                                          groupingUsed="true"/>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- Toast, About, Footer, Lightbox, etc. (unchanged) -->
    <c:if test="${not empty sessionScope.cartMessage}">…</c:if>
    <div class="about-section">…</div>
    <div class="chat-scroll-container">…</div>
    <div id="lightbox" class="lightbox" onclick="this.style.display='none'">
        <img id="lightbox-img" src=""/>
    </div>
    <footer>© 2025 PETFPT - Đồng hành cùng bạn và thú cưng mỗi ngày!</footer>

    <script>
        function showImage(src) {
            document.getElementById("lightbox-img").src = src;
            document.getElementById("lightbox").style.display = "flex";
        }
    </script>
</body>
</html>
