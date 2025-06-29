<%-- 
    Document   : customer_order_page
    Created on : 24 June 2025, 11:17:35 pm
    Author     : HuyHoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lịch sử đơn hàng</title>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link href="css/header_footer.css" rel="stylesheet" type="text/css"/>
        <link href="css/customer_order_page.css" rel="stylesheet" type="text/css"/>
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
                                            <a href="manager-panel" class="dropdown-item">
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
                                            <a href="shipper-panel" class="dropdown-item">
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
                                            <a href="orders?status=pending" class="dropdown-item">
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
        <c:if test="${not empty requestScope.successMessage}">
            <div class="success-alert">
                <i class="fas fa-check-circle"></i> ${requestScope.successMessage}
            </div>
            <c:remove var="successMessage" scope="request" />
        </c:if>

        <c:if test="${not empty requestScope.errorMessage}">
            <div class="error-alert">
                <i class="fas fa-exclamation-triangle"></i> ${requestScope.errorMessage}
            </div>
            <c:remove var="errorMessage" scope="request" />
        </c:if>

        <div class="orders-container">
            <div class="orders-card">
                <div class="orders-header">
                    <h1 class="orders-title">Lịch sử đơn hàng</h1>
                    <p class="orders-subtitle">Theo dõi và quản lý các đơn hàng của bạn</p>
                </div>

                <div class="orders-content">
                    <div class="order-tabs">
<!--                        <a href="orders?status=all" class="${empty param.status || param.status eq 'all' ? 'active' : ''}">
                            <i class="fas fa-list-ul"></i> Tất cả
                        </a>-->
                        <a href="orders?status=pending" class="${param.status eq 'pending' ? 'active' : ''}">
                            <i class="fas fa-clock"></i> Chờ xác nhận
                        </a>
                        <a href="orders?status=confirmed" class="${param.status eq 'confirmed' ? 'active' : ''}">
                            <i class="fas fa-check"></i> Đã xác nhận
                        </a>
                        <a href="orders?status=pendingShipper" class="${param.status eq 'pendingShipper' ? 'active' : ''}">
                            <i class="fas fa-user-clock"></i> Chờ shipper nhận
                        </a>
                        <a href="orders?status=shipping" class="${param.status eq 'shipping' ? 'active' : ''}">
                            <i class="fas fa-shipping-fast"></i> Đang giao
                        </a>
                        <a href="orders?status=delivered" class="${param.status eq 'delivered' ? 'active' : ''}">
                            <i class="fas fa-box-open"></i> Đã giao
                        </a>
                        <a href="orders?status=rejected" class="${param.status eq 'rejected' ? 'active' : ''}">
                            <i class="fas fa-times"></i> Đã hủy
                        </a>
                    </div>


                    <div class="orders-list">
                        <c:choose>
                            <c:when test="${param.status eq 'pending'}">
                                <div class="order-search">
                                    <form action="orders" method="GET">
                                        <input type="hidden" name="status" value="${param.status}">
                                        <div class="search-container">
                                            <input type="text" name="keyword" placeholder="Tìm kiếm theo mã đơn hàng..." value="${param.keyword}">
                                            <button type="submit">
                                                <i class="fas fa-search"></i>
                                            </button>
                                        </div>
                                    </form>
                                </div> 
                                <c:forEach items="${orderList}" var="order">

                                    <div class="order-item">
                                        <div class="order-header">
                                            <div class="order-id">
                                                <span class="label">Mã đơn hàng:</span>
                                                <span class="value">#${order.orderId}</span>
                                            </div>

                                            <div class="order-date">
                                                <i class="far fa-calendar-alt"></i>
                                                <span><fmt:formatDate value="${order.orderDate}" pattern="HH:mm dd-MM-yyyy"/></span>
                                            </div>

                                            <c:choose>
                                                <c:when test="${order.orderStatus eq 'Pending'}">
                                                    <div class="order-status pending">
                                                        <i class="fas fa-clock"></i>Chờ xác nhận
                                                    </div>
                                                </c:when>
                                                <c:when test="${order.orderStatus eq 'Rejected'}">
                                                    <div class="order-status rejected">
                                                        <i class="fas fa-times"></i>Đã hủy
                                                    </div>
                                                </c:when>
                                                <c:when test="${order.orderStatus eq 'Confirmed'}">
                                                    <div class="order-status confirmed">
                                                        <i class="fas fa-check"></i>Đã xác nhận
                                                    </div>
                                                </c:when>
                                                <c:when test="${order.orderStatus eq 'Shipping'}">
                                                    <div class="order-status shipping">
                                                        <i class="fas fa-shipping-fast"></i>Đang giao
                                                    </div>
                                                </c:when>
                                                <c:when test="${order.orderStatus eq 'Delivered'}">
                                                    <div class="order-status delivered">
                                                        <i class="fas fa-box-open"></i>Đã giao
                                                    </div>
                                                </c:when>
                                            </c:choose>
                                        </div>


                                        <div class="order-products">
                                            <div class="order-product">
                                                <div class="product-image">
                                                    <img src="images/pets/dog1.jpg" alt="Golden Retriever">
                                                </div>
                                                <div class="product-details">
                                                    <h4 class="product-name">${order.petName}</h4>
                                                    <!--<p class="product-price">${order.totalPrice} VNĐ</p>-->
                                                    <div class="product-price">
                                                        <fmt:formatNumber  value="${order.totalPrice}" pattern="#,##0" /> VNĐ
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="order-details">
                                            <div class="shipping-info">
                                                <h4><i class="fas fa-map-marker-alt"></i> Thông tin giao hàng</h4>
                                                <p><span class="label">Người nhận:</span> ${order.customerName}</p>
                                                <p><span class="label">Số điện thoại:</span> ${order.customerPhone}</p>
                                                <p><span class="label">Địa chỉ:</span> ${order.customerAddress}</p>
                                            </div>

                                            <div class="payment-info">
                                                <h4><i class="fas fa-money-check-alt"></i> Thông tin thanh toán</h4>
                                                <p><span class="label">Phương thức:</span> 
                                                    <c:choose>
                                                        <c:when test="${order.paymentMethod eq 'Cash on Delivery'}">
                                                            Thanh toán khi nhận hàng
                                                        </c:when>
                                                    </c:choose>
                                                </p>
                                                <p><span class="label">Trạng thái:</span>
                                                    <c:choose>
                                                        <c:when test="${order.paymentStatus eq 'Paid'}">
                                                            <span class="payment-status paid">
                                                                Đã thanh toán
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${order.paymentStatus eq 'Unpaid'}">
                                                            <span class="payment-status unpaid">
                                                                Chưa thanh toán
                                                            </span>
                                                        </c:when>
                                                    </c:choose>
                                                </p>
                                                <p class="total-price"><span class="label">Tổng tiền:</span> <span class="value"><fmt:formatNumber  value="${order.totalPrice}" pattern="#,##0" /> VNĐ</span></p>
                                            </div>
                                        </div>

                                        <div class="order-actions">
                                            <a href="orders?action=view&id=10" class="btn view-btn">
                                                <i class="fas fa-eye"></i> Xem chi tiết
                                            </a>
                                            <c:choose>
                                                <c:when test="${order.orderStatus eq 'Pending'}">
                                                    <a href="#" class="btn cancel-btn">
                                                        <i class="fas fa-times"></i> Hủy đơn hàng
                                                    </a>
                                                </c:when>
                                                <c:when test="${order.orderStatus eq 'Rejected'}">
                                                    <div class="rejection-reason">
                                                        <span class="label">Lý do hủy:</span>
                                                        <span class="value">${order.rejectionReason}</span>
                                                    </div>
                                                </c:when>
                                                <c:when test="${order.orderStatus eq 'Confirmed'}">
                                                </c:when>
                                                <c:when test="${order.orderStatus eq 'Shipping'}">
                                                </c:when>
                                                <c:when test="${order.orderStatus eq 'Delivered'}">
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                        </c:choose>

                    </div>
                    <c:if test="${empty orderList}">
                        <div class="empty-orders">
                            <i class="fas fa-shopping-bag"></i>
                            <p>Bạn chưa có đơn hàng nào</p>
                            <a href="listshoppet" class="shop-now-btn">Mua sắm ngay</a>
                        </div>
                    </c:if>
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
    </body>
</html>