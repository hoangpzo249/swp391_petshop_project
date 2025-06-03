<%-- 
    Document   : review
    Created on : Jun 1, 2025, 1:08:24 PM
    Author     : QuangAnh
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Interger accId = (Interger) session.getAttribute("accId");
    if(accId == null){
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Viết đánh giá</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>


<h2>Đánh giá cửa hàng</h2>

<c:if test="${param.msg == 'success'}">
    <form action="review" method="post">
    <label for="rating">Chọn số sao (1-5):</label><br/>
    <select name="rating" id="rating" required>
        <option value="5">5 - Rất tốt</option>
        <option value="4">4 - Tốt</option>
        <option value="3">3 - Bình thường</option>
        <option value="2">2 - Kém</option>
        <option value="1">1 - Rất tệ</option>
    </select><br/><br/>

    <label for="reviewText">Viết đánh giá:</label><br/>
    <textarea name="reviewText" id="reviewText" rows="5" cols="50" placeholder="Nhập nội dung đánh giá tại đây..." required></textarea><br/><br/>

    <button type="submit">Gửi đánh giá</button>
</form>
</c:if>
<c:if test="${param.msg == 'error'}">
    <p class="error">Có lỗi xảy ra. Vui lòng thử lại.</p>
</c:if>


</body>
</html>

