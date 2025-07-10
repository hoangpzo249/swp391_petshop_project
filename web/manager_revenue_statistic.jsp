<%-- 
    Document   : manager_revenue_statistic
    Created on : 8 Jul 2025, 15:52:56
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thống Kê Doanh Thu - PETFPT Shop</title>
        <link href="css/manager_panel_page.css" rel="stylesheet" type="text/css"/> 
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns/dist/chartjs-adapter-date-fns.bundle.min.js"></script>
    </head>
    <body>
        <div class="manager-header">
            <div class="logo-container">
                <a href="homepage">
                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                </a>
                <h1 class="manager-title">MANAGER</h1> 
            </div>

            <div class="manager-profile">
                <c:choose>
                    <c:when test="${not empty sessionScope.userAccount.displayAccImage()}">
                        <img src="${sessionScope.userAccount.displayAccImage()}" alt="Manager Avatar"/>
                    </c:when>
                    <c:otherwise>
                        <img src="images/support button/account.png" alt="Manager Avatar"/>
                    </c:otherwise>
                </c:choose>
                <div class="manager-info">
                    <span class="manager-name">${sessionScope.userAccount.accFname} ${sessionScope.userAccount.accLname}</span>
                    <span class="manager-role">Manager</span>
                </div>
                <div class="manager-actions">
                    <a href="profile" class="manager-action" title="Thông tin cá nhân"><i class="fas fa-user-circle"></i></a>
                    <a href="logout" class="manager-action" title="Đăng xuất"><i class="fas fa-sign-out-alt"></i></a>
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

        <div class="manager-container">
            <div class="manager-sidebar">
                <div class="sidebar-header">
                    <h2 class="sidebar-title">MANAGER PANEL</h2>
                    <p class="sidebar-subtitle">Quản lý hệ thống PETFPT Shop</p>
                </div>
                <div class="sidebar-menu">
                    <div class="menu-category">
                        <h5 class="category-title">Báo cáo & Phân tích</h5>
                        <a href="managerstatistics" class="sidebar-link active"><i class="fas fa-chart-pie"></i> Bảng điều khiển</a>
                        <a href="managerreports" class="sidebar-link"><i class="fas fa-file-invoice-dollar"></i> Báo cáo doanh thu</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="displayorder" class="sidebar-link"><i class="fas fa-bag-shopping"></i> Quản lý đơn hàng</a>
                        <a href="displayallpet" class="sidebar-link"><i class="fas fa-dog"></i> Quản lý thú cưng</a>
                        <a href="displayallbreed" class="sidebar-link"><i class="fas fa-tags"></i> Quản lý giống</a>
                        <a href="managediscount" class="sidebar-link"><i class="fas fa-percent"></i> Quản lý mã giảm giá</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Cá nhân</h5>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </div>

            <div class="manager-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-chart-pie"></i> Bảng Điều Khiển Doanh Thu</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="#">Manager</a></li>
                        <li>Bảng điều khiển</li>
                    </ul>
                </div>

                <div class="card">
                    <div class="card-body">
                        <form action="displayrevenuestatistic" method="GET" id="statsFilterForm">
                            <div class="stats-filter-bar">
                                <div class="date-range-group">
                                    <input type="date" name="startDate" value="${param.startDate}">
                                    <span>đến</span>
                                    <input type="date" name="endDate" value="${param.endDate}">
                                </div>
                                <div class="quick-filter-group">
                                    <button type="button" class="btn btn-outline btn-sm" onclick="setRange('7days')">7 Ngày Qua</button>
                                    <button type="button" class="btn btn-outline btn-sm" onclick="setRange('30days')">30 Ngày Qua</button>
                                    <button type="button" class="btn btn-outline btn-sm" onclick="setRange('thisMonth')">Tháng Này</button>
                                </div>
                                <button type="submit" class="btn btn-primary"><i class="fas fa-filter"></i> Lọc</button>
                                <button type="button" class="btn btn-success" onclick="exportFinancialSummary()">
                                    <i class="fas fa-file-export"></i> Xuất Báo Cáo
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="kpi-grid">
                    <div class="kpi-card">
                        <div class="kpi-icon" style="background-color: rgba(46, 204, 113, 0.2); color: #2ecc71;">
                            <i class="fas fa-dollar-sign"></i>
                        </div>
                        <div class="kpi-content">
                            <div class="kpi-value"><fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></div>
                            <div class="kpi-label">Tổng Doanh Thu</div>
                        </div>
                    </div>
                    <div class="kpi-card">
                        <div class="kpi-icon" style="background-color: rgba(52, 152, 219, 0.2); color: #3498db;">
                            <i class="fas fa-shopping-cart"></i>
                        </div>
                        <div class="kpi-content">
                            <div class="kpi-value">${totalOrders}</div>
                            <div class="kpi-label">Tổng Đơn Hàng</div>
                        </div>
                    </div>
                    <div class="kpi-card">
                        <div class="kpi-icon" style="background-color: rgba(242, 111, 33, 0.2); color: #f26f21;">
                            <i class="fas fa-file-invoice-dollar"></i>
                        </div>
                        <div class="kpi-content">
                            <div class="kpi-value"><fmt:formatNumber value="${averageOrderValue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></div>
                            <div class="kpi-label">Giá Trị Đơn Trung Bình</div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title"><i class="fas fa-chart-line"></i> Biểu Đồ Doanh Thu Theo Ngày</h3>
                    </div>
                    <div class="card-body">
                        <canvas id="revenueOverTimeChart"></canvas>
                    </div>
                </div>

                <div class="grid-layout-2-col">
                    <div class="card">
                        <div class="card-header">
                            <h3 class="card-title"><i class="fas fa-trophy"></i> Top 5 Giống Doanh Thu Cao Nhất</h3>
                        </div>
                        <div class="card-body">
                            <div class="table-container">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Giống</th>
                                            <th>Số Lượng Bán</th>
                                            <th>Tổng Doanh Thu</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${topBreedsByRevenue}" var="breed">
                                            <tr>
                                                <td><strong>${breed.breedName}</strong></td>
                                                <td>${breed.totalPurchases}</td>
                                                <td><span class="price-value"><fmt:formatNumber value="${breed.totalRevenue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></span></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <h3 class="card-title"><i class="fas fa-receipt"></i> Top 10 Đơn Hàng Giá Trị Cao Nhất</h3>
                        </div>
                        <div class="card-body">
                            <div class="table-container">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Mã Đơn Hàng</th>
                                            <th>Khách Hàng</th>
                                            <th>Ngày Đặt</th>
                                            <th>Giá Trị</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${topHighValueOrders}" var="order">
                                            <tr>
                                                <td><strong>#${order.orderId}</strong></td>
                                                <td>${order.customerName}</td>
                                                <td><fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy"/></td>
                                                <td><span class="price-value"><fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></span></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <div class="manager-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const ctx = document.getElementById('revenueOverTimeChart').getContext('2d');

                const revenueData = {
                    labels: [<c:forEach items="${revenueByDay}" var="item" varStatus="loop">"${item.date}"<c:if test="${!loop.last}">, </c:if></c:forEach>],
                            data: [<c:forEach items="${revenueByDay}" var="item" varStatus="loop">${item.totalRevenue}<c:if test="${!loop.last}">, </c:if></c:forEach>]
                };

                new Chart(ctx, {
                    type: 'line',
                    data: {
                        labels: revenueData.labels,
                        datasets: [{
                                label: 'Doanh Thu (₫)',
                                data: revenueData.data,
                                fill: true,
                                backgroundColor: 'rgba(242, 111, 33, 0.2)',
                                borderColor: 'rgba(242, 111, 33, 1)',
                                tension: 0.1,
                                pointBackgroundColor: 'rgba(242, 111, 33, 1)',
                                pointBorderColor: '#fff',
                                pointHoverRadius: 7
                            }]
                    },
                    options: {
                        scales: {
                            x: {
                                type: 'time',
                                time: {
                                    unit: '${chartTimeUnit}',
                                    tooltipFormat: 'dd-MM-yyyy'
                                }
                            },
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    callback: function (value, index, values) {
                                        return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(value);
                                    }
                                }
                            }
                        },
                        plugins: {
                            tooltip: {
                                callbacks: {
                                    label: function (context) {
                                        return ' Doanh thu: ' + new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(context.raw);
                                    }
                                }
                            }
                        }
                    }
                });
            });

            function setRange(range) {
                const endDate = new Date();
                let startDate = new Date();

                if (range === '7days') {
                    startDate.setDate(endDate.getDate() - 7);
                } else if (range === '30days') {
                    startDate.setDate(endDate.getDate() - 30);
                } else if (range === 'thisMonth') {
                    startDate.setDate(1);
                }

                document.querySelector('input[name="startDate"]').value = startDate.toISOString().split('T')[0];
                document.querySelector('input[name="endDate"]').value = endDate.toISOString().split('T')[0];

                document.getElementById('statsFilterForm').submit();
            }

            function exportFinancialSummary() {
                const startDate = document.querySelector('input[name="startDate"]').value || 'N/A';
                const endDate = document.querySelector('input[name="endDate"]').value || 'N/A';

                let csvContent = "\uFEFF";

                csvContent += `BÁO CÁO TÀI CHÍNH TỔNG QUAN\n`;
                csvContent += `Khoảng thời gian: Từ ${startDate} đến ${endDate}\n\n`;

                csvContent += `Chỉ số,Giá trị\n`;
                csvContent += `Tổng Doanh Thu (VND),${totalRevenue}\n`;
                csvContent += `Tổng Đơn Hàng,${totalOrders}\n`;
                csvContent += `Giá Trị Đơn Trung Bình (VND),${averageOrderValue}\n\n`;

                csvContent += `TOP 5 GIỐNG DOANH THU CAO NHẤT\n`;
                csvContent += `Giống,Số Lượng Bán,Tổng Doanh Thu (VND)\n`;
            <c:forEach items="${topBreedsByRevenue}" var="breed">
                csvContent += `"${breed.breedName}",${breed.totalPurchases},${breed.totalRevenue}\n`;
            </c:forEach>
                csvContent += `\n`;

                csvContent += `TOP 10 ĐƠN HÀNG GIÁ TRỊ CAO NHẤT\n`;
                csvContent += `Mã Đơn Hàng,Khách Hàng,Ngày Đặt,Giá Trị (VND)\n`;
            <c:forEach items="${topHighValueOrders}" var="order">
                csvContent += `#${order.orderId},"${order.customerName}","<fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy"/>",${order.totalPrice}\n`;
            </c:forEach>

                const blob = new Blob([csvContent], {type: 'text/csv;charset=utf-8;'});
                const link = document.createElement("a");
                const url = URL.createObjectURL(blob);
                link.setAttribute("href", url);
                link.setAttribute("download", "Bao_Cao_Doanh_Thu.csv");
                link.style.visibility = 'hidden';
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
                URL.revokeObjectURL(url);
            }
        </script>
    </body>
</html>