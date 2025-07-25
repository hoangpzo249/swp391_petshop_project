<%-- 
    Document   : update_discount
    Created on : Jun 25, 2025, 8:44:02 AM
    Author     : ADMIN
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cập Nhật Mã Giảm Giá - PETFPT Shop</title>
        <link href="css/manager_panel_page.css?v=3" rel="stylesheet" type="text/css"/>
        <link href="css/seller_panel_page.css?v=4" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <script src="js/add_discount_validate.js?v=2"></script>
        <script src="js/discount_fixed.js?v=4"></script>
        <script src="js/money.js?"></script>
        <fmt:setLocale value="en" />
    </head>
    <body>
        <div class="seller-header">
            <div class="logo-container">
                <a href="homepage">
                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                </a>
                <h1 class="seller-title">Manager</h1>
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
                    <span class="seller-role">Manager</span>
                </div>
                <div class="seller-actions">
                    <a href="profile" class="seller-action"><i class="fas fa-user-circle"></i></a>
                    <a href="logout" class="seller-action"><i class="fas fa-sign-out-alt"></i></a>
                </div>
            </div>
        </div>
        <c:set var="discountType" value="${param.type != null ? param.type : discount.discountType}" />
        <c:if test="${not empty errMess}">
            <div class="alert-message error">${errMess}</div>
            <c:remove var="errMess" scope="request" />
        </c:if>
        <div class="manager-container">
            <div class="manager-sidebar">
                <div class="manager-sidebar">
                    <div class="sidebar-header">
                        <h2 class="sidebar-title">MANAGER PANEL</h2>
                        <p class="sidebar-subtitle">Quản lý hệ thống PETFPT Shop</p>
                    </div>
                    <div class="sidebar-menu">
                        <div class="menu-category">
                            <h5 class="category-title">Điều hướng</h5>
                            <a href="displayrevenuestatistic" class="sidebar-link"><i class="fas fa-tachometer-alt"></i> Tổng quan</a>
                        </div>
                        <div class="menu-category">
                            <h5 class="category-title">Quản lý</h5>
                            <a href="displaybreed" class="sidebar-link"><i class="fas fa-dna"></i> Quản lý giống thú cưng</a>
                            <a href="discountmanager" class="sidebar-link active"><i class="fas fa-tags"></i> Quản lý mã giảm giá</a>
                            <a href="displayrefund" class="sidebar-link "><i class="fas fa-undo-alt"></i> Quản lý hoàn tiền</a>
                        </div>
                        <div class="menu-category">
                            <h5 class="category-title">Thao tác</h5>
                            <a href="addbreed" class="sidebar-link"><i class="fas fa-plus-circle"></i> Thêm giống mới</a>
                            <a href="adddiscount.jsp" class="sidebar-link"><i class="fas fa-plus-circle"></i> Thêm mã giảm giá mới</a>
                            <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                            <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                            <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-edit"></i> Cập Nhật Mã Giảm Giá</h1>
                </div>

                <div class="card">
                    <form id="add_discount" action="updatediscount" method="POST">
                        <input type="hidden" name="id" value="${discount.discountId}">
                        <div class="card-body">
                            <div class="form-grid">
                                <div class="form-group">
                                    <label for="code">Mã giảm giá</label>
                                    <input type="text" id="code" name="code" class="form-control" value="${discount.discountCode}" readonly>
                                    <input type="hidden" id="code" name="code" class="form-control" value="${discount.discountCode}" >

                                </div>
                                <div class="form-group">
                                    <label for="type">Loại giảm</label>
                                    <select id="type" name="type" class="form-control"  >
                                        <option value="Percent" ${param.type == 'Percent' || (empty param.type && discount.discountType == 'Percent') ? 'selected' : ''}>Percent</option>
                                        <option value="Fixed" ${param.type == 'Fixed' || (empty param.type && discount.discountType == 'Fixed') ? 'selected' : ''}>Fixed</option>

                                    </select>
                                </div>
                                <c:set var="discountValue" value="${param.value != null ? param.value : discount.discountValue}" />
                                <c:choose>
                                    <c:when test="${discountType eq 'Fixed'}">
                                        <c:set var="formattedValue">
                                            <fmt:formatNumber value="${discountValue}" type="currency" groupingUsed="true"/>
                                        </c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="formattedValue" value="${discountValue}" />
                                    </c:otherwise>
                                </c:choose>                              

                                <div class="form-group">
                                    <label for="value">Giá trị</label>
                                    <input type="text" id="value" name="value" class="form-control"
                                           value="${param.value != null ? param.value : discountValue}" required>
                                </div>

                                <div class="form-group">
                                    <label for="minAmount">Đơn hàng tối thiểu (₫)</label>
                                    <c:choose>
                                        <c:when test="${param.minAmount != null}">
                                            <input type="text" id="minAmount" name="minAmount" class="form-control"
                                                   value="<fmt:formatNumber value="${param.minAmount}" pattern="#" />" />
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" id="minAmount" name="minAmount" class="form-control"
                                                   value="<fmt:formatNumber value="${discount.minOrderAmount}" pattern="#" />" />
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="form-group">
                                    <label for="maxValue">Giảm tối đa (₫)</label>
                                    <c:choose>
                                        <c:when test="${param.maxValue != null}">
                                            <input type="text" id="maxValue" name="maxValue" class="form-control"
                                                   value="<fmt:formatNumber value="${param.maxValue}" pattern="#" />" />
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" id="maxValue" name="maxValue" class="form-control"
                                                   value="<fmt:formatNumber value="${discount.maxValue}" pattern="#" />" />
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="form-group">
                                    <label for="maxUsage">Số lần dùng tối đa</label>
                                    <input type="text" id="maxUsage" name="maxUsage" class="form-control"
                                           value="${param.maxUsage != null ? param.maxUsage : (discount.maxUsage != null ? discount.maxUsage : '')}">

                                </div>
                                <div class="form-group">
                                    <label for="validFrom">Hiệu lực từ</label>
                                    <input type="date" id="validFrom" name="validFrom" class="form-control"
                                           value="${param.validFrom != null ? param.validFrom : fn:substring(discount.validFrom, 0, 10)}" required>

                                </div>
                                <div class="form-group">
                                    <label for="validTo">Tới ngày</label>
                                    <input type="date" id="validTo" name="validTo" class="form-control"
                                           value="${param.validTo != null ? param.validTo :fn:substring(discount.validTo, 0, 10)}" required>

                                </div>
                                <div class="form-group">
                                    <label for="status">Trạng thái</label>
                                    <select id="status" name="status" class="form-control"   >
                                        <option value="1" ${param.status == '1' || (empty param.status && discount.active) ? 'selected' : ''}>Active</option>
                                        <option value="0" ${param.status == '0' || (empty param.status && !discount.active) ? 'selected' : ''}>Deactive</option>
                                    </select>
                                </div>
                                <div class="form-full-width">
                                    <label for="description">Mô tả</label>
                                    <textarea id="description" name="description" class="form-control" rows="4">${param.description != null ? param.description : discount.description}</textarea>

                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <a href="discountmanager" class="btn btn-outline">Hủy bỏ</a>
                            <button type="submit" class="btn btn-primary">Cập nhật mã giảm giá</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
        <div id="toast" class="toast-message"></div>
    </body>
</html>