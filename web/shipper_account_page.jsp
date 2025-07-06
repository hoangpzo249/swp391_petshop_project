<%-- 
    Document   : shipper_account_page
    Created on : 6 July 2025, 10:52:22 am
    Author     : HuyHoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shipper Dashboard - PETFPT Shop</title>
        <link href="css/shipper_account_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
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
                        <a href="shipper-dashboard" class="sidebar-link active">
                            <i class="fas fa-tachometer-alt"></i> Tổng quan
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Quản lý đơn hàng</h5>
                        <a href="shipper-dashboard?action=pending" class="sidebar-link">
                            <i class="fas fa-clipboard-list"></i> Đơn hàng cần giao
                            <span class="badge">${pendingCount}</span>
                        </a>
                        <a href="shipper-dashboard?action=delivering" class="sidebar-link">
                            <i class="fas fa-truck"></i> Đang giao hàng
                            <span class="badge">${deliveringCount}</span>
                        </a>
                        <a href="shipper-dashboard?action=completed" class="sidebar-link">
                            <i class="fas fa-check-circle"></i> Đã giao hàng
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Tài khoản</h5>
                        <a href="profile" class="sidebar-link">
                            <i class="fas fa-user-circle"></i> Thông tin cá nhân
                        </a>
                        <a href="change-password" class="sidebar-link">
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
                        <li>Tổng quan</li>
                    </ul>
                </div>
                
                <!-- Status Toggle Section -->
                <div class="status-toggle-container">
                    <div class="status-info">
                        <div class="status-icon">
                            <i class="fas fa-user-clock"></i>
                        </div>
                        <div class="status-text">
                            <h3>Trạng thái hoạt động</h3>
                            <p>Bật để nhận đơn hàng mới từ cửa hàng</p>
                        </div>
                    </div>
                    
                    <div class="status-controls">
                        <span id="statusText" class="current-status ${shipperStatus eq 'Active' ? 'status-online' : 'status-offline'}">
                            ${shipperStatus eq 'Active' ? 'Đang hoạt động' : 'Không hoạt động'}
                        </span>
                        <label class="toggle-switch">
                            <input type="checkbox" id="statusSwitch" ${shipperStatus eq 'Active' ? 'checked' : ''} onchange="toggleStatus()">
                            <span class="switch-slider"></span>
                        </label>
                    </div>
                </div>

                <!-- Statistics Cards -->
                <div class="stats-container">
                    <div class="stat-card">
                        <div class="stat-header">
                            <div class="stat-icon orange">
                                <i class="fas fa-clipboard-list"></i>
                            </div>
                        </div>
                        <h3 class="stat-title">Đơn hàng cần giao</h3>
                        <div class="stat-value">${pendingCount}</div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-header">
                            <div class="stat-icon blue">
                                <i class="fas fa-truck"></i>
                            </div>
                        </div>
                        <h3 class="stat-title">Đơn hàng đang giao</h3>
                        <div class="stat-value">${deliveringCount}</div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-header">
                            <div class="stat-icon green">
                                <i class="fas fa-check-circle"></i>
                            </div>
                        </div>
                        <h3 class="stat-title">Đơn hàng đã giao</h3>
                        <div class="stat-value">${completedCount}</div>
                    </div>
                </div>

                <!-- Pending Orders Table -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-clipboard-list"></i> Đơn hàng cần giao
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
                            
                            <form action="shipper-dashboard" method="get">
                                <div class="select-group">
                                    <select name="status" onchange="this.form.submit()">
                                        <option value="">Tất cả trạng thái</option>
                                        <option value="pending" ${param.status == 'pending' ? 'selected' : ''}>Chờ xác nhận</option>
                                        <option value="confirmed" ${param.status == 'confirmed' ? 'selected' : ''}>Đã xác nhận</option>
                                        <option value="delivering" ${param.status == 'delivering' ? 'selected' : ''}>Đang giao</option>
                                        <option value="completed" ${param.status == 'completed' ? 'selected' : ''}>Đã giao</option>
                                    </select>
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
                                    <c:forEach items="${pendingOrders}" var="order">
                                        <tr>
                                            <td>#ORD-${order.orderId}</td>
                                            <td>
                                                <div class="user-info">
                                                    <c:choose>
                                                        <c:when test="${not empty order.customer.accImage}">
                                                            <img src="${order.customer.accImage}" class="table-avatar" alt="Customer">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="images/support button/account.png" class="table-avatar" alt="Customer">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div>
                                                        <div class="user-name">${order.customer.accFname} ${order.customer.accLname}</div>
                                                        <div class="user-email">${order.customer.accPhone}</div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>${order.orderAddress}</td>
                                            <td>${order.orderDate}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${order.orderStatus eq 'Pending'}">
                                                        <span class="status-badge status-pending">Chờ lấy hàng</span>
                                                    </c:when>
                                                    <c:when test="${order.orderStatus eq 'Delivering'}">
                                                        <span class="status-badge status-active">Đang giao</span>
                                                    </c:when>
                                                    <c:when test="${order.orderStatus eq 'Completed'}">
                                                        <span class="status-badge status-completed">Đã giao</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="status-badge status-cancelled">Đã hủy</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="table-actions">
                                                    <a class="action-btn view-btn" title="Xem chi tiết" href="shipper-dashboard?action=view&id=${order.orderId}">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                    <c:choose>
                                                        <c:when test="${order.orderStatus eq 'Pending'}">
                                                            <a class="action-btn edit-btn" title="Nhận đơn" href="shipper-dashboard?action=pickup&id=${order.orderId}">
                                                                <i class="fas fa-truck-loading"></i>
                                                            </a>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus eq 'Delivering'}">
                                                            <a class="action-btn complete-btn" title="Hoàn thành" href="shipper-dashboard?action=complete&id=${order.orderId}">
                                                                <i class="fas fa-check"></i>
                                                            </a>
                                                        </c:when>
                                                    </c:choose>
                                                    <a class="action-btn note-btn" title="Ghi chú" href="shipper-dashboard?action=note&id=${order.orderId}">
                                                        <i class="fas fa-sticky-note"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    
                                    <c:if test="${empty pendingOrders}">
                                        <tr>
                                            <td colspan="6" style="text-align: center; padding: 30px;">Không có đơn hàng nào</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                        
                        <c:if test="${totalPages > 1}">
                            <ul class="pagination">
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="shipper-dashboard?page=${currentPage - 1}${not empty param.search ? '&search='.concat(param.search) : ''}${not empty param.status ? '&status='.concat(param.status) : ''}">
                                        <i class="fas fa-angle-left"></i>
                                    </a>
                                </li>
                                
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                        <a class="page-link" href="shipper-dashboard?page=${i}${not empty param.search ? '&search='.concat(param.search) : ''}${not empty param.status ? '&status='.concat(param.status) : ''}">
                                            ${i}
                                        </a>
                                    </li>
                                </c:forEach>
                                
                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="shipper-dashboard?page=${currentPage + 1}${not empty param.search ? '&search='.concat(param.search) : ''}${not empty param.status ? '&status='.concat(param.status) : ''}">
                                        <i class="fas fa-angle-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </c:if>
                    </div>
                </div>
                
                <!-- Recent Deliveries Table -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-check-circle"></i> Đơn hàng gần đây
                        </h3>
                        <div class="card-tools">
                            <button class="btn btn-outline" onclick="location.href='shipper-dashboard?action=completed'">
                                <i class="fas fa-eye"></i> Xem tất cả
                            </button>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Mã đơn hàng</th>
                                        <th>Khách hàng</th>
                                        <th>Địa chỉ</th>
                                        <th>Ngày giao</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${recentOrders}" var="order">
                                        <tr>
                                            <td>#ORD-${order.orderId}</td>
                                            <td>
                                                <div class="user-info">
                                                    <c:choose>
                                                        <c:when test="${not empty order.customer.accImage}">
                                                            <img src="${order.customer.accImage}" class="table-avatar" alt="Customer">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="images/support button/account.png" class="table-avatar" alt="Customer">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div>
                                                        <div class="user-name">${order.customer.accFname} ${order.customer.accLname}</div>
                                                        <div class="user-email">${order.customer.accPhone}</div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>${order.orderAddress}</td>
                                            <td>${order.deliveryDate}</td>
                                            <td><span class="status-badge status-completed">Đã giao</span></td>
                                            <td>
                                                <div class="table-actions">
                                                    <a class="action-btn view-btn" title="Xem chi tiết" href="shipper-dashboard?action=view&id=${order.orderId}">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                    <a class="action-btn note-btn" title="Ghi chú" href="shipper-dashboard?action=note&id=${order.orderId}">
                                                        <i class="fas fa-sticky-note"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    
                                    <c:if test="${empty recentOrders}">
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
