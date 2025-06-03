<%-- 
    Document   : notification-history
    Created on : Jun 1, 2025, 12:56:55 PM
    Author     : QuangAnh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Notification" %>
<%
    Integer accId = (Integer) session.getAttribute("accId");
    if(accId == null){
        response.sendRedirect("login_account_page.jsp");
        return;
    }
%>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
</head>

<%
    List<Notification> notifications = (List<Notification>) request.getAttribute("notifications");
    String selectedType = (String) request.getAttribute("selectedType");
%>

<h2>Notification History</h2>

<form method="get" action="notification-history">
    <label>Filter by Type:</label>
    <select name="type">
        <option value="">-- All --</option>
        <option value="system" <%= "system".equals(selectedType) ? "selected" : "" %>>System</option>
        <option value="order" <%= "order".equals(selectedType) ? "selected" : "" %>>Order</option>
    </select>
    <input type="submit" value="Filter">
</form>

<table border="1">
    <tr>
        <th>Title</th>
        <th>Message</th>
        <th>Type</th>
        <th>Date</th>
        <th>Read</th>
    </tr>
    <%
        if (notifications != null) {
            for (Notification n : notifications) {
    %>
    <tr>
        <td><%= n.getTitle() %></td>
        <td><%= n.getMessage() %></td>
        <td><%= n.getNotifType() %></td>
        <td><%= n.getCreatedAt() %></td>
        <td><%= n.isIsRead() ? "Yes" : "No" %></td>
    </tr>
    <%
            }
        }
    %>
</table>

