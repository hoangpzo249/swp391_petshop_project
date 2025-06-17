<%-- 
    Document   : admin_accRole_page
    Created on : 8 June 2025, 10:51:54 am
    Author     : HuyHoang
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cập nhật vai trò - PETFPT Shop</title>
        <link href="css/admin_accRole_page.css" rel="stylesheet" type="text/css"/>
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
                        <i class="fas fa-user-edit"></i> Cập nhật vai trò tài khoản
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="admin-panel">Admin</a></li>
                        <li><a href="admin-panel?action=account&type=all">Quản lý tài khoản</a></li>
                        <li>Cập nhật vai trò</li>
                    </ul>
                </div>
                <form action="admin-panel" method="post">
                    <input type="hidden" name="action" value="account">
                    <input type="hidden" name="type" value="${updateRole.accRole}">
                    <input type="hidden" name="act" value="update-role">
                    <input type="hidden" name="id" value="${updateRole.accId}">
                    
                    <div class="card">
                        <div class="card-header">
                            <h3 class="card-title">
                                <i class="fas fa-user-shield"></i> Thay đổi vai trò và quyền hạn
                            </h3>
                        </div>
                        <div class="card-body">
                            <div class="update-role-container">
                                <div class="account-info-section">
                                    <div class="account-info-card">
                                        <div class="account-profile">
                                            <img src="images/support button/account.png" alt="User Avatar" class="account-avatar">
                                            <h2 class="account-name">${updateRole.accFname} ${updateRole.accLname}</h2>
                                            <p class="account-username">@${updateRole.accUsername}</p>
                                            <c:choose>
                                                <c:when test="${updateRole.accRole eq 'Admin'}">
                                                    <div class="account-role-badge admin-role">
                                                        ${updateRole.accRole}
                                                    </div>
                                                </c:when>
                                                <c:when test="${updateRole.accRole eq 'Manager'}">
                                                    <div class="account-role-badge manager-role">
                                                        ${updateRole.accRole}
                                                    </div>
                                                </c:when>
                                                <c:when test="${updateRole.accRole eq 'Seller'}">
                                                    <div class="account-role-badge Seller-role">
                                                        ${updateRole.accRole}
                                                    </div>
                                                </c:when>
                                                <c:when test="${updateRole.accRole eq 'Shipper'}">
                                                    <div class="account-role-badge shipper-role">
                                                        ${updateRole.accRole}
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="account-role-badge customer-role">
                                                        ${updateRole.accRole}
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>


                                        </div>

                                        <div class="account-details">
                                            <div class="detail-item">
                                                <div class="detail-label">ID</div>
                                                <div class="detail-value">${updateRole.accId}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Email</div>
                                                <div class="detail-value">${updateRole.accEmail}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Ngày tạo</div>
                                                <div class="detail-value">${updateRole.accCreateDate}</div>
                                            </div>
                                            <div class="detail-item">
                                                <div class="detail-label">Trạng thái</div>
                                                <div class="detail-value">
                                                    <span class="status-badge status-${updateRole.accStatus.toLowerCase()}">${updateRole.accStatus}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="role-info-card">
                                        <h3><i class="fas fa-info-circle"></i> Thông tin vai trò</h3>

                                        <div class="role-descriptions">
                                            <div class="role-item">
                                                <div class="role-badge admin-role"><i class="fas fa-user-shield"></i> Admin</div>
                                                <div class="role-desc">Toàn quyền quản trị hệ thống, quản lý tài khoản</div>
                                            </div>
                                            <div class="role-item">
                                                <div class="role-badge manager-role"><i class="fas fa-user-tie"></i> Manager</div>
                                                <div class="role-desc">Quản lý cửa hàng, sản phẩm, đơn hàng</div>
                                            </div>
                                            <div class="role-item">
                                                <div class="role-badge Seller-role"><i class="fas fa-user-tag"></i> Seller</div>
                                                <div class="role-desc">Quản lý đơn hàng, bán hàng và chăm sóc khách hàng</div>
                                            </div>
                                            <div class="role-item">
                                                <div class="role-badge shipper-role"><i class="fas fa-truck"></i> Shipper</div>
                                                <div class="role-desc">Quản lý giao hàng và cập nhật trạng thái đơn hàng</div>
                                            </div>
                                            <div class="role-item">
                                                <div class="role-badge customer-role"><i class="fas fa-user"></i> Customer</div>
                                                <div class="role-desc">Khách hàng mua sắm, không có quyền quản trị</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="update-role-form-section">
                                    <div class="update-form-card">
                                        <h3 class="form-title"><i class="fas fa-edit"></i> Thay đổi vai trò</h3>



                                        <div class="form-group">
                                            <label for="currentRole">Vai trò hiện tại</label>
                                            <input type="text" class="form-control" value="${updateRole.accRole}" readonly>
                                        </div>

                                        <div class="form-group">
                                            <label for="newRole">Vai trò mới</label>
                                            <div class="select-group">
                                                
                                                <select name="newRole" class="form-control">
                                                    <option value="">-- Chọn vai trò --</option>
                                                    <c:choose>
                                                        <c:when test="${updateRole.accRole eq 'Manager'}">
                                                            <option value="Seller">Seller</option>
                                                        </c:when>
                                                        <c:when test="${updateRole.accRole eq 'Seller'}">

                                                            <option value="Manager">Manager</option>
                                                        </c:when>
                                                    </c:choose>
                                                </select>
                                                
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="adminPassword">Mật khẩu Admin (Xác nhận hành động)</label>
                                            <div class="password-input-group">
                                                <input type="password" name="adminPassword" class="form-control" required>
                                            </div>
                                        </div>

                                        <div class="confirmation-card">
                                            <h3><i class="fas fa-exclamation-triangle"></i> Lưu ý quan trọng</h3>
                                            <ul class="confirmation-notes">
                                                <li>Để đảm bảo hệ thống vấn hành tốt, hiện tại bạn chỉ có thể cập nhật vai trò cho Manager và Seller</li>
                                                <li>Việc thay đổi vai trò sẽ đến khả năng truy cập hệ thống của người dùng</li>
                                                <li>Người dùng sẽ được thông báo về sự thay đổi vai trò này qua Email*</li>
                                                <li>Đảm bảo bạn đã kiểm tra kỹ thông tin trước khi xác nhận thay đổi</li>
                                            </ul>

                                            <div class="form-check">
                                                <input type="checkbox" name="confirmAction">
                                                <label for="confirmAction">Tôi xác nhận thay đổi vai trò này</label>
                                            </div>
                                            <br>
                                            <div class="form-check">
                                                <input type="checkbox" name="sendEmail">
                                                <label for="confirmAction">Gửi Email thông báo tới người dùng này.</label>
                                            </div>
                                        </div>

                                        <div class="form-buttons">
                                            <button type="button" class="btn btn-outline" onclick="location.href = 'admin-panel?action=account&type=all'">
                                                <i class="fas fa-times"></i> Hủy
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
