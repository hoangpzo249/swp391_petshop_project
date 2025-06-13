<%-- 
    Document   : seller_pet_edit
    Created on : 12 Jun 2025, 22:35:01
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
                        <a href="displayallpet" class="sidebar-link active"><i class="fas fa-dog"></i> Quản lý thú cưng</a>
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
                    <h1 class="page-title"><i class="fas fa-pencil-alt"></i> Chỉnh sửa thú cưng #${pet.petId}</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="seller-pet-management">Quản lý thú cưng</a></li>
                        <li>Chỉnh sửa</li>
                    </ul>
                </div>

                <div class="card">
                    <form action="seller-edit-pet" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="petId" value="${pet.petId}">
                        <div class="card-body">
                            <div class="form-grid">
                                <%-- Left Column --%>
                                <div class="form-group">
                                    <label for="petName">Tên thú cưng</label>
                                    <input type="text" id="petName" name="petName" class="form-control" value="${pet.petName}" required>
                                </div>

                                <%-- Right Column --%>
                                <div class="form-group">
                                    <label for="petGender">Giới tính</label>
                                    <select id="petGender" name="petGender" class="form-control">
                                        <option value="Male" ${pet.petGender == 'Male' ? 'selected' : ''}>Đực</option>
                                        <option value="Female" ${pet.petGender == 'Female' ? 'selected' : ''}>Cái</option>
                                    </select>
                                </div>

                                <%-- Left Column --%>
                                <div class="form-group">
                                    <label for="breedId">Giống</label>
                                    <select id="breedId" name="breedId" class="form-control" required>
                                        <c:forEach items="${breedList}" var="b">
                                            <option value="${b.breedId}" ${pet.breedId == b.breedId ? 'selected' : ''}>${b.breedName}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <%-- Right Column --%>
                                <div class="form-group">
                                    <label for="petColor">Màu sắc</label>
                                    <input type="text" id="petColor" name="petColor" class="form-control" value="${pet.petColor}">
                                </div>

                                <%-- Left Column --%>
                                <div class="form-group">
                                    <label for="petDob">Ngày sinh</label>
                                    <input type="date" id="petDob" name="petDob" class="form-control" value="<fmt:formatDate value='${pet.petDob}' pattern='yyyy-MM-dd'/>" required>
                                </div>

                                <%-- Right Column --%>
                                <div class="form-group">
                                    <label for="petOrigin">Nguồn gốc</label>
                                    <input type="text" id="petOrigin" name="petOrigin" class="form-control" value="${pet.petOrigin}">
                                </div>

                                <%-- Left Column --%>
                                <div class="form-group">
                                    <label for="petPrice">Giá bán (₫)</label>
                                    <fmt:formatNumber value="${pet.petPrice}" pattern="0" var="formattedPrice" />
                                    <input type="number" id="petPrice" name="petPrice" class="form-control" value="${formattedPrice}" required min="0" step="1000">
                                </div>

                                <%-- Right Column (empty for alignment, or add another field here) --%>
                                <div></div>


                                <%-- Full Width Sections (Spanning both columns) --%>
                                <div class="form-full-width">
                                    <div class="form-group">
                                        <label>Trạng thái</label>
                                        <div style="display: flex; gap: 20px;">
                                            <div class="select-group" style="flex:1;">
                                                <label for="petAvailability">Trạng thái bán</label>
                                                <select id="petAvailability" name="petAvailability" class="form-control">
                                                    <option value="1" ${pet.petAvailability == 1 ? 'selected' : ''}>Còn hàng</option>
                                                    <option value="0" ${pet.petAvailability == 0 ? 'selected' : ''}>Đã bán</option>
                                                </select>
                                            </div>
                                            <div class="select-group" style="flex:1;">
                                                <label for="petStatus">Trạng thái đăng</label>
                                                <select id="petStatus" name="petStatus" class="form-control">
                                                    <option value="1" ${pet.petStatus == 1 ? 'selected' : ''}>Hiển thị</option>
                                                    <option value="0" ${pet.petStatus == 0 ? 'selected' : ''}>Ẩn</option>
                                                </select>
                                            </div>
                                            <div class="select-group" style="flex:1;">
                                                <label for="petVaccination">Tiêm phòng</label>
                                                <select id="petVaccination" name="petVaccination" class="form-control">
                                                    <option value="1" ${pet.petVaccination == 1 ? 'selected' : ''}>Đã tiêm</option>
                                                    <option value="0" ${pet.petVaccination == 0 ? 'selected' : ''}>Chưa tiêm</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-full-width">
                                    <div class="form-group">
                                        <label>Quản lý hình ảnh</label>
                                        <div class="image-management-grid">
                                            <c:forEach items="${pet.images}" var="image">
                                                <div class="image-preview-item">
                                                    <img src="data:image/jpeg;base64,${image.getBase64ImageData()}" alt="Pet Image">
                                                    <input type="checkbox" name="deleteImageIds" value="${image.imageId}" class="image-delete-check" title="Chọn để xóa ảnh này">
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="newImages">Tải lên ảnh mới</label>
                                        <input type="file" id="newImages" name="newImages" class="form-control" multiple accept="image/*">
                                    </div>
                                </div>

                                <div class="form-full-width">
                                    <div class="form-group">
                                        <label for="petDescription">Mô tả</label>
                                        <textarea id="petDescription" name="petDescription" class="form-control" rows="5">${pet.petDescription}</textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <a href="seller-pet-management" class="btn btn-outline">Hủy bỏ</a>
                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="seller-footer">
        © 2025 PETFPT Shop - Hệ thống quản lý
    </div>
</body>
</html>