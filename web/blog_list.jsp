<%-- 
    Document   : blog_list
    Created on : Jun 19, 2025, 8:25:46 AM
    Author     : AD
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Danh sách Blog</title>
</head>
<body>

<h2>📄 Danh sách Blog</h2>
<a href="blog?action=add">➕ Thêm blog mới</a><br><br>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Tiêu đề</th>
        <th>Ảnh</th>
        <th>Trạng thái</th>
        <th>Ngày tạo</th>
        <th>Hành động</th>
    </tr>
    <c:forEach var="b" items="${blogList}">
        <tr>
            <td>${b.blogId}</td>
            <td>${b.title}</td>
            <td><img src="${b.featuredImage}" style="max-height: 60px;"></td>
            <td>${b.status}</td>
            <td>${b.createdDate}</td>
            <td>
                <a href="blog?action=edit&id=${b.blogId}">Sửa</a> |
                <a href="blog?action=view&id=${b.blogId}">👁️ Xem</a> |

                <a href="blog?action=delete&id=${b.blogId}" onclick="return confirm('Xoá blog này?')">Xoá</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
