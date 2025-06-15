<%-- 
    Document   : admin_accDetail_page
    Created on : 7 June 2025, 6:41:31 pm
    Author     : HuyHoang
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi Tiết Tài Khoản - PETFPT Shop</title>
        <link href="css/admin_accDetail_page.css" rel="stylesheet" type="text/css"/>
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
                        <i class="fas fa-user"></i> Chi tiết tài khoản
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="admin-panel">Admin</a></li>
                        <li><a href="admin-panel?action=account&type=all">Quản lý tài khoản</a></li>
                        <li>Chi tiết tài khoản</li>
                    </ul>
                </div>

                <!-- Account Details Card -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-id-card"></i> Thông tin tài khoản
                        </h3>
                        <div class="card-tools">
                            <c:choose>
                                <c:when test="${accDetail.accRole eq 'Admin'}">

                                </c:when>

                                <c:when test="${accDetail.accRole eq 'Manager'}">
                                    <button class="btn btn-outline" onclick="location.href = 'admin-panel?action=account&type=${accDetail.accRole}&act=update-role&id=${accDetail.accId}'">
                                        <i class="fas fa-edit"></i> Chỉnh sửa
                                    </button>
                                    <button class="btn btn-primary" onclick="location.href = 'admin-panel?action=account&type=${accDetail.accRole}&act=reset-pass&id=${accDetail.accId}'">
                                        <i class="fas fa-key"></i> Đặt lại mật khẩu
                                    </button>
                                </c:when>

                                <c:when test="${accDetail.accRole eq 'Seller'}">
                                    <button class="btn btn-outline" onclick="location.href = 'admin-panel?action=account&type=${accDetail.accRole}&act=update-role&id=${accDetail.accId}'">
                                        <i class="fas fa-edit"></i> Chỉnh sửa
                                    </button>
                                    <button class="btn btn-primary" onclick="location.href = 'admin-panel?action=account&type=${accDetail.accRole}&act=reset-pass&id=${accDetail.accId}'">
                                        <i class="fas fa-key"></i> Đặt lại mật khẩu
                                    </button>
                                </c:when>

                                <c:when test="${accDetail.accRole eq 'Shipper'}">

                                    <button class="btn btn-primary" onclick="location.href = 'admin-panel?action=account&type=${accDetail.accRole}&act=reset-pass&id=${accDetail.accId}'">
                                        <i class="fas fa-key"></i> Đặt lại mật khẩu
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-primary" onclick="location.href = 'admin-panel?action=account&type=${accDetail.accRole}&act=reset-pass&id=${accDetail.accId}'">
                                        <i class="fas fa-key"></i> Đặt lại mật khẩu
                                    </button>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                    <div class="card-body">
                        <form action="admin-panel" method="POST">
                            <input type="hidden" name="action" value="account" />
                            <input type="hidden" name="type" value="${accDetail.accRole}" />
                            <input type="hidden" name="act" value="view" />
                            <input type="hidden" name="id" value="${accDetail.accId}" />


                            <div class="account-view-container">
                                <div class="account-profile-section">
                                    <div class="account-profile">
                                        <img src="images/support button/account.png" alt="User Avatar" class="account-avatar">
                                        <h2 class="account-name">${accDetail.accFname} ${accDetail.accLname}</h2>
                                        <p class="account-username">@${accDetail.accUsername}</p>

                                        <c:choose>
                                            <c:when test="${accDetail.accRole eq 'Admin'}">
                                                <div class="account-role-badge admin-role">
                                                    ${accDetail.accRole}
                                                </div>
                                            </c:when>
                                            <c:when test="${accDetail.accRole eq 'Manager'}">
                                                <div class="account-role-badge manager-role">
                                                    ${accDetail.accRole}
                                                </div>
                                            </c:when>
                                            <c:when test="${accDetail.accRole eq 'Seller'}">
                                                <div class="account-role-badge Seller-role">
                                                    ${accDetail.accRole}
                                                </div>
                                            </c:when>
                                            <c:when test="${accDetail.accRole eq 'Shipper'}">
                                                <div class="account-role-badge shipper-role">
                                                    ${accDetail.accRole}
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="account-role-badge customer-role">
                                                    ${accDetail.accRole}
                                                </div>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:choose>
                                            <c:when test="${accDetail.accStatus eq 'Active'}">
                                                <td><span class="account-status-badge status-active">${accDetail.accStatus}</span></td>
                                                </c:when>
                                                <c:when test="${accDetail.accStatus eq 'Inactive'}">
                                                <td><span class="account-status-badge status-blocked">${accDetail.accStatus}</span></td>
                                                </c:when>
                                            </c:choose>

                                    </div>

                                    <div class="account-quick-stats">
                                        <div class="stat-item">
                                            <div class="stat-icon"><i class="fas fa-calendar-alt"></i></div>
                                            <div class="stat-info">
                                                <div class="stat-label">Ngày tạo</div>
                                                <div class="stat-value">${accDetail.accCreateDate}</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="account-details-section">
                                    <div class="details-section">
                                        <h3 class="section-title"><i class="fas fa-info-circle"></i> Thông tin cơ bản</h3>
                                        <div class="details-grid">
                                            <div class="detail-item">
                                                <div class="detail-label">ID</div>
                                                <div class="detail-value">${accDetail.accId}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Tên tài khoản</div>
                                                <div class="detail-value">${accDetail.accUsername}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Họ và tên</div>
                                                <div class="detail-value">${accDetail.accFname} ${accDetail.accLname}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Email</div>
                                                <div class="detail-value">${accDetail.accEmail}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Số điện thoại</div>
                                                <div class="detail-value">${accDetail.accPhoneNumber}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Ngày sinh</div>
                                                <div class="detail-value">${accDetail.accDob}</div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="details-section">
                                        <h3 class="section-title"><i class="fas fa-map-marker-alt"></i> Địa chỉ</h3>
                                        <div class="details-grid">
                                            <div class="detail-item full-width">
                                                <div class="detail-label">Địa chỉ</div>
                                                <div class="detail-value">${accDetail.accAddress}</div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="details-section">
                                        <h3 class="section-title"><i class="fas fa-shield-alt"></i> Thông tin tài khoản</h3>
                                        <div class="details-grid">
                                            <div class="detail-item">
                                                <div class="detail-label">Loại tài khoản</div>
                                                <div class="detail-value">${accDetail.accRole}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Trạng thái</div>
                                                <div class="detail-value">${accDetail.accStatus}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Ngày tạo</div>
                                                <div class="detail-value">${accDetail.accCreateDate}</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="action-buttons">
                    <button class="btn btn-outline" onclick="location.href = 'admin-panel?action=account&type=all'">
                        <i class="fas fa-arrow-left"></i> Quay lại danh sách
                    </button>
                </div>
            </div>
        </div>

        <div class="admin-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
    </body>
</html>
