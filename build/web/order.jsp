<%-- 
    Document   : order
    Created on : Jun 14, 2025, 11:02:43 PM
    Author     : QuangAnh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, model.Order" %>

<!--
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    Integer accId = (Integer) session.getAttribute("accId");
    if(accId == null){
        response.sendRedirect("login_account_page.jsp");
        return;
    }
-->

<html>
<head>
    <title>Order List</title>
    <link rel="stylesheet" type="text/CSS" href="CSS/order.css">
</head>
<body>
    <h2>Your Orders</h2>
    <table border="1">
        <thead>
            <tr>
                <th>orderId</th>
                <th>accId</th>
                <th>orderDate</th>
                <th>orderStatus</th>
                <th>customerName</th>
                <th>customerEmail</th>
                <th>customerPhone</th>
                <th>customerAddress</th>
                <th>shipperId</th>
                <th>paymentMethod</th>
                <th>paymentStatus</th>
                <th>rejectionReason</th>
            </tr>
        </thead>
        <c:forEach var="order" items="${requestScope.orderList}">
        <tr>
                    <td>${order.orderId}</td>
                    <td>${order.accId}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.orderStatus}</td>
                    <td>${order.customerName}</td>
                    <td>${order.customerEmail}</td>
                    <td>${order.customerPhone}</td>
                    <td>${order.customerAddress}</td>
                    <td>
                        <c:choose>
                            <c:when test="${order.shipperId == 0}">NULL</c:when>
                            <c:otherwise>${order.shipperId}</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${order.paymentMethod}</td>
                    <td>${order.paymentStatus}</td>
                    <td>
                        <c:out value="${order.rejectionReason}" default="NULL" />
                    </td>

              <c:choose>
                    <c:when test="${order.status == 'Pending'}">
                        <form action="CancelOrderServlet" method="post">
                            <input type="hidden" name="orderId" value="${order.id}" />
                            <button type="submit" onclick="return confirm('Bạn có chắc muốn hủy đơn này?')">Cancel</button>
                        </form>
                    </c:when>
                    <c:when test="${order.status == 'Paid'}">
                        <form action="RequestCancelServlet" method="post">
                            <input type="hidden" name="orderId" value="${order.id}" />
                            <button type="submit" onclick="return confirm('Bạn muốn gửi yêu cầu hủy đơn?')">Request Cancel</button>
                        </form>
                        </form>
                    </c:when>
                    <c:when test="${order.status == 'Delivered'}">
                        <form action="ReviewServlet" method="get">
                            <input type="hidden" name="petId" value="${order.petId}" />
                            <button type="submit">Review</button>
                        </form>
                        <form action="InvoiceServlet" method="get">
                            <input type="hidden" name="orderId" value="${order.id}" />
                            <button type="submit">View Invoice</button>
                        </form>
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
