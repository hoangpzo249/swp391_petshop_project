<%-- 
    Document   : customer_orderDetail_page
    Created on : 30 June 2025, 8:41:37 am
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
        <link href="css/customer_orderDetail_page.css" rel="stylesheet" type="text/css"/>
        <link href="css/invoice.css" rel="stylesheet" type="text/css"/>
        <link href="css/ai_chat.css" rel="stylesheet" type="text/css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
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
                                            <a href="displayrevenuestatistic" class="dropdown-item">
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
            <div class="order-detail-container">
                <div class="order-detail-card">
                    <div class="order-detail-header">
                        <h1>Chi tiết đơn hàng #${orderDetail.orderId}</h1>
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

                        <div class="order-progress">
                            <div class="progress-track">
                                <div class="progress-step ${orderDetail.orderStatus eq 'Pending' || orderDetail.orderStatus eq 'Confirmed' || orderDetail.orderStatus eq 'Pending Shipper' || orderDetail.orderStatus eq 'Shipping' || orderDetail.orderStatus eq 'Delivered' ? 'active' : ''}">
                                    <div class="step-icon">
                                        <i class="fas fa-clipboard-check"></i>
                                    </div>
                                    <div class="step-label">Đã đặt hàng</div>
                                    <div class="step-date">
                                        <fmt:formatDate value="${orderDetail.orderDate}" pattern="dd/MM/yyyy"/>
                                    </div>
                                </div>

                                <div class="progress-step ${orderDetail.orderStatus eq 'Confirmed' || orderDetail.orderStatus eq 'Pending Shipper' || orderDetail.orderStatus eq 'Shipping' || orderDetail.orderStatus eq 'Delivered' ? 'active' : ''}">
                                    <div class="step-icon">
                                        <i class="fas fa-check"></i>
                                    </div>
                                    <div class="step-label">Đã xác nhận</div>
                                </div>

                                <div class="progress-step ${orderDetail.orderStatus eq 'Shipping' || orderDetail.orderStatus eq 'Delivered' ? 'active' : ''}">
                                    <div class="step-icon">
                                        <i class="fas fa-shipping-fast"></i>
                                    </div>
                                    <div class="step-label">Đang giao hàng</div>
                                </div>

                                <div class="progress-step ${orderDetail.orderStatus eq 'Delivered' ? 'active' : ''}">
                                    <div class="step-icon">
                                        <i class="fas fa-box-open"></i>
                                    </div>
                                    <div class="step-label">Đã giao hàng</div>
                                    <div class="step-date">
                                        <c:if test="${orderDetail.orderStatus eq 'Delivered'}">
                                            <fmt:formatDate value="${orderDetail.deliveryDate}" pattern="dd/MM/yyyy"/>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="order-info-sections">
                            <div class="order-section">
                                <div class="section-header">
                                    <h2><i class="fas fa-paw"></i> Thông tin thú cưng</h2>
                                </div>
                                <div class="section-content">
                                    <div class="pet-card">
                                        <div class="pet-image">
                                            <img src="${order.petImage != null ? order.petImage : 'images/pets/defaultcatdog.jpg'}" alt="${order.petName}">
                                        </div>
                                        <div class="pet-details">
                                            <h3 class="pet-name">${order.petName}</h3>
                                            <p class="pet-info"><span>Tên:</span> ${order.petSpecies}</p>
                                            <p class="pet-info"><span>Giống:</span> ${order.petBreed}</p>
                                            <p class="pet-info"><span>Giới tính:</span> ${order.petGender}</p>
                                            <p class="pet-info"><span>Màu sắc:</span> ${order.petColor}</p>
                                            <p class="pet-info"><span>Ngày sinh:</span> <fmt:formatDate value="${order.petDob}" pattern="dd-MM-yyyy"/></p>
                                            <p class="pet-info"><span>Xuất xứ:</span> ${order.petOrigin}</p>
                                            <p class="pet-info"><span>Tiêm vắc-xin:</span> ${order.petVaccination ? 'Đã tiêm' : 'Chưa tiêm'}</p>
                                            <div class="pet-price">
                                                <span>Giá:</span> <fmt:formatNumber value="${order.totalPrice}" pattern="#,##0"/> VNĐ
                                            </div>
                                        </div>
                                    </div>
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
                                    </div>
                                </div>
                            </div>

                            <div class="order-section">
                                <div class="section-header">
                                    <h2><i class="fas fa-truck"></i> Thông tin giao hàng</h2>
                                </div>
                                <div class="section-content">
                                    <div class="info-group">
                                        <div class="info-item full-width">
                                            <div class="info-label"><i class="fas fa-map-marker-alt"></i> Địa chỉ giao hàng</div>
                                            <div class="info-value">${orderDetail.customerAddress}</div>
                                        </div>

                                        <c:if test="${orderDetail.orderStatus eq 'Shipping' || orderDetail.orderStatus eq 'Delivered'}">
                                            <div class="info-item">
                                                <div class="info-label"><i class="fas fa-user-tie"></i> Người giao hàng</div>
                                                <div class="info-value">${orderDetail.shipperName}</div>
                                            </div>
                                            <div class="info-item">
                                                <div class="info-label"><i class="fas fa-phone"></i> Số điện thoại</div>
                                                <div class="info-value">${orderDetail.shipperPhone}</div>
                                            </div>
                                        </c:if>

                                        <c:if test="${orderDetail.orderStatus eq 'Rejected'}">
                                            <div class="info-item full-width rejection-info">
                                                <div class="info-label"><i class="fas fa-times-circle"></i> Lý do hủy</div>
                                                <div class="info-value rejection-reason">${orderDetail.rejectionReason}</div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            Price Information 
                            <div class="order-section">
                                <div class="section-header">
                                    <h2><i class="fas fa-receipt"></i> Thông tin thanh toán</h2>
                                </div>
                                <div class="section-content">
                                    <div class="price-summary">
                                        <div class="price-item">
                                            <div class="price-label">Giá thú cưng</div>
                                            <div class="price-value"><fmt:formatNumber value="${orderDetail.petPrice}" pattern="#,##0"/> VNĐ</div>
                                        </div>
                                        <div class="price-item discount">
                                            <div class="price-label">Giảm giá</div>
                                            <div class="price-value">-<fmt:formatNumber value="${orderDetail.discountAmountAtApply}" pattern="#,##0"/> VNĐ</div>
                                        </div>
                                        <div class="price-item total">
                                            <div class="price-label">Tổng thanh toán</div>
                                            <div class="price-value"><fmt:formatNumber value="${orderDetail.totalPrice}" pattern="#,##0"/> VNĐ</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="invoice-content">
                            <c:choose>
                                <c:when test="${not empty invoice}">
                                    <div class="invoice-actions">
                                        <button id="downloadBtn" class="btn btn-primary">
                                            <i class="fas fa-download"></i> Tải hóa đơn (PDF)
                                        </button>
                                    </div>

                                    <div id="invoice-paper-container" class="invoice-paper">
                                        <div class="invoice-header">
                                            <div class="logo-section">
                                                <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                                                <p style="font-weight: 600; margin-top:10px;">PETFPT Shop</p>
                                                <p>Lô E2a-7, Đường D1, Khu Công nghệ cao, Long Thạnh Mỹ, Thành Phố Thủ Đức, Thành phố Hồ Chí Minh</p>
                                            </div>
                                            <div class="invoice-title-section">
                                                <h2>HÓA ĐƠN BÁN HÀNG</h2>
                                                <p>Mã hóa đơn: <strong>#${invoice.invoiceId}</strong></p>
                                                <p>Mã đơn hàng: #${invoice.order.orderId}</p>
                                                <p>Ngày xuất: <fmt:formatDate value="${invoice.issueDate}" pattern="HH:mm, dd/MM/yyyy"/></p>
                                            </div>
                                        </div>
                                        <div class="invoice-body">
                                            <div class="invoice-details">
                                                <div class="shop-details">
                                                    <h3 class="section-title">Thông tin người bán</h3>
                                                    <p><strong>Cửa hàng:</strong> PETFPT Shop</p>
                                                    <p><strong>Điện thoại:</strong> 0767.676.770</p>
                                                    <p><strong>Email:</strong> fptpet@gmail.com</p>
                                                </div>
                                                <div class="customer-details">
                                                    <h3 class="section-title">Thông tin khách hàng</h3>
                                                    <p><strong>Tên khách hàng:</strong> ${invoice.order.customerName}</p>
                                                    <p><strong>Địa chỉ:</strong> ${invoice.order.customerAddress}</p>
                                                    <p><strong>Điện thoại:</strong> ${invoice.order.customerPhone}</p>
                                                    <p><strong>Email:</strong> ${invoice.order.customerEmail}</p>
                                                </div>
                                            </div>
                                            <table class="items-table">
                                                <thead>
                                                    <tr>
                                                        <th>STT</th>
                                                        <th>Sản phẩm</th>
                                                        <th class="text-right">Giá</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="invoice-items-body">
                                                    <c:forEach var="item" items="${invoice.invoiceContents}" varStatus="loop">
                                                        <tr>
                                                            <td>${loop.count}</td>
                                                            <td>
                                                                <strong>${item.petName}</strong><br>
                                                                <small>Giống: ${item.petBreed} - Giới tính: ${item.petGender}</small>
                                                            </td>
                                                            <td class="text-right"><fmt:formatNumber value="${item.priceAtInvoice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <div id="detail-pagination" class="invoice-pagination"></div>
                                        </div>
                                        <div class="invoice-summary">
                                            <div class="payment-method-section">
                                                <h3 class="section-title">Phương thức thanh toán</h3>
                                                <p>${invoice.paymentMethod}</p>
                                            </div>
                                            <div class="invoice-totals">
                                                <table>
                                                    <tr>
                                                        <td class="label">Tổng tiền hàng:</td>
                                                        <td class="text-right"><fmt:formatNumber value="${invoice.totalAmount + invoice.order.discountAmountAtApply}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td class="label">Giảm giá:</td>
                                                        <td class="text-right">- <fmt:formatNumber value="${invoice.order.discountAmountAtApply}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                                    </tr>
                                                    <tr class="grand-total">
                                                        <td class="label">TỔNG CỘNG:</td>
                                                        <td class="text-right"><fmt:formatNumber value="${invoice.totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="invoice-footer">
                                            <p>Cảm ơn quý khách đã mua sắm tại <strong class="shop-name">PETFPT Shop</strong>!</p>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert-message error">Không tìm thấy thông tin hóa đơn. Vui lòng thử lại.</div>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="order-actions">
                            <a href="orders?status=${orderDetail.orderStatus}" class="btn back-btn">
                                <i class="fas fa-arrow-left"></i> Quay lại
                            </a>

                            <c:choose>
                                <c:when test="${order.orderStatus eq 'Pending'}">
                                    <button type="button" class="btn cancel-btn" onclick="showCancelModal()">
                                        <i class="fas fa-times"></i> Hủy đơn hàng
                                    </button>
                                </c:when>
                            </c:choose>
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
        <script>
                                        document.addEventListener('DOMContentLoaded', function () {
                                            const itemsTbody = document.getElementById('invoice-items-body');
                                            if (!itemsTbody)
                                                return;

                                            const invoiceElement = document.getElementById('invoice-paper-container');
                                            const items = Array.from(itemsTbody.getElementsByTagName('tr'));
                                            const paginationContainer = document.getElementById('detail-pagination');
                                            const downloadBtn = document.getElementById('downloadBtn');
                                            const invoiceId = "${invoice.invoiceId}";

                                            const ITEMS_PER_PAGE = 7;
                                            const totalItems = items.length;
                                            const totalPages = Math.ceil(totalItems / ITEMS_PER_PAGE);
                                            let currentPage = 1;

                                            function showPage(page) {
                                                currentPage = page;
                                                const startIndex = (page - 1) * ITEMS_PER_PAGE;
                                                const endIndex = startIndex + ITEMS_PER_PAGE;
                                                items.forEach((item, i) => {
                                                    item.style.display = (i >= startIndex && i < endIndex) ? '' : 'none';
                                                });
                                                updatePaginationControls();
                                            }

                                            function updatePaginationControls() {
                                                paginationContainer.innerHTML = '';
                                                if (totalPages <= 1)
                                                    return;

                                                for (let i = 1; i <= totalPages; i++) {
                                                    const pageLink = document.createElement('a');
                                                    pageLink.className = 'page-nav';
                                                    if (i === currentPage) {
                                                        pageLink.classList.add('active');
                                                    }
                                                    pageLink.href = '#';
                                                    pageLink.innerText = i;
                                                    pageLink.addEventListener('click', e => {
                                                        e.preventDefault();
                                                        showPage(i);
                                                    });
                                                    paginationContainer.appendChild(pageLink);
                                                }
                                            }

                                            function waitForRender() {
                                                return new Promise(resolve => requestAnimationFrame(resolve));
                                            }

                                            async function downloadInvoiceAsPdf() {
                                                downloadBtn.disabled = true;
                                                downloadBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang tạo PDF...';

                                                const {jsPDF} = window.jspdf;
                                                const pdf = new jsPDF('p', 'mm', 'a4');
                                                const pdfWidth = pdf.internal.pageSize.getWidth();
                                                const pdfHeight = pdf.internal.pageSize.getHeight();

                                                const originalPaginationDisplay = paginationContainer.style.display;
                                                if (totalPages > 1) {
                                                    paginationContainer.style.display = 'none';
                                                }

                                                for (let i = 1; i <= totalPages; i++) {
                                                    showPage(i);
                                                    await waitForRender();

                                                    const canvas = await html2canvas(invoiceElement, {scale: 2, useCORS: true});
                                                    const imgData = canvas.toDataURL('image/png');

                                                    const imgProps = pdf.getImageProperties(imgData);
                                                    const imgWidth = imgProps.width;
                                                    const imgHeight = imgProps.height;
                                                    const ratio = Math.min((pdfWidth - 10) / imgWidth, (pdfHeight - 10) / imgHeight);
                                                    const finalImgWidth = imgWidth * ratio;
                                                    const finalImgHeight = imgHeight * ratio;
                                                    const x = (pdfWidth - finalImgWidth) / 2;
                                                    const y = (pdfHeight - finalImgHeight) / 2;

                                                    if (i > 1) {
                                                        pdf.addPage();
                                                    }
                                                    pdf.addImage(imgData, 'PNG', x, y, finalImgWidth, finalImgHeight);
                                                }

                                                if (totalPages > 1) {
                                                    paginationContainer.style.display = originalPaginationDisplay;
                                                }

                                                pdf.save(`hoa-don-${orderDetail.orderId}.pdf`);

                                                showPage(1);
                                                downloadBtn.disabled = false;
                                                downloadBtn.innerHTML = '<i class="fas fa-download"></i> Tải hóa đơn (PDF)';
                                            }

                                            if (downloadBtn) {
                                                downloadBtn.addEventListener('click', downloadInvoiceAsPdf);
                                            }

                                            showPage(1);
                                        });
        </script>
    </body>
</html>
