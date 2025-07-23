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
                <h1 class="seller-title">SELLER</h1>
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
                        <a href="displaysalesstatistic" class="sidebar-link"><i class="fas fa-tachometer-alt"></i> Tổng quan</a>
                        <a href="sellerdisplayinvoice" class="sidebar-link">
                            <i class="fas fa-file-invoice"></i> Danh sách hóa đơn
                        </a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="displayorder" class="sidebar-link"><i class="fas fa-bag-shopping"></i> Quản lý đơn hàng</a>
                        <a href="displayallpet" class="sidebar-link active"><i class="fas fa-dog"></i> Quản lý thú cưng</a>
                        <a href="displayrefund" class="sidebar-link"><i class="fas fa-undo-alt"></i> Quản lý hoàn tiền</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="addpet" class="sidebar-link"><i class="fas fa-paw"></i> Đăng bán thú cưng</a>
                        <a href="add_refund.jsp" class="sidebar-link"><i class="fas fa-plus-circle"></i> Gửi yêu cầu hoàn tiền</a>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
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
                        <li><a href="displaysalesstatistic">Seller</a></li>
                        <li>Quản lý thú cưng</li>
                    </ul>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title"><i class="fas fa-list-ul"></i> Danh sách thú cưng</h3>
                        <div class="card-tools">
                            <button class="btn btn-primary" onclick="location.href = 'addpet'">
                                <i class="fas fa-paw"></i> Đăng bán thú cưng
                            </button>
                        </div>
                    </div>
                    <div class="card-body">

                        <form action="displayallpet" method="GET" id="filterForm">
                            <div class="filter-controls">
                                <div class="input-group" style="flex-grow: 1;">
                                    <i class="fas fa-search"></i>
                                    <input type="text" name="searchKey" placeholder="Tìm theo Mã, Tên thú cưng..." value="${param.searchKey}">
                                </div>
                                <div class="select-group">
                                    <select name="availability" onchange="this.form.submit()">
                                        <option value="">Tất cả trạng thái</option>
                                        <option value="1" ${param.availability == '1' ? 'selected' : ''}>Còn hàng</option>
                                        <option value="0" ${param.availability == '0' ? 'selected' : ''}>Đã bán</option>
                                    </select>
                                </div>
                                <div class="select-group">
                                    <select name="petStatus" onchange="this.form.submit()">
                                        <option value="">Trạng thái đăng</option>
                                        <option value="1" ${param.petStatus == '1' ? 'selected' : ''}>Đang hiển thị</option>
                                        <option value="0" ${param.petStatus == '0' ? 'selected' : ''}>Đã ẩn</option>
                                    </select>
                                </div>
                                <div class="select-group">
                                    <select name="breedStatus" onchange="this.form.submit()">
                                        <option value="">Trạng thái giống</option>
                                        <option value="1" ${param.breedStatus == '1' ? 'selected' : ''}>Đang hiển thị</option>
                                        <option value="0" ${param.breedStatus == '0' ? 'selected' : ''}>Đã ẩn</option>
                                    </select>
                                </div>
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
                            </div>

                            <div class="filter-actions">
                                <button type="button" class="btn btn-outline" onclick="location.href = 'displayallpet'">
                                    <i class="fas fa-times"></i> Xóa bộ lọc
                                </button>
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
                                        <th>Trạng Thái Bán</th>
                                        <th>Trạng Thái Đăng</th>
                                        <th>Trạng Thái Giống</th>
                                        <th style="width: 15%;">Thao Tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${petList}" var="pet">
                                        <tr>
                                            <td>
                                                <img src="${pet.getFirstImage()}" alt="${pet.petName}" class="table-avatar"/>
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
                                                <c:choose>
                                                    <c:when test="${pet.petStatus == 1}">
                                                        <span class="status-badge status-active">Hiển thị</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="status-badge status-blocked">Đã ẩn</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${pet.breed.breedStatus == 1}">
                                                        <span class="status-badge status-active">Hiển thị</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="status-badge status-blocked">Đã ẩn</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="table-actions">
                                                    <a class="action-btn view-btn" title="Xem chi tiết" href="displaypet?id=${pet.petId}">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                    <a class="action-btn edit-btn" title="Chỉnh sửa" href="updatepet?id=${pet.petId}">
                                                        <i class="fas fa-pencil-alt"></i>
                                                    </a>

                                                    <c:choose>
                                                        <c:when test="${pet.petStatus == 1}">
                                                            <a class="action-btn unhide-btn" title="Ẩn thú cưng" 
                                                               href="updatepetstatus?petId=${pet.petId}&status=0"
                                                               onclick="showConfirmationModal(event, 'ẩn thú cưng #${pet.petId} - ${pet.petName}', this.href, 'btn-danger')">
                                                                <i class="fas fa-toggle-off"></i>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a class="action-btn block-btn" title="Hiện thú cưng" 
                                                               href="updatepetstatus?petId=${pet.petId}&status=1"
                                                               onclick="showConfirmationModal(event, 'hiển thị thú cưng #${pet.petId} - ${pet.petName}', this.href, 'btn-success')">
                                                                <i class="fas fa-toggle-on"></i>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty petList}">
                                        <tr>
                                            <td colspan="8" style="text-align: center; padding: 30px;">
                                                Không tìm thấy thú cưng nào phù hợp.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>

                        <!-- Pagination -->
                        <c:if test="${pagination.totalPages > 1}">
                            <div class="pagination-container">
                                <ul class="pagination">
                                    <li class="page-item ${!pagination.hasPrevious() ? 'disabled' : ''}">
                                        <c:url value="displayallpet" var="prevUrl">
                                            <c:param name="page" value="${pagination.currentPage - 1}"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.availability}"><c:param name="availability" value="${param.availability}"/></c:if>
                                            <c:if test="${not empty param.petStatus}"><c:param name="petStatus" value="${param.petStatus}"/></c:if>
                                            <c:if test="${not empty param.species}"><c:param name="species" value="${param.species}"/></c:if>
                                            <c:if test="${not empty param.breedId}"><c:param name="breedId" value="${param.breedId}"/></c:if>
                                            <c:if test="${not empty param.gender}"><c:param name="gender" value="${param.gender}"/></c:if>
                                            <c:if test="${not empty param.vaccination}"><c:param name="vaccination" value="${param.vaccination}"/></c:if>
                                        </c:url>
                                        <a class="page-link" href="${pagination.hasPrevious() ? prevUrl : '#'}">«</a>
                                    </li>

                                    <c:if test="${pagination.startPage > 1}">
                                        <c:url value="displayallpet" var="firstPageUrl">
                                            <c:param name="page" value="1"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.availability}"><c:param name="availability" value="${param.availability}"/></c:if>
                                            <c:if test="${not empty param.petStatus}"><c:param name="petStatus" value="${param.petStatus}"/></c:if>
                                            <c:if test="${not empty param.species}"><c:param name="species" value="${param.species}"/></c:if>
                                            <c:if test="${not empty param.breedId}"><c:param name="breedId" value="${param.breedId}"/></c:if>
                                            <c:if test="${not empty param.gender}"><c:param name="gender" value="${param.gender}"/></c:if>
                                            <c:if test="${not empty param.vaccination}"><c:param name="vaccination" value="${param.vaccination}"/></c:if>
                                        </c:url>
                                        <li class="page-item"><a class="page-link" href="${firstPageUrl}">1</a></li>
                                            <c:if test="${pagination.startPage > 2}">
                                            <li class="page-item disabled"><span class="page-ellipsis">...</span></li>
                                            </c:if>
                                        </c:if>

                                    <c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="i">
                                        <c:url value="displayallpet" var="pageUrl">
                                            <c:param name="page" value="${i}"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.availability}"><c:param name="availability" value="${param.availability}"/></c:if>
                                            <c:if test="${not empty param.petStatus}"><c:param name="petStatus" value="${param.petStatus}"/></c:if>
                                            <c:if test="${not empty param.species}"><c:param name="species" value="${param.species}"/></c:if>
                                            <c:if test="${not empty param.breedId}"><c:param name="breedId" value="${param.breedId}"/></c:if>
                                            <c:if test="${not empty param.gender}"><c:param name="gender" value="${param.gender}"/></c:if>
                                            <c:if test="${not empty param.vaccination}"><c:param name="vaccination" value="${param.vaccination}"/></c:if>
                                        </c:url>
                                        <li class="page-item ${pagination.currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="${pageUrl}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <c:if test="${pagination.endPage < pagination.totalPages}">
                                        <c:if test="${pagination.endPage < pagination.totalPages - 1}">
                                            <li class="page-item disabled"><span class="page-ellipsis">...</span></li>
                                            </c:if>
                                            <c:url value="displayallpet" var="lastPageUrl">
                                                <c:param name="page" value="${pagination.totalPages}"/>
                                                <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                                <c:if test="${not empty param.availability}"><c:param name="availability" value="${param.availability}"/></c:if>
                                                <c:if test="${not empty param.petStatus}"><c:param name="petStatus" value="${param.petStatus}"/></c:if>
                                                <c:if test="${not empty param.species}"><c:param name="species" value="${param.species}"/></c:if>
                                                <c:if test="${not empty param.breedId}"><c:param name="breedId" value="${param.breedId}"/></c:if>
                                                <c:if test="${not empty param.gender}"><c:param name="gender" value="${param.gender}"/></c:if>
                                                <c:if test="${not empty param.vaccination}"><c:param name="vaccination" value="${param.vaccination}"/></c:if>
                                            </c:url>
                                        <li class="page-item"><a class="page-link" href="${lastPageUrl}">${pagination.totalPages}</a></li>
                                        </c:if>

                                    <li class="page-item ${!pagination.hasNext() ? 'disabled' : ''}">
                                        <c:url value="displayallpet" var="nextUrl">
                                            <c:param name="page" value="${pagination.currentPage + 1}"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.availability}"><c:param name="availability" value="${param.availability}"/></c:if>
                                            <c:if test="${not empty param.petStatus}"><c:param name="petStatus" value="${param.petStatus}"/></c:if>
                                            <c:if test="${not empty param.species}"><c:param name="species" value="${param.species}"/></c:if>
                                            <c:if test="${not empty param.breedId}"><c:param name="breedId" value="${param.breedId}"/></c:if>
                                            <c:if test="${not empty param.gender}"><c:param name="gender" value="${param.gender}"/></c:if>
                                            <c:if test="${not empty param.vaccination}"><c:param name="vaccination" value="${param.vaccination}"/></c:if>
                                        </c:url>
                                        <a class="page-link" href="${pagination.hasNext() ? nextUrl : '#'}">»</a>
                                    </li>
                                </ul>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal-backdrop" id="confirmationModal">
            <div class="modal-dialog">
                <div class="modal-header">
                    <h3 class="modal-title">Xác nhận hành động</h3>
                    <button class="modal-close" onclick="closeModal()">×</button>
                </div>
                <div class="modal-body">
                    <p id="modalText">Bạn có chắc chắn muốn thực hiện hành động này không?</p>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline" onclick="closeModal()">Hủy bỏ</button>
                    <button id="modalConfirmButton" class="btn">Xác nhận</button>
                </div>
            </div>
        </div>
        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
        <script src="js/modal_confirmation.js"></script>
    </body>
</html>