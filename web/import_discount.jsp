<%-- 
    Document   : import_discount
    Created on : Jul 4, 2025, 8:22:57 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Import Mã Giảm Giá - PETFPT Shop</title>
        <link href="css/manager_panel_page.css" rel="stylesheet" type="text/css"/>
        <link href="css/seller_panel_page.css?v=4" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <div class="seller-header">
            <div class="logo-container">
                <a href="homepage">
                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                </a>
                <h1 class="seller-title">Manager</h1>
            </div>
            <div class="seller-profile">
                <c:choose>
                    <c:when test="${not empty sessionScope.userAccount.displayAccImage()}">
                        <img src="${sessionScope.userAccount.displayAccImage()}" alt="Seller Avatar"/>
                    </c:when>
                    <c:otherwise>
                        <img src="images/support button/account.png" alt="Seller Avatar"/>
                    </c:otherwise>
                </c:choose>
                <div class="seller-info">
                    <span class="seller-name">${sessionScope.userAccount.accFname} ${sessionScope.userAccount.accLname}</span>
                    <span class="seller-role">Manager</span>
                </div>
                <div class="seller-actions">
                    <a href="profile" class="seller-action"><i class="fas fa-user-circle"></i></a>
                    <a href="logout" class="seller-action"><i class="fas fa-sign-out-alt"></i></a>
                </div>
            </div>
        </div>

        <c:if test="${not empty errMess}">
            <div class="alert-message error">${errMess}</div>
            <c:remove var="errMess" scope="session"/>
        </c:if>
        <c:if test="${not empty successMess}">
            <div class="alert-message">${successMess}</div>
            <c:remove var="successMess" scope="session"/>
        </c:if>

        <div class="manager-container">
            <div class="manager-sidebar">
                <div class="sidebar-header">
                    <h2 class="sidebar-title">MANAGER PANEL</h2>
                    <p class="sidebar-subtitle">Quản lý hệ thống PETFPT Shop</p>
                </div>
                <div class="sidebar-menu">
                    <div class="menu-category">
                        <h5 class="category-title">Điều hướng</h5>
                        <a href="comingsoon" class="sidebar-link"><i class="fas fa-tachometer-alt"></i> Tổng quan</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="displaybreed" class="sidebar-link"><i class="fas fa-dna"></i> Quản lý giống thú cưng</a>
                        <a href="discountmanager" class="sidebar-link"><i class="fas fa-tags"></i> Quản lý mã giảm giá</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="addbreed" class="sidebar-link"><i class="fas fa-plus-circle"></i> Thêm giống mới</a>
                        <a href="adddiscount.jsp" class="sidebar-link active"><i class="fas fa-plus-circle"></i> Thêm mã giảm giá mới</a>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </div>

            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-file-upload"></i> Import Mã Giảm Giá Từ Excel</h1>
                </div>

                <div class="card">
                    <form action="importdiscount" method="POST" enctype="multipart/form-data">
                        <div class="card-body">
                            <div class="form-group">
                                <label for="excelFile">Chọn file Excel (.xlsx)</label>
                                <input type="file" id="excelFile" name="excelFile" class="form-control styled-file-input" accept=".xlsx" required>
                            </div>
                        </div>
                        <div class="card-footer">
                            <a href="discountmanager" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay lại</a>
                            <a href="sample/template.xlsx" class="btn btn-outline" download>
                                <i class="fas fa-download"></i> Tải file mẫu
                            </a>
                            <button type="submit" class="btn btn-primary"><i class="fas fa-upload"></i> Import file</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>

        <style>
            .styled-file-input {
                padding: 10px;
                border: 2px dashed #ccc;
                background: #f9f9f9;
                transition: border-color 0.3s, background 0.3s;
            }
            .styled-file-input:hover {
                border-color: #007bff;
                background: #eef7ff;
            }
            .btn-primary i, .btn-outline i {
                margin-right: 5px;
            }
        </style>
    </body>
</html>
