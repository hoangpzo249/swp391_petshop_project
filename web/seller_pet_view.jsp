<%-- 
    Document   : seller_pet_view
    Created on : 3 Jun 2025, 14:00:00
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Thú Cưng - PETFPT Shop</title>
        <link href="css/seller_panel_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <div class="seller-header">
            <div class="logo-container">
                <a href="homepage">
                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                </a>
                <h1 class="seller-title">Quản trị hệ thống</h1>
            </div>

            <div class="seller-profile">
                <c:choose>
                    <c:when test="${not empty sessionScope.userAccount.accImage}">
                        <img src="data:image/jpeg;base64,${sessionScope.userAccount.getBase64Image()}" alt="Seller Avatar"/>
                    </c:when>
                    <c:otherwise>
                        <img src="images/support button/account.png" alt="Seller Avatar"/>
                    </c:otherwise>
                </c:choose>
                <div class="seller-info">
                    <span class="seller-name">${sessionScope.userAccount.accFname} ${sessionScope.userAccount.accLname}</span>
                    <span class="seller-role">Seller</span>
                </div>
                <div class="seller-actions">
                    <a href="profile" class="seller-action" title="Thông tin cá nhân"><i class="fas fa-user-circle"></i></a>
                    <a href="logout" class="seller-action" title="Đăng xuất"><i class="fas fa-sign-out-alt"></i></a>
                </div>
            </div>
        </div>

        <c:if test="${not empty successMess}">
            <div class="alert-message">${successMess}</div>
            <c:remove var="successMess" scope="session" />
        </c:if>
        <c:if test="${not empty errMess}">
            <div class="alert-message error">${errMess}</div>
            <c:remove var="errMess" scope="session" />
        </c:if>

        <div class="seller-container">
            <!-- Sidebar -->
            <div class="seller-sidebar">
                <div class="sidebar-header">
                    <h2 class="sidebar-title">SELLER PANEL</h2>
                    <p class="sidebar-subtitle">Quản lý hệ thống PETFPT Shop</p>
                </div>
                <div class="sidebar-menu">
                    <div class="menu-category">
                        <h5 class="category-title">Điều hướng</h5>
                        <a href="sellerpanel" class="sidebar-link"><i class="fas fa-tachometer-alt"></i> Tổng quan</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="displayorder" class="sidebar-link"><i class="fas fa-bag-shopping"></i> Quản lý đơn hàng</a>
                        <a href="seller-pet-management" class="sidebar-link active"><i class="fas fa-dog"></i> Quản lý thú cưng</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="seller-create-pet" class="sidebar-link"><i class="fas fa-paw"></i> Đăng bán thú cưng</a>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </div>

            <%-- Main Body --%>
            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-dog"></i> Quản lý thú cưng</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="sellerpanel">Seller</a></li>
                        <li>Quản lý thú cưng</li>
                    </ul>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title"><i class="fas fa-list-ul"></i> Danh sách thú cưng</h3>
                        <div class="card-tools">
                            <button class="btn btn-primary" onclick="location.href = 'admin-panel?action=create-account&type=customer'">
                                <i class="fas fa-paw"></i> Đăng bán thú cưng
                            </button>
                            <button class="btn btn-outline">
                                <i class="fas fa-file-export"></i> Xuất dữ liệu
                            </button>
                        </div>
                    </div>
                    <div class="card-body">
                        <form action="seller-pet-management" method="GET" id="filterForm">
                            <div class="filter-controls">
                                <div class="input-group">
                                    <i class="fas fa-search"></i>
                                    <input type="text" name="searchKey" placeholder="Tìm theo Mã, Tên thú cưng..." value="${param.searchKey}">
                                </div>

                                <div class="select-group">
                                    <select name="availability" onchange="this.form.submit()">
                                        <option value="" ${empty param.availability ? 'selected' : ''}>Tất cả trạng thái</option>
                                        <option value="1" ${param.availability == '1' ? 'selected' : ''}>Còn hàng</option>
                                        <option value="0" ${param.availability == '0' ? 'selected' : ''}>Đã bán</option>
                                    </select>
                                </div>

                                <%-- NEW FILTERS --%>
                                <div class="select-group">
                                    <select name="species" onchange="this.form.submit()">
                                        <option value="">Tất cả loài</option>
                                        <c:forEach items="${speciesList}" var="s">
                                            <option value="${s}" ${param.species == s ? 'selected' : ''}>${s}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="select-group">
                                    <select name="breedId" onchange="this.form.submit()">
                                        <option value="">Tất cả giống</option>
                                        <c:forEach items="${breedList}" var="b">
                                            <option value="${b.breedId}" ${param.breedId == b.breedId ? 'selected' : ''}>${b.breedName}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="select-group">
                                    <select name="gender" onchange="this.form.submit()">
                                        <option value="">Tất cả giới tính</option>
                                        <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Đực</option>
                                        <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Cái</option>
                                    </select>
                                </div>

                                <div class="select-group">
                                    <select name="vaccination" onchange="this.form.submit()">
                                        <option value="">Tiêm phòng</option>
                                        <option value="1" ${param.vaccination == '1' ? 'selected' : ''}>Đã tiêm</option>
                                        <option value="0" ${param.vaccination == '0' ? 'selected' : ''}>Chưa tiêm</option>
                                    </select>
                                </div>

                                <button type="submit" class="btn btn-primary"><i class="fas fa-filter"></i> Lọc</button>
                            </div>
                        </form>

                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Ảnh</th>
                                        <th>Mã Pet</th>
                                        <th>Tên Thú Cưng</th>
                                        <th>Giống</th>
                                        <th>Giá</th>
                                        <th>Trạng Thái</th>
                                        <th style="width: 15%;">Thao Tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${petList}" var="pet">
                                        <tr>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty pet.getBase64Image()}">
                                                        <img src="data:image/jpeg;base64,${pet.getBase64Image()}" alt="${pet.petName}" class="table-avatar"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="images/placeholder.jpg" alt="No Image" class="table-avatar"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><strong>#${pet.petId}</strong></td>
                                            <td>${pet.petName}</td>
                                            <td>${pet.breed.breedName}</td>
                                            <td>
                                                <span class="price-value">
                                                    <fmt:formatNumber value="${pet.petPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                                </span>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${pet.petAvailability == 1}">
                                                        <span class="status-badge status-active">Còn hàng</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="status-badge status-blocked">Đã bán</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="table-actions">
                                                    <a class="action-btn view-btn" title="Xem chi tiết" href="displaypet?id=${pet.petId}"><i class="fas fa-eye"></i></a>
                                                    <a class="action-btn edit-btn" title="Chỉnh sửa" href="seller-edit-pet?id=${pet.petId}"><i class="fas fa-pencil-alt"></i></a>
                                                    <a class="action-btn block-btn" title="Xóa" href="#"><i class="fas fa-trash"></i></a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty petList}">
                                        <tr>
                                            <td colspan="7" style="text-align: center; padding: 30px;">
                                                Không tìm thấy thú cưng nào phù hợp.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
    </body>
</html>