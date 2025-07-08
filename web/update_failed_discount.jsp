<%-- 
    Document   : update_failed_discount
    Created on : Jul 4, 2025, 9:24:50 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách mã không import được - PETFPT Shop</title>
        <link href="css/manager_panel_page.css" rel="stylesheet" type="text/css"/>
        <link href="css/seller_panel_page.css?v=5" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <script src="js/retry_discount_fixed.js"></script>
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
                    <c:when test="${not empty sessionScope.userAccount.displayAccImage()}">
                        <img src="${sessionScope.userAccount.displayAccImage()}" alt="Seller Avatar"/>
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
        <c:if test="${not empty sessionScope.successMess}">
            <div class="alert-message">
                ${sessionScope.successMess}
            </div>
            <c:remove var="successMess" scope="session" />
        </c:if>



        <div class="manager-container">
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
                        <a href="displayrefund" class="sidebar-link "><i class="fas fa-undo-alt"></i> Quản lý hoàn tiền</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="addbreed" class="sidebar-link"><i class="fas fa-plus-circle"></i> Thêm giống mới</a>
                        <a href="adddiscount.jsp" class="sidebar-link active"><i class="fas fa-plus-circle"></i> Thêm mã giảm giá mới</a>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </div>

            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-exclamation-triangle"></i> Danh sách mã không import được</h1>

                    <a href="downloadfailed" class="btn btn-outline">
                        <i class="fas fa-download"></i> Tải danh sách lỗi Excel
                    </a>

                </div>
                <c:if test="${not empty errMess}">
                    <div class="alert-message error">${errMess}</div>
                    <c:remove var="errMess" scope="session" />
                </c:if>

                <c:forEach var="d" items="${failedDiscounts}" varStatus="st">
                    <div class="card" style="margin-bottom: 30px;">
                        <form action="retryadddiscount" method="POST">
                            <div class="card-body">
                                <h3 class="card-title">Mã #${st.index + 1}</h3>
                                <input type="hidden" name="index" value="${st.index}">
                                <div class="form-grid">
                                    <div class="form-group">
                                        <label>Mã giảm giá</label>
                                        <input type="text" name="code" class="form-control" value="${d.discountCode}" required>
                                        <c:if test="${not empty d.discountCodeErr}">
                                            <div class="field-error">${d.discountCodeErr}</div>
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <label>Loại giảm</label>
                                        <select name="type" class="form-control">
                                            <option value="Percent" ${d.discountType=='Percent'?'selected':''}>Percent</option>
                                            <option value="Fixed" ${d.discountType=='Fixed'?'selected':''}>Fixed</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Giá trị</label>
                                        <input type="text" name="value" class="form-control" value="${d.discountValue}">
                                        <c:if test="${not empty d.discountValueErr}">
                                            <div class="field-error">${d.discountValueErr}</div>
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <label>Đơn hàng tối thiểu (₫)</label>
                                        <input type="text" name="minAmount" class="form-control" value="${d.minOrderAmount}">
                                        <c:if test="${not empty d.minOrderAmountErr}">
                                            <div class="field-error">${d.minOrderAmountErr}</div>
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <label>Giảm tối đa (₫)</label>
                                        <input type="text" name="maxValue" class="form-control" value="${d.maxValue}">
                                        <c:if test="${not empty d.maxValueErr}">
                                            <div class="field-error">${d.maxValueErr}</div>
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <label>Số lần dùng tối đa</label>
                                        <input type="text" name="maxUsage" class="form-control" value="${d.maxUsage}">
                                        <c:if test="${not empty d.maxUsageErr}">
                                            <div class="field-error">${d.maxUsageErr}</div>
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <label>Hiệu lực từ</label>
                                        <input type="date" name="validFrom" class="form-control" value="${d.validFrom}">
                                        <c:if test="${not empty d.validFromErr}">
                                            <div class="field-error">${d.validFromErr}</div>
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <label>Tới ngày</label>
                                        <input type="date" name="validTo" class="form-control" value="${d.validTo}">
                                        <c:if test="${not empty d.validToErr}">
                                            <div class="field-error">${d.validToErr}</div>
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <label>Trạng thái</label>
                                        <select name="status" class="form-control">
                                            <option value="1" ${d.active?'selected':''}>Active</option>
                                            <option value="0" ${!d.active?'selected':''}>Deactive</option>
                                        </select>
                                    </div>
                                    <div class="form-full-width">
                                        <label>Mô tả</label>
                                        <textarea name="description" class="form-control" rows="3">${d.description}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer">
                                <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Lưu mã này</button>
                            </div>
                        </form>
                    </div>
                </c:forEach>

                <a href="discountmanager" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay về quản lý mã giảm giá</a>
            </div>
        </div>

        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
    </body>
</html>

