<%-- 
    Document   : Invoice
    Created on : Jun 1, 2025, 1:06:52 PM
    Author     : QuangAnh
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Invoice" %>
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
    <link rel="stylesheet" type="text/CSS" href="CSS/Invoice.css">
</head>
<body>


<h2>Lịch sử hóa đơn</h2>

<c:if test="${not empty errorMessage}">
    <div class="message error">${errorMessage}</div>
</c:if>

<c:if test="${empty invoices}">
    <p>Không có hóa đơn nào.</p>
</c:if>

<c:if test="${not empty invoices}">
    <table>
        <thead>
        <tr><th>Mã hóa đơn</th><th>Mã đơn</th><th>Ngày</th><th>Thuế</th><th>Tổng</th><th>Thanh toán</th></tr>
        </thead>
        <tbody>
<c:forEach var="inv" items="${invoices}">
        <tr>
            <td>${inv.invoiceId}</td>
            <td>${inv.orderId}</td>
            <td>${inv.issueDate}</td>
            <td>${inv.taxAmount}</td>
            <td>${inv.totalAmount}</td>
            <td>${inv.paymentMethod}</td>
        </tr>
    </c:forEach>
        </tbody>
    </table>
</c:if>


</body>
</html>

