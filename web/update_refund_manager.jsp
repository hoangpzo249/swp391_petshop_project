<%-- 
    Document   : update_refund_manager
    Created on : Jul 2, 2025, 10:31:21 AM
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
        <title>Duyệt Hoàn Tiền (Manager)</title>
        <link href="css/seller_panel_page.css?v=11" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <script src="js/refund.js?v=3"></script>
    </head>
    <body>
        <div class="seller-header">
            <div class="logo-container">
                <a href="homepage"><img src="images/logo_banner/logo2.png" alt="Logo"/></a>
                <h1 class="seller-title">MANAGER</h1>
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
                    <span class="seller-role">Manager</span>
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
                        <c:if test="${role ne 'Manager'}">
                            <a href="displaysalesstatistic" class="sidebar-link"><i class="fas fa-tachometer-alt"></i> Tổng quan</a>
                            <a href="sellerdisplayinvoice" class="sidebar-link"><i class="fas fa-file-invoice"></i> Danh sách hóa đơn</a>
                        </c:if>
                        <c:if test="${role eq 'Manager'}">
                            <a href="displayrevenuestatistic" class="sidebar-link"><i class="fas fa-tachometer-alt"></i> Tổng quan</a>
                        </c:if>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <c:if test="${role ne 'Manager'}">
                            <a href="displayorder" class="sidebar-link"><i class="fas fa-bag-shopping"></i> Quản lý đơn hàng</a>
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
                    <h1 class="page-title"><i class="fas fa-money-check"></i> Duyệt Yêu Cầu Hoàn Tiền</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="displayrefund">Quản lý hoàn tiền</a></li>
                        <li>Duyệt hoàn tiền</li>
                    </ul>
                </div>


                <c:if test="${not empty successMess}">
                    <div class="alert-message">${successMess}</div>
                    <c:remove var="successMess" scope="session"/>
                </c:if>
                <c:if test="${not empty errMess}">
                    <div class="alert-message error">${errMess}</div>
                    <c:remove var="errMess" scope="session"/>
                </c:if>

                <div class="card">
                    <form action="updaterefundmanager" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="refundId" value="${refundId!=null ? refundId: refund.refundId}" />
                        <input type="hidden" name="orderId" value="${orderId != null ? orderId : refund.orderId}" />
                        <input type="hidden" name="accountHolderName" value="${accountHolderName != null ? accountHolderName : refund.accountHolderName}" />
                        <input type="hidden" name="bankName" value="${bankName != null ? bankName : refund.bankName}" />
                        <input type="hidden" name="bankAccountNumber" value="${bankAccountNumber != null ? bankAccountNumber : refund.bankAccountNumber}" />
                        <input type="hidden" name="refundAmount" value="${refundAmount != null ? refundAmount : refund.refundAmount}" />
                        <div class="card-body">
                            <div class="form-grid">

                                <div class="form-group">
                                    <label for="orderId">Mã đơn hàng</label>
                                    <input type="text" id="orderId" name="orderId"
                                           value="${orderId != null ? orderId : refund.orderId}"
                                           class="form-control" readonly />
                                </div>

                                <div class="form-group">
                                    <label for="accountHolderName">Chủ tài khoản</label>
                                    <input type="text" id="accountHolderName" name="accountHolderName"
                                           value="${accountHolderName != null ? accountHolderName : refund.accountHolderName}"
                                           class="form-control" readonly />
                                </div>

                                <div class="form-group">
                                    <label for="bankName">Ngân hàng</label>
                                    <input type="text" id="bankName" name="bankName"
                                           value="${bankName != null ? bankName : refund.bankName}"
                                           class="form-control" readonly />
                                </div>

                                <div class="form-group">
                                    <label for="bankAccountNumber">Số tài khoản</label>
                                    <input type="text" id="bankAccountNumber" name="bankAccountNumber"
                                           value="${bankAccountNumber != null ? bankAccountNumber : refund.bankAccountNumber}"
                                           class="form-control" readonly />
                                </div>

                                <div class="form-group">
                                    <label for="refundReasonDescription">Lý do hoàn tiền</label>
                                    <textarea id="refundReasonDescription" name="refundReasonDescription" class="form-control" rows="4" required style="height: 90px" readonly>${refundReasonDescription != null ? refundReasonDescription : refund.refundReasonDescription }</textarea>
                                </div>  
                                <div class="form-group">
                                    <label for="proofImage">Hình ảnh chứng minh đã thanh toán</label>
                                    <c:if test="${not empty sessionScope.previousImage}">
                                        <img id="previewImage" src="data:image/jpeg;base64,${sessionScope.previousImage}"  width="120" height="90px"  onclick="showImage(this.src)"/>
                                    </c:if>
                                </div>
                                <div class="form-group">
                                    <label for="refundAmount">Số tiền hoàn (₫)</label>
                                    <c:set var="amountValue" value="${refundAmount != null ? refundAmount : refund.refundAmount}" />
                                    <fmt:formatNumber value="${amountValue}" type="number" groupingUsed="true" var="formatAmount"/>
                                    <input type="hidden" id="refundAmount" name="refundAmount" value="${amountValue}" />
                                    <input type="text" value="${formatAmount}" class="form-control" readonly />
                                </div>
                                <div class="form-group">
                                    <label for="refundStatus">Trạng thái hoàn tiền</label>
                                    <c:set var="status" value="${refundStatus != null ? refundStatus : refund.refundStatus}" />
                                    <select id="refundStatus" name="refundStatus" class="form-control">
                                        <option value="Pending" ${status eq 'Pending' ? 'selected' : ''}>Pending</option>
                                        <option value="Approved" ${status eq 'Approved' ? 'selected' : ''}>Approved</option>
                                        <option value="Rejected" ${status eq 'Rejected' ? 'selected' : ''}>Rejected</option>
                                        <option value="Completed" ${status eq 'Completed' ? 'selected' : ''}>Completed</option>
                                    </select>
                                </div>
                                <div class="form-group">

                                </div>

                                <div class="form-group form-proof" id="proofRefundedGroup" style="display:none;">
                                    <label for="proofRefundedImage">Hình ảnh chứng minh đã hoàn tiền</label>
                                    <input type="file" id="proofRefundedImage" name="proofRefundedImage" class="hidden-file-input" accept="image/*"/>
                                    <label for="proofRefundedImage" class="btn-upload" style="margin-left: 260px;margin-top: -30px;width: 74.06px">Chọn tệp</label>
                                    <c:if test="${not empty refund.proofRefundedImage}">
                                        <img id="previewRefundedImage" src="data:image/jpeg;base64,${refund.base64ProofRefundedImage}" alt="Ảnh hoàn tiền" width="120" onclick="showImage(this.src)"/>
                                    </c:if>
                                    <img id="previewRefundedImage" src="" style="display:none;" width="120" onclick="showImage(this.src)"/>
                                </div>

                                <div class="form-group" id="reasonGroup" style="display:none;">
                                    <label for="rejectReason">Lý do từ chối</label>
                                    <textarea id="rejectReason" name="rejectReason" class="form-control">${refund.rejectReason}</textarea>
                                </div>

                            </div>
                        </div>

                        <div class="card-footer">
                            <a href="displayrefund" class="btn btn-outline">Hủy</a>
                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                        </div>
                    </form>
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
