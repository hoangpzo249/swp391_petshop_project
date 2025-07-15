<%-- 
    Document   : shipper_orderPickup_page
    Created on : 8 July 2025, 11:54:12 pm
    Author     : HuyHoang
--%>
<%@taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nhận Đơn Hàng - PETFPT Shop</title>
        <link href="css/shipper_orderPickup_Page.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <div class="admin-header">
            <div class="logo-container">
                <a href="homepage">
                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                </a>
                <h1 class="admin-title">Hệ thống giao hàng</h1>
            </div>

            <div class="admin-profile">
                <c:choose>
                    <c:when test="${not empty sessionScope.userAccount.displayAccImage()}">
                        <img src="${sessionScope.userAccount.displayAccImage()}" width="45" height="45" class="table-avatar"/>
                    </c:when>
                    <c:otherwise>
                        <img src="images/support button/account.png" alt="Shipper Avatar"/>
                    </c:otherwise>
                </c:choose>
                <div class="admin-info">
                    <span class="admin-name">${sessionScope.userAccount.accFname} ${sessionScope.userAccount.accLname}</span>
                    <span class="admin-role">Shipper</span>
                </div>
                <div class="admin-actions">
                    <a href="profile" class="admin-action" title="Thông tin cá nhân">
                        <i class="fas fa-user-circle"></i>
                    </a>
                    <a href="logout" class="admin-action" title="Đăng xuất">
                        <i class="fas fa-sign-out-alt"></i>
                    </a>
                </div>
            </div>
        </div>

        <c:if test="${not empty successMessage}">
            <div class="alert-message">
                ${successMessage}
            </div>
            <c:remove var="successMessage" scope="session" />
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="alert-message error">
                ${errorMessage}
            </div>
            <c:remove var="errorMessage" scope="session" />
        </c:if>

        <div class="admin-container">
            <!-- Sidebar -->
            <div class="admin-sidebar">
                <div class="sidebar-header">
                    <h2 class="sidebar-title">SHIPPER PANEL</h2>
                    <p class="sidebar-subtitle">Quản lý giao hàng PETFPT Shop</p>
                </div>

                <div class="sidebar-menu">
                    <div class="menu-category">
                        <h5 class="category-title">Điều hướng</h5>
                        <a href="shipper_panel" class="sidebar-link">
                            <i class="fas fa-tachometer-alt"></i> Tổng quan
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Quản lý đơn hàng</h5>
                        <a href="shipper_panel?action=Pending%20Shipper" class="sidebar-link ${action eq 'Pending Shipper' ? 'active' : ''}">
                            <i class="fas fa-clipboard-list"></i> Đơn hàng cần giao
                            <c:choose>
                                <c:when test="${pendingCount eq 0}">

                                </c:when>
                                <c:otherwise>
                                    <span class="badge">${pendingCount}</span>
                                </c:otherwise>
                            </c:choose>
                        </a>
                        <a href="shipper_panel?action=Shipping" class="sidebar-link ${action eq 'Shipping' ? 'active' : ''}">
                            <i class="fas fa-truck"></i> Đang giao hàng
                            <c:choose>
                                <c:when test="${shippingCount eq 0}">
                                </c:when>
                                <c:otherwise>
                                    <span class="badge">${shippingCount}</span>
                                </c:otherwise>
                            </c:choose>
                        </a>
                        <a href="shipper_panel?action=Delivered" class="sidebar-link ${action eq 'Delivered' ? 'active' : ''}">
                            <i class="fas fa-check-circle"></i> Đã giao hàng
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Tài khoản</h5>
                        <a href="profile" class="sidebar-link">
                            <i class="fas fa-user-circle"></i> Thông tin cá nhân
                        </a>
                        <a href="profile?action=change-password" class="sidebar-link">
                            <i class="fas fa-key"></i> Đổi mật khẩu
                        </a>
                        <a href="logout" class="sidebar-link">
                            <i class="fas fa-sign-out-alt"></i> Đăng xuất
                        </a>
                    </div>
                </div>
            </div>

            <!-- Main Content -->
            <div class="admin-content">
                <!-- Order Pickup Header -->
                <div class="page-header">
                    <h1 class="page-title">
                        <i class="fas fa-clipboard-list"></i> Nhận đơn hàng
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="shipper_panel">Shipper</a></li>
                        <li>Nhận đơn hàng</li>
                    </ul>
                </div>

                <!-- Order Pickup Form -->
                <div class="pickup-container">
                    <div class="pickup-header">
                        <div class="pickup-icon">
                            <i class="fas fa-box-open"></i>
                        </div>
                        <div class="pickup-title">
                            <h2>Xác nhận giao đơn hàng #${orderDetail.orderId} thành công</h2>
                            <p>Vui lòng xác nhận rằng bạn đã giao đơn hàng này cho khách</p>
                        </div>
                    </div>

                    <div class="pickup-content">
                        <div class="order-details-container">
                            <div class="order-detail-section">
                                <h3 class="section-title"><i class="fas fa-info-circle"></i> Thông tin đơn hàng</h3>
                                <div class="detail-row">
                                    <div class="detail-group">
                                        <div class="detail-label">Mã đơn hàng</div>
                                        <div class="detail-value">#${orderDetail.orderId}</div>
                                    </div>
                                    <div class="detail-group">
                                        <div class="detail-label">Ngày đặt hàng</div>
                                        <div class="detail-value"><fmt:formatDate value="${orderDetail.orderDate}" pattern="HH:mm dd-MM-yyyy"/></div>
                                    </div>
                                </div>
                                <div class="detail-row">
                                    <div class="detail-group">
                                        <div class="detail-label">Phương thức thanh toán</div>
                                        <div class="detail-value">
                                            <c:choose>
                                                <c:when test="${orderDetail.paymentMethod eq 'Cash on Delivery'}">
                                                    Thanh toán khi nhận hàng
                                                </c:when>
                                                <c:when test="${orderDetail.paymentMethod eq 'Credit Card'}">
                                                    Thẻ tín dụng
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="detail-group">
                                        <div class="detail-label">Trạng thái thanh toán</div>
                                        <div class="detail-value">
                                            <span class="status-badge ${orderDetail.paymentStatus eq 'Paid' ? 'status-completed' : 'status-pending'}">
                                                ${orderDetail.paymentStatus eq 'Paid' ? 'Đã thanh toán' : 'Chưa thanh toán'}
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="detail-row">
                                    <div class="detail-group">
                                        <div class="detail-label">Tên khách hàng</div>
                                        <div class="detail-value">${orderDetail.customerName}</div>
                                    </div>
                                    <div class="detail-group">
                                        <div class="detail-label">Số điện thoại</div>
                                        <div class="detail-value">${orderDetail.customerPhone}</div>
                                    </div>
                                </div>
                                <div class="detail-row">
                                    <div class="detail-group full-width">
                                        <div class="detail-label">Địa chỉ giao hàng</div>
                                        <div class="detail-value address">${orderDetail.customerAddress}</div>
                                    </div>
                                </div>
                            </div>

                            <div class="order-detail-section">
                                <h3 class="section-title"><i class="fas fa-box"></i> Danh sách sản phẩm</h3>
                                <div class="order-items">

                                    <c:forEach items="${orderListDetail}" var="order">
                                        <c:if test="${order.orderId == orderDetail.orderId}">
                                            <div class="order-item">
                                                <div class="item-details">
                                                    <div class="item-name">${order.petName}</div>
                                                </div>
                                                <div class="item-total">
                                                    <span><fmt:formatNumber value="${order.petPrice}" pattern="#,###" /> ₫</span>
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <div class="order-summary">
                                    <div class="summary-row">
                                        <span>Tổng tiền hàng:</span>
                                        <span><fmt:formatNumber value="${orderDetail.totalPrice}" pattern="#,###" /> ₫</span>
                                    </div>
                                    <div class="summary-row">
                                        <span>Giảm giá:</span>
                                        <span>- <fmt:formatNumber value="${orderDetail.discountAmountAtApply}" pattern="#,###" /> ₫</span>
                                    </div>
                                    <div class="summary-row total">
                                        <span>Tổng thanh toán:</span>
                                        <span><fmt:formatNumber value="${orderDetail.totalPrice - orderDetail.discountAmountAtApply}" pattern="#,###" /> ₫</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="shipper-note-section">
                            <form action="shipper_panel" method="post">
                                <input type="hidden" name="action" value="delivered-order">
                                <input type="hidden" name="id" value="${orderDetail.orderId}">

                                <div class="confirm-checkbox">
                                    <input type="checkbox" name="confirmInfo">
                                    <label>
                                        Tôi xác nhận đã giao hàng thành công cho khách hàng.
                                    </label>
                                </div>

                                <div class="pickup-actions">
                                    <a href="shipper_panel" class="btn btn-outline">
                                        <i class="fas fa-arrow-left"></i> Quay lại
                                    </a>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-truck-loading"></i> Xác nhận giao đơn hàng thành công
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Admin Footer -->
        <div class="admin-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý giao hàng
        </div>
    </body>
</html>
