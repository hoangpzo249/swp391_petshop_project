<%-- 
    Document   : admin_accResetPass_Page
    Created on : 7 June 2025, 7:29:06 pm
    Author     : HuyHoang
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đặt lại mật khẩu - PETFPT Shop</title>
        <link href="css/admin_accResetPass_Page.css" rel="stylesheet" type="text/css"/>
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
                        <a href="admin-panel?action=account&type=all" class="sidebar-link active">
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
                <!-- Page Header -->
                <div class="page-header">
                    <h1 class="page-title">
                        <i class="fas fa-key"></i> Đặt lại mật khẩu
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="admin-panel">Admin</a></li>
                        <li><a href="admin-panel?action=account&type=all">Quản lý tài khoản</a></li>
                        <li>Đặt lại mật khẩu</li>
                    </ul>
                </div>

                <!-- Reset Password Card -->
                <form action="admin-panel" method="post">
                      <input type="hidden" name="action" value="account">
                    <input type="hidden" name="type" value="${resetpass.accRole}">
                    <input type="hidden" name="act" value="reset-pass">
                    <input type="hidden" name="id" value="${resetpass.accId}">
                    <div class="card">
                        <div class="card-header">
                            <h3 class="card-title">
                                <i class="fas fa-lock"></i> Đặt lại mật khẩu tài khoản
                            </h3>
                        </div>
                        <div class="card-body">
                            <div class="reset-password-container">

                                <div class="account-info-section">
                                    <div class="account-info-card">
                                        <div class="account-profile">
                                            <img src="images/support button/account.png" alt="User Avatar" class="account-avatar">
                                            <h2 class="account-name">${resetpass.accFname} ${resetpass.accLname}</h2>
                                            <p class="account-username">@${resetpass.accUsername}</p>
                                            <c:choose>
                                                <c:when test="${resetpass.accRole eq 'Admin'}">
                                                    <div class="account-role-badge admin-role">
                                                        ${resetpass.accRole}
                                                    </div>
                                                </c:when>
                                                <c:when test="${resetpass.accRole eq 'Manager'}">
                                                    <div class="account-role-badge manager-role">
                                                        ${resetpass.accRole}
                                                    </div>
                                                </c:when>
                                                <c:when test="${resetpass.accRole eq 'Saler'}">
                                                    <div class="account-role-badge saler-role">
                                                        ${resetpass.accRole}
                                                    </div>
                                                </c:when>
                                                <c:when test="${resetpass.accRole eq 'Shipper'}">
                                                    <div class="account-role-badge shipper-role">
                                                        ${resetpass.accRole}
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="account-role-badge customer-role">
                                                        ${resetpass.accRole}
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                        <div class="account-details">
                                            <div class="detail-item">
                                                <div class="detail-label">ID</div>
                                                <div class="detail-value">${resetpass.accId}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Email</div>
                                                <div class="detail-value">${resetpass.accEmail}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Trạng thái</div>
                                                <c:choose>
                                                    <c:when test="${resetpass.accStatus eq 'Active'}">
                                                        <td><span class="status-badge status-active">${resetpass.accStatus}</span></td>
                                                        </c:when>
                                                        <c:when test="${resetpass.accStatus eq 'Inactive'}">
                                                        <td><span class="status-badge status-blocked">${resetpass.accStatus}</span></td>
                                                        </c:when>
                                                    </c:choose>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="security-notes">
                                        <h3><i class="fas fa-shield-alt"></i> Lưu ý bảo mật</h3>
                                        <ul>
                                            <li>Mật khẩu mới phải có ít nhất 8 ký tự</li>
                                            <li>Nên bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt</li>
                                            <li>Không nên sử dụng mật khẩu đã dùng trước đó</li>
                                            <li>Việc đặt lại mật khẩu sẽ khiến người dùng phải đăng nhập lại</li>
                                        </ul>
                                    </div>
                                </div>

                                <!-- Reset Password Form Section -->
                                <div class="reset-password-form-section">
                                    <div class="reset-form-card">
                                        <h3 class="form-title"><i class="fas fa-key"></i> Đặt mật khẩu mới</h3>



                                        <div class="form-group">
                                            <label for="newPassword">Mật khẩu mới</label>
                                            <div class="password-input-group">
                                                <input type="password" name="password" class="form-control" value="${password}">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="confirmPassword">Xác nhận mật khẩu</label>
                                            <div class="password-input-group">
                                                <input type="password" name="confirm_Password" class="form-control" value="${confirm_Password}">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="adminPassword">Mật khẩu Admin (Xác nhận hành động)</label>
                                            <div class="password-input-group">
                                                <input type="password" name="adminPassword" class="form-control" >
                                            </div>
                                        </div>

                                        <div class="form-buttons">
                                            <button type="button" class="btn btn-outline" onclick="location.href = 'admin-panel?action=account&type=all'">
                                                <i class="fas fa-arrow-left"></i> Hủy
                                            </button>
                                            <button type="submit" class="btn btn-primary">
                                                <i class="fas fa-save"></i> Lưu thay đổi
                                            </button>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </form>
            </div>
        </div>

        <div class="admin-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
    </body>
</html>