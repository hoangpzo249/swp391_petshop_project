<%-- 
    Document   : Order-success
    Created on : Jun 21, 2025, 1:10:39 PM
    Author     : QuangAnh
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Notification" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Đặt hàng thành công</title>
    <style>
        .notification-icon {
            position: absolute;
            top: 10px;
            right: 20px;
            font-size: 24px;
            cursor: pointer;
        }
        .notification-icon .badge {
            background: red;
            color: white;
            padding: 2px 6px;
            border-radius: 50%;
            font-size: 14px;
            vertical-align: top;
            margin-left: -10px;
        }
    </style>
</head>
<body>

<h2>Đơn hàng của bạn đã được đặt thành công!</h2>
<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.</p>

<!-- Biểu tượng thông báo -->
<a href="notification-history.jsp" class="notification-icon">
    🔔<span class="badge">
        <c:out value="${unreadCount}" />
    </span>
</a>

</body>
</html>

