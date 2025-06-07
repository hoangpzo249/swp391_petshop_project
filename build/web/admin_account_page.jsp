<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tổng Quan Quản Lý - PETFPT Shop</title>
        <link href="css/admin_account_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <div class="admin-header">
            <div class="logo-container">
                <a href="homepage">
                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                </a>
                <h1 class="admin-title">Quản trị hệ thống</h1>
            </div>

            <div class="admin-profile">
                <img src="images/support button/account.png" alt="Admin Avatar"/>
                <div class="admin-info">
                    <span class="admin-name">${sessionScope.userAccount.accFname} ${sessionScope.userAccount.accLname}</span>
                    <span class="admin-role">Admin</span>
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
                    <h2 class="sidebar-title">ADMIN PANEL</h2>
                    <p class="sidebar-subtitle">Quản lý hệ thống PETFPT Shop</p>
                </div>

                <div class="sidebar-menu">
                    <div class="menu-category">
                        <h5 class="category-title">Điều hướng</h5>
                        <a href="admin-panel" class="sidebar-link active">
                            <i class="fas fa-tachometer-alt"></i> Tổng quan
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Quản lý tài khoản</h5>
                        <a href="admin-panel?action=account&type=all" class="sidebar-link">
                            <i class="fas fa-users"></i> Tất cả tài khoản
                        </a>
                        <a href="admin-panel?action=accounts&type=manager" class="sidebar-link">
                            <i class="fas fa-user-tie"></i> Quản lý (Manager)
                        </a>
                        <a href="admin-panel?action=accounts&type=saler" class="sidebar-link">
                            <i class="fas fa-user-tag"></i> Nhân viên bán hàng
                        </a>
                        <a href="admin-panel?action=accounts&type=shipper" class="sidebar-link">
                            <i class="fas fa-truck"></i> Nhân viên giao hàng
                        </a>
                        <a href="admin-panel?action=accounts&type=customer" class="sidebar-link">
                            <i class="fas fa-user"></i> Khách hàng
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Phân quyền</h5>
                        <a href="admin-panel?page=roles" class="sidebar-link">
                            <i class="fas fa-user-shield"></i> Quản lý phân quyền
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="admin-panel?page=create-account" class="sidebar-link">
                            <i class="fas fa-user-plus"></i> Tạo tài khoản mới
                        </a>
                        <a href="profile" class="sidebar-link">
                            <i class="fas fa-user-circle"></i> Tài khoản của tôi
                        </a>
                        <a href="admin-panel?action=change-password" class="sidebar-link">
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
                        <i class="fas fa-tachometer-alt"></i> Tổng quan hệ thống
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="admin-panel">Admin</a></li>
                        <li>Tổng quan</li>
                    </ul>
                </div>

                <!-- Statistics Cards -->
                <div class="stats-container">
                    <div class="stat-card">
                        <div class="stat-header">
                            <div class="stat-icon orange">
                                <i class="fas fa-users"></i>
                            </div>
                        </div>
                        <h3 class="stat-title">Tổng số tài khoản</h3>
                        <div class="stat-value">357</div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-header">
                            <div class="stat-icon blue">
                                <i class="fas fa-user-check"></i>
                            </div>
                        </div>
                        <h3 class="stat-title">Tài khoản đang hoạt động</h3>
                        <div class="stat-value">298</div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-header">
                            <div class="stat-icon green">
                                <i class="fas fa-lock"></i>
                            </div>
                        </div>
                        <h3 class="stat-title">Tài khoản bị khóa</h3>
                        <div class="stat-value">8</div>
                    </div>
                </div>

                <!-- Accounts Table -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-users"></i> Danh sách tài khoản gần đây
                        </h3>
                        <div class="card-tools">
                            <button class="btn btn-primary" onclick="location.href = 'admin-panel?action=account&type=all'">
                                <i class="fas fa-eye"></i> Xem tất cả
                            </button>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="filter-controls">
                            <div class="input-group">
                                <i class="fas fa-search"></i>
                                <input type="text" placeholder="Tìm kiếm người dùng..." id="searchUsers">
                            </div>
                            <div class="select-group">
                                <select id="filterRole">
                                    <option value="">Tất cả loại tài khoản</option>
                                    <option value="Admin">Admin</option>
                                    <option value="Manager">Manager</option>
                                    <option value="Saler">Saler</option>
                                    <option value="Shipper">Shipper</option>
                                    <option value="Customer">Khách hàng</option>
                                </select>
                            </div>
                            <div class="select-group">
                                <select id="filterStatus">
                                    <option value="">Tất cả trạng thái</option>
                                    <option value="active">Đang hoạt động</option>
                                    <option value="blocked">Đã khóa</option>
                                </select>
                            </div>
                        </div>
                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Người dùng</th>
                                        <th>Loại tài khoản</th>
                                        <th>Ngày đăng ký</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach items="${accListNew}" var="accnew">
                                        <tr>
                                            <td>
                                                <div class="user-info">
                                                    <img src="images/support button/account.png" class="table-avatar" alt="User">
                                                    <div>
                                                        <div class="user-name">${accnew.accUsername}</div>
                                                        <div class="user-email">${accnew.accEmail}</div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>${accnew.accRole}</td>
                                            <td>${accnew.accCreateDate}</td>
                                            <c:choose>
                                                <c:when test="${accnew.accStatus eq 'Active'}">
                                                    <td><span class="status-badge status-active">${accnew.accStatus}</span></td>
                                                    </c:when>
                                                    <c:when test="${accnew.accStatus eq 'Inactive'}">
                                                    <td><span class="status-badge status-blocked">${accnew.accStatus}</span></td>
                                                    </c:when>
                                                </c:choose>
                                            <td>
                                                <a class="action-btn view-btn" title="Xem chi tiết" href = 'admin-panel?action=account&type=all&id=${accnew.getAccId()}'>
                                                    <i class="fas fa-eye"></i>
                                                </a>

                                                <c:choose>

                                                    <c:when test="${accnew.getAccRole() eq 'Admin'}">
                                                    </c:when>

                                                    <c:when test="${accnew.getAccRole() eq 'Manager'}">
                                                        <a class="action-btn edit-btn" title="Sửa" href = 'admin-panel?action=account&type=all&act=update-role&id=${accnew.getAccId()}'>
                                                            <i class="fas fa-edit"></i>
                                                        </a>
                                                        <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=all&act=ban-acc&id=${accnew.getAccId()}'>
                                                            <i class="fas fa-lock"></i>
                                                        </a>
                                                        <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=all&act=reset-pass&id=${accnew.getAccId()}'>
                                                            <i class="fas fa-key"></i>
                                                        </a>

                                                    </c:when>
                                                    <c:when test="${accnew.getAccRole() eq 'Saler'}">
                                                        <a class="action-btn edit-btn" title="Sửa" href = 'admin-panel?action=account&type=all&act=update-role&id=${accnew.getAccId()}'>
                                                            <i class="fas fa-edit"></i>
                                                        </a>
                                                        <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=all&act=ban-acc&id=${accnew.getAccId()}'>
                                                            <i class="fas fa-lock"></i>
                                                        </a>
                                                        <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=all&act=reset-pass&id=${accnew.getAccId()}'>
                                                            <i class="fas fa-key"></i>
                                                        </a>

                                                    </c:when>
                                                    <c:when test="${accnew.getAccRole() eq 'Shipper'}">
                                                        <a class="action-btn edit-btn" title="Sửa" href = 'admin-panel?action=account&type=all&act=update-role&id=${accnew.getAccId()}'>
                                                            <i class="fas fa-edit"></i>
                                                        </a>
                                                        <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=all&act=ban-acc&id=${accnew.getAccId()}'>
                                                            <i class="fas fa-lock"></i>
                                                        </a>
                                                        <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=all&act=reset-pass&id=${accnew.getAccId()}'>
                                                            <i class="fas fa-key"></i>
                                                        </a>
                                                    </c:when>

                                                    <c:otherwise>

                                                        <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=all&act=ban-acc&id=${accnew.getAccId()}'>
                                                            <i class="fas fa-lock"></i>
                                                        </a>
                                                        <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=all&act=reset-pass&id=${accnew.getAccId()}'>
                                                            <i class="fas fa-key"></i>
                                                        </a>

                                                    </c:otherwise>

                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Admin Footer -->
        <div class="admin-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
    </body>
</html>