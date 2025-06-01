<%-- 
    Document   : order
    Created on : Jun 1, 2025, 1:09:33 PM
    Author     : QuangAnh
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lịch sử đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

<h2>Lịch sử đơn hàng của bạn</h2>

<c:if test="${not empty errorMessage}">
    <p class="error">${errorMessage}</p>
</c:if>

<c:if test="${empty orders}">
    <p>Bạn chưa có đơn hàng nào.</p>
</c:if>

<c:if test="${not empty orders}">
    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>Mã đơn</th>
            <th>Ngày đặt</th>
            <th>Trạng thái</th>
            <th>Phương thức thanh toán</th>
            <th>Trạng thái thanh toán</th>
            <th>Xem chi tiết</th>
        </tr>

        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.orderDate}</td>
                <td>${order.orderStatus}</td>
                <td>${order.paymentMethod}</td>
                <td>${order.paymentStatus}</td>
                <td><a href="order-detail.jsp?orderId=${order.orderId}">Chi tiết</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<jsp:include page="footer.jsp" />
</body>
</html>
