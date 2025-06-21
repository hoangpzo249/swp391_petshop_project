<%-- 
    Document   : Order
    Created on : Jun 20, 2025, 10:26:29 PM
    Author     : QuangAnh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>




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
        <link rel="stylesheet" type="text/CSS" href="CSS/Order.css">
    </head>
    <body>
        <h2>Your Orders</h2>
        <c:if test="${not empty message}">
    <div style="color: green; font-weight: bold; margin-bottom: 10px;">
        ${message}
    </div>
</c:if>

<c:if test="${not empty error}">
    <div style="color: red; font-weight: bold; margin-bottom: 10px;">
        ${error}
    </div>
</c:if>

        <a href="notification" style="float:right;">
            <button>🔔 Thông báo</button>
        </a>
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
                    <th>Action</th>

                </tr>
            </thead>
            <c:forEach var="o" items="${requestScope.orderList}">
                <tr>
                    <td>${o.orderId}</td>
                    <td>${o.accId}</td>
                    <td>${o.orderDate}</td>
                    <td>${o.orderStatus}</td>
                    <td>${o.customerName}</td>
                    <td>${o.customerEmail}</td>
                    <td>${o.customerPhone}</td>
                    <td>${o.customerAddress}</td>
                    <td>
                        <c:choose>
                            <c:when test="${o.shipperId == 0}">NULL</c:when>
                            <c:otherwise>${o.shipperId}</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${o.paymentMethod}</td>
                    <td>${o.paymentStatus}</td>
                    <td>
                        <c:out value="${o.rejectionReason}" default="NULL" />
                    </td>

                    <td>
                        <c:choose>
                            <c:when test="${o.orderStatus == 'Pending'}">
                                <form action="cancelOrder" method="get">
                                    <input type="hidden" name="orderId" value="${o.orderId}" />
                                    <button type="submit" onclick="return confirm('Bạn có chắc muốn hủy đơn này?')">Huỷ đơn</button>
                                </form> <!-- 🔴 cần thêm dòng này -->
                            </c:when>

                            <c:when test="${o.orderStatus == 'Confirmed'}">
                                <form action="requestCancel" method="get">
                                    <input type="hidden" name="orderId" value="${o.orderId}" />
                                    <button type="submit" onclick="return confirm('Bạn muốn gửi yêu cầu hủy đơn?')">Yêu cầu huỷ</button>
                                </form>
                            </c:when>

                            <c:when test="${o.orderStatus == 'Rejected'}">
                                <form action="deleteOrder" method="get">
                                    <input type="hidden" name="orderId" value="${o.orderId}" />
                                    <button type="submit" onclick="return confirm('Bạn có chắc muốn xóa hóa đơn này?')">Xóa hóa đơn</button>
                                </form>
                            </c:when>
                        </c:choose>



                    </td>
                    <td>
                        <a href="orderContent?orderId=${o.orderId}">Xem chi tiết</a>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${o.orderStatus == 'Confirmed' || o.paymentStatus == 'Paid'}">
                                <form action="invoice" method="get">
                                    <input type="hidden" name="orderId" value="${o.orderId}" />
                                    <button type="submit">Xem hóa đơn</button>
                                </form>
                            </c:when>


                            <c:when test="${o.paymentStatus == 'Paid'}">
                                <form action="review" method="post" style="display:inline;">
                                    <input type="hidden" name="petId" value="${o.petId}" />
                                    <button type="submit">Đánh giá</button>
                                </form>
                            </c:when>

                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>


