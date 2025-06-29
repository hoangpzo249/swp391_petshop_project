<%-- 
    Document   : seller_pet_add
    Created on : 14 Jun 2025, 15:40:11
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng Bán Thú Cưng - PETFPT Shop</title>
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
                <h1 class="seller-title">SELLER</h1>
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
                        <a href="comingsoon" class="sidebar-link"><i class="fas fa-tachometer-alt"></i> Tổng quan</a>
                        <a href="sellerdisplayinvoice" class="sidebar-link">
                            <i class="fas fa-file-invoice"></i> Danh sách hóa đơn
                        </a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="displayorder" class="sidebar-link"><i class="fas fa-bag-shopping"></i> Quản lý đơn hàng</a>
                        <a href="displayallpet" class="sidebar-link"><i class="fas fa-dog"></i> Quản lý thú cưng</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="addpet" class="sidebar-link active"><i class="fas fa-paw"></i> Đăng bán thú cưng</a>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </div>

            <%-- Main Body --%>
            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-paw"></i> Đăng Bán Thú Cưng Mới</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="displayallpet">Quản lý thú cưng</a></li>
                        <li>Đăng bán</li>
                    </ul>
                </div>

                <div class="card">
                    <form action="addpet" method="POST" enctype="multipart/form-data">
                        <div class="card-body">
                            <div class="form-grid">
                                <%-- Left Column --%>
                                <div class="form-group">
                                    <label for="petName">Tên thú cưng</label>
                                    <input type="text" id="petName" name="petName" class="form-control" placeholder="Ví dụ: Lạp Xưởng Vàng" value="${petName}" required>
                                </div>

                                <%-- Right Column --%>
                                <div class="form-group">
                                    <label for="petGender">Giới tính</label>
                                    <select id="petGender" name="petGender" class="form-control">
                                        <option value="Male" ${petGender == 'Male' ? 'selected' : ''}>Đực</option>
                                        <option value="Female" ${petGender == 'Female' ? 'selected' : ''}>Cái</option>
                                    </select>
                                </div>


                                <%-- Left Column --%>
                                <div class="form-group">
                                    <label for="breedId">Giống</label>
                                    <select id="breedId" name="breedId" class="form-control" required>
                                        <option value="" disabled selected>-- Chọn giống --</option>
                                        <c:forEach items="${breedList}" var="b">
                                            <option value="${b.breedId}" ${breedId == b.breedId ? 'selected' : ''}>${b.breedName}</option>
                                        </c:forEach> 
                                    </select>
                                </div>

                                <%-- Right Column --%>
                                <div class="form-group">
                                    <label for="colorInput">Màu sắc</label>
                                    <div class="autocomplete-container">
                                        <input type="text" id="colorInput" class="form-control" placeholder="Ví dụ: Vàng, Trắng" value="${petColor}" autocomplete="off">
                                        <input type="hidden" id="petColor" name="petColor" value="${petColor}">
                                        <div id="color-autocomplete-list" class="autocomplete-items"></div>
                                    </div>
                                </div>
                                <%-- Left Column --%>
                                <div class="form-group">
                                    <label for="petDob">Ngày sinh</label>
                                    <input type="date" id="petDob" name="petDob" class="form-control" value="${petDob}" required>
                                </div>

                                <%-- Right Column --%>
                                <div class="form-group">
                                    <label for="originInput">Nguồn gốc</label>
                                    <div class="autocomplete-container">
                                        <input type="text" id="originInput" class="form-control" placeholder="Ví dụ: Việt Nam" value="${petOrigin}" autocomplete="off">
                                        <input type="hidden" id="petOrigin" name="petOrigin" value="${petOrigin}">
                                        <div id="origin-autocomplete-list" class="autocomplete-items"></div>
                                    </div>
                                </div>

                                <%-- Left Column --%>
                                <div class="form-group">
                                    <label for="petPrice">Giá bán (₫)</label>
                                    <input type="number" id="petPrice" name="petPrice" class="form-control" placeholder="Nhập giá bán" required step="1000" value="${petPrice}">
                                </div>

                                <%-- Right Column (empty for alignment, or add another field here) --%>
                                <div></div>


                                <%-- Full Width Section for the Statuses --%>
                                <div class="form-full-width">
                                    <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 30px;">

                                        <%-- Trạng thái bán --%>
                                        <div class="form-group">
                                            <label for="petAvailability">Trạng thái bán</label>
                                            <select id="petAvailability" name="petAvailability" class="form-control">
                                                <option value="1" ${petAvailability == '1' ?'selected':''}>Còn hàng</option>
                                                <option value="0" ${petAvailability == '0' ?'selected':''}>Đã bán</option>
                                            </select>
                                        </div>

                                        <%-- Trạng thái đăng --%>
                                        <div class="form-group">
                                            <label for="petStatus">Trạng thái đăng</label>
                                            <select id="petStatus" name="petStatus" class="form-control">
                                                <option value="1" ${petStatus == '1' ?'selected':''}>Hiển thị</option>
                                                <option value="0" ${petStatus == '0' ?'selected':''}>Ẩn</option>
                                            </select>
                                        </div>

                                        <%-- Tiêm phòng --%>
                                        <div class="form-group">
                                            <label for="petVaccination">Tiêm phòng</label>
                                            <select id="petVaccination" name="petVaccination" class="form-control">
                                                <option value="1" ${petVaccination == '1' ?'selected':''}>Đã tiêm</option>
                                                <option value="0" ${petVaccination == '0' ?'selected':''}>Chưa tiêm</option>
                                            </select>
                                        </div>

                                    </div>
                                </div>

                                <div class="form-full-width">
                                    <div class="form-group">
                                        <label for="images">Hình ảnh thú cưng</label>
                                        <input type="file" id="images" name="images" class="form-control" multiple accept="image/*">
                                    </div>
                                </div>

                                <div class="form-full-width">
                                    <div class="form-group">
                                        <label for="petDescription">Mô tả</label>
                                        <textarea id="petDescription" name="petDescription" class="form-control" rows="5" placeholder="Nhập mô tả chi tiết về thú cưng...">${petDescription}</textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <a href="displayallpet" class="btn btn-outline">Hủy bỏ</a>
                            <button type="submit" class="btn btn-primary">Đăng bán</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
        <script>
            const colorData = [
            <c:forEach var="c" items="${colorList}" varStatus="loop">
            '${c}'<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
            ];
            const originData = [
            <c:forEach var="o" items="${originList}" varStatus="loop">
            '${o}'<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
            ];
        </script>
        <script src="js/pet_autocomplete.js"></script>
    </body>
</html>