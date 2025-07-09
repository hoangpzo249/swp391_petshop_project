<%-- 
    Document   : add_refund
    Created on : Jun 26, 2025, 8:11:35 AM
    Author     : ADMIN
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gửi Yêu Cầu Hoàn Tiền - PETFPT Shop</title>
        <link href="css/seller_panel_page.css?v=12" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <script src="js/refund.js"></script>

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
            <div class="seller-sidebar">
                <div class="sidebar-header">
                    <h2 class="sidebar-title">SELLER PANEL</h2>
                    <p class="sidebar-subtitle">Quản lý hệ thống PETFPT Shop</p>
                </div>
                <div class="sidebar-menu">
                    <div class="menu-category">
                        <h5 class="category-title">Điều hướng</h5>
                        <a href="comingsoon" class="sidebar-link"><i class="fas fa-tachometer-alt"></i> Tổng quan</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="displayorder" class="sidebar-link"><i class="fas fa-bag-shopping"></i> Quản lý đơn hàng</a>
                        <a href="displayrefund" class="sidebar-link"><i class="fas fa-undo-alt"></i> Quản lý hoàn tiền</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="addrefund" class="sidebar-link active"><i class="fas fa-plus-circle"></i> Gửi yêu cầu hoàn tiền</a>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </div>

            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-undo-alt"></i> Gửi Yêu Cầu Hoàn Tiền</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="displayrefund">Quản lý hoàn tiền</a></li>
                        <li>Gửi yêu cầu</li>
                    </ul>
                </div>

                <div class="card">
                    <form action="addrefund" method="POST" enctype="multipart/form-data" id="refund">
                        <div class="card-body">
                            <div class="form-grid">
                                <div class="form-group">
                                    <label for="orderId">Mã đơn hàng</label>
                                    <input type="text" id="orderId" name="orderId" class="form-control" placeholder="Nhập mã đơn hàng" required value=${orderId}>
                                </div>

                                <div class="form-group">
                                    <label for="accountHolderName">Chủ tài khoản</label>
                                    <input type="text" id="accountHolderName" name="accountHolderName" class="form-control" placeholder="Họ tên chủ tài khoản" required value=${accountHolderName}>
                                </div>

                                <div class="form-group">
                                    <label for="bankName">Ngân hàng</label>
                                    <input type="text" id="bankName" name="bankName" class="form-control" placeholder="Tên ngân hàng" required value=${bankName}>
                                </div>

                                <div class="form-group">
                                    <label for="bankAccountNumber">Số tài khoản</label>
                                    <input type="text" id="bankAccountNumber" name="bankAccountNumber" class="form-control" placeholder="Số tài khoản ngân hàng" required value=${bankAccountNumber}>
                                </div>



                                <div class="form-group">
                                    <label for="refundReasonDescription">Lý do hoàn tiền</label>
                                    <textarea id="refundReasonDescription" name="refundReasonDescription" class="form-control" rows="4"required>${refundReasonDescription}</textarea>
                                </div>

                                <div class="form-group form-proof">
                                    <label class="proof-label" for="proofImage">Hình ảnh bằng chứng</label>
                                    <input type="file" id="proofImage" name="proofImage" class="hidden-file-input" accept="image/*" />
                                    <label for="proofImage" class="btn-upload">Chọn tệp</label><br/>

                                    <div class="preview">
                                        <c:if test="${not empty sessionScope.preview}">
                                            <img id="previewImage" src="data:image/jpeg;base64,${sessionScope.preview}" class="preview-image" onclick="showImage(this.src)" />
                                        </c:if>
                                        <img id="previewImage" src="" class="preview-image" style="display: none" onclick="showImage(this.src)" />
                                    </div>
                                </div>


                            </div>
                        </div>

                        <div class="card-footer">
                            <a href="displayrefund" class="btn btn-outline">Hủy bỏ</a>
                            <button type="submit" class="btn btn-primary">Gửi yêu cầu</button>
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

