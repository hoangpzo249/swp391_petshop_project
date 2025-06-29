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
        <link href="create.css" rel="stylesheet" type="text/css"/>
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
                    <a href="admin-panel?action=create-account&type=customer" class="account-tab ${param.type == 'customer' ? 'active':''}">
                        <i class="fas fa-user"></i> Tài khoản khách hàng
                    </a>
                    <a href="admin-panel?action=create-account&type=staff" class="account-tab ${param.type == 'staff' ? 'active':'' }">
                        <i class="fas fa-briefcase"></i> Tài khoản nhân viên
                    </a>
                    <a href="admin-panel?action=create-account&type=bulk" class="account-tab ${param.type == 'bulk' ? 'active':'' }">
                        <i class="fas fa-file-excel"></i> Tạo hàng loạt
                    </a>
                </div>

                <!-- Create Account Form Container -->
                <div class="create-account-container">
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
                                                <label for="usernameCus">Tên đăng nhập <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-user"></i></span>
                                                    <input type="text" id="usernameCus" name="usernameCus" class="form-control" required value="${usernameCus}">
                                                </div>
                                                <div class="field-info" id="usernameCus-info">
                                                    <i class="fas fa-info-circle"></i> Tên đăng nhập phải từ 5-20 ký tự, không chứa ký tự đặc biệt.
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="emailCus">Email <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-envelope"></i></span>
                                                    <input type="email" id="emailCus" name="emailCus" class="form-control" required value="${emailCus}">
                                                </div>
                                                <div class="field-info" id="emailCus-info">
                                                    <i class="fas fa-info-circle"></i> Email phải có định dạng hợp lệ (example@domain.com).
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="passCus">Mật khẩu <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-lock"></i></span>
                                                    <input type="password" id="passCus" name="passCus" class="form-control" required value="${passCus}">
                                                    <span class="password-toggle" onclick="togglePassword('passCus')">
                                                        <i class="fas fa-eye"></i>
                                                    </span>
                                                </div>
                                                <div class="field-info" id="passCus-info">
                                                    <i class="fas fa-info-circle"></i> Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số.
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="comfirmPassCus">Nhập lại mật khẩu <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-lock"></i></span>
                                                    <input type="password" id="comfirmPassCus" name="comfirmPassCus" class="form-control" required value="${comfirmPassCus}">
                                                    <span class="password-toggle" onclick="togglePassword('comfirmPassCus')">
                                                        <i class="fas fa-eye"></i>
                                                    </span>
                                                </div>
                                                <div class="field-info" id="comfirmPassCus-info">
                                                    <i class="fas fa-info-circle"></i> Nhập lại mật khẩu phải khớp với mật khẩu ở trên.
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
                                                <label for="fNameCus">Họ <span class="required">*</span></label>
                                                <input type="text" id="fNameCus" name="fNameCus" class="form-control" required value="${fNameCus}">
                                                <div class="field-info" id="fNameCus-info">
                                                    <i class="fas fa-info-circle"></i> Họ của khách hàng, viết không dấu.
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="lNameCus">Tên <span class="required">*</span></label>
                                                <input type="text" id="lNameCus" name="lNameCus" class="form-control" required value="${lNameCus}">
                                                <div class="field-info" id="lNameCus-info">
                                                    <i class="fas fa-info-circle"></i> Tên của khách hàng, viết không dấu.
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="phoneCus">Số điện thoại <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-phone"></i></span>
                                                    <input type="tel" id="phoneCus" name="phoneCus" class="form-control" required value="${phoneCus}">
                                                </div>
                                                <div class="field-info" id="phoneCus-info">
                                                    <i class="fas fa-info-circle"></i> Số điện thoại 10 số, bắt đầu bằng số 0.
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="dobCus">Ngày sinh</label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-calendar"></i></span>
                                                    <input type="date" id="dobCus" name="dobCus" class="form-control" value="${dobCus}" required>
                                                </div>
                                                <div class="field-info" id="dobCus-info">
                                                    <i class="fas fa-info-circle"></i> Ngày sinh của khách hàng, phải trên 18 tuổi.
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-full">
                                                <label for="addressCus">Địa chỉ <span class="required">*</span></label>
                                                <input type="text" id="addressCus" name="addressCus" class="form-control" value="${addressCus}" required/>
                                                <div class="field-info" id="addressCus-info">
                                                    <i class="fas fa-info-circle"></i> Địa chỉ đầy đủ của khách hàng để giao hàng.
                                                </div>
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
                            <!-- Bulk Account Creation Form -->
                            <div class="create-form-container">
                                <div class="form-header">
                                    <h3><i class="fas fa-file-excel"></i> Tạo tài khoản hàng loạt</h3>
                                    <p>Tải lên file Excel chứa danh sách tài khoản cần tạo</p>
                                </div>

                                <div class="bulk-upload-container">
                                    <div class="file-upload-area">
                                        <div class="file-upload-icon">
                                            <i class="fas fa-file-excel"></i>
                                        </div>
                                        <h3>Tải lên file Excel</h3>
                                        <p>Kéo thả file hoặc nhấn vào đây để chọn file</p>
                                        <input type="file" id="excel-file-upload" accept=".xlsx, .xls" />
                                        <button type="button" id="upload-button" class="btn btn-primary">
                                            <i class="fas fa-upload"></i> Chọn file
                                        </button>
                                    </div>
                                    
                                    <div class="file-template-info">
                                        <h4><i class="fas fa-info-circle"></i> Hướng dẫn</h4>
                                        <p>Sử dụng mẫu Excel được cung cấp để đảm bảo dữ liệu chính xác:</p>
                                        <ol>
                                            <li>Tải xuống <a href="#" class="template-link">mẫu Excel</a> cho tài khoản khách hàng hoặc nhân viên</li>
                                            <li>Điền thông tin vào mẫu, không thay đổi cấu trúc cột</li>
                                            <li>Lưu file và tải lên hệ thống</li>
                                        </ol>
                                        <div class="template-buttons">
                                            <a href="#" class="template-button">
                                                <i class="fas fa-download"></i> Mẫu tài khoản khách hàng
                                            </a>
                                            <a href="#" class="template-button">
                                                <i class="fas fa-download"></i> Mẫu tài khoản nhân viên
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="preview-container hidden" id="excel-preview">
                                    <h3><i class="fas fa-table"></i> Xem trước dữ liệu</h3>
                                    <div class="preview-table-wrapper">
                                        <table class="preview-table">
                                            <thead>
                                                <tr>
                                                    <th>STT</th>
                                                    <th>Tên đăng nhập</th>
                                                    <th>Email</th>
                                                    <th>Họ</th>
                                                    <th>Tên</th>
                                                    <th>Số điện thoại</th>
                                                    <th>Trạng thái</th>
                                                </tr>
                                            </thead>
                                            <tbody id="preview-data">
                                                <!-- Data will be populated here -->
                                            </tbody>
                                        </table>
                                    </div>
                                    
                                    <div class="preview-actions">
                                        <button type="button" class="btn btn-outline" id="cancel-upload">
                                            <i class="fas fa-times"></i> Hủy
                                        </button>
                                        <button type="button" class="btn btn-primary" id="process-upload">
                                            <i class="fas fa-check"></i> Xác nhận tạo tài khoản
                                        </button>
                                    </div>
                                </div>
                                
                                <!-- Upload Progress -->
                                <div class="upload-progress hidden" id="upload-progress">
                                    <div class="progress-bar-container">
                                        <div class="progress-bar" id="progress-bar-fill"></div>
                                    </div>
                                    <div class="progress-text">Đang xử lý... <span id="progress-percentage">0%</span></div>
                                </div>
                            </div>
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
                                                <label for="usernameStaff">Tên đăng nhập <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-user"></i></span>
                                                    <input type="text" id="usernameStaff" name="usernameStaff" class="form-control" required value="${usernameStaff}">
                                                </div>
                                                <div class="field-info" id="usernameStaff-info">
                                                    <i class="fas fa-info-circle"></i> Tên đăng nhập phải từ 5-20 ký tự, không chứa ký tự đặc biệt.
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="emailStaff">Email <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-envelope"></i></span>
                                                    <input type="email" id="emailStaff" name="emailStaff" class="form-control" required value="${emailStaff}">
                                                </div>
                                                <div class="field-info" id="emailStaff-info">
                                                    <i class="fas fa-info-circle"></i> Email công ty có định dạng staff@petfpt.com.
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="passStaff">Mật khẩu <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-lock"></i></span>
                                                    <input type="password" id="passStaff" name="passStaff" class="form-control" required value="${passStaff}">
                                                    <span class="password-toggle" onclick="togglePassword('passStaff')">
                                                        <i class="fas fa-eye"></i>
                                                    </span>
                                                </div>
                                                <div class="field-info" id="passStaff-info">
                                                    <i class="fas fa-info-circle"></i> Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="comfirmPassStaff">Nhập lại mật khẩu <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-lock"></i></span>
                                                    <input type="password" id="comfirmPassStaff" name="comfirmPassStaff" class="form-control" required value="${comfirmPassStaff}">
                                                    <span class="password-toggle" onclick="togglePassword('comfirmPassStaff')">
                                                        <i class="fas fa-eye"></i>
                                                    </span>
                                                </div>
                                                <div class="field-info" id="comfirmPassStaff-info">
                                                    <i class="fas fa-info-circle"></i> Nhập lại mật khẩu phải khớp với mật khẩu ở trên.
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="roleStaff">Vai trò <span class="required">*</span></label>
                                                <div class="select-group">
                                                    <select name="roleStaff" id="roleStaff" class="form-control" required>
                                                        <option value="">-- Chọn vai trò --</option>
                                                        <option value="Manager">Manager</option>
                                                        <option value="Seller">Seller</option>
                                                        <option value="Shipper">Shipper</option>
                                                    </select>
                                                    <span class="select-arrow"><i class="fas fa-chevron-down"></i></span>
                                                </div>
                                                <div class="field-info" id="roleStaff-info">
                                                    <i class="fas fa-info-circle"></i> Chọn vai trò phù hợp với chức năng của nhân viên.
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="statusStaff">Trạng thái</label>
                                                <div class="select-group">
                                                    <select name="statusStaff" id="statusStaff" class="form-control" required>
                                                        <option value="Active">Hoạt động</option>
                                                        <option value="Inactive">Không hoạt động</option>
                                                    </select>
                                                    <span class="select-arrow"><i class="fas fa-chevron-down"></i></span>
                                                </div>
                                                <div class="field-info" id="statusStaff-info">
                                                    <i class="fas fa-info-circle"></i> Trạng thái tài khoản, mặc định là "Hoạt động".
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
                                                <label for="fNameStaff">Họ <span class="required">*</span></label>
                                                <input type="text" id="fNameStaff" name="fNameStaff" class="form-control" required value="${fNameStaff}">
                                                <div class="field-info" id="fNameStaff-info">
                                                    <i class="fas fa-info-circle"></i> Họ của nhân viên, viết không dấu.
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="lNameStaff">Tên <span class="required">*</span></label>
                                                <input type="text" id="lNameStaff" name="lNameStaff" class="form-control" required value="${lNameStaff}">
                                                <div class="field-info" id="lNameStaff-info">
                                                    <i class="fas fa-info-circle"></i> Tên của nhân viên, viết không dấu.
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-half">
                                                <label for="phoneStaff">Số điện thoại <span class="required">*</span></label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-phone"></i></span>
                                                    <input type="tel" id="phoneStaff" name="phoneStaff" class="form-control" required value="${phoneStaff}">
                                                </div>
                                                <div class="field-info" id="phoneStaff-info">
                                                    <i class="fas fa-info-circle"></i> Số điện thoại 10 số, bắt đầu bằng số 0.
                                                </div>
                                            </div>
                                            <div class="form-group col-half">
                                                <label for="dobStaff">Ngày sinh</label>
                                                <div class="input-group">
                                                    <span class="input-icon"><i class="fas fa-calendar"></i></span>
                                                    <input type="date" id="dobStaff" name="dobStaff" class="form-control" value="${dobStaff}" required>
                                                </div>
                                                <div class="field-info" id="dobStaff-info">
                                                    <i class="fas fa-info-circle"></i> Ngày sinh của nhân viên, phải trên 18 tuổi.
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group col-full">
                                                <label for="addressStaff">Địa chỉ <span class="required">*</span> </label>
                                                <input type="text" id="addressStaff" name="addressStaff" class="form-control" value="${addressStaff}" required/>
                                                <div class="field-info" id="addressStaff-info">
                                                    <i class="fas fa-info-circle"></i> Địa chỉ đầy đủ của nhân viên.
                                                </div>
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
                </div>
            </div>
        </div>

        <!-- Admin Footer -->
        <div class="admin-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>

    </body>
</html>