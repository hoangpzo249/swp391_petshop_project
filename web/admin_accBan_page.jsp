<%-- 
    Document   : admin_accBan_page
    Created on : 8 June 2025, 1:59:26 pm
    Author     : HuyHoang
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Khóa tài khoản - PETFPT Shop</title>
        <link href="css/admin_accBan_page.css" rel="stylesheet" type="text/css"/>
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
                    <span class="admin-role1">Admin</span>
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
                        <i class="fas fa-user-lock"></i> Khóa tài khoản
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="admin-panel">Admin</a></li>
                        <li><a href="admin-panel?action=account&type=all">Quản lý tài khoản</a></li>
                        <li>Khóa tài khoản</li>
                    </ul>
                </div>

                <!-- Ban Account Card -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-lock"></i> Xác nhận khóa tài khoản
                        </h3>
                    </div>
                    <div class="card-body">
                        <div class="ban-account-container">
                            <!-- Account Info Section -->
                            <div class="account-info-section">
                                <div class="account-info-card">
                                    <div class="account-profile">
                                        <img src="images/support button/account.png" alt="User Avatar" class="account-avatar">
                                        <h2 class="account-name">${banacc.accFname} ${banacc.accLname}</h2>
                                        <p class="account-username">@${banacc.accUsername}</p>
                                        <c:choose>
                                            <c:when test="${banacc.accRole eq 'Admin'}">
                                                <div class="account-role-badge admin-role">
                                                    ${banacc.accRole}
                                                </div>
                                            </c:when>
                                            <c:when test="${banacc.accRole eq 'Manager'}">
                                                <div class="account-role-badge manager-role">
                                                    ${banacc.accRole}
                                                </div>
                                            </c:when>
                                            <c:when test="${banacc.accRole eq 'Seller'}">
                                                <div class="account-role-badge Seller-role">
                                                    ${banacc.accRole}
                                                </div>
                                            </c:when>
                                            <c:when test="${banacc.accRole eq 'Shipper'}">
                                                <div class="account-role-badge shipper-role">
                                                    ${banacc.accRole}
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="account-role-badge customer-role">
                                                    ${banacc.accRole}
                                                </div>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:choose>
                                            <c:when test="${banacc.accStatus eq 'Active'}">
                                                <div class="account-status-badge status-active">
                                                    ${banacc.accStatus}
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="account-status-badge status-blocked">
                                                    ${banacc.accStatus}
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="account-details">
                                        <div class="detail-item">
                                            <div class="detail-label">ID</div>
                                            <div class="detail-value">${banacc.accId}</div>
                                        </div>
                                        <div class="detail-item">
                                            <div class="detail-label">Email</div>
                                            <div class="detail-value">${banacc.accEmail}</div>
                                        </div>
                                        <div class="detail-item">
                                            <div class="detail-label">Ngày tạo</div>
                                            <div class="detail-value">${banacc.accCreateDate}</div>
                                        </div>
                                    </div>
                                </div>

                            </div>

                            <!-- Ban Form Section -->
                            <div class="ban-form-section">
                                <div class="ban-form-card">
                                    <c:choose>
                                        <c:when test="${banacc.accStatus eq 'Active'}">
                                            <form action="admin-panel" method="post">
                                                <input type="hidden" name="action" value="account">
                                                <input type="hidden" name="type" value="${banacc.accRole}">
                                                <input type="hidden" name="act" value="ban-acc">
                                                <input type="hidden" name="id" value="${banacc.accId}">
                                                <input type="hidden" name="check" value="${banacc.accStatus}">
                                                <h3 class="form-title danger"><i class="fas fa-lock"></i> Khóa tài khoản này</h3>
                                                <div class="ban-warning">
                                                    <div class="warning-icon">
                                                        <i class="fas fa-exclamation-triangle"></i>
                                                    </div>
                                                    <div class="warning-text">
                                                        <strong>Cảnh báo:</strong> Khóa tài khoản sẽ ngăn người dùng đăng nhập và sử dụng hệ thống.
                                                    </div>
                                                </div>


                                                <div class="form-group">
                                                    <label for="banReason">Lý do khóa tài khoản</label>
                                                    <textarea name="banReason" class="form-control custom-textarea" rows="4" placeholder="Nhập lý do khóa tài khoản..."></textarea>
                                                </div>

                                                <div class="form-group">
                                                    <label for="notifyUser">Thông báo cho người dùng</label>
                                                    <div class="notification-options">
                                                        <div class="form-check">
                                                            <input type="checkbox" name="sendEmail" checked>
                                                            <label for="sendEmail">Gửi email thông báo</label>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="adminPassword">Mật khẩu Admin (Xác nhận hành động)</label>
                                                    <div class="password-input-group">
                                                        <input type="password" name="adminPassword" class="form-control">
                                                    </div>
                                                </div>

                                                <div class="form-confirmation">
                                                    <div class="form-check">
                                                        <input type="checkbox" name="confirmAction">
                                                        <label for="confirmAction">Tôi xác nhận việc khóa tài khoản này.</label>
                                                    </div>
                                                </div>

                                                <div class="form-buttons">
                                                    <button type="button" class="btn btn-outline" onclick="location.href = 'admin-panel?action=account&type=all'">
                                                        <i class="fas fa-times"></i> Hủy
                                                    </button>
                                                    <button type="submit" class="btn btn-danger">
                                                        <i class="fas fa-lock"></i> Khóa tài khoản
                                                    </button>
                                                </div>
                                            </form>
                                        </c:when>
                                        <c:when test="${banacc.accStatus eq 'Inactive'}">
                                            <form action="admin-panel" method="post">
                                                <input type="hidden" name="action" value="account">
                                                <input type="hidden" name="type" value="${banacc.accRole}">
                                                <input type="hidden" name="act" value="ban-acc">
                                                <input type="hidden" name="id" value="${banacc.accId}">
                                                <input type="hidden" name="check" value="${banacc.accStatus}">


                                                <h3 class="form-title success"><i class="fas fa-unlock"></i> Mở khóa tài khoản này</h3>
                                                <div class="unban-notice">
                                                    <div class="notice-icon">
                                                        <i class="fas fa-info-circle"></i>
                                                    </div>
                                                    <div class="notice-text">
                                                        <strong>Thông báo:</strong> Mở khóa tài khoản sẽ cho phép người dùng đăng nhập và sử dụng hệ thống trở lại.
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="notifyUserUnban">Thông báo cho người dùng</label>
                                                    <div class="notification-options">
                                                        <div class="form-check">
                                                            <input type="checkbox" name="sendEmailUnBan" checked>
                                                            <label for="notifyEmailUnban">Gửi email thông báo</label>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="adminPasswordUnban">Mật khẩu Admin (Xác nhận hành động)</label>
                                                    <div class="password-input-group">
                                                        <input type="password" name="adminPasswordUnBan" class="form-control">
                                                    </div>
                                                </div>

                                                <div class="form-confirmation">
                                                    <div class="form-check">
                                                        <input type="checkbox" name="confirmActionUnBan">
                                                        <label for="confirmActionUnban">Tôi xác nhận việc mở khóa tài khoản này và hiểu rõ các tác động của nó</label>
                                                    </div>
                                                </div>

                                                <div class="form-buttons">
                                                    <button type="button" class="btn btn-outline" onclick="location.href = 'admin-panel?action=account&type=all'">
                                                        <i class="fas fa-times"></i> Hủy
                                                    </button>
                                                    <button type="submit" class="btn btn-success">
                                                        <i class="fas fa-unlock"></i> Mở khóa tài khoản
                                                    </button>
                                                </div>
                                            </form>
                                        </c:when>                                   
                                    </c:choose>

                                </div>

                                <div class="impact-card">
                                    <h3><i class="fas fa-exclamation-circle"></i> Tác động của việc khóa tài khoản</h3>
                                    <ul class="impact-list">
                                        <li><i class="fas fa-ban"></i> Người dùng sẽ không thể đăng nhập vào hệ thống</li>
                                        <li><i class="fas fa-shopping-cart"></i> Tài khoản không thể thực hiện giao dịch mua hàng</li>
                                        <li><i class="fas fa-comment-slash"></i> Mất quyền tương tác và bình luận</li>
                                        <li><i class="fas fa-lock-open"></i> Có thể mở khóa bất cứ lúc nào bởi Admin</li>
                                    </ul>
                                </div>
                            </div>
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
