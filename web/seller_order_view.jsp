<%-- 
    Document   : Seller_order_view
    Created on : 23 May 2025, 10:04:28
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Đơn Hàng - PETFPT Shop</title>
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
                    <a href="profile" class="seller-action" title="Thông tin cá nhân">
                        <i class="fas fa-user-circle"></i>
                    </a>
                    <a href="logout" class="seller-action" title="Đăng xuất">
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
                        <a href="displaysalesstatistic" class="sidebar-link">
                            <i class="fas fa-tachometer-alt"></i> Tổng quan
                        </a>
                        <a href="sellerdisplayinvoice" class="sidebar-link">
                            <i class="fas fa-file-invoice"></i> Danh sách hóa đơn
                        </a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="displayorder" class="sidebar-link active"><i class="fas fa-bag-shopping"></i> Quản lý đơn hàng</a>
                        <a href="displayallpet" class="sidebar-link"><i class="fas fa-dog"></i> Quản lý thú cưng</a>
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
                    <h1 class="page-title">
                        <i class="fas fa-bag-shopping"></i> Quản lý đơn hàng
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="displaysalesstatistic">Seller</a></li>
                        <li>Quản lý đơn hàng</li>
                    </ul>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-list-ul"></i> Danh sách đơn hàng
                        </h3>
                        <div class="card-tools">
                            <button class="btn btn-outline">
                                <i class="fas fa-file-export"></i> Xuất dữ liệu
                            </button>
                        </div>
                    </div>
                    <div class="card-body">
                        <form action="displayorder" method="GET" id="filterForm">
                            <div class="filter-controls">
                                <div class="input-group">
                                    <i class="fas fa-search"></i>
                                    <input type="text" name="searchKey" placeholder="Tìm theo Mã ĐH, Tên khách hàng..." value="${param.searchKey}">
                                </div>

                                <div class="select-group">
                                    <select name="status" onchange="this.form.submit()">
                                        <option value="" ${empty param.status ? 'selected' : ''}>Tất cả trạng thái</option>
                                        <option value="Pending" ${param.status == 'Pending' ? 'selected' : ''}>Chờ xử lý</option>
                                        <option value="Confirmed" ${param.status == 'Confirmed' ? 'selected' : ''}>Đã xác nhận</option>
                                        <option value="Pending Shipper" ${param.status == 'Pending Shipper' ? 'selected' : ''}>Chờ Shipper xác nhận</option>
                                        <option value="Rejected" ${param.status == 'Rejected' ? 'selected' : ''}>Bị từ chối</option>
                                        <option value="Shipping" ${param.status == 'Shipping' ? 'selected' : ''}>Đang giao</option>
                                        <option value="Delivered" ${param.status == 'Delivered' ? 'selected' : ''}>Đã giao</option>
                                        <option value="Cancelled" ${param.status == 'Cancelled' ? 'selected' : ''}>Đã hủy</option>
                                    </select>
                                </div>

                                <div class="date-range-group">
                                    <input type="date" name="startDate" value="${param.startDate}" onchange="this.form.submit()">
                                    <span>đến</span>
                                    <input type="date" name="endDate" value="${param.endDate}" onchange="this.form.submit()">
                                </div>

                                <div class="select-group">
                                    <select name="sort" onchange="this.form.submit()">
                                        <option value="DESC" ${param.sort == 'DESC' ? 'selected' : ''}>Đơn mới nhất</option>
                                        <option value="ASC" ${param.sort == 'ASC' ? 'selected' : ''}>Đơn cũ nhất</option>
                                    </select>
                                </div>

                                <button type="button" class="btn btn-outline" onclick="location.href = 'displayorder'">
                                    <i class="fas fa-times"></i> Xóa bộ lọc
                                </button>
                            </div>
                        </form>

                        <!-- Orders Table -->
                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Mã ĐH</th>
                                        <th>Ngày Đặt</th>
                                        <th>Khách Hàng</th>
                                        <th>Tổng Tiền</th>
                                        <th>Trạng Thái</th>
                                        <th>Thanh Toán</th>
                                        <th style="width: 15%;">Thao tác</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach items="${orderList}" var="order">
                                        <tr>
                                            <td><strong>#${order.orderId}</strong></td>
                                            <td>
                                                <fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy HH:mm"/>
                                            </td>
                                            <td>${order.customerName}</td>
                                            <td>
                                                <span class="price-value">
                                                    <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                                </span>
                                            </td>
                                            <td>
                                                <span class="status-badge status-${order.orderStatus.toLowerCase()}">
                                                    <c:choose>
                                                        <c:when test="${order.orderStatus == 'Pending'}">Chờ xử lý</c:when>
                                                        <c:when test="${order.orderStatus == 'Confirmed'}">Đã xác nhận</c:when>
                                                        <c:when test="${order.orderStatus == 'Cancelled'}">Đã hủy</c:when>
                                                        <c:when test="${order.orderStatus == 'Rejected'}">Bị từ chối</c:when>
                                                        <c:when test="${order.orderStatus == 'Shipping'}">Đang giao</c:when>
                                                        <c:when test="${order.orderStatus == 'Pending Shipper'}">Chờ Shipper</c:when>
                                                        <c:when test="${order.orderStatus == 'Delivered'}">Đã giao</c:when>
                                                        <c:otherwise>${order.orderStatus}</c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </td>
                                            <td>
                                                <span class="status-badge status-payment-${order.paymentStatus.toLowerCase()}">
                                                    <c:choose>
                                                        <c:when test="${order.paymentStatus == 'Paid'}">Đã thanh toán</c:when>
                                                        <c:when test="${order.paymentStatus == 'Unpaid'}">Chưa thanh toán</c:when>
                                                        <c:otherwise>${order.paymentStatus}</c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </td>
                                            <td>
                                                <div class="table-actions">
                                                    <a class="action-btn view-btn" title="Xem chi tiết" href="displayorderdetail?orderId=${order.orderId}">
                                                        <i class="fas fa-eye"></i>
                                                    </a>

                                                    <c:if test="${order.orderStatus == 'Pending'}">
                                                        <a class="action-btn confirm-btn" title="Xác nhận đơn hàng" 
                                                           href="#" 
                                                           onclick="showConfirmationModal(event, 'xác nhận đơn hàng này', 'updateorderstatus?action=confirm&status=Confirmed&orderId=${order.orderId}', 'btn-success')">
                                                            <i class="fas fa-check"></i>
                                                        </a>
                                                        <a class="action-btn reject-btn" title="Từ chối đơn hàng" 
                                                           href="#" 
                                                           onclick="showConfirmationModal(event, 'từ chối đơn hàng này', 'updateorderstatus?action=reject&status=Rejected&orderId=${order.orderId}', 'btn-danger', true)"> <%-- ADDED 'true' --%>
                                                            <i class="fas fa-times"></i>
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${order.orderStatus == 'Confirmed'}">
                                                        <a class="action-btn shipping-btn" title="Chỉ định Shipper"
                                                           href="displayorderdetail?orderId=${order.orderId}">
                                                            <i class="fas fa-truck"></i>
                                                        </a>
                                                    </c:if>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty orderList}">
                                        <tr>
                                            <td colspan="7" style="text-align: center; padding: 30px;">
                                                Không tìm thấy đơn hàng nào phù hợp.
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
                                        <c:url value="displayorder" var="prevUrl">
                                            <c:param name="page" value="${pagination.currentPage - 1}"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.status}"><c:param name="status" value="${param.status}"/></c:if>
                                            <c:if test="${not empty param.startDate}"><c:param name="startDate" value="${param.startDate}"/></c:if>
                                            <c:if test="${not empty param.endDate}"><c:param name="endDate" value="${param.endDate}"/></c:if>
                                            <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
                                        </c:url>
                                        <a class="page-link" href="${pagination.hasPrevious() ? prevUrl : '#'}">«</a>
                                    </li>

                                    <c:if test="${pagination.startPage > 1}">
                                        <c:url value="displayorder" var="firstPageUrl">
                                            <c:param name="page" value="1"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.status}"><c:param name="status" value="${param.status}"/></c:if>
                                            <c:if test="${not empty param.startDate}"><c:param name="startDate" value="${param.startDate}"/></c:if>
                                            <c:if test="${not empty param.endDate}"><c:param name="endDate" value="${param.endDate}"/></c:if>
                                            <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
                                        </c:url>
                                        <li class="page-item"><a class="page-link" href="${firstPageUrl}">1</a></li>
                                            <c:if test="${pagination.startPage > 2}">
                                            <li class="page-item disabled"><span class="page-ellipsis">...</span></li>
                                            </c:if>
                                        </c:if>

                                    <c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="i">
                                        <c:url value="displayorder" var="pageUrl">
                                            <c:param name="page" value="${i}"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.status}"><c:param name="status" value="${param.status}"/></c:if>
                                            <c:if test="${not empty param.startDate}"><c:param name="startDate" value="${param.startDate}"/></c:if>
                                            <c:if test="${not empty param.endDate}"><c:param name="endDate" value="${param.endDate}"/></c:if>
                                            <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
                                        </c:url>
                                        <li class="page-item ${pagination.currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="${pageUrl}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <c:if test="${pagination.endPage < pagination.totalPages}">
                                        <c:if test="${pagination.endPage < pagination.totalPages - 1}">
                                            <li class="page-item disabled"><span class="page-ellipsis">...</span></li>
                                            </c:if>
                                            <c:url value="displayorder" var="lastPageUrl">
                                                <c:param name="page" value="${pagination.totalPages}"/>
                                                <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                                <c:if test="${not empty param.status}"><c:param name="status" value="${param.status}"/></c:if>
                                                <c:if test="${not empty param.startDate}"><c:param name="startDate" value="${param.startDate}"/></c:if>
                                                <c:if test="${not empty param.endDate}"><c:param name="endDate" value="${param.endDate}"/></c:if>
                                                <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
                                            </c:url>
                                        <li class="page-item"><a class="page-link" href="${lastPageUrl}">${pagination.totalPages}</a></li>
                                        </c:if>

                                    <li class="page-item ${!pagination.hasNext() ? 'disabled' : ''}">
                                        <c:url value="displayorder" var="nextUrl">
                                            <c:param name="page" value="${pagination.currentPage + 1}"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.status}"><c:param name="status" value="${param.status}"/></c:if>
                                            <c:if test="${not empty param.startDate}"><c:param name="startDate" value="${param.startDate}"/></c:if>
                                            <c:if test="${not empty param.endDate}"><c:param name="endDate" value="${param.endDate}"/></c:if>
                                            <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
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
        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
        <div class="modal-backdrop" id="confirmationModal">
            <div class="modal-dialog">
                <div class="modal-header">
                    <h3 class="modal-title" id="modalTitle">Xác nhận hành động</h3>
                    <button class="modal-close" onclick="closeModal()">×</button>
                </div>
                <div class="modal-body">
                    <p id="modalText">Bạn có chắc chắn muốn thực hiện hành động này không?</p>

                    <div id="rejectionReasonContainer" style="display: none;">
                        <label for="rejectionReason">Lý do từ chối:</label>
                        <textarea id="rejectionReason" class="form-control" rows="3" placeholder="Nhập lý do từ chối đơn hàng..."></textarea>
                        <div class="checkbox-container">
                            <input type="checkbox" id="hidePetsCheckbox" name="hidePets">
                            <label for="hidePetsCheckbox">Đồng thời ẩn các thú cưng trong đơn hàng này.</label>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline" onclick="closeModal()">Hủy bỏ</button>
                    <button id="modalConfirmButton" class="btn">Xác nhận</button>
                </div>
            </div>
        </div>
        <script src="js/order_confirmation_window.js"></script>
    </body>
</html>