<%-- 
    Document   : seller_invoice_detail
    Created on : 2 Jul 2025, 20:57:10
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi Tiết Hóa Đơn #${invoice.invoiceId} - PETFPT Shop</title>
        <link href="css/seller_panel_page.css" rel="stylesheet" type="text/css"/>
        <link href="css/invoice.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2.0.5/FileSaver.min.js"></script>
    </head>
    <body>
        <div class="seller-header">
            <div class="logo-container">
                <a href="homepage"><img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/></a>
                <h1 class="seller-title">SELLER</h1>
            </div>
            <div class="seller-profile">
                <img src="${sessionScope.userAccount.displayAccImage()}" alt="Seller Avatar"/>
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

        <div class="seller-container">
            <div class="seller-sidebar">
                <div class="sidebar-header"><h2 class="sidebar-title">SELLER PANEL</h2><p class="sidebar-subtitle">Quản lý hệ thống PETFPT Shop</p></div>
                <div class="sidebar-menu">
                    <div class="menu-category"><h5 class="category-title">Điều hướng</h5>
                        <a href="comingsoon" class="sidebar-link"><i class="fas fa-tachometer-alt"></i> Tổng quan</a>
                        <a href="sellerdisplayinvoice" class="sidebar-link active"><i class="fas fa-file-invoice"></i> Danh sách hóa đơn</a>
                    </div>
                    <div class="menu-category"><h5 class="category-title">Quản lý</h5>
                        <a href="displayorder" class="sidebar-link"><i class="fas fa-bag-shopping"></i> Quản lý đơn hàng</a>
                        <a href="displayallpet" class="sidebar-link"><i class="fas fa-dog"></i> Quản lý thú cưng</a>
                    </div>
                    <div class="menu-category"><h5 class="category-title">Thao tác</h5>
                        <a href="addpet" class="sidebar-link"><i class="fas fa-paw"></i> Đăng bán thú cưng</a>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </div>

            <div class="seller-content">
                <c:choose>
                    <c:when test="${not empty invoice}">
                        <div class="invoice-actions">
                            <a href="sellerdisplayinvoice" class="btn btn-outline">
                                <i class="fas fa-arrow-left"></i> Quay lại danh sách
                            </a>
                            <button id="downloadBtn" class="btn btn-primary">
                                <i class="fas fa-download"></i> Tải tất cả các trang
                            </button>
                        </div>

                        <div id="invoice-paper-container" class="invoice-paper">
                            <div class="invoice-header">
                                <div class="logo-section">
                                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                                    <p style="font-weight: 600; margin-top:10px;">PETFPT Shop</p>
                                    <p>Lô E2a-7, Đường D1, Khu Công nghệ cao, Long Thạnh Mỹ, Thành Phố Thủ Đức, Thành phố Hồ Chí Minh</p>
                                </div>
                                <div class="invoice-title-section">
                                    <h2>HÓA ĐƠN BÁN HÀNG</h2>
                                    <p>Mã hóa đơn: <strong>#${invoice.invoiceId}</strong></p>
                                    <p>Mã đơn hàng: #${invoice.order.orderId}</p>
                                    <p>Ngày xuất: <fmt:formatDate value="${invoice.issueDate}" pattern="HH:mm, dd/MM/yyyy"/></p>
                                </div>
                            </div>

                            <div class="invoice-details">
                                <div class="shop-details">
                                    <h3 class="section-title">Thông tin người bán</h3>
                                    <p><strong>Cửa hàng:</strong> PETFPT Shop</p>
                                    <p><strong>Điện thoại:</strong> 0987.654.321</p>
                                    <p><strong>Email:</strong> support@petfpt.com</p>
                                </div>
                                <div class="customer-details">
                                    <h3 class="section-title">Thông tin khách hàng</h3>
                                    <p><strong>Tên khách hàng:</strong> ${invoice.order.customerName}</p>
                                    <p><strong>Địa chỉ:</strong> ${invoice.order.customerAddress}</p>
                                    <p><strong>Điện thoại:</strong> ${invoice.order.customerPhone}</p>
                                    <p><strong>Email:</strong> ${invoice.order.customerEmail}</p>
                                </div>
                            </div>

                            <table class="items-table">
                                <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Sản phẩm</th>
                                        <th class="text-right">Giá</th>
                                    </tr>
                                </thead>
                                <tbody id="invoice-items-body">
                                    <c:forEach var="item" items="${invoice.invoiceContents}" varStatus="loop">
                                        <tr>
                                            <td>${loop.count}</td>
                                            <td>
                                                <strong>${item.petName}</strong><br>
                                                <small>Giống: ${item.petBreed} - Giới tính: ${item.petGender}</small>
                                            </td>
                                            <td class="text-right"><fmt:formatNumber value="${item.priceAtInvoice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                            <div id="detail-pagination" class="invoice-pagination"></div>

                            <div class="invoice-totals">
                                <table>
                                    <tr>
                                        <td class="label">Tổng tiền hàng:</td>
                                        <td class="text-right"><fmt:formatNumber value="${invoice.totalAmount + invoice.order.discountAmountAtApply}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                    </tr>
                                    <tr>
                                        <td class="label">Giảm giá:</td>
                                        <td class="text-right">- <fmt:formatNumber value="${invoice.order.discountAmountAtApply}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                    </tr>
                                    <tr class="grand-total">
                                        <td class="label">TỔNG CỘNG:</td>
                                        <td class="text-right"><fmt:formatNumber value="${invoice.totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                    </tr>
                                </table>
                            </div>

                            <div class="invoice-footer">
                                <p>Phương thức thanh toán: <strong>${invoice.paymentMethod}</strong></p>
                                <p>Cảm ơn quý khách đã mua sắm tại <strong class="shop-name">PETFPT Shop</strong>!</p>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert-message error">Không tìm thấy thông tin hóa đơn. Vui lòng thử lại.</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const itemsTbody = document.getElementById('invoice-items-body');
                if (!itemsTbody)
                    return;

                const invoiceElement = document.getElementById('invoice-paper-container');
                const items = Array.from(itemsTbody.getElementsByTagName('tr'));
                const paginationContainer = document.getElementById('detail-pagination');
                const downloadBtn = document.getElementById('downloadBtn');

                const ITEMS_PER_PAGE = 7;
                const totalItems = items.length;
                const totalPages = Math.ceil(totalItems / ITEMS_PER_PAGE);
                let currentPage = 1;

                function showPage(page) {
                    currentPage = page;
                    const startIndex = (page - 1) * ITEMS_PER_PAGE;
                    const endIndex = startIndex + ITEMS_PER_PAGE;
                    items.forEach((item, i) => {
                        item.style.display = (i >= startIndex && i < endIndex) ? '' : 'none';
                    });
                    updatePaginationControls();
                }

                function updatePaginationControls() {
                    paginationContainer.innerHTML = '';
                    if (totalPages <= 1)
                        return;

                    for (let i = 1; i <= totalPages; i++) {
                        const pageLink = document.createElement('a');
                        pageLink.className = 'page-nav';
                        if (i === currentPage) {
                            pageLink.classList.add('active');
                        }
                        pageLink.href = '#';
                        pageLink.innerText = i;
                        pageLink.addEventListener('click', e => {
                            e.preventDefault();
                            showPage(i);
                        });
                        paginationContainer.appendChild(pageLink);
                    }
                }

                function waitForRender() {
                    return new Promise(resolve => requestAnimationFrame(resolve));
                }

                async function downloadInvoiceAsZip() {
                    downloadBtn.disabled = true;
                    downloadBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang xử lý...';

                    const zip = new JSZip();

                    for (let i = 1; i <= totalPages; i++) {
                        showPage(i);
                        await waitForRender();
                        const canvas = await html2canvas(invoiceElement, {scale: 2, useCORS: true});
                        const imageData = canvas.toDataURL('image/png').split(',')[1];
                        const filename = `hoa-don-${invoice.invoiceId}-trang-\${i}.png`;
                        zip.file(filename, imageData, {base64: true});
                    }

                    downloadBtn.innerHTML = '<i class="fas fa-archive"></i> Đang nén file...';
                    const content = await zip.generateAsync({type: "blob"});
                    saveAs(content, `hoa-don-${invoice.invoiceId}.zip`);
                    showPage(1);
                    downloadBtn.disabled = false;
                    downloadBtn.innerHTML = '<i class="fas fa-download"></i> Tải hóa đơn (ZIP)';
                }

                async function downloadSingleImage() {
                    downloadBtn.disabled = true;
                    downloadBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang tải...';
                    await waitForRender();
                    const canvas = await html2canvas(invoiceElement, {scale: 2, useCORS: true});
                    const link = document.createElement('a');
                    link.download = `hoa-don-${invoice.invoiceId}.png`;
                    link.href = canvas.toDataURL('image/png');
                    link.click();
                    downloadBtn.disabled = false;
                    downloadBtn.innerHTML = '<i class="fas fa-download"></i> Tải hóa đơn (Ảnh)';
                }

                if (totalPages > 1) {
                    downloadBtn.innerHTML = '<i class="fas fa-download"></i> Tải hóa đơn (ZIP)';
                    downloadBtn.addEventListener('click', downloadInvoiceAsZip);
                } else {
                    downloadBtn.innerHTML = '<i class="fas fa-download"></i> Tải hóa đơn (Ảnh)';
                    downloadBtn.addEventListener('click', downloadSingleImage);
                }

                showPage(1);
            });
        </script>
    </body>
</html>