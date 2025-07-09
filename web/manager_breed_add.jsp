<%-- 
    Document   : manager_breed_add
    Created on : 23 Jun 2025, 08:18:07
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm Giống Mới - PETFPT Shop</title>
        <link href="css/manager_panel_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <%-- Manager Header --%>
        <div class="manager-header">
            <div class="logo-container">
                <a href="homepage">
                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                </a>
                <h1 class="manager-title">MANAGER</h1>
            </div>

            <div class="manager-profile">
                <c:choose>
                    <c:when test="${not empty sessionScope.userAccount.displayAccImage()}">
                        <img src="${sessionScope.userAccount.displayAccImage()}" alt="Manager Avatar"/>
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

        <%-- Notification Messages --%>
        <c:if test="${not empty successMess}">
            <div class="alert-message">${successMess}</div>
            <c:remove var="successMess" scope="session" />
        </c:if>
        <c:if test="${not empty errMess}">
            <div class="alert-message error">${errMess}</div>
            <c:remove var="errMess" scope="session" />
        </c:if>

        <div class="manager-container">
            <!-- Manager Sidebar -->
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
                        <a href="addbreed" class="sidebar-link active"><i class="fas fa-plus-circle"></i> Thêm giống mới</a>
                        <a href="adddiscount.jsp" class="sidebar-link"><i class="fas fa-plus-circle"></i> Thêm mã giảm giá mới</a>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </div>

            <%-- Main Body --%>
            <div class="manager-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-plus-circle"></i> Thêm Giống Mới</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="displaybreed">Quản lý giống thú cưng</a></li>
                        <li>Thêm mới</li>
                    </ul>
                </div>

                <div class="card">
                    <form action="addbreed" method="POST" enctype="multipart/form-data">
                        <div class="card-body">
                            <div class="form-grid">
                                <%-- Left Column --%>
                                <div class="form-group">
                                    <label for="breedName">Tên giống</label>
                                    <input type="text" id="breedName" name="breedName" class="form-control" placeholder="Ví dụ: Golden Retriever" value="${breedName}" required>
                                </div>

                                <%-- Right Column --%>
                                <div class="form-group">
                                    <label for="speciesInput">Loài</label>
                                    <div class="autocomplete-container">
                                        <input type="text" id="speciesInput" class="form-control" placeholder="Ví dụ: Chó" value="${breedSpecies}" autocomplete="off" required>

                                        <input type="hidden" id="breedSpecies" name="breedSpecies" value="${breedSpecies}">

                                        <div id="species-autocomplete-list" class="autocomplete-items"></div>
                                    </div>
                                </div>

                                <%-- Full Width Section for Status and Image --%>
                                <div class="form-full-width">
                                    <div class="form-group">
                                        <label for="breedStatus">Trạng thái</label>
                                        <select id="breedStatus" name="breedStatus" class="form-control">
                                            <option value="1" ${breedStatus != '0' ? 'selected' : ''}>Hiển thị</option>
                                            <option value="0" ${breedStatus == '0' ? 'selected' : ''}>Ẩn</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-full-width">
                                    <div class="form-group">
                                        <label for="breedImage">Hình ảnh giống</label>
                                        <input type="file" id="breedImage" name="breedImage" class="form-control" accept="image/*" >
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <a href="displaybreed" class="btn btn-outline">Hủy bỏ</a>
                            <button type="submit" class="btn btn-primary">Thêm giống</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="manager-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
        <script>
            const speciesData = [
            <c:forEach var="s" items="${speciesList}" varStatus="loop">
            '${s}'<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
            ];
        </script>
        <script src="js/breed_autocomplete.js"></script>
    </body>
</html>