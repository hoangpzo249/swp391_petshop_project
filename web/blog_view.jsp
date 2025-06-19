<%-- 
    Document   : blog_view
    Created on : Jun 19, 2025, 8:58:05 AM
    Author     : AD
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chi tiết Blog</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        img { max-width: 400px; margin-top: 10px; }
        .back { margin-top: 30px; display: inline-block; }
    </style>
</head>
<body>

<h2>${title}</h2>
<p><strong>Trạng thái:</strong> ${status}</p>
<p><strong>Ngày tạo:</strong> ${createdDate}</p>

<c:if test="${not empty featuredImage}">
    <img src="${featuredImage}" alt="Ảnh đại diện">
</c:if>

<h3>Nội dung:</h3>
<div style="white-space: pre-wrap;">${content}</div>

<a href="blog" class="back">← Quay lại danh sách</a>

</body>
</html>
