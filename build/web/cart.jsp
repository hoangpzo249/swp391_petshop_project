<%-- 
    Document   : cart
    Created on : Jun 6, 2025, 9:08:28 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng - TEST</title>
</head>
<body>
    <h2>Giỏ hàng</h2>

    <c:if test="${not empty cartMessage}">
        <p>${cartMessage}</p>
    </c:if>

    <form id="checkoutForm" action="ajaxServlet" method="POST">
        <table border="1" cellpadding="5" cellspacing="0">
            <tr>
                <th>Chọn</th>
                <th>Tên</th>
                <th>Giá</th>
                <th>Ảnh</th>
                <th>Xóa</th>
            </tr>

            <c:forEach items="${pets}" var="pet" varStatus="status">
                <tr>
                    <td><input type="checkbox" name="selectedPets" value="${pet.petId}" /></td>
                    <td>${pet.petName}</td>
                    <td>
                        <fmt:formatNumber value="${petList[status.index].price}" type="currency" currencySymbol="₫" />
                    </td>
                    <td><img src="${pet.petImageBase64}" width="80" height="80" /></td>
                    <td>
                        <form action="deletefromcart" method="GET">
                            <input type="hidden" name="id" value="${pet.petId}" />
                            <input type="hidden" name="type" value="pet" />
                            <button type="submit">Xóa</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <h3>Thông tin người nhận</h3>
        <c:choose>
            <c:when test="${not empty sessionScope.account}">
                <p>Tên: ${name}</p>
                <p>SDT: ${phone}</p>
                <p>Địa chỉ: ${address}</p>
                <input type="hidden" name="guestName" value="${name}" />
                <input type="hidden" name="guestPhone" value="${phone}" />
                <input type="hidden" name="guestAddress" value="${address}" />
            </c:when>
            <c:otherwise>
                <p>Tên: <input type="text" name="guestName" required /></p>
                <p>SDT: <input type="text" name="guestPhone" required /></p>
                <p>Địa chỉ: <input type="text" name="guestAddress" required /></p>
            </c:otherwise>
        </c:choose>

        <h3>Tổng tiền</h3>
        <p>
            <fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="₫" />
        </p>

        <input type="hidden" name="totalprice" value="${totalPrice}" />

        <button type="submit">Thanh toán</button>
    </form>
</body>
</html>

