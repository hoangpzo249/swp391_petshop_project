<%-- 
    Document   : adddiscount
    Created on : Jun 23, 2025, 9:11:21 AM
    Author     : AD
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm Mã Giảm Giá - PETFPT Shop</title>
        <link href="css/seller_panel_page.css?v=4" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <script src="js/add_discount_validate.js?v=2"></script>
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
        <c:if test="${not empty errMess}">
            <div class="alert-message error">${errMess}</div>
            <c:remove var="errMess" scope="session" />
        </c:if>

        <div class="seller-content">
            <div class="page-header">
                <h1 class="page-title"><i class="fas fa-tags"></i> Thêm Mã Giảm Giá Mới</h1>
            </div>

            <div class="card">
                <form id="add_discount" action="adddiscount" method="POST">
                    <div class="card-body">
                        <div class="form-grid">
                            <div class="form-group">
                                <label for="code">Mã giảm giá</label>
                                <input type="text" id="code" name="code" class="form-control" value="${code}" required>
                            </div>
                            <div class="form-group">
                                <label for="type">Loại giảm</label>
                                <select id="type" name="type" class="form-control">
                                    <option value="Percent" ${type == 'Percent' ? 'selected' : ''}>Percent</option>
                                    <option value="Fixed" ${type == 'Fixed' ? 'selected' : ''}>Fixed</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="value">Giá trị</label>
                                <input type="number" step="0.01" id="value" name="value" class="form-control" value="${value}" required>
                            </div>
                            <div class="form-group">
                                <label for="minAmount">Đơn hàng tối thiểu (₫)</label>
                                <input type="number" step="100" id="minAmount" name="minAmount" class="form-control" value="${minAmount}">
                            </div>
                            <div class="form-group">
                                <label for="maxValue">Giảm tối đa (₫)</label>
                                <input type="number" step="100" id="maxValue" name="maxValue" class="form-control" value="${maxValue}">
                            </div>
                            <div class="form-group">
                                <label for="maxUsage">Số lần dùng tối đa</label>
                                <input type="number" id="maxUsage" name="maxUsage" class="form-control" value="${maxUsage}">
                            </div>
                            <div class="form-group">
                                <label for="validFrom">Hiệu lực từ</label>
                                <input type="date" id="validFrom" name="validFrom" class="form-control" value="${validFrom}" required>
                            </div>
                            <div class="form-group">
                                <label for="validTo">Tới ngày</label>
                                <input type="date" id="validTo" name="validTo" class="form-control" value="${validTo}" required>
                            </div>
                            <div class="form-group">
                                <label for="status">Trạng thái</label>
                                <select id="status" name="status" class="form-control">
                                    <option value="1" ${status == '1' ? 'selected' : ''}>Active</option>
                                    <option value="0" ${status == '0' ? 'selected' : ''}>Deactive</option>
                                </select>
                            </div>
                            <div class="form-full-width">
                                <label for="description">Mô tả</label>
                                <textarea id="description" name="description" class="form-control" rows="4">${description}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="discountmanager" class="btn btn-outline">Hủy bỏ</a>
                        <button type="submit" class="btn btn-primary">Thêm mã giảm giá</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
        <div id="toast" class="toast-message"></div>
    </body>

</html>
