<%-- 
    Document   : order-content
    Created on : Jun 14, 2025, 12:37:14 AM
    Author     : QuangAnh
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" type="text/css" href="CSS/order-content.css" />
</head>
<body>
    <h2>Chi tiết đơn hàng</h2>

    <c:if test="${empty orderContents}">
        <p>Không có dữ liệu đơn hàng.</p>
    </c:if>

    <c:if test="${not empty orderContents}">
        <table border="1">
            <tr>
                <th>Mã chi tiết đơn hàng</th>
                <th>Mã đơn hàng</th>
                <th>Mã thú cưng</th>
            </tr>
            <c:forEach var="oc" items="${orderContents}">
                <tr>
                    <td>${oc.orderContentId}</td>
                    <td>${oc.orderId}</td>
                    <td>${oc.petId}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <br/>
    <a href="order.jsp">Quay lại danh sách đơn</a>
</body>
</html>
