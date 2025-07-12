<%-- 
    Document   : customer_orderCancelled_page
    Created on : 11 July 2025, 12:46:31 am
    Author     : HuyHoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xác nhận hủy đơn hàng</title>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link href="css/header_footer.css" rel="stylesheet" type="text/css"/>
        <link href="css/customer_orderDetail_page.css" rel="stylesheet" type="text/css"/>
        <link href="css/ai_chat.css" rel="stylesheet" type="text/css"/>
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

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Manager'}">
                                            <a href="displaybreed" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Manager</span>
                                            </a>
                                        </c:when>

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Seller'}">
                                            <a href="displayorder" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Seller</span>
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
                        <li><a href="">Giới Thiệu</a></li>
                        <li><a href="">Liên Hệ</a></li>
                    </ul>
                </nav>
            </div>
        </div>

        <!-- Notification alerts -->
        <c:if test="${not empty successMessage}">
            <div class="success-alert">
                <i class="fas fa-check-circle"></i> ${successMessage}
            </div>
            <c:remove var="successMessage" scope="session" />
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="error-alert">
                <i class="fas fa-exclamation-triangle"></i> ${errorMessage}
            </div>
            <c:remove var="errorMessage" scope="session" />
        </c:if>

        <div class="orders-container">
            <div class="order-detail-container">
                <div class="order-detail-card">
                    <div class="order-detail-header">
                        <h1>Xác nhận hủy đơn hàng #${orderDetail.orderId}</h1>
                        <div class="back-button">
                            <a href="orders?status=${orderDetail.orderStatus}">
                                <i class="fas fa-arrow-left"></i> Quay lại
                            </a>
                        </div>
                    </div>
                    <div class="order-detail-content">
                        <div class="order-summary">
                            <div class="summary-item">
                                <div class="summary-label">
                                    <i class="far fa-calendar-alt"></i> Ngày đặt hàng
                                </div>
                                <div class="summary-value">
                                    <fmt:formatDate value="${orderDetail.orderDate}" pattern="HH:mm dd-MM-yyyy"/>
                                </div>
                            </div>

                            <div class="summary-item">
                                <div class="summary-label">
                                    <i class="fas fa-truck"></i> Trạng thái đơn hàng
                                </div>
                                <div class="summary-value">
                                    <div class="status-badge ${orderDetail.orderStatus.toLowerCase()}">
                                        <c:choose>
                                            <c:when test="${orderDetail.orderStatus eq 'Pending'}">
                                                <i class="fas fa-clock"></i> Chờ xác nhận
                                            </c:when>
                                            <c:when test="${orderDetail.orderStatus eq 'Rejected'}">
                                                <i class="fas fa-times"></i> Đã hủy
                                            </c:when>
                                            <c:when test="${orderDetail.orderStatus eq 'Cancelled'}">
                                                <i class="fas fa-user-times"></i> Đã hủy
                                            </c:when>
                                            <c:when test="${orderDetail.orderStatus eq 'Confirmed'}">
                                                <i class="fas fa-check"></i> Đã xác nhận
                                            </c:when>
                                            <c:when test="${orderDetail.orderStatus eq 'Pending Shipper'}">
                                                <i class="fas fa-user-clock"></i> Chờ shipper nhận
                                            </c:when>
                                            <c:when test="${orderDetail.orderStatus eq 'Shipping'}">
                                                <i class="fas fa-shipping-fast"></i> Đang giao
                                            </c:when>
                                            <c:when test="${orderDetail.orderStatus eq 'Delivered'}">
                                                <i class="fas fa-box-open"></i> Đã giao
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>

                            <div class="summary-item">
                                <div class="summary-label">
                                    <i class="fas fa-money-check-alt"></i> Phương thức thanh toán
                                </div>
                                <div class="summary-value">
                                    <c:choose>
                                        <c:when test="${orderDetail.paymentMethod eq 'Cash on Delivery'}">
                                            <span class="payment-method cod">
                                                <i class="fas fa-money-bill-wave"></i> Thanh toán khi nhận hàng
                                            </span>
                                        </c:when>
                                        <c:when test="${orderDetail.paymentMethod eq 'Credit Card'}">
                                            <span class="payment-method card">
                                                <i class="fas fa-credit-card"></i> Thẻ tín dụng
                                            </span>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>

                            <div class="summary-item">
                                <div class="summary-label">
                                    <i class="fas fa-check-circle"></i> Trạng thái thanh toán
                                </div>
                                <div class="summary-value">
                                    <c:choose>
                                        <c:when test="${orderDetail.paymentStatus eq 'Paid'}">
                                            <span class="payment-status paid">
                                                <i class="fas fa-check-circle"></i> Đã thanh toán
                                            </span>
                                        </c:when>
                                        <c:when test="${orderDetail.paymentStatus eq 'Unpaid'}">
                                            <span class="payment-status unpaid">
                                                <i class="fas fa-clock"></i> Chưa thanh toán
                                            </span>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                        </div>

                        <div class="order-info-sections">
                            <div class="order-section">
                                <div class="section-header">
                                    <h2><i class="fas fa-paw"></i> Thông tin thú cưng</h2>
                                </div>
                                <div class="section-content">
                                    <c:forEach items="${orderPet}" var="pet">
                                        <c:if test="${pet.orderId == orderDetail.orderId}">
                                            <div class="pet-card">
                                                <div class="pet-image">
                                                    <c:choose>
                                                        <c:when test="${pet.getImage() != null}">
                                                            <img src="${pet.getImage()}" alt="${pet.getImage()}">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="images/defaultcatdog.png" alt=""/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="pet-details">
                                                    <h3 class="pet-name">${pet.petName}</h3>
                                                    <p class="pet-info"><span>Giống:</span> ${pet.petBreed}</p>
                                                    <p class="pet-info"><span>Giới tính:</span> ${pet.petGender}</p>
                                                    <p class="pet-info"><span>Màu sắc:</span> ${pet.petColor}</p>
                                                    <p class="pet-info"><span>Ngày sinh:</span> <fmt:formatDate value="${pet.petDob}" pattern="dd-MM-yyyy"/></p>
                                                    <p class="pet-info"><span>Xuất xứ:</span> ${pet.petOrigin}</p>
                                                    <p class="pet-info"><span>Tiêm vắc-xin:</span> 
                                                        <c:choose>
                                                            <c:when test="${pet.petVacxin == 1}">
                                                                Đã tiêm
                                                            </c:when>
                                                            <c:when test="${pet.petVacxin == 0}">
                                                                Chưa tiêm
                                                            </c:when>
                                                        </c:choose>

                                                    </p>
                                                    <div class="pet-price">
                                                        <span>Giá:</span> <fmt:formatNumber value="${pet.petPrice}" pattern="#,##0"/> VNĐ
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                        <br>
                                    </c:forEach>
                                </div>
                            </div>

                            <div class="order-section">
                                <div class="section-header">
                                    <h2><i class="fas fa-user"></i> Thông tin khách hàng</h2>
                                </div>
                                <div class="section-content">
                                    <div class="info-group">
                                        <div class="info-item">
                                            <div class="info-label"><i class="fas fa-user"></i> Họ và tên</div>
                                            <div class="info-value">${orderDetail.customerName}</div>
                                        </div>
                                        <div class="info-item">
                                            <div class="info-label"><i class="fas fa-phone"></i> Số điện thoại</div>
                                            <div class="info-value">${orderDetail.customerPhone}</div>
                                        </div>
                                        <div class="info-item">
                                            <div class="info-label"><i class="fas fa-envelope"></i> Email</div>
                                            <div class="info-value">${orderDetail.customerEmail}</div>
                                        </div>
                                        <div class="info-item full-width">
                                            <div class="info-label"><i class="fas fa-map-marker-alt"></i> Địa chỉ giao hàng</div>
                                            <div class="info-value">${orderDetail.customerAddress}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="order-section">
                                <div class="section-header">
                                    <h2><i class="fas fa-receipt"></i> Thông tin thanh toán</h2>
                                </div>
                                <div class="section-content">
                                    <div class="price-summary">
                                        <c:forEach items="${orderPet}" var="pet">
                                            <c:if test="${pet.orderId == orderDetail.orderId}">
                                                <div class="price-item">
                                                    <div class="price-label">Giá thú cưng</div>
                                                    <div class="price-value"><fmt:formatNumber value="${pet.petPrice}" pattern="#,##0"/> VNĐ</div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                        <div class="price-item discount">
                                            <div class="price-label">Giảm giá</div>
                                            <div class="price-value">-<fmt:formatNumber value="${orderDetail.discountAmountAtApply}" pattern="#,##0"/> VNĐ</div>
                                        </div>
                                        <div class="price-item total">
                                            <div class="price-label">Tổng thanh toán</div>
                                            <div class="price-value"><fmt:formatNumber value="${orderDetail.totalPrice - orderDetail.discountAmountAtApply}" pattern="#,##0"/> VNĐ</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="order-actions-container">
                            <form action="orders" method="post" class="order-cancel-form">
                                <input type="hidden" name="status" value="Pending">
                                <input type="hidden" name="action" value="Cancelled">
                                <input type="hidden" name="id" value="${orderDetail.orderId}">

                                <div class="form-group">
                                    <label for="reasonCancel">Lý do hủy đơn hàng:</label>
                                    <textarea name="reasonCancel" rows="4" required placeholder="Nhập lý do hủy đơn..." class="cancel-textarea"></textarea>
                                </div>

                                <div class="confirm-checkbox">
                                    <input type="checkbox" name="confirmInfo" id="confirmInfo" required>
                                    <label for="confirmInfo">
                                        Tôi xác nhận hủy đơn hàng.
                                    </label>
                                </div>

                                <div class="order-actions">
                                    <a href="orders?status=${orderDetail.orderStatus}" class="btn back-btn">
                                        <i class="fas fa-arrow-left"></i> Quay lại
                                    </a>

                                    <button type="submit" class="btn cancel-btn">
                                        <i class="fas fa-times"></i> Hủy đơn hàng
                                    </button>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>

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
    </body>
</html>
