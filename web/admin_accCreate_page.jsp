<%-- 
    Document   : admin_accCreate_page
    Created on : 8 June 2025, 8:29:41 pm
    Author     : HuyHoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tạo Tài Khoản Mới - PETFPT Shop</title>
        <link href="css/admin_accCreate_page.css" rel="stylesheet" type="text/css"/>
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

        <c:if test="${not empty successMess}">
            <div class="alert-message">
                ${successMess}
            </div>
            <c:remove var="successMess" scope="session" />
        </c:if>

        <c:if test="${not empty errMess}">
            <div class="alert-message error">
                ${errMess}
            </div>
            <c:remove var="errMess" scope="session" />
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
                        <a href="admin-panel?action=account&type=all" class="sidebar-link">
                            <i class="fas fa-users"></i> Tất cả tài khoản
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="admin-panel?action=create-account&type=customer" class="sidebar-link active">
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
                <!-- Create Account Page Header -->
                <div class="page-header">
                    <h1 class="page-title">
                        <i class="fas fa-user-plus"></i> Tạo tài khoản mới
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="admin-panel">Admin</a></li>
                        <li>Tạo tài khoản mới</li>
                    </ul>
                </div>

                <!-- Tab Selection -->
                <div class="account-type-tabs">
                    <a href="admin-panel?action=create-account&type=customer" class="account-tab">
                        <i class="fas fa-user"></i> Tài khoản khách hàng
                    </a>
                    <a href="admin-panel?action=create-account&type=staff" class="account-tab">
                        <i class="fas fa-briefcase"></i> Tài khoản nhân viên
                    </a>

                </div>

                <!-- Create Account Form Container -->
                <div class="create-account-container">
                    <c:choose>
                        <c:when test="${param.type == 'customer'}">
                            <!-- Customer Account Form -->
                            <div class="create-form-container">
                                <div class="form-header">
                                    <h3><i class="fas fa-user"></i> Tạo tài khoản khách hàng mới</h3>
                                    <p>Điền thông tin để tạo tài khoản khách hàng mới</p>
                                </div>

                                <form action="admin-panel" method="post" class="create-account-form">
                                    <input type="hidden" name="action" value="create-account">
                                    <input type="hidden" name="type" value="customer">

                                    <div class="form-section">
                                        <h3 class="section-title">
                                            <i class="fas fa-id-card"></i> Thông tin tài khoản
                                        </h3>
                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="username">Tên đăng nhập <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-user"></i></span>
                                                    <input type="text" id="username" name="username" class="form-control" required value="${username}">
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="email">Email <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-envelope"></i></span>
                                                    <input type="email" id="email" name="email" class="form-control" required value="${email}">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="password">Mật khẩu <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-lock"></i></span>
                                                    <input type="password" id="password" name="password" class="form-control" required value="${password}">
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="confirmPassword">Nhập lại mật khẩu <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-lock"></i></span>
                                                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required value="${confirmPassword}">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-section">
                                        <h3 class="section-title">
                                            <i class="fas fa-user-circle"></i> Thông tin cá nhân
                                        </h3>
                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="firstName">Họ <span class="required">*</span></label>
                                                <input type="text" id="firstName" name="firstName" class="form-control" required value="${fName}">
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="lastName">Tên <span class="required">*</span></label>
                                                <input type="text" id="lastName" name="lastName" class="form-control" required value="${lName}">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="phone">Số điện thoại <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-phone"></i></span>
                                                    <input type="tel" id="phone" name="phone" class="form-control" required value="${phone}">
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="birthday">Ngày sinh</label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-calendar"></i></span>
                                                    <input type="date" id="dob" name="dob" class="form-control" value="${dob}">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-full">
                                                <label for="address">Địa chỉ</label>
                                                <textarea id="address" name="address" class="form-control" rows="3"></textarea>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-actions">
                                        <button type="button" class="btn btn-outline" onclick="window.location.href = 'admin-panel'">
                                            <i class="fas fa-times"></i> Hủy
                                        </button>
                                        <button type="submit" class="btn btn-primary">
                                            <i class="fas fa-save"></i> Tạo tài khoản khách hàng
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <!-- Staff Account Form -->
                            <div class="create-form-container">
                                <div class="form-header">
                                    <h3><i class="fas fa-briefcase"></i> Tạo tài khoản nhân viên mới</h3>
                                    <p>Điền thông tin để tạo tài khoản nhân viên mới</p>
                                </div>

                                <form action="admin-panel" method="post" class="create-account-form">
                                    <input type="hidden" name="action" value="create-account">
                                    <input type="hidden" name="type" value="staff">

                                    <div class="form-section">
                                        <h3 class="section-title">
                                            <i class="fas fa-id-card"></i> Thông tin tài khoản
                                        </h3>
                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="username">Tên đăng nhập <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-user"></i></span>
                                                    <input type="text" id="username" name="username" class="form-control" required value="${username}">
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="email">Email <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-envelope"></i></span>
                                                    <input type="email" id="email" name="email" class="form-control" required value="${email}">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="password">Mật khẩu <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-lock"></i></span>
                                                    <input type="password" id="password" name="password" class="form-control" required value="${password}">
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="confirmPassword">Nhập lại mật khẩu <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-lock"></i></span>
                                                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required value="${confirmPassword}">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="role">Vai trò <span class="required">*</span></label>
                                                <div class="select-group">
                                                    <select id="role" name="role" class="form-control" required>
                                                        <option value="">-- Chọn vai trò --</option>
                                                        <option value="Manager">Manager</option>
                                                        <option value="Saler">Saler</option>
                                                        <option value="Shipper">Shipper</option>
                                                    </select>
                                                    <span class="select-arrow"><i class="fas fa-chevron-down"></i></span>
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="status">Trạng thái</label>
                                                <div class="select-group">
                                                    <select id="status" name="status" class="form-control">
                                                        <option value="Active">Hoạt động</option>
                                                        <option value="Inactive">Không hoạt động</option>
                                                    </select>
                                                    <span class="select-arrow"><i class="fas fa-chevron-down"></i></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-section">
                                        <h3 class="section-title">
                                            <i class="fas fa-user-circle"></i> Thông tin cá nhân
                                        </h3>
                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="firstName">Họ <span class="required">*</span></label>
                                                <input type="text" id="firstName" name="firstName" class="form-control" required value="${fName}">
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="lastName">Tên <span class="required">*</span></label>
                                                <input type="text" id="lastName" name="lastName" class="form-control" required value="${lName}">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="phone">Số điện thoại <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-phone"></i></span>
                                                    <input type="tel" id="phone" name="phone" class="form-control" required value="${phone}">
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="birthday">Ngày sinh</label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-calendar"></i></span>
                                                    <input type="date" id="dob" name="dob" class="form-control" value="${dob}">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-full">
                                                <label for="address">Địa chỉ</label>
                                                <textarea id="address" name="address" class="form-control" rows="3"></textarea>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="form-actions">
                                        <button type="button" class="btn btn-outline" onclick="window.location.href = 'admin-panel'">
                                            <i class="fas fa-times"></i> Hủy
                                        </button>
                                        <button type="submit" class="btn btn-primary">
                                            <i class="fas fa-save"></i> Tạo tài khoản nhân viên
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Admin Footer -->
        <div class="admin-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
    </body>
</html>