<%-- 
    Document   : seller_order_detail
    Created on : 2 Jun 2025, 21:34:21
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%-- Add this for formatting numbers/dates --%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Đơn Hàng - PETFPT Shop</title>
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
            <div class="alert-message">
                ${successMess}
            </div>
            <c:remove var="successMess" scope="session" />
        </c:if>

        <c:if test="${not empty errMess}">
            <div class="alert-message error">
                ${errMess}
            </div>
            <c:remove var="errMess" scope="session" />
        </c:if>


        <div class="seller-container">
            <!-- Sidebar -->
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
                        <a href="sellerdisplayinvoice" class="sidebar-link">
                            <i class="fas fa-file-invoice"></i> Danh sách hóa đơn
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="displayorder" class="sidebar-link active">
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
            <!-- Main Body -->

            <div class="seller-content">
                <!-- Page Header -->
                <div class="page-header">
                    <h1 class="page-title">
                        <i class="fas fa-file-invoice-dollar"></i> Chi tiết đơn hàng #${order.orderId}
                    </h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="displaysalesstatistic">Seller</a></li>
                        <li><a href="displayorder">Quản lý đơn hàng</a></li>
                        <li>Chi tiết đơn hàng</li>
                    </ul>
                </div>

                <!-- Order Details Card -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-info-circle"></i> Thông tin chi tiết
                        </h3>
                        <div class="card-tools">
                            <!-- Action Buttons -->
                            <c:if test="${order.orderStatus == 'Pending'}">
                                <button type="button" class="btn btn-success" 
                                        onclick="showConfirmationModal(event, 'xác nhận đơn hàng này', 'updateorderstatus?action=confirm&status=Confirmed&orderId=${order.orderId}', 'btn-success')">
                                    <i class="fas fa-check"></i> Xác nhận
                                </button>
                                <button type="button" class="btn btn-danger" 
                                        onclick="showConfirmationModal(event, 'từ chối đơn hàng này', 'updateorderstatus?action=reject&status=Rejected&orderId=${order.orderId}', 'btn-danger', true)"> <%-- ADD ", true" HERE --%>
                                    <i class="fas fa-times"></i> Từ chối
                                </button>
                            </c:if>

                        </div>
                    </div>
                    <div class="card-body">
                        <div class="order-details-container">
                            <!-- Customer Information Section -->
                            <div class="customer-info-section">
                                <div class="details-section">
                                    <h3 class="section-title"><i class="fas fa-user-circle"></i> Thông tin khách hàng</h3>
                                    <div class="details-grid">
                                        <div class="detail-label">Tên khách hàng</div>
                                        <div class="detail-value">${order.customerName}</div>
                                        <div class="detail-label">Email</div>
                                        <div class="detail-value">${order.customerEmail}</div>
                                        <div class="detail-label">Số điện thoại</div>
                                        <div class="detail-value">${order.customerPhone}</div>
                                        <div class="detail-label">Địa chỉ giao hàng</div>
                                        <div class="detail-value">${order.customerAddress}</div>
                                    </div>
                                </div>
                            </div>
                            <!-- Order Metadata Section -->
                            <div class="order-meta-section">
                                <div class="details-section">
                                    <h3 class="section-title"><i class="fas fa-receipt"></i> Thông tin đơn hàng</h3>
                                    <div class="details-grid">
                                        <div class="detail-label">Ngày đặt hàng</div>
                                        <div class="detail-value"><fmt:formatDate value="${order.orderDate}" pattern="HH:mm, dd-MM-yyyy"/></div>

                                        <div class="detail-label">Phương thức TT</div>
                                        <div class="detail-value">
                                            <c:choose>
                                                <c:when test="${order.paymentMethod == 'Cash on Delivery'}">Tiền mặt</c:when>
                                                <c:when test="${order.paymentMethod == 'Credit Card'}">Thẻ tín dụng</c:when>
                                                <c:otherwise>${order.paymentMethod}</c:otherwise>
                                            </c:choose>
                                        </div>

                                        <div class="detail-label">Trạng thái TT</div>
                                        <div class="detail-value">
                                            <span class="status-badge status-payment-${order.paymentStatus.toLowerCase()}">
                                                <c:choose>
                                                    <c:when test="${order.paymentStatus == 'Paid'}">Đã thanh toán</c:when>
                                                    <c:when test="${order.paymentStatus == 'Unpaid'}">Chưa thanh toán</c:when>
                                                    <c:otherwise>${order.paymentStatus}</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>

                                        <div class="detail-label">Trạng thái ĐH</div>
                                        <div class="detail-value">
                                            <span class="status-badge status-${order.orderStatus.toLowerCase()}">
                                                <c:choose>
                                                    <c:when test="${order.orderStatus == 'Pending'}">Chờ xử lý</c:when>
                                                    <c:when test="${order.orderStatus == 'Confirmed'}">Đã xác nhận</c:when>
                                                    <c:when test="${order.orderStatus == 'Cancelled'}">Đã hủy</c:when>
                                                    <c:when test="${order.orderStatus == 'Rejected'}">Bị từ chối</c:when>
                                                    <c:when test="${order.orderStatus == 'Shipping'}">Đang giao</c:when>
                                                    <c:when test="${order.orderStatus == 'Pending Shipper'}">Chờ Shipper</c:when>
                                                    <c:when test="${order.orderStatus == 'Delivered'}">Đã giao</c:when>
                                                    <c:otherwise>${order.orderStatus}</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>

                                        <div class="detail-label">Shipper ID</div>
                                        <div class="detail-value">
                                            <c:out value="${empty order.shipperId or order.shipperId == 0 ? 'Chưa chỉ định' : order.shipperId}"/>
                                        </div>

                                        <div class="detail-label">Tổng giá trị</div>
                                        <div class="detail-value" style="font-weight: bold; color: #f26f21; font-size: 1.1rem;">
                                            <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                        </div>

                                        <div class="detail-label">Mã giảm giá</div>
                                        <div class="detail-value">
                                            <c:choose>
                                                <c:when test="${empty order.discountId or order.discountId == 0}">
                                                    Không sử dụng
                                                </c:when>

                                                <c:otherwise>
                                                    ${order.discountId} 

                                                    (<fmt:formatNumber value="${order.discountAmountAtApply}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>)
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:if test="${order.orderStatus == 'Rejected' and not empty order.rejectionReason}">
                            <div class="rejection-reason-section">
                                <h3 class="section-title">
                                    <i class="fas fa-comment-slash"></i> Lý do từ chối
                                </h3>
                                <p class="rejection-text">${order.rejectionReason}</p>
                            </div>
                        </c:if>
                    </div>
                </div>

                <c:if test="${order.shipperId != null and order.shipperId != 0}">
                    <div class="card">
                        <div class="card-header">
                            <h3 class="card-title">
                                <i class="fas fa-user-check"></i> Thông tin Shipper đã chỉ định
                            </h3>
                        </div>
                        <div class="card-body">
                            <c:if test="${not empty assignedShipper}">
                                <div class="shipper-details-block">
                                    <div class="shipper-avatar-container">
                                        <img src="${assignedShipper.shipperAccount.displayAccImage()}" alt="Shipper Avatar" class="shipper-avatar"/>
                                    </div>
                                    <div class="shipper-text-details">
                                        <div class="details-grid">
                                            <div class="detail-label">Tên Shipper</div>
                                            <div class="detail-value">${assignedShipper.shipperAccount.accFname} ${assignedShipper.shipperAccount.accLname}</div>
                                            <div class="detail-label">Username</div>
                                            <div class="detail-value">@${assignedShipper.shipperAccount.accUsername}</div>
                                            <div class="detail-label">Số điện thoại</div>
                                            <div class="detail-value">
                                                <c:out value="${not empty assignedShipper.shipperAccount.accPhoneNumber ? assignedShipper.shipperAccount.accPhoneNumber : 'Chưa cập nhật'}" />
                                            </div>
                                            <div class="detail-label">Ghi chú</div>
                                            <div class="detail-value">
                                                <c:out value="${empty assignedShipper.shipperNote ? 'Không có' : assignedShipper.shipperNote}" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${order.orderStatus == 'Pending Shipper'}">
                                    <div class="form-actions">
                                        <button type="button" class="btn btn-danger"
                                                onclick="showConfirmationModal(event, 'huỷ chỉ định shipper cho đơn hàng này', 'assignshipper?orderId=${order.orderId}&action=unassign', 'btn-danger')">
                                            <i class="fas fa-user-minus"></i> Huỷ chỉ định & Chọn lại
                                        </button>
                                    </div>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                </c:if>

                <c:if test="${order.orderStatus == 'Confirmed' and (order.shipperId == null or order.shipperId == 0)}">
                    <div class="card">
                        <div class="card-header">
                            <h3 class="card-title">
                                <i class="fas fa-user-plus"></i> Chỉ định Shipper
                            </h3>
                        </div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${not empty shipperList}">
                                    <form action="assignshipper" method="POST" class="shipper-assignment-form">
                                        <input type="hidden" name="orderId" value="${order.orderId}">
                                        <div class="form-group">
                                            <label for="shipperSelect">Chọn shipper để giao hàng:</label>
                                            <div class="select-wrapper">
                                                <select name="shipperId" id="shipperSelect" class="form-control" required>
                                                    <option value="" disabled selected>-- Vui lòng chọn shipper --</option>
                                                    <c:forEach items="${shipperList}" var="shipper">
                                                        <option value="${shipper.shipperAccount.accId}"
                                                                title="Ghi chú: ${not empty shipper.shipperNote ? shipper.shipperNote : 'Không có'}">
                                                            ${shipper.shipperAccount.accLname} ${shipper.shipperAccount.accFname} (@${shipper.shipperAccount.accUsername})
                                                            - Đang giao: ${shipper.currentShippingOrders} đơn
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-actions">
                                            <button type="submit" class="btn btn-primary">
                                                <i class="fas fa-check-circle"></i> Chỉ định
                                            </button>
                                        </div>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <div class="notice notice-warning">
                                        <i class="fas fa-exclamation-triangle"></i>
                                        <span>Hiện không có shipper nào sẵn sàng.</span>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:if>


                <!-- Pets in Order Card -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title"><i class="fas fa-paw"></i> Thú cưng trong đơn hàng</h3>
                    </div>
                    <div class="card-body">
                        <div class="pet-grid">
                            <c:forEach items="${petList}" var="pet">
                                <a href="displaypet?id=${pet.petId}" class="pet-card-link">
                                    <div class="pet-item-card">
                                        <div class="pet-item-header">${pet.petName}</div>
                                        <div class="pet-item-body">
                                            <p><strong>ID:</strong> ${pet.petId}</p>
                                            <p><strong>Giống:</strong> ${pet.breed.breedName}</p>
                                            <p><strong>Màu sắc:</strong> ${pet.petColor}</p>
                                            <p><strong>Giới tính:</strong> ${pet.petGender}</p>
                                            <p><strong>Giá tại thời điểm đặt:</strong> 
                                                <span style="font-weight: bold; color: #f26f21;">
                                                    <fmt:formatNumber value="${pet.priceAtOrder}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                                </span>
                                            </p>
                                        </div>
                                    </div>
                                </a>
                            </c:forEach>
                            <c:if test="${empty petList}">
                                <p>Không có thông tin thú cưng cho đơn hàng này.</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="action-buttons" style="margin-top: 20px;">
                    <button class="btn btn-outline" onclick="location.href = 'displayorder'">
                        <i class="fas fa-arrow-left"></i> Quay lại danh sách
                    </button>
                </div>
            </div>
        </div>
        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>
        <div class="modal-backdrop" id="confirmationModal">
            <div class="modal-dialog">
                <div class="modal-header">
                    <h3 class="modal-title" id="modalTitle">Xác nhận hành động</h3>
                    <button class="modal-close" onclick="closeModal()">×</button>
                </div>
                <div class="modal-body">
                    <p id="modalText">Bạn có chắc chắn muốn thực hiện hành động này không?</p>

                    <div id="rejectionReasonContainer" style="display: none;">
                        <label for="rejectionReason">Lý do từ chối:</label>
                        <textarea id="rejectionReason" class="form-control" rows="3" placeholder="Nhập lý do từ chối đơn hàng..."></textarea>
                        <div class="checkbox-container">
                            <input type="checkbox" id="hidePetsCheckbox" name="hidePets">
                            <label for="hidePetsCheckbox">Đồng thời ẩn các thú cưng trong đơn hàng này.</label>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline" onclick="closeModal()">Hủy bỏ</button>
                    <button id="modalConfirmButton" class="btn">Xác nhận</button>
                </div>
            </div>
        </div>
        <script src="js/order_confirmation_window.js"></script>
    </body>
</html>