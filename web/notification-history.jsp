<%-- 
    Document   : notification
    Created on : Jun 14, 2025, 11:02:03 PM
    Author     : QuangAnh
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Notification" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Lịch sử thông báo</title>
    <style>
        .notification-table {
            width: 100%;
            border-collapse: collapse;
        }
        .notification-table th, .notification-table td {
            border: 1px solid #ccc;
            padding: 8px;
        }
        .notification-table th {
            background-color: #f2f2f2;
        }
        .notification-unread {
            background-color: #ffecec;
        }
        .filter-form {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<h2>Lịch sử thông báo</h2>

<%
    List<Notification> notifications = (List<Notification>) request.getAttribute("notifications");
    String selectedType = (String) request.getAttribute("selectedType");
%>

<form class="filter-form" method="get" action="notification">
    <label>Lọc theo loại:</label>
    <select name="type">
        <option value="" <%= (selectedType == null || selectedType.isEmpty()) ? "selected" : "" %>>-- Tất cả --</option>
        <option value="system" <%= "system".equals(selectedType) ? "selected" : "" %>>Hệ thống</option>
        <option value="order" <%= "order".equals(selectedType) ? "selected" : "" %>>Đơn hàng</option>
    </select>
    <input type="submit" value="Lọc">
</form>

<table class="notification-table">
    <tr>
        <th>Tiêu đề</th>
        <th>Nội dung</th>
        <th>Loại</th>
        <th>Ngày</th>
        <th>Trạng thái</th>
    </tr>

    <c:if test="${empty notifications}">
        <tr>
            <td colspan="5">Không có thông báo nào.</td>
        </tr>
    </c:if>

    <c:forEach var="n" items="${requestScopenotifications}">
        <tr class="${!n.isRead ? 'notification-unread' : ''}">
            <td>${n.title}</td>
            <td>${n.message}</td>
            <td>${n.notifType}</td>
            <td><fmt:formatDate value="${n.createdAt}" pattern="dd-MM-yyyy HH:mm"/></td>
            <td>${n.isRead ? "Đã đọc" : "Chưa đọc"}</td>
        </tr>
    </c:forEach>
</table>

    <a href="viewOrder">Quay ve trang hoa don</a>
</body>
</html>
