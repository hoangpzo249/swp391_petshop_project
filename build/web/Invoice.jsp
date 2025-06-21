<%-- 
    Document   : Invoice
    Created on : Jun 14, 2025, 11:01:47 PM
    Author     : QuangAnh
--%>



<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="CSS/invoice.css">


<h2>Chi tiết Hóa đơn</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<c:if test="${not empty invoice}">
    <table border="1" cellpadding="5">
        <tr><td><strong>Mã hóa đơn:</strong></td><td>${invoice.invoiceId}</td></tr>
        <tr><td><strong>Mã đơn hàng:</strong></td><td>${invoice.orderId}</td></tr>
        <tr><td><strong>Ngày lập:</strong></td>
            <td><fmt:formatDate value="${invoice.issueDate}" pattern="dd-MM-yyyy" /></td></tr>
        <tr><td><strong>Tổng tiền:</strong></td>
            <td>
                <c:choose>
                    <c:when test="${invoice.totalAmount != null}">
                        <fmt:formatNumber value="${invoice.totalAmount}" type="currency" currencySymbol="₫" groupingUsed="true" />
                    </c:when>
                    <c:otherwise>Không xác định</c:otherwise>
                </c:choose>
            </td></tr>
        <tr><td><strong>Phương thức thanh toán:</strong></td><td>${invoice.paymentMethod}</td></tr>
    </table>
</c:if>

<c:if test="${empty invoice}">
    <p style="color:gray;">Không tìm thấy thông tin hóa đơn.</p>
</c:if>

<br><h3>Danh sách thú cưng trong hóa đơn</h3>
<c:if test="${not empty orderContents}">
    <table border="1" cellpadding="5">
        <tr>
            <th>#</th>
            <th>Mã thú cưng</th>
        </tr>
        <c:forEach var="item" items="${orderContents}" varStatus="loop">
            <tr>
                <td>${loop.index + 1}</td>
                <td>${item.petId}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty orderContents}">
    <p style="color:gray;">Không có thú cưng nào trong hóa đơn.</p>
</c:if>
    
        <a href="viewOrder">Quay lại danh sách đơn</a>