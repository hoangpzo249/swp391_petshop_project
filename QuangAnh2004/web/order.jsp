<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, model.Order" %>
<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    Integer accId = (Integer) session.getAttribute("accId");
    if(accId == null){
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head><title>Order List</title></head>
<body>
    <h2>Your Orders</h2>
    <table border="1">
        <tr>
            <th>Order ID</th><<th>Order Date</th><th>Status</th><th>Action</th>
        </tr>
        <c:forEach var="o" items="${orders}">
            <c:if test="${o.accId == accId}">
                        <tr>
            <td>${o.orderId}</td>
            <td>${o.orderDate}</td>
            <td>${o.orderStatus}</td>
            <td>${o.paymentMethod}</td>
            <td>
                <c:choose>
                    <c:when test="${o.orderStatus == 'PENDING'}">
                        <form action="cancelorderServlet" method="get">
                            <input type="hidden" name="orderId" value="${o.orderId}">
                            <input type="submit" value="Cancel">
                        </form>
                    </c:when>
                    <c:when test="${o.paymentStatus == 'PAID' && o.orderStatus == 'DELIVERED'}">
                        <form action="requestCancelServlet" method="get">
                            <input type="hidden" name="orderId" value="${o.orderId}">
                            <input type="submit" value="Request Cancel">
                        </form>
                    </c:when>
                    <c:otherwise>
                        CANNOT DELETE ORDER
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
            </c:if>
        </c:forEach>
    </table>
</body>
</html>

