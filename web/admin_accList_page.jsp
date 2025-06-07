<%-- 
    Document   : admin_accList_page
    Created on : 7 June 2025, 10:34:43 am
    Author     : HuyHoang
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Tài khoản - PETFPT Shop</title>
        <link href="css/admin_accList_page.css" rel="stylesheet" type="text/css"/>
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
                        <a href="admin-panel" class="sidebar-link">
                            <i class="fas fa-tachometer-alt"></i> Tổng quan
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Quản lý tài khoản</h5>
                        <a href="admin-panel?action=account&type=all" class="sidebar-link active">
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
                <!-- Accounts List Header -->
                <div class="page-header">
                    <h1 class="page-title">
                        <i class="fas fa-users"></i> Quản lý tài khoản
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="admin-panel">Admin</a></li>
                        <li>Quản lý tài khoản</li>
                    </ul>
                </div>

                <!-- Accounts Table -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-users"></i> Danh sách tài khoản
                        </h3>
                        <div class="card-tools">
                            <button class="btn btn-primary" onclick="location.href = 'admin-panel?page=create-account'">
                                <i class="fas fa-user-plus"></i> Tạo tài khoản mới
                            </button>
                            <button class="btn btn-outline">
                                <i class="fas fa-file-export"></i> Xuất dữ liệu
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
                                        <th>ID</th>
                                        <th>Tài khoản</th>
                                        <th>Loại tài khoản</th>
                                        <th>Email</th>
                                        <th>Ngày tạo</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach items="${accList}" var="acc">
                                        <tr>
                                            <td>${acc.getAccId()}</td>
                                            <td>
                                                <div class="user-info">
                                                    <img src="images/support button/account.png" class="table-avatar" alt="User">
                                                    <div>
                                                        <div class="user-name">${acc.getAccUsername()}</div>
                                                        <div class="user-email">${acc.getAccEmail()}</div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>${acc.getAccRole()}</td>
                                            <td>${acc.getAccEmail()}</td>
                                            <td>${acc.getAccCreateDate()}</td>
                                            <c:choose>
                                                <c:when test="${acc.getAccStatus() eq 'Active'}">
                                                    <td><span class="status-badge status-active">${acc.getAccStatus()}</span></td>
                                                    </c:when>
                                                    <c:when test="${acc.getAccStatus() eq 'Inactive'}">
                                                    <td><span class="status-badge status-blocked">${acc.getAccStatus()}</span></td>
                                                    </c:when>
                                                </c:choose>
                                            <td>

                                                <div class="table-actions">

                                                    <a class="action-btn view-btn" title="Xem chi tiết" href = 'admin-panel?action=account&type=all&id=${acc.getAccId()}'>
                                                        <i class="fas fa-eye"></i>
                                                    </a>


                                                    <c:choose>

                                                        <c:when test="${acc.getAccRole() eq 'Admin'}">
                                                        </c:when>

                                                        <c:when test="${acc.getAccRole() eq 'Manager'}">
                                                            <a class="action-btn edit-btn" title="Sửa" href = 'admin-panel?action=account&type=all&act=update-role&id=${acc.getAccId()}'>
                                                                <i class="fas fa-edit"></i>
                                                            </a>
                                                            <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=all&act=ban-acc&id=${acc.getAccId()}'>
                                                                <i class="fas fa-lock"></i>
                                                            </a>
                                                            <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=all&act=reset-pass&id=${acc.getAccId()}'>
                                                                <i class="fas fa-key"></i>
                                                            </a>

                                                        </c:when>
                                                        <c:when test="${acc.getAccRole() eq 'Saler'}">
                                                            <a class="action-btn edit-btn" title="Sửa" href = 'admin-panel?action=account&type=all&act=update-role&id=${acc.getAccId()}'>
                                                                <i class="fas fa-edit"></i>
                                                            </a>
                                                            <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=all&act=ban-acc&id=${acc.getAccId()}'>
                                                                <i class="fas fa-lock"></i>
                                                            </a>
                                                            <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=all&act=reset-pass&id=${acc.getAccId()}'>
                                                                <i class="fas fa-key"></i>
                                                            </a>

                                                        </c:when>
                                                        <c:when test="${acc.getAccRole() eq 'Shipper'}">
                                                            <a class="action-btn edit-btn" title="Sửa" href = 'admin-panel?action=account&type=all&act=update-role&id=${acc.getAccId()}'>
                                                                <i class="fas fa-edit"></i>
                                                            </a>
                                                            <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=all&act=ban-acc&id=${acc.getAccId()}'>
                                                                <i class="fas fa-lock"></i>
                                                            </a>
                                                            <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=all&act=reset-pass&id=${acc.getAccId()}'>
                                                                <i class="fas fa-key"></i>
                                                            </a>
                                                        </c:when>

                                                        <c:otherwise>

                                                            <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=all&act=ban-acc&id=${acc.getAccId()}'>
                                                                <i class="fas fa-lock"></i>
                                                            </a>
                                                            <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=all&act=reset-pass&id=${acc.getAccId()}'>
                                                                <i class="fas fa-key"></i>
                                                            </a>

                                                        </c:otherwise>

                                                    </c:choose>
                                                </div>
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
        <div class="admin-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
    </body>
</html>