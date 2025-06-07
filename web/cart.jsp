<%-- 
    Document   : cart
    Created on : Jun 6, 2025, 9:08:28 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Giỏ hàng - TEST</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link href="css/cart.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h2>Giỏ hàng của bạn</h2>

        <table class="cart-table">
            <thead>
                <tr>
                    <th>Chọn</th> 
                    <th>Sản phẩm</th>
                    <th>Tạm tính</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pets}" var="pet">
                    <tr class="cart-item">
                        <td>
                            <input type="checkbox" form="checkoutForm" name="selectedPets" value="${pet.petId}" />
                        </td>
                        <td class="item-info">
                            <img src="${pet.petImageBase64}" alt="${pet.petName}" class="cart-img" />
                            <div class="item-title">${pet.petName}</div>
                        </td>
                        <td>
                            <fmt:formatNumber value="${pet.petPrice}" type="currency" currencySymbol="₫" />
                        </td>
                        <td>
                            <form action="deletefromcart" method="GET">
                                <input type="hidden" name="id" value="${pet.petId}" />
                                <input type="hidden" name="type" value="pet" />
                                <button type="submit">Xóa</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <form action="checkoutServlet" method="POST">
            <h3>Thông tin nhận hàng</h3>
            <c:choose>
                <c:when test="${not empty sessionScope.account}">
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
            <p><fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="₫" /></p>
            <input type="hidden" name="totalprice" value="${totalPrice}" />

            <button type="submit">Thanh toán</button>
        </form>
    </body>
</html>
