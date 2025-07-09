<%-- 
    Document   : refund_view
    Created on : Jun 26, 2025, 8:10:55 AM
    Author     : ADMIN
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="role" value="${sessionScope.userAccount.accRole}" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Hoàn Tiền - PETFPT Shop</title>
        <link href="css/seller_panel_page.css?v=3" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <div class="seller-header">
            <div class="logo-container">
                <a href="homepage">
                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                </a>
                <h1 class="seller-title">${role}</h1>
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
                    <span class="seller-role">${role}</span>
                </div>
                <div class="seller-actions">
                    <a href="profile" class="seller-action" title="Thông tin cá nhân"><i class="fas fa-user-circle"></i></a>
                    <a href="logout" class="seller-action" title="Đăng xuất"><i class="fas fa-sign-out-alt"></i></a>
                </div>
            </div>
        </div>

        <div class="seller-container">
            <div class="seller-sidebar">
                <div class="sidebar-header">
                    <h2 class="sidebar-title">${role} PANEL</h2>
                    <p class="sidebar-subtitle">Quản lý hệ thống PETFPT Shop</p>
                </div>
                <div class="sidebar-menu">
                    <div class="menu-category">
                        <h5 class="category-title">Điều hướng</h5>
                        <a href="comingsoon" class="sidebar-link"><i class="fas fa-tachometer-alt"></i> Tổng quan</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <c:if test="${role ne 'Manager'}">
                            <a href="displayorder" class="sidebar-link"><i class="fas fa-bag-shopping"></i> Quản lý đơn hàng</a>
                            <a href="displayallpet" class="sidebar-link"><i class="fas fa-dog"></i> Quản lý thú cưng</a>
                        </c:if>
                        <c:if test="${role eq 'Manager'}">
                            <a href="displaybreed" class="sidebar-link"><i class="fas fa-dna"></i> Quản lý giống thú cưng</a>
                            <a href="discountmanager" class="sidebar-link"><i class="fas fa-tags"></i> Quản lý mã giảm giá</a>
                        </c:if>
                        <a href="displayrefund" class="sidebar-link active"><i class="fas fa-undo-alt"></i> Quản lý hoàn tiền</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <c:if test="${role ne 'Manager'}">
                            <a href="addpet" class="sidebar-link"><i class="fas fa-paw"></i> Đăng bán thú cưng</a>
                            <a href="add_refund.jsp" class="sidebar-link"><i class="fas fa-plus-circle"></i> Gửi yêu cầu hoàn tiền</a>
                        </c:if>
                        <c:if test="${role eq 'Manager'}">
                            <a href="addbreed" class="sidebar-link"><i class="fas fa-plus-circle"></i> Thêm giống mới</a>
                            <a href="adddiscount.jsp" class="sidebar-link"><i class="fas fa-plus-circle"></i> Thêm mã giảm giá mới</a>
                        </c:if>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
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

            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-undo-alt"></i> Quản lý yêu cầu hoàn tiền</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li>Quản lý hoàn tiền</li>
                    </ul>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title"><i class="fas fa-list-ul"></i> Danh sách yêu cầu hoàn tiền</h3>
                        <div class="card-tools">
                            <c:if test="${role ne 'Manager'}">
                                <button class="btn btn-primary" onclick="location.href = 'add_refund.jsp'">
                                    <i class="fas fa-undo-alt"></i> Gửi yêu cầu hoàn tiền
                                </button>
                            </c:if>
                        </div>
                    </div>
                    <div class="card-body">
                        <form action="displayrefund" method="GET" id="filterForm">
                            <div class="filter-controls">
                                <div class="select-group">
                                    <select name="statusFilter">
                                        <option value="">Tất cả trạng thái</option>
                                        <option value="Pending" ${param.statusFilter == 'Pending' ? 'selected' : ''}>Pending</option>
                                        <option value="Approved" ${param.statusFilter == 'Approved' ? 'selected' : ''}>Approved</option>
                                        <option value="Rejected" ${param.statusFilter == 'Rejected' ? 'selected' : ''}>Rejected</option>
                                        <option value="Completed" ${param.statusFilter == 'Completed' ? 'selected' : ''}>Completed</option>
                                    </select>
                                </div>
                                <div class="select-group">
                                    <select name="sortByDate">
                                        <option value="desc" ${param.sortByDate == 'desc' ? 'selected' : ''}>Mới nhất</option>
                                        <option value="asc" ${param.sortByDate == 'asc' ? 'selected' : ''}>Cũ nhất</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary"><i class="fas fa-filter"></i> Lọc</button>
                                <button type="button" class="btn btn-outline" onclick="location.href = 'displayrefund'">
                                    <i class="fas fa-times"></i> Xóa bộ lọc
                                </button>
                            </div>
                        </form>

                        <c:choose>
                            <c:when test="${role eq 'Manager'}">
                                <c:set var="editUrlBase" value="updaterefundmanager"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="editUrlBase" value="updaterefundseller"/>
                            </c:otherwise>
                        </c:choose>

                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Mã Hoàn Tiền</th>
                                        <th>Mã Đơn Hàng</th>                                        
                                        <th>Trạng Thái</th>
                                        <th>Ngày Yêu Cầu</th>
                                        <th>Ngày Xử Lý</th>                                        
                                        <th>Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${refundList}" var="r">
                                        <tr>
                                            <td>#${r.refundId}</td>
                                            <td>${r.orderId}</td>                                         
                                            <td>
                                                <c:choose>
                                                    <c:when test="${r.refundStatus eq 'Pending'}">
                                                        <span class="status-badge status-pending">Pending</span>
                                                    </c:when>
                                                    <c:when test="${r.refundStatus eq 'Approved'}">
                                                        <span class="status-badge status-approved">Approved</span>
                                                    </c:when>
                                                    <c:when test="${r.refundStatus eq 'Rejected'}">
                                                        <span class="status-badge status-rejected">Rejected</span>
                                                    </c:when>
                                                    <c:when test="${r.refundStatus eq 'Completed'}">
                                                        <span class="status-badge status-completed">Completed</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="status-badge">${r.refundStatus}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td><fmt:formatDate value="${r.refundRequestDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${r.refundProcessedDate != null}">
                                                        <fmt:formatDate value="${r.refundProcessedDate}" pattern="dd/MM/yyyy HH:mm"/>
                                                    </c:when>
                                                    <c:otherwise>--</c:otherwise>
                                                </c:choose>
                                            </td>                                            
                                            <td>

                                                <c:choose>                                     
                                                    <c:when test="${r.refundStatus eq 'Completed' or r.refundStatus eq 'Rejected'}"> 
                                                        <a class="action-btn view-btn" href="viewrefund?id=${r.refundId}" title="Xem chi tiết">
                                                            <i class="fas fa-eye"></i>
                                                        </a>
                                                    </c:when>                           
                                                    <c:when test="${r.refundStatus eq 'Approved'}">
                                                        <c:if test="${role eq 'Manager'}">                                                           
                                                            <a class="action-btn edit-btn" href="${editUrlBase}?id=${r.refundId}" title="Chỉnh sửa">
                                                                <i class="fas fa-pencil-alt"></i>
                                                            </a>                                                       
                                                        </c:if>                                               
                                                        <c:if test="${role eq 'Seller'}">                                                           
                                                            <a class="action-btn view-btn" href="viewrefund?id=${r.refundId}" title="Xem chi tiết">
                                                                <i class="fas fa-eye"></i>
                                                            </a>                                                      
                                                        </c:if>                                               
                                                    </c:when>                                                 
                                                    <c:otherwise>                                                      
                                                        <a class="action-btn edit-btn" href="${editUrlBase}?id=${r.refundId}" title="Chỉnh sửa">
                                                            <i class="fas fa-pencil-alt"></i>
                                                        </a>                                                       
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty refundList}">
                                        <tr>
                                            <td colspan="11" style="text-align: center; padding: 30px;">Không có yêu cầu hoàn tiền nào.</td>
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
