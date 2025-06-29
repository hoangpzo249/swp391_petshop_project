<%-- 
    Document   : manager_discount_view
    Created on : Jun 23, 2025, 8:51:44 AM
    Author     : AD
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Mã Giảm Giá - PETFPT Shop</title>
        <link href="css/manager_panel_page.css?v=3" rel="stylesheet" type="text/css"/>
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
                <img src="images/support button/account.png" alt="Manager Avatar"/>
                <div class="manager-info">
                    <span class="manager-name">${sessionScope.userAccount.accFname} ${sessionScope.userAccount.accLname}</span>
                    <span class="manager-role">Manager</span>
                </div>
                <div class="manager-actions">
                    <a href="profile" class="manager-action"><i class="fas fa-user-circle"></i></a>
                    <a href="logout" class="manager-action"><i class="fas fa-sign-out-alt"></i></a>
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
            <div class="manager-sidebar">
                <div class="sidebar-header">
                    <h2 class="sidebar-title">MANAGER PANEL</h2>
                    <p class="sidebar-subtitle">Quản lý hệ thống PETFPT Shop</p>
                </div>
                <div class="sidebar-menu">
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="discountmanager" class="sidebar-link active"><i class="fas fa-tags"></i> Quản lý mã giảm giá</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="adddiscount.jsp" class="sidebar-link"><i class="fas fa-plus-circle"></i> Thêm mã giảm giá mới</a>
                    </div>
                </div>
            </div>

            <div class="manager-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-tags"></i> Quản lý mã giảm giá</h1>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title"><i class="fas fa-list-ul"></i> Danh sách mã giảm giá</h3>
                        <div class="card-tools">
                            <button class="btn btn-primary" onclick="location.href = 'adddiscount.jsp'">
                                <i class="fas fa-plus-circle"></i> Thêm mã mới
                            </button>
                        </div>
                    </div>
                    <div class="card-body">
                        <form action="discountmanager" method="GET" id="filterForm">
                            <div class="filter-controls">

                                <div class="input-group" style="flex-grow: 1;">
                                    <i class="fas fa-search"></i>
                                    <input type="text" name="searchKey" placeholder="Tìm theo mã hoặc mô tả..."
                                           value="${param.searchKey}">
                                </div>

                                <div class="select-group">
                                    <select name="type">
                                        <option value="">Tất cả loại</option>
                                        <option value="Percent" ${param.type == 'Percent' ? 'selected' : ''}>Percent</option>
                                        <option value="Fixed" ${param.type == 'Fixed' ? 'selected' : ''}>Fixed</option>
                                    </select>
                                </div>

                                <div class="select-group">
                                    <select name="status">
                                        <option value="">Tất cả trạng thái</option>
                                        <option value="1" ${param.status == '1' ? 'selected' : ''}>Active</option>
                                        <option value="0" ${param.status == '0' ? 'selected' : ''}>Deactive</option>
                                    </select>
                                </div>

                                <div class="date-group">
                                    <label for="fromDate">HSD Từ:</label>
                                    <input type="date" id="fromDate" name="fromDate" value="${param.fromDate}">
                                    <label for="toDate">HSD Đến:</label>
                                    <input type="date" id="toDate" name="toDate" value="${param.toDate}">
                                </div>

                                <div class="select-group">
                                    <select name="sortBy">
                                        <option value="">Sắp xếp theo</option>
                                        <option value="validTo_asc" ${param.sortBy == 'validTo_asc' ? 'selected' : ''}>HSD tăng dần</option>
                                        <option value="validTo_desc" ${param.sortBy == 'validTo_desc' ? 'selected' : ''}>HSD giảm dần</option>
                                        <option value="usageCount_asc" ${param.sortBy == 'usageCount_asc' ? 'selected' : ''}>Số lần sử dụng tăng</option>
                                        <option value="usageCount_desc" ${param.sortBy == 'usageCount_desc' ? 'selected' : ''}>Số lần sử dụng giảm</option>
                                    </select>
                                </div>

                                <button type="submit" class="btn btn-primary"><i class="fas fa-filter"></i> Lọc</button>
                                <button type="button" class="btn btn-outline" onclick="location.href = 'discountmanager'">
                                    <i class="fas fa-times"></i> Xóa bộ lọc
                                </button>
                            </div>
                        </form>

                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th>#ID</th>
                                        <th>Mã</th>
                                        <th>Loại</th>
                                        <th>Giá trị</th>
                                        <th>HSD</th>
                                        <th>Tối thiểu</th>
                                        <th>Tối đa</th>
                                        <th>Sử dụng</th>
                                        <th>Trạng thái</th>
                                        <th>Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${discountList}" var="d">
                                        <tr>
                                            <td>${d.discountId}</td>
                                            <td>${d.discountCode}</td>
                                            <td>${d.discountType}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${d.discountType == 'Percent'}">
                                                        ${d.discountValue}%
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fmt:formatNumber value="${d.discountValue}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td>
                                                <fmt:formatDate value="${d.validTo}" pattern="dd/MM/yyyy"/>
                                            </td>
                                            <td><fmt:formatNumber value="${d.minOrderAmount}" type="currency" currencySymbol="₫" groupingUsed="true"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${d.discountType == 'Fixed'}">
                                                        -
                                                    </c:when>
                                                    <c:when test="${d.maxValue != null}">
                                                        <fmt:formatNumber value="${d.maxValue}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                                                    </c:when>
                                                    <c:otherwise>Không giới hạn</c:otherwise>
                                                </c:choose>

                                            </td>

                                            <td>${d.usageCount}/${d.maxUsage != null ? d.maxUsage : '∞'}</td>

                                            <td>
                                                <c:choose>
                                                    <c:when test="${d.active}">
                                                        <span class="status-badge status-active">Active</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="status-badge status-blocked">Deactive</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="table-actions">
                                                    <a class="action-btn edit-btn" href="updatediscount?id=${d.discountId}" title="Chỉnh sửa">
                                                        <i class="fas fa-pencil-alt"></i>
                                                    </a>
                                                    <a class="action-btn ${d.active ? 'toggle-btn-active' : 'toggle-btn-inactive'}"
                                                       href="deactivatediscount?id=${d.discountId}" >
                                                        <i class="fas ${d.active ? 'fa-check-square' : 'fa-ban'} fa-fw"></i>
                                                    </a>
                                                    <a class="action-btn reset-btn " href="resetdiscountusage?id=${d.discountId}" style="background: red" >
                                                        <i class="fas fa-sync-alt fa-fw"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty discountList}">
                                        <tr>
                                            <td colspan="10" style="text-align: center; padding: 30px;">Không tìm thấy mã giảm giá nào.</td>
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
