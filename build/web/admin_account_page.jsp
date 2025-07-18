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
                <c:choose>
                    <c:when test="${not empty sessionScope.userAccount.displayAccImage()}">
                        <img src="${sessionScope.userAccount.displayAccImage()}" width="45" height="45" class="table-avatar"/>
                    </c:when>
                    <c:otherwise>
                        <img src="images/support button/account.png" alt="Admin Avatar"/>
                    </c:otherwise>
                </c:choose>
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
                    </div>



                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="admin-panel?action=create-account&type=customer" class="sidebar-link">
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
                        <div class="stat-value">${totalAcc}</div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-header">
                            <div class="stat-icon blue">
                                <i class="fas fa-user-check"></i>
                            </div>
                        </div>
                        <h3 class="stat-title">Tài khoản đang hoạt động</h3>
                        <div class="stat-value">${totalActive}</div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-header">
                            <div class="stat-icon green">
                                <i class="fas fa-lock"></i>
                            </div>
                        </div>
                        <h3 class="stat-title">Tài khoản bị khóa</h3>
                        <div class="stat-value">${totalInactive}</div>
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
                            <form action="admin-panel">
                                <input type="hidden" name="action" value="account">

                                <div class="input-group">
                                    <i class="fas fa-search"></i>
                                    <input type="text" name="key" placeholder="Tìm kiếm người dùng..." value="${key}">

                                </div>
                                <button style="display: none" type="submit"></button>
                            </form>

                            <form action="admin-panel">
                                <input type="hidden" name="action" value="account">
                                <div class="select-group">
                                    <select id="filterRole" name="role" onchange="this.form.submit()">
                                        <option value="">Tất cả loại tài khoản</option>
                                        <option value="Admin" ${param.role == 'Admin' ? 'selected' : ''}>Admin</option>
                                        <option value="Manager" ${param.role == 'Manager' ? 'selected' : ''}>Manager</option>
                                        <option value="Seller" ${param.role == 'Seller' ? 'selected' : ''}>Seller</option>
                                        <option value="Shipper" ${param.role == 'Shipper' ? 'selected' : ''}>Shipper</option>
                                        <option value="Customer" ${param.role == 'Customer' ? 'selected' : ''}>Khách hàng</option>
                                    </select>
                                </div>
                            </form>

                            <form action="admin-panel">
                                <input type="hidden" name="action" value="account">
                                <div class="select-group">
                                    <select id="filterStatus" name="status" onchange="this.form.submit()" >
                                        <option value="">Tất cả trạng thái</option>
                                        <option value="Active" ${param.status == 'Active' ? 'selected' : ''}>Đang hoạt động</option>
                                        <option value="Inactive" ${param.status == 'Inactive' ? 'selected' : ''}>Đã khóa</option>
                                    </select>
                                </div>
                            </form>
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
                                                    <c:choose>
                                                        <c:when test="${not empty accnew.displayAccImage()}">
                                                            <img src="${accnew.displayAccImage()}" alt="Avatar" width="45" height="45" class="table-avatar"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="images/support button/account.png" class="table-avatar" alt="User">
                                                        </c:otherwise>
                                                    </c:choose>

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
                                                <a class="action-btn view-btn" title="Xem chi tiết" href = 'admin-panel?action=account&type=${accnew.accRole}&act=view&id=${accnew.getAccId()}'>
                                                    <i class="fas fa-eye"></i>
                                                </a>

                                                <c:choose>

                                                    <c:when test="${accnew.getAccRole() eq 'Admin'}">
                                                    </c:when>

                                                    <c:when test="${accnew.getAccRole() eq 'Manager'}">
                                                        <a class="action-btn edit-btn" title="Sửa" href = 'admin-panel?action=account&type=${accnew.accRole}&act=update-role&id=${accnew.accId}'>
                                                            <i class="fas fa-edit"></i>
                                                        </a>
                                                        <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=${accnew.accRole}&act=ban-acc&id=${accnew.accId}&check=${accnew.accStatus}'>
                                                            <i class="fas fa-lock"></i>
                                                        </a>
                                                        <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=${accnew.accRole}&act=reset-pass&id=${accnew.accId}'>
                                                            <i class="fas fa-key"></i>
                                                        </a>

                                                    </c:when>
                                                    <c:when test="${accnew.getAccRole() eq 'Seller'}">
                                                        <a class="action-btn edit-btn" title="Sửa" href = 'admin-panel?action=account&type=${accnew.accRole}&act=update-role&id=${accnew.accId}'>
                                                            <i class="fas fa-edit"></i>
                                                        </a>
                                                        <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=${accnew.accRole}&act=ban-acc&id=${accnew.accId}&check=${accnew.accStatus}'>
                                                            <i class="fas fa-lock"></i>
                                                        </a>
                                                        <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=${accnew.accRole}&act=reset-pass&id=${accnew.accId}'>
                                                            <i class="fas fa-key"></i>
                                                        </a>

                                                    </c:when>
                                                    <c:when test="${accnew.getAccRole() eq 'Shipper'}">
                                                    <!--                                                        <a class="action-btn edit-btn" title="Sửa" href = 'admin-panel?action=account&type=all&act=update-role&id=${accnew.accId}'>
                                                    <i class="fas fa-edit"></i>
                                                    </a>-->
                                                        <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=${accnew.accRole}&act=ban-acc&id=${accnew.accId}&check=${accnew.accStatus}'>
                                                            <i class="fas fa-lock"></i>
                                                        </a>
                                                        <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=${accnew.accRole}&act=reset-pass&id=${accnew.accId}'>
                                                            <i class="fas fa-key"></i>
                                                        </a>
                                                    </c:when>

                                                    <c:otherwise>

                                                        <a class="action-btn block-btn" title="Khóa" href = 'admin-panel?action=account&type=${accnew.accRole}&act=ban-acc&id=${accnew.accId}&check=${accnew.accStatus}'>
                                                            <i class="fas fa-lock"></i>
                                                        </a>
                                                        <a class="action-btn reset-btn" title="Đặt lại mật khẩu" href = 'admin-panel?action=account&type=${accnew.accRole}&act=reset-pass&id=${accnew.accId}'>
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