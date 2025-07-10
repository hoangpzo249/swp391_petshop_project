<%-- 
    Document   : seller_invoice_view
    Created on : 28 Jun 2025, 22:05:25
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Hóa Đơn - PETFPT Shop</title>
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
                <img src="${sessionScope.userAccount.displayAccImage()}" alt="Seller Avatar"/>
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
            <div class="alert-message">${successMess}</div>
            <c:remove var="successMess" scope="session" />
        </c:if>
        <c:if test="${not empty errMess}">
            <div class="alert-message error">${errMess}</div>
            <c:remove var="errMess" scope="session" />
        </c:if>

        <div class="seller-container">
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
                        <a href="sellerdisplayinvoice" class="sidebar-link active">
                            <i class="fas fa-file-invoice"></i> Danh sách hóa đơn
                        </a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="displayorder" class="sidebar-link">
                            <i class="fas fa-bag-shopping"></i> Quản lý đơn hàng
                        </a>
                        <a href="displayallpet" class="sidebar-link">
                            <i class="fas fa-dog"></i> Quản lý thú cưng
                        </a>
                        <a href="displayrefund" class="sidebar-link"><i class="fas fa-undo-alt"></i> Quản lý hoàn tiền</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="addpet" class="sidebar-link">
                            <i class="fas fa-paw"></i> Đăng bán thú cưng
                        </a>
                        <a href="add_refund.jsp" class="sidebar-link"><i class="fas fa-plus-circle"></i> Gửi yêu cầu hoàn tiền</a>
                        <a href="profile" class="sidebar-link">
                            <i class="fas fa-user-circle"></i> Tài khoản của tôi
                        </a>
                        <a href="profile?action=change-password" class="sidebar-link">
                            <i class="fas fa-key"></i> Đổi mật khẩu
                        </a>
                        <a href="logout" class="sidebar-link">
                            <i class="fas fa-sign-out-alt"></i> Đăng xuất
                        </a>
                    </div>
                </div>
            </div>

            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title">
                        <i class="fas fa-file-invoice"></i> Quản lý Hóa Đơn
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="displaysalesstatistic">Seller</a></li>
                        <li>Quản lý Hóa Đơn</li>
                    </ul>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-list-ul"></i> Danh sách Hóa Đơn
                        </h3>
                    </div>
                    <div class="card-body">
                        <form action="sellerdisplayinvoice" method="GET" id="filterForm">
                            <div class="filter-controls">
                                <div class="input-group">
                                    <i class="fas fa-search"></i>
                                    <input type="text" name="searchKey" placeholder="Tìm theo Mã Đơn Hàng..." value="${param.searchKey}">
                                </div>

                                <div class="select-group">
                                    <select name="paymentMethod" onchange="this.form.submit()">
                                        <option value="" ${empty param.paymentMethod ? 'selected' : ''}>Tất cả phương thức TT</option>
                                        <option value="Thanh toán khi nhận hàng" ${param.paymentMethod == 'Thanh toán khi nhận hàng' ? 'selected' : ''}>Thanh toán khi nhận hàng</option>
                                        <option value="Thanh toán qua VNPAY" ${param.paymentMethod == 'Thanh toán qua VNPAY' ? 'selected' : ''}>Thanh toán qua VNPAY</option>
                                    </select>
                                </div>

                                <div class="date-range-group">
                                    <input type="date" name="startDate" value="${param.startDate}" onchange="this.form.submit()">
                                    <span>đến</span>
                                    <input type="date" name="endDate" value="${param.endDate}" onchange="this.form.submit()">
                                </div>

                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-filter"></i> Lọc
                                </button>
                                <button type="button" class="btn btn-outline" onclick="location.href = 'sellerdisplayinvoice'">
                                    <i class="fas fa-times"></i> Xóa bộ lọc
                                </button>
                            </div>
                        </form>

                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Mã HĐ</th>
                                        <th>Mã Đơn Hàng</th>
                                        <th>Ngày Xuất</th>
                                        <th>Tổng Tiền</th>
                                        <th>Phương thức TT</th>
                                        <th style="width: 10%;">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${invoiceList}" var="invoice">
                                        <tr>
                                            <td><strong>#${invoice.invoiceId}</strong></td>
                                            <td>#${invoice.order.orderId}</td>
                                            <td>
                                                <fmt:formatDate value="${invoice.issueDate}" pattern="dd-MM-yyyy HH:mm"/>
                                            </td>
                                            <td>
                                                <span class="price-value">
                                                    <fmt:formatNumber value="${invoice.totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                                </span>
                                            </td>
                                            <td>${invoice.paymentMethod}</td>
                                            <td>
                                                <div class="table-actions">
                                                    <a class="action-btn view-btn" title="Xem hóa đơn" href="sellerdisplayinvoicedetail?invoiceId=${invoice.invoiceId}">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty invoiceList}">
                                        <tr>
                                            <td colspan="6" style="text-align: center; padding: 30px;">
                                                Không tìm thấy hóa đơn nào phù hợp.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>

                        <c:if test="${pagination.totalPages > 1}">
                            <div class="pagination-container">
                                <ul class="pagination">
                                    <li class="page-item ${!pagination.hasPrevious() ? 'disabled' : ''}">
                                        <c:url value="sellerdisplayinvoice" var="prevUrl">
                                            <c:param name="page" value="${pagination.currentPage - 1}"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.paymentMethod}"><c:param name="paymentMethod" value="${param.paymentMethod}"/></c:if>
                                            <c:if test="${not empty param.startDate}"><c:param name="startDate" value="${param.startDate}"/></c:if>
                                            <c:if test="${not empty param.endDate}"><c:param name="endDate" value="${param.endDate}"/></c:if>
                                        </c:url>
                                        <a class="page-link" href="${pagination.hasPrevious() ? prevUrl : '#'}">«</a>
                                    </li>

                                    <c:if test="${pagination.startPage > 1}">
                                        <c:url value="sellerdisplayinvoice" var="firstPageUrl">
                                            <c:param name="page" value="1"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.paymentMethod}"><c:param name="paymentMethod" value="${param.paymentMethod}"/></c:if>
                                            <c:if test="${not empty param.startDate}"><c:param name="startDate" value="${param.startDate}"/></c:if>
                                            <c:if test="${not empty param.endDate}"><c:param name="endDate" value="${param.endDate}"/></c:if>
                                        </c:url>
                                        <li class="page-item"><a class="page-link" href="${firstPageUrl}">1</a></li>
                                            <c:if test="${pagination.startPage > 2}">
                                            <li class="page-item disabled"><span class="page-ellipsis">...</span></li>
                                            </c:if>
                                        </c:if>

                                    <c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="i">
                                        <c:url value="sellerdisplayinvoice" var="pageUrl">
                                            <c:param name="page" value="${i}"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.paymentMethod}"><c:param name="paymentMethod" value="${param.paymentMethod}"/></c:if>
                                            <c:if test="${not empty param.startDate}"><c:param name="startDate" value="${param.startDate}"/></c:if>
                                            <c:if test="${not empty param.endDate}"><c:param name="endDate" value="${param.endDate}"/></c:if>
                                        </c:url>
                                        <li class="page-item ${pagination.currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="${pageUrl}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <c:if test="${pagination.endPage < pagination.totalPages}">
                                        <c:if test="${pagination.endPage < pagination.totalPages - 1}">
                                            <li class="page-item disabled"><span class="page-ellipsis">...</span></li>
                                            </c:if>
                                            <c:url value="sellerdisplayinvoice" var="lastPageUrl">
                                                <c:param name="page" value="${pagination.totalPages}"/>
                                                <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                                <c:if test="${not empty param.paymentMethod}"><c:param name="paymentMethod" value="${param.paymentMethod}"/></c:if>
                                                <c:if test="${not empty param.startDate}"><c:param name="startDate" value="${param.startDate}"/></c:if>
                                                <c:if test="${not empty param.endDate}"><c:param name="endDate" value="${param.endDate}"/></c:if>
                                            </c:url>
                                        <li class="page-item"><a class="page-link" href="${lastPageUrl}">${pagination.totalPages}</a></li>
                                        </c:if>

                                    <li class="page-item ${!pagination.hasNext() ? 'disabled' : ''}">
                                        <c:url value="sellerdisplayinvoice" var="nextUrl">
                                            <c:param name="page" value="${pagination.currentPage + 1}"/>
                                            <c:if test="${not empty param.searchKey}"><c:param name="searchKey" value="${param.searchKey}"/></c:if>
                                            <c:if test="${not empty param.paymentMethod}"><c:param name="paymentMethod" value="${param.paymentMethod}"/></c:if>
                                            <c:if test="${not empty param.startDate}"><c:param name="startDate" value="${param.startDate}"/></c:if>
                                            <c:if test="${not empty param.endDate}"><c:param name="endDate" value="${param.endDate}"/></c:if>
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
    </body>
</html>