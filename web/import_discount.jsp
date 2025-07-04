<%-- 
    Document   : import_discount
    Created on : Jul 4, 2025, 8:22:57 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Import Mã Giảm Giá từ Excel</title>
</head>
<body>
    <h1>Import Mã Giảm Giá từ Excel</h1>
    <form action="importdiscount" method="post" enctype="multipart/form-data">
        <input type="file" name="excelFile" accept=".xlsx" required>
        <button type="submit">Upload và Import</button>
    </form>
    <c:if test="${not empty successMess}">
        <div style="color: green">${successMess}</div>
    </c:if>
    <c:if test="${not empty errMess}">
        <div style="color: red">${errMess}</div>
    </c:if>
</body>
</html>
