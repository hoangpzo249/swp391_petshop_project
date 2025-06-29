<%-- 
    Document   : admin_accChangePass_page
    Created on : 8 June 2025, 10:35:59 pm
    Author     : HuyHoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đổi Mật Khẩu - PETFPT Shop</title>
        <link href="css/admin_accChangePass_page.css" rel="stylesheet" type="text/css"/>
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
                        <a href="admin-panel?action=create-account&type=customer" class="sidebar-link">
                            <i class="fas fa-user-plus"></i> Tạo tài khoản mới
                        </a>
                        <a href="profile" class="sidebar-link">
                            <i class="fas fa-user-circle"></i> Tài khoản của tôi
                        </a>
                        <a href="admin-panel?action=change-password" class="sidebar-link active">
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
                <!-- Change Password Page Header -->
                <div class="page-header">
                    <h1 class="page-title">
                        <i class="fas fa-key"></i> Đổi mật khẩu
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="admin-panel">Admin</a></li>
                        <li>Đổi mật khẩu</li>
                    </ul>
                </div>

                <!-- Change Password Container -->
                <div class="change-password-container">
                    <div class="account-info-section">
                        <div class="account-avatar">
                            <c:choose>
                                <c:when test="${not empty sessionScope.userAccount.displayAccImage()}">
                                    <img src="${sessionScope.userAccount.displayAccImage()}" alt="User Avatar"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="images/support button/account.png" alt="User Avatar">
                                </c:otherwise>
                            </c:choose>
                            <div class="account-status online"></div>
                        </div>
                        <div class="account-details">
                            <h3 class="account-name">${sessionScope.userAccount.accFname} ${sessionScope.userAccount.accLname}</h3>
                            <p class="account-role">${sessionScope.userAccount.accRole}</p>
                            <p class="account-id">ID: #${sessionScope.userAccount.accId}</p>
                            <p class="account-username">@${sessionScope.userAccount.accUsername}</p>
                            <p class="account-email">
                                <i class="fas fa-envelope"></i> ${sessionScope.userAccount.accEmail}
                            </p>
                        </div>
                    </div>

                    <div class="password-form-section">
                        <div class="section-header">
                            <h3><i class="fas fa-lock"></i> Thay đổi mật khẩu</h3>
                            <p>Để bảo mật tài khoản, vui lòng không chia sẻ mật khẩu với người khác</p>
                        </div>

                        <form action="admin-panel" method="post" class="change-password-form">
                            <input type="hidden" name="action" value="change-password">

                            <div class="form-group">
                                <label for="currentPassword">Mật khẩu hiện tại <span class="required">*</span></label>
                                <div class="password-input-container">
                                    <span class="input-icon"><i class="fas fa-lock"></i></span>
                                    <input type="password" id="currentPassword" name="currentPassword" class="form-control" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="newPassword">Mật khẩu mới <span class="required">*</span></label>
                                <div class="password-input-container">
                                    <span class="input-icon"><i class="fas fa-lock"></i></span>
                                    <input type="password" id="newPassword" name="newPassword" class="form-control" required>

                                </div>

                            </div>

                            <div class="form-group">
                                <label for="confirmPassword">Nhập lại mật khẩu mới <span class="required">*</span></label>
                                <div class="password-input-container">
                                    <span class="input-icon"><i class="fas fa-lock"></i></span>
                                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>

                                </div>
                                <div class="form-feedback" id="confirmPassword-feedback"></div>
                            </div>

                            <div class="security-note">
                                <i class="fas fa-shield-alt"></i>
                                <p>Để bảo mật, sau khi đổi mật khẩu thành công, bạn sẽ được chuyển hướng đến trang đăng nhập và cần đăng nhập lại với mật khẩu mới.</p>
                            </div>

                            <div class="form-actions">
                                <button type="button" class="btn btn-outline" onclick="window.location.href = 'admin-panel'">
                                    <i class="fas fa-times"></i> Hủy
                                </button>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Cập nhật mật khẩu
                                </button>
                            </div>
                        </form>
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
