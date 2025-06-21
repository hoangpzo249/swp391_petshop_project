<%-- 
    Document   : review
    Created on : Jun 14, 2025, 11:02:54 PM
    Author     : QuangAnh
--%>


<!--
    Integer accId = (Integer) session.getAttribute("accId");
    if(accId == null){
        response.sendRedirect("login_account_page.jsp");
        return;
    }
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>Đánh giá thú cưng</h2>
<link rel="stylesheet" type="text/css" href="CSS/review.css">
<c:if test="${not empty message}">
    <p style="color:green;">${message}</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form action="review" method="post">
    <label for="accId">ID người dùng:</label><br>
    <input type="number" name="accId" required><br><br>

    <label for="rating">Đánh giá (1-5):</label><br>
    <select name="rating" required>
        <option value="">--Chọn--</option>
        <c:forEach var="i" begin="1" end="5">
            <option value="${i}">${i} sao</option>
        </c:forEach>
    </select><br><br>

    <label for="comment">Nhận xét:</label><br>
    <textarea name="comment" rows="4" cols="50" placeholder="Viết đánh giá tại đây..." required></textarea><br><br>

    <button type="submit">Gửi đánh giá</button>
</form>
   <hr>
<h3>Đánh giá đã gửi</h3>

<c:if test="${empty reviewList}">
    <p>Chưa có đánh giá nào cho thú cưng này.</p>
</c:if>

<c:if test="${not empty reviewList}">
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>STT</th>
            <th>Số sao</th>
            <th>Nội dung</th>
            <th>Ngày gửi</th>
        </tr>
        <c:forEach var="r" items="${reviewList}" varStatus="loop">
            <tr>
                <td>${loop.index + 1}</td>
                <td>${r.rating} ⭐</td>
                <td>${r.reviewText}</td>
                <td><fmt:formatDate value="${r.reviewDate}" pattern="dd-MM-yyyy" /></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
 
    