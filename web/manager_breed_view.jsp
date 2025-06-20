<%-- 
    Document   : manager_breed_view
    Created on : 18 Jun 2025, 20:55:31
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Giống Thú Cưng - PETFPT Shop</title>
        <link href="css/manager_panel_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <div class="manager-header">
            <div class="logo-container">
                <a href="homepage">
                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                </a>
                <h1 class="manager-title">MANAGER</h1>
            </div>

            <div class="manager-profile">
                <c:choose>
                    <c:when test="${not empty sessionScope.userAccount.accImage}">
                        <img src="data:image/jpeg;base64,${sessionScope.userAccount.getBase64Image()}" alt="Manager Avatar"/>
                    </c:when>
                    <c:otherwise>
                        <img src="images/support button/account.png" alt="Manager Avatar"/>
                    </c:otherwise>
                </c:choose>
                <div class="manager-info">
                    <span class="manager-name">${sessionScope.userAccount.accFname} ${sessionScope.userAccount.accLname}</span>
                    <span class="manager-role">Manager</span>
                </div>
                <div class="manager-actions">
                    <a href="profile" class="manager-action" title="Thông tin cá nhân"><i class="fas fa-user-circle"></i></a>
                    <a href="logout" class="manager-action" title="Đăng xuất"><i class="fas fa-sign-out-alt"></i></a>
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

        <div class="manager-container">
            <!-- Sidebar -->
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
                        <a href="displayallbreed" class="sidebar-link active"><i class="fas fa-dna"></i> Quản lý giống thú cưng</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="addbreed" class="sidebar-link"><i class="fas fa-plus-circle"></i> Thêm giống mới</a>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </div>

            <%-- Main Body --%>
            <div class="manager-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-dna"></i> Quản lý giống thú cưng</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="comingsoon">Manager</a></li>
                        <li>Quản lý giống thú cưng</li>
                    </ul>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title"><i class="fas fa-list-ul"></i> Danh sách giống thú cưng</h3>
                        <div class="card-tools">
                            <button class="btn btn-primary" onclick="location.href = 'addbreed'">
                                <i class="fas fa-plus-circle"></i> Thêm giống mới
                            </button>
                            <button class="btn btn-outline">
                                <i class="fas fa-file-export"></i> Xuất dữ liệu
                            </button>
                        </div>
                    </div>
                    <div class="card-body">

                        <form action="displayallbreed" method="GET" id="filterForm">
                            <div class="filter-controls">
                                <div class="input-group" style="flex-grow: 1;">
                                    <i class="fas fa-search"></i>
                                    <input type="text" name="searchKey" placeholder="Tìm theo Mã, Tên giống..." value="${param.searchKey}">
                                </div>
                                <div class="select-group">
                                    <select name="species">
                                        <option value="">Tất cả loài</option>
                                        <c:forEach items="${speciesList}" var="s">
                                            <option value="${s}" ${param.species == s ? 'selected' : ''}>${s}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="select-group">
                                    <select name="status">
                                        <option value="">Tất cả trạng thái</option>
                                        <option value="1" ${param.status == '1' ? 'selected' : ''}>Hoạt động</option>
                                        <option value="0" ${param.status == '0' ? 'selected' : ''}>Ngừng hoạt động</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary"><i class="fas fa-filter"></i> Lọc</button>
                                <button type="button" class="btn btn-outline" onclick="location.href = 'displayallbreed'">
                                    <i class="fas fa-times"></i> Xóa bộ lọc
                                </button>
                            </div>
                        </form>

                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th style="width: 10%;">Ảnh</th>
                                        <th style="width: 10%;">Mã Giống</th>
                                        <th>Tên Giống</th>
                                        <th>Loài</th>
                                        <th style="width: 15%;">Đã bán</th>
                                        <th style="width: 15%;">Trạng Thái</th>
                                        <th style="width: 15%;">Thao Tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${breedList}" var="b">
                                        <tr>
                                            <td>
                                                <img src="${b.breedImage}" alt="${b.breedName}" class="table-avatar"/>
                                            </td>
                                            <td><strong>#${b.breedId}</strong></td>
                                            <td>${b.breedName}</td>
                                            <td>${b.breedSpecies}</td>
                                            <td>${b.totalPurchases}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${b.breedStatus}">
                                                        <span class="status-badge status-active">Hoạt động</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="status-badge status-blocked">Ngừng hoạt động</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="table-actions">
                                                    <a class="action-btn edit-btn" title="Chỉnh sửa" href="updatebreed?id=${b.breedId}">
                                                        <i class="fas fa-pencil-alt"></i>
                                                    </a>
                                                    <c:choose>
                                                        <c:when test="${b.breedStatus}">
                                                            <a class="action-btn block-btn" title="Ẩn giống" 
                                                               href="updatebreedstatus?breedId=${b.breedId}&status=0">
                                                                <i class="fas fa-toggle-off"></i>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a class="action-btn unhide-btn" title="Hiện giống" 
                                                               href="updatebreedstatus?breedId=${b.breedId}&status=1">
                                                                <i class="fas fa-toggle-on"></i>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty breedList}">
                                        <tr>
                                            <td colspan="6" style="text-align: center; padding: 30px;">
                                                Không tìm thấy giống thú cưng nào phù hợp.
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
        <div class="manager-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
    </body>
</html>