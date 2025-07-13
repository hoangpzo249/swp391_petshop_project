<%-- 
    Document   : shipper_orderManage_page
    Created on : 9 July 2025, 9:06:53 pm
    Author     : HuyHoang
--%>
<%@taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shipper Dashboard - PETFPT Shop</title>
        <link href="css/shipper_account_page.css" rel="stylesheet" type="text/css"/>
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
                <!-- Dashboard Overview -->
                <div class="page-header">
                    <h1 class="page-title">
                        <i class="fas fa-tachometer-alt"></i> Tổng quan shipper
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="shipper_panel">Shipper</a></li>
                        <li>Quản lý đơn hàng</li>
                    </ul>
                </div>

                <!-- Pending Orders Table -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-clipboard-list"></i> Quản lý đơn hàng
                        </h3>
                    </div>
                    <div class="card-body">
                        <div class="filter-controls">
                            <form action="shipper-dashboard" method="get">
                                <div class="input-group">
                                    <i class="fas fa-search"></i>
                                    <input type="text" name="search" placeholder="Tìm kiếm đơn hàng..." value="${param.search}">
                                </div>
                            </form>
                        </div>
                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Mã đơn hàng</th>
                                        <th>Khách hàng</th>
                                        <th>Địa chỉ</th>
                                        <th>Ngày đặt</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${orderList}" var="order">
                                        <tr>
                                            <td>#${order.orderId}</td>
                                            <td>
                                                <div class="user-info">
                                                    <div>
                                                        <div class="user-name">${order.customerName}</div>
                                                        <div class="user-email">${order.customerPhone}</div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>${order.customerAddress}</td>
                                            <td><fmt:formatDate value="${order.orderDate}" pattern="HH:mm dd-MM-yyyy"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${order.orderStatus eq 'Pending Shipper'}">
                                                        <span class="status-badge status-pending">Chờ lấy hàng</span>
                                                    </c:when>
                                                    <c:when test="${order.orderStatus eq 'Shipping'}">
                                                        <span class="status-badge status-active">Đang giao</span>
                                                    </c:when>
                                                    <c:when test="${order.orderStatus eq 'Delivered'}">
                                                        <span class="status-badge status-completed">Đã giao</span>
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="table-actions">
                                                    <a class="action-btn view-btn" title="Xem chi tiết" href="shipper_panel?action=view&id=${order.orderId}">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                    <c:choose>
                                                        <c:when test="${order.orderStatus eq 'Pending Shipper'}">
                                                            <a class="action-btn edit-btn" title="Nhận đơn" href="shipper_panel?action=pickup-order&id=${order.orderId}">
                                                                <i class="fas fa-truck-loading"></i>
                                                            </a>
                                                            <a class="action-btn cancel-btn" title="Từ chối nhận đơn" href="shipper_panel?action=reject-order&id=${order.orderId}">
                                                                <i class="fas fa-times-circle"></i>
                                                            </a>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus eq 'Shipping'}">
                                                            <a class="action-btn complete-btn" title="Hoàn thành" href="shipper_panel?action=delivered-order&id=${order.orderId}">
                                                                <i class="fas fa-check"></i>
                                                            </a>
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty orderList}">
                                        <tr>
                                            <td colspan="6" style="text-align: center; padding: 30px;">Không có đơn hàng nào</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
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
