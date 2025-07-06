<%-- 
    Document   : seller_sales_statistic
    Created on : 6 Jul 2025, 10:31:02
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thống Kê Bán Hàng - PETFPT Shop</title>
        <link href="css/seller_panel_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
                        <%-- Add a new link to this page and make it active --%>
                        <a href="sellerstatistics" class="sidebar-link active"><i class="fas fa-chart-line"></i> Thống kê bán hàng</a>
                        <a href="sellerdisplayinvoice" class="sidebar-link">
                            <i class="fas fa-file-invoice"></i> Danh sách hóa đơn
                        </a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="displayorder" class="sidebar-link"><i class="fas fa-bag-shopping"></i> Quản lý đơn hàng</a>
                        <a href="displayallpet" class="sidebar-link"><i class="fas fa-dog"></i> Quản lý thú cưng</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="addpet" class="sidebar-link"><i class="fas fa-paw"></i> Đăng bán thú cưng</a>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </div>

            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-chart-line"></i> Thống Kê Bán Hàng</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="#">Seller</a></li>
                        <li>Thống kê bán hàng</li>
                    </ul>
                </div>

                <div class="card">
                    <div class="card-body">
                        <form action="sellerstatistics" method="GET" id="statsFilterForm">
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
                                <button type="button" class="btn btn-success" onclick="exportToCSV()">
                                    <i class="fas fa-file-export"></i> Xuất CSV
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="kpi-grid">
                    <div class="kpi-card">
                        <div class="kpi-icon" style="background-color: rgba(46, 204, 113, 0.2); color: #2ecc71;">
                            <i class="fas fa-paw"></i>
                        </div>
                        <div class="kpi-content">
                            <div class="kpi-value">${kpi.totalPetsSold}</div>
                            <div class="kpi-label">Tổng Thú Cưng Đã Bán</div>
                        </div>
                    </div>
                    <div class="kpi-card">
                        <div class="kpi-icon" style="background-color: rgba(52, 152, 219, 0.2); color: #3498db;">
                            <i class="fas fa-check-circle"></i>
                        </div>
                        <div class="kpi-content">
                            <div class="kpi-value">${kpi.ordersFulfilled}</div>
                            <div class="kpi-label">Đơn Hàng Hoàn Thành</div>
                        </div>
                    </div>
                    <div class="kpi-card">
                        <div class="kpi-icon" style="background-color: rgba(242, 111, 33, 0.2); color: #f26f21;">
                            <i class="fas fa-star"></i>
                        </div>
                        <div class="kpi-content">
                            <div class="kpi-value">${kpi.mostPopularBreed}</div>
                            <div class="kpi-label">Giống Phổ Biến Nhất</div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title"><i class="fas fa-chart-bar"></i> Doanh Số Theo Giống</h3>
                    </div>
                    <div class="card-body">
                        <canvas id="salesByBreedChart"></canvas>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title"><i class="fas fa-list-ul"></i> Thú Cưng Đã Bán Gần Đây</h3>
                    </div>
                    <div class="card-body">
                        <div class="table-container">
                            <table id="recentSalesTable">
                                <thead>
                                    <tr>
                                        <th>Mã Pet</th>
                                        <th>Tên Thú Cưng</th>
                                        <th>Giống</th>
                                        <th>Ngày Bán</th>
                                        <th>Giá Bán</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${recentSales}" var="sale">
                                        <tr>
                                            <td><strong>#${sale.petId}</strong></td>
                                            <td>${sale.petName}</td>
                                            <td>${sale.breedName}</td>
                                            <td><fmt:formatDate value="${sale.dateSold}" pattern="dd-MM-yyyy"/></td>
                                            <td>
                                                <span class="price-value">
                                                    <fmt:formatNumber value="${sale.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                                </span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty recentSales}">
                                        <tr>
                                            <td colspan="5" style="text-align: center; padding: 30px;">
                                                Không có dữ liệu bán hàng trong khoảng thời gian này.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
            const ctx = document.getElementById('salesByBreedChart').getContext('2d');
            // You need to pass data from your servlet to this script
            // Example data structure:
            // const salesData = {
            //     labels: ['Corgi', 'Golden Retriever', 'Bengal', 'Poodle', 'Samoyed'],
            //     data: [12, 19, 8, 5, 7]
            // };

            const salesData = {
            labels: [<c:forEach items="${salesByBreed}" var="item" varStatus="loop">"${item.breedName}"<c:if test="${!loop.last}">, </c:if></c:forEach>],
                    data: [<c:forEach items="${salesByBreed}" var="item" varStatus="loop">${item.quantitySold}<c:if test="${!loop.last}">, </c:if></c:forEach>]
            };
            new Chart(ctx, {
            type: 'bar',
                    data: {
                    labels: salesData.labels,
                            datasets: [{
                            label: 'Số lượng đã bán',
                                    data: salesData.data,
                                    backgroundColor: 'rgba(242, 111, 33, 0.6)',
                                    borderColor: 'rgba(242, 111, 33, 1)',
                                    borderWidth: 1
                            }]
                    },
                    options: {
                    indexAxis: 'y',
                            scales: {
                            x: {
                            beginAtZero: true,
                                    ticks: {
                                    stepSize: 1
                                    }
                            }
                            },
                            plugins: {
                            legend: {
                            display: false
                            },
                                    tooltip: {
                                    callbacks: {
                                    label: function (context) {
                                    return ' Đã bán: ' + context.raw + ' bé';
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

            function exportToCSV() {
            let csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "Mã Pet,Tên Thú Cưng,Giống,Ngày Bán,Giá Bán\n";
            const table = document.getElementById("recentSalesTable");
            const rows = table.querySelectorAll("tbody tr");
            rows.forEach(row => {
            const cols = row.querySelectorAll("td");
            const rowData = Array.from(cols).map(col => `"\${col.innerText.replace(/"/g, '""')}"`);
                
                const encodedUri = encodeURI(csvContent);
                const link = document.createElement("a");
                link.setAttribute("href", encodedUri);
                link.setAttribute("download", "sales_statistics.csv");
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            }
        </script>
    </body>
</html>