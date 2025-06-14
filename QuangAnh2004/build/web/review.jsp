<%-- 
    Document   : review
    Created on : Jun 1, 2025, 1:08:24 PM
    Author     : QuangAnh
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--
    Integer accId = (Integer) session.getAttribute("accId");
    if(accId == null){
        response.sendRedirect("login_account_page.jsp");
        return;
    }
-->
<html>
<head>
    <title>Viết đánh giá</title>
    <link rel="stylesheet" type="text/CSS" href="CSS/review.css">
</head>
<body>


<h2>Đánh giá cửa hàng</h2>

<table>
    <tr>
        <th>Review ID</th>
        <th>Account ID</th>
        <th>Rating</th>
        <th>Review Text</th>
        <th>Review Date</th>
    </tr>

<%
    ShopReviewDAO dao = new ShopReviewDAO();
    List<ShopReview> list = dao.getAllReviews();
    for (ShopReview r : list) {
%>
    <tr>
        <td><%= r.getReviewId() %></td>
        <td><%= r.getAccId() %></td>
        <td><%= r.getRating() %></td>
        <td><%= r.getReviewText() %></td>
        <td><%= r.getReviewDate() %></td>
    </tr>
<%
    }
%>
</table>
</body>
</html>

