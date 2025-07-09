<%-- 
    Document   : view_detail_refund
    Created on : Jul 2, 2025, 8:28:30 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="role" value="${sessionScope.userAccount.accRole}" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi Tiết Yêu Cầu Hoàn Tiền</title>
        <link href="css/seller_panel_page.css?v=7" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <div class="seller-header">
            <div class="logo-container">
                <a href="homepage"><img src="images/logo_banner/logo2.png" alt="Logo"/></a>
                <h1 class="seller-title">CHI TIẾT HOÀN TIỀN</h1>
            </div>
            <div class="seller-profile">
                <c:choose>
                    <c:when test="${not empty sessionScope.userAccount.accImage}">
                        <img src="data:image/jpeg;base64,${sessionScope.userAccount.getBase64Image()}" alt="Avatar"/>
                    </c:when>
                    <c:otherwise>
                        <img src="images/support button/account.png" alt="Avatar"/>
                    </c:otherwise>
                </c:choose>
                <div class="seller-info">
                    <span class="seller-name">${sessionScope.userAccount.accFname} ${sessionScope.userAccount.accLname}</span>
                    <span class="seller-role">${sessionScope.userAccount.accRole}</span>
                </div>
                <div class="seller-actions">
                    <a href="profile" class="seller-action"><i class="fas fa-user-circle"></i></a>
                    <a href="logout" class="seller-action"><i class="fas fa-sign-out-alt"></i></a>
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
            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-eye"></i> Chi Tiết Yêu Cầu Hoàn Tiền</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="displayrefund">Quản lý hoàn tiền</a></li>
                        <li>Chi tiết yêu cầu</li>
                    </ul>
                </div>

                <div class="card">
                    <div class="card-body">
                        <div class="form-grid">

                            <div class="form-group">
                                <label>Mã đơn hàng</label>
                                <input type="text" class="form-control" value="${refund.orderId}" readonly />
                            </div>

                            <div class="form-group">
                                <label>Chủ tài khoản</label>
                                <input type="text" class="form-control" value="${refund.accountHolderName}" readonly />
                            </div>

                            <div class="form-group">
                                <label>Ngân hàng</label>
                                <input type="text" class="form-control" value="${refund.bankName}" readonly />
                            </div>

                            <div class="form-group">
                                <label>Số tài khoản</label>
                                <input type="text" class="form-control" value="${refund.bankAccountNumber}" readonly />
                            </div>

                            <div class="form-group">
                                <label>Ngày yêu cầu</label>
                                <fmt:formatDate value="${refund.refundRequestDate}" pattern="dd/MM/yyyy HH:mm" var="requestDateFormatted"/>
                                <input type="text" class="form-control" value="${requestDateFormatted}" readonly />
                            </div>

                            <div class="form-group">
                                <label>Ngày xử lý</label>
                                <c:choose>
                                    <c:when test="${refund.refundProcessedDate != null}">
                                        <fmt:formatDate value="${refund.refundProcessedDate}" pattern="dd/MM/yyyy HH:mm" var="processedDateFormatted"/>
                                        <input type="text" class="form-control" value="${processedDateFormatted}" readonly />
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="form-control" value="--" readonly />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="form-full-width">
                                <label for="refundReasonDescription">Lý do hoàn tiền</label>
                                <textarea id="refundReasonDescription" name="refundReasonDescription" class="form-control" rows="4"required readonly>${refund.refundReasonDescription} </textarea>
                            </div>
                            <div class="form-group">
                                <label>Số tiền hoàn (₫)</label>
                                <fmt:formatNumber value="${refund.refundAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0" var="formattedAmount"/>
                                <input type="text" class="form-control" value="${formattedAmount}" readonly />
                            </div>

                            <div class="form-group">
                                <label>Trạng thái hoàn tiền</label>
                                <input type="text" class="form-control" value="${refund.refundStatus}" readonly />
                            </div>


                            <c:if test="${not empty refund.proofImage}">
                                <div class="form-group">
                                    <label>Hình ảnh chứng minh đã thanh toán</label>
                                    <img src="data:image/jpeg;base64,${refund.base64ProofImage}" alt="Ảnh bằng chứng" width="150" onclick="showImage(this.src)"/>
                                </div>
                            </c:if>

                            <c:if test="${refund.refundStatus eq 'Rejected'}">
                                <div class="form-group">
                                    <label>Lý do từ chối</label>
                                    <textarea class="form-control" readonly>${refund.rejectReason}</textarea>
                                </div>
                            </c:if>

                            <c:if test="${refund.refundStatus eq 'Completed' and not empty refund.proofRefundedImage}">
                                <div class="form-group">
                                    <label>Hình ảnh chứng minh đã hoàn tiền</label>
                                    <img src="data:image/jpeg;base64,${refund.base64ProofRefundedImage}" alt="Ảnh hoàn tiền" width="150" onclick="showImage(this.src)"/>
                                </div>
                            </c:if>

                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="displayrefund" class="btn btn-outline">Quay lại</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="seller-footer">
            © 2025 PETFPT Shop
        </div>
        <div id="lightbox" class="lightbox" onclick="this.style.display = 'none'">
            <img id="lightbox-img" src="" />
        </div>
        <script>
            function showImage(src) {
                document.getElementById("lightbox-img").src = src;
                document.getElementById("lightbox").style.display = "flex";
            }
        </script>
    </body>
</html>
