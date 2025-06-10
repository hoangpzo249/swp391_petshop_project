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
                <h1 class="seller-title">Quản trị hệ thống</h1>
            </div>

            <div class="seller-profile">
                <%-- Assuming user's image is available, otherwise use placeholder --%>
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
                    <a href="profile" class="seller-action" title="Thông tin cá nhân">
                        <i class="fas fa-user-circle"></i>
                    </a>
                    <a href="logout" class="seller-action" title="Đăng xuất">
                        <i class="fas fa-sign-out-alt"></i>
                    </a>
                </div>
            </div>
        </div>

        <c:if test="${not empty successMessage}">
            <div class="alert-message">
                ${successMessage}
            </div>
            <c:remove var="successMessage" scope="session" />
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="alert-message error">
                ${errorMessage}
            </div>
            <c:remove var="errorMessage" scope="session" />
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
                        <a href="sellerpanel" class="sidebar-link">
                            <i class="fas fa-tachometer-alt"></i> Tổng quan
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="seller-order-management" class="sidebar-link active"> <%-- Correct Link --%>
                            <i class="fas fa-bag-shopping"></i> Quản lý đơn hàng
                        </a>
                        <a href="seller-pet-management" class="sidebar-link"> <%-- Correct Link --%>
                            <i class="fas fa-dog"></i> Quản lý thú cưng
                        </a>
                    </div>

                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="seller-create-pet" class="sidebar-link"> <%-- Correct Link --%>
                            <i class="fas fa-paw"></i> Đăng bán thú cưng
                        </a>
                        <a href="profile" class="sidebar-link">
                            <i class="fas fa-user-circle"></i> Tài khoản của tôi
                        </a>
                        <a href="change-password" class="sidebar-link"> <%-- Correct Link --%>
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
                        <li><a href="sellerpanel">Seller</a></li>
                        <li><a href="seller-order-management">Quản lý đơn hàng</a></li>
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
                            <form method="post" action="your-servlet-url" id="updateStatusForm">
                                <input type="hidden" name="orderId" value="${order.orderId}">

                                <input type="hidden" name="status" id="statusInput">

                                <c:if test="${order.orderStatus == 'Pending'}">
                                    <button type="button" class="btn btn-success" 
                                            onclick="showConfirmationModal('xác nhận đơn hàng này', 'Confirmed', 'btn-success')">
                                        <i class="fas fa-check"></i> Xác nhận
                                    </button>
                                    <button type="button" class="btn btn-danger" 
                                            onclick="showConfirmationModal('từ chối đơn hàng này', 'Rejected', 'btn-danger')">
                                        <i class="fas fa-times"></i> Từ chối
                                    </button>
                                </c:if>

                                <c:if test="${order.orderStatus == 'Confirmed'}">
                                    <button type="button" class="btn btn-primary" 
                                            onclick="showConfirmationModal('giao hàng cho đơn này', 'Shipping', 'btn-primary')">
                                        <i class="fas fa-truck"></i> Giao hàng
                                    </button>
                                </c:if>


                            </form>
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
                                        <div class="detail-value">${order.paymentMethod}</div>

                                        <div class="detail-label">Trạng thái TT</div>
                                        <div class="detail-value">
                                            <span class="status-badge status-payment-${order.paymentStatus.toLowerCase()}">${order.paymentStatus}</span>
                                        </div>

                                        <div class="detail-label">Trạng thái ĐH</div>
                                        <div class="detail-value">
                                            <span class="status-badge status-${order.orderStatus.toLowerCase()}">${order.orderStatus}</span>
                                        </div>

                                        <div class="detail-label">Shipper ID</div>
                                        <div class="detail-value">
                                            <c:out value="${empty order.shipperId or order.shipperId == 0 ? 'Chưa chỉ định' : order.shipperId}"/>
                                        </div>

                                        <div class="detail-label">Tổng giá trị</div>
                                        <div class="detail-value" style="font-weight: bold; color: #f26f21; font-size: 1.1rem;">
                                            <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Pets in Order Card -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title"><i class="fas fa-paw"></i> Thú cưng trong đơn hàng</h3>
                    </div>
                    <div class="card-body">
                        <div class="pet-grid">
                            <c:forEach items="${petList}" var="pet">
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
                            </c:forEach>
                            <c:if test="${empty petList}">
                                <p>Không có thông tin thú cưng cho đơn hàng này.</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="action-buttons" style="margin-top: 20px;">
                    <button class="btn btn-outline" onclick="location.href = 'seller-order-management'">
                        <i class="fas fa-arrow-left"></i> Quay lại danh sách
                    </button>
                </div>
            </div>
        </div>
        <!-- ======================== END OF MAIN CONTENT (EDITED SECTION) ======================== -->
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
                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline" onclick="closeModal()">Hủy bỏ</button>
                    <button id="modalConfirmButton" class="btn">Xác nhận</button>
                </div>
            </div>
        </div>
        <script>
            const modal = document.getElementById('confirmationModal');
            const modalText = document.getElementById('modalText');
            const modalConfirmButton = document.getElementById('modalConfirmButton');

            const updateForm = document.getElementById('updateStatusForm');
            const statusInput = document.getElementById('statusInput');

            let statusToSubmit = '';

            function showConfirmationModal(actionText, statusValue, buttonClass) {
                if (event) {
                    event.preventDefault();
                }

                statusToSubmit = statusValue;

                modalText.innerText = `Bạn có chắc chắn muốn ${actionText}?`;

                modalConfirmButton.className = 'btn'; // Reset classes
                modalConfirmButton.classList.add(buttonClass); // Add the specific class

                modal.classList.add('show');
            }

            function closeModal() {
                modal.classList.remove('show');
            }

            modalConfirmButton.addEventListener('click', function () {
                statusInput.value = statusToSubmit;

                updateForm.submit();

                closeModal();
            });

            window.onclick = function (event) {
                if (event.target === modal) {
                    closeModal();
                }
            };
        </script>
    </body>
</html>