<%-- 
    Document   : Invoice
    Created on : Jun 14, 2025, 11:01:47 PM
    Author     : QuangAnh
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Invoice" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Hóa đơn</title>
</head>
<body>
    <h2>Chi tiết hóa đơn</h2>

    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <table border="1" cellpadding="10">
        <tr><th>Mã hóa đơn</th><td>${invoice.invoiceId}</td></tr>
        <tr><th>Ngày xuất</th><td>${invoice.issuaDate}</td></tr>
        <tr><th>Phương thức thanh toán</th><td>${invoice.paymentMethod}</td></tr>
        <tr><th>Tổng tiền</th><td>${invoice.totalAmount}</td></tr>
        <tr><th>Thuế</th><td>${invoice.taxAmount}</td></tr>
    </table>

    <h3>Danh sách thú cưng</h3>
    <table border="1" cellpadding="10">
        <tr>
            <th>Pet ID</th>
            <th>Giá tại thời điểm đặt</th>
        </tr>
        <c:forEach var="oc" items="${orderContents}">
            <tr>
                <td>${oc.petId}</td>
                <td>${oc.priceAtOrder}</td>
            </tr>
        </c:forEach>
    </table>

    <br/>
    <!-- ✅ Nút xuất hóa đơn PDF -->
    <form method="get" action="exportInvoice">
        <input type="hidden" name="invoiceId" value="${invoice.invoiceId}" />
        <button type="submit">Xuất hóa đơn PDF</button>
    </form>

</body>
</html>