<%-- 
    Document   : saler_order_view
    Created on : 23 May 2025, 10:04:28
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Order List</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                padding: 8px;
                border: 1px solid #ccc;
                text-align: left;
            }
            th {
                background-color: #f4f4f4;
            }
        </style>
    </head>
    <body>
        <h1>All Orders</h1>
        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Account ID</th>
                    <th>Order Date</th>
                    <th>Status</th>
                    <th>Customer Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Shipper ID</th>
                    <th>Payment Method</th>
                    <th>Payment Status</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orderList}">
                    <tr>
                        <td>${order.orderId}</td>
                        <td>${order.accId}</td>
                        <td>${order.orderDate}</td>
                        <td>${order.orderStatus}</td>
                        <td>${order.customerName}</td>
                        <td>${order.customerEmail}</td>
                        <td>${order.customerPhone}</td>
                        <td>${order.customerAddress}</td>
                        <td>${order.shipperId}</td>
                        <td>${order.paymentMethod}</td>
                        <td>${order.paymentStatus}</td>
                        <td><a href="orderdetail?id=${order.orderId}">View detail</a></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty orderList}">
                    <tr>
                        <td colspan="11" style="text-align:center;">No orders found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </body>
</html>
