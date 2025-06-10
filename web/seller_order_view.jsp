<%-- 
    Document   : saler_order_view
    Created on : 23 May 2025, 10:04:28
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%-- Add this for formatting numbers/dates --%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Đơn Hàng - PETFPT Shop</title>
        <link href="css/seller_panel_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <div class="seller-header">
            <div class="logo-container">
                <a href="homepage">
                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                </a>
                <h1 class="seller-title">Quản trị hệ thống</h1>
            </div>

            <div class="seller-profile">
                <%-- Assuming user's image is available, otherwise use placeholder --%>
                <c:choose>
                    <c:when test="${not empty sessionScope.userAccount.accImage}">
                        <img src="data:image/jpeg;base64,${sessionScope.userAccount.getBase64Image()}" alt="Seller Avatar"/>
                    </c:when>
                    <c:otherwise>
                        <img src="images/support button/account.png" alt="Seller Avatar"/>
                    </c:otherwise>
                </c:choose>
                <div class="seller-info">
                    <span class="seller-name">${sessionScope.userAccount.accFname} ${sessionScope.userAccount.accLname}</span>
                    <span class="seller-role">Seller</span>
                </div>
                <div class="seller-actions">
                    <a href="profile" class="seller-action" title="Thông tin cá nhân">
                        <i class="fas fa-user-circle"></i>
                    </a>
                    <a href="logout" class="seller-action" title="Đăng xuất">
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


        <div class="seller-container">
            <!-- Sidebar -->
            <div class="seller-sidebar">
                <div class="sidebar-header">
                    <h2 class="sidebar-title">SELLER PANEL</h2>
                    <p class="sidebar-subtitle">Quản lý hệ thống PETFPT Shop</p>
                </div>

                <div class="sidebar-menu">
                    <div class="menu-category">
                        <h5 class="category-title">Điều hướng</h5>
                        <a href="sellerpanel" class="sidebar-link">
                            <i class="fas fa-tachometer-alt"></i> Tổng quan
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="seller-order-management" class="sidebar-link active"> <%-- Correct Link --%>
                            <i class="fas fa-bag-shopping"></i> Quản lý đơn hàng
                        </a>
                        <a href="seller-pet-management" class="sidebar-link"> <%-- Correct Link --%>
                            <i class="fas fa-dog"></i> Quản lý thú cưng
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="seller-create-pet" class="sidebar-link"> <%-- Correct Link --%>
                            <i class="fas fa-paw"></i> Đăng bán thú cưng
                        </a>
                        <a href="profile" class="sidebar-link">
                            <i class="fas fa-user-circle"></i> Tài khoản của tôi
                        </a>
                        <a href="change-password" class="sidebar-link"> <%-- Correct Link --%>
                            <i class="fas fa-key"></i> Đổi mật khẩu
                        </a>
                        <a href="logout" class="sidebar-link">
                            <i class="fas fa-sign-out-alt"></i> Đăng xuất
                        </a>
                    </div>
                </div>
            </div>

            <!-- ======================= START OF MAIN CONTENT (EDITED SECTION) ======================= -->
            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title">
                        <i class="fas fa-bag-shopping"></i> Quản lý đơn hàng
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="sellerpanel">Seller</a></li>
                        <li>Quản lý đơn hàng</li>
                    </ul>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-list-ul"></i> Danh sách đơn hàng
                        </h3>
                        <div class="card-tools">
                            <button class="btn btn-outline">
                                <i class="fas fa-file-export"></i> Xuất dữ liệu
                            </button>
                        </div>
                    </div>
                    <div class="card-body">
                        <!-- Filter Form -->
                        <form action="seller-order-management" method="get" id="filterForm">
                            <div class="filter-controls">
                                <div class="input-group">
                                    <i class="fas fa-search"></i>
                                    <input type="text" name="searchKey" placeholder="Tìm theo Mã ĐH, Tên khách hàng..." value="${param.searchKey}">
                                </div>

                                <div class="select-group">
                                    <select name="status" onchange="this.form.submit()">
                                        <option value="" ${empty param.status ? 'selected' : ''}>Tất cả trạng thái</option>
                                        <option value="Pending" ${param.status == 'Pending' ? 'selected' : ''}>Chờ xử lý</option>
                                        <option value="Confirmed" ${param.status == 'Confirmed' ? 'selected' : ''}>Đã xác nhận</option>
                                        <option value="Rejected" ${param.status == 'Rejected' ? 'selected' : ''}>Bị từ chối</option>
                                        <option value="Shipping" ${param.status == 'Shipping' ? 'selected' : ''}>Đang giao</option>
                                        <option value="Delivered" ${param.status == 'Delivered' ? 'selected' : ''}>Đã giao</option>
                                        <option value="Cancelled" ${param.status == 'Cancelled' ? 'selected' : ''}>Đã hủy</option>
                                    </select>
                                </div>

                                <div class="date-range-group">
                                    <input type="date" name="startDate" value="${param.startDate}">
                                    <span>đến</span>
                                    <input type="date" name="endDate" value="${param.endDate}">
                                </div>

                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-filter"></i> Lọc
                                </button>
                            </div>
                        </form>

                        <!-- Orders Table -->
                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Mã ĐH</th>
                                        <th>Ngày Đặt</th>
                                        <th>Khách Hàng</th>
                                        <th>Tổng Tiền</th>
                                        <th>Trạng Thái</th>
                                        <th>Thanh Toán</th>
                                        <th style="width: 15%;">Thao tác</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach items="${orderList}" var="order">
                                        <tr>
                                            <td><strong>#${order.orderId}</strong></td>
                                            <td>
                                                <fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy HH:mm"/>
                                            </td>
                                            <td>${order.customerName}</td>
                                            <td>
                                                <span class="price-value">
                                                    <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                                </span>
                                            </td>
                                            <td>
                                                <span class="status-badge status-${order.orderStatus.toLowerCase()}">
                                                    ${order.orderStatus}
                                                </span>
                                            </td>
                                            <td>
                                                <span class="status-badge status-payment-${order.paymentStatus.toLowerCase()}">
                                                    ${order.paymentStatus}
                                                </span>
                                            </td>
                                            <td>
                                                <div class="table-actions">
                                                    <a class="action-btn view-btn" title="Xem chi tiết" href="displayorderdetail?orderId=${order.orderId}">
                                                        <i class="fas fa-eye"></i>
                                                    </a>

                                                    <c:if test="${order.orderStatus == 'Pending'}">
                                                        <a class="action-btn confirm-btn" title="Xác nhận đơn hàng" href="seller-order-management?action=confirm&orderId=${order.orderId}">
                                                            <i class="fas fa-check"></i>
                                                        </a>
                                                        <a class="action-btn reject-btn" title="Từ chối đơn hàng" href="seller-order-management?action=reject&orderId=${order.orderId}">
                                                            <i class="fas fa-times"></i>
                                                        </a>
                                                    </c:if>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty orderList}">
                                        <tr>
                                            <td colspan="7" style="text-align: center; padding: 30px;">
                                                Không tìm thấy đơn hàng nào phù hợp.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                        <%-- Add Pagination here if needed --%>
                    </div>
                </div>
            </div>
            <!-- ======================== END OF MAIN CONTENT (EDITED SECTION) ======================== -->
        </div>
        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
    </body>
</html>