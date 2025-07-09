<%-- 
    Document   : update_refund_seller
    Created on : Jul 2, 2025, 10:30:07 AM
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
        <title>Cập Nhật Yêu Cầu Hoàn Tiền - PETFPT Shop</title>
        <link href="css/seller_panel_page.css?v=12" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
         <script src="js/refund.js?v=3"></script>
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
                    <h1 class="page-title"><i class="fas fa-undo-alt"></i> Cập Nhật Yêu Cầu Hoàn Tiền</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="viewallrefund">Quản lý hoàn tiền</a></li>
                        <li>Cập nhật yêu cầu</li>
                    </ul>
                </div>

                <c:if test="${not empty successMess}">
                    <div class="alert-message">${successMess}</div>
                    <c:remove var="successMess" scope="session" />
                </c:if>
                <c:if test="${not empty errMess}">
                    <div class="alert-message error">${errMess}</div>
                    <c:remove var="errMess" scope="session" />
                </c:if>

                <div class="card">
                    <form action="updaterefundseller" method="POST" enctype="multipart/form-data" id="refund">
                        <input type="hidden" name="refundId" value="${refundId != null ? refundId : refund.refundId}" />
                        <div class="card-body">
                            <div class="form-grid">

                                <div class="form-group">
                                    <label for="orderId">Mã đơn hàng</label>
                                    <input type="text" id="orderId" name="orderId"
                                           value="${orderId != null ? orderId : refund.orderId}"
                                           class="form-control" />
                                </div>

                                <div class="form-group">
                                    <label for="accountHolderName">Chủ tài khoản</label>
                                    <input type="text" id="accountHolderName" name="accountHolderName"
                                           value="${accountHolderName != null ? accountHolderName : refund.accountHolderName}"
                                           class="form-control" required />
                                </div>

                                <div class="form-group">
                                    <label for="bankName">Ngân hàng</label>
                                    <input type="text" id="bankName" name="bankName"
                                           value="${bankName != null ? bankName : refund.bankName}"
                                           class="form-control" required />
                                </div>

                                <div class="form-group">
                                    <label for="bankAccountNumber">Số tài khoản</label>
                                    <input type="text" id="bankAccountNumber" name="bankAccountNumber"
                                           value="${bankAccountNumber != null ? bankAccountNumber : refund.bankAccountNumber}"
                                           class="form-control" required />
                                </div>


                                <div class="form-group">
                                    <label for="refundReasonDescription">Lý do hoàn tiền</label>
                                    <textarea id="refundReasonDescription" name="refundReasonDescription" class="form-control" rows="4" required >${refundReasonDescription != null ? refundReasonDescription : refund.refundReasonDescription }</textarea>
                                </div>

                                <div class="form-group form-proof">
                                    <label class="proof-label" for="proofImage">Hình ảnh chứng minh đã thanh toán</label>
                                    <input type="file" id="proofImage" name="proofImage" class="hidden-file-input" accept="image/*" />
                                    <label for="proofImage" class="btn-upload">Chọn tệp</label><br/>
                                    <c:choose>
                                        <c:when test="${not empty preview}">
                                            <img id="previewImage" src="data:image/jpeg;base64,${preview}"  class="preview-image" onclick="showImage(this.src)"/>
                                        </c:when>
                                        <c:when test="${not empty refund.proofImage}">
                                            <img id="previewImage" src="data:image/jpeg;base64,${refund.base64ProofImage}"  class="preview-image" onclick="showImage(this.src)"/>
                                        </c:when>
                                    </c:choose>
                                </div>

                            </div>
                        </div>

                        <div class="card-footer">
                            <a href="displayrefund" class="btn btn-outline">Hủy bỏ</a>
                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
        <div id="toast"></div>
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
