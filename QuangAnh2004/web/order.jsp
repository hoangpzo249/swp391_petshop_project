<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, model.Order" %>
<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
%>
<html>
<head><title>Order List</title></head>
<body>
    <h2>Your Orders</h2>
    <table border="1">
        <tr>
            <th>Order ID</th><th>Status</th><th>Action</th>
        </tr>
        <c:forEach var="o" items="${orders}">
        <tr>
            <td>${o.orderId}</td>
            <td>${o.orderStatus}</td>
            <td>
                <c:choose>
                    <c:when test="${o.orderStatus == 'Unconfirmed'}">
                        <form action="CancelOrderServlet" method="get">
                            <input type="hidden" name="orderId" value="${o.orderId}">
                            <input type="submit" value="Cancel">
                        </form>
                    </c:when>
                    <c:when test="${o.paymentStatus == 'Paid' && !o.cancellationRequested}">
                        <form action="RequestCancelServlet" method="get">
                            <input type="hidden" name="orderId" value="${o.orderId}">
                            <input type="submit" value="Request Cancel">
                        </form>
                    </c:when>
                    <c:otherwise>
                        N/A
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>

