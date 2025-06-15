<%-- 
    Document   : managediscount
    Created on : Jun 7, 2025, 12:48:07 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>this is managediscountpage</h2>
        <h4>${requestScope.actionStatus}</h4>
        <c:forEach var="error" items="${requestScope.errors}">
            <h4 style="color: red">${error}</h4>
        </c:forEach>
        <a href="managerpages/adddiscount.jsp"> ấn vào đây để thêm discount</a>
        <table border="1">
            <thead>
                <tr>
                    <th>Discount Id</th>
                    <th>Discount Code</th>
                    <th>Discount Type</th>
                    <th>DisCount Value</th>
                    <th>Description</th>
                    <th>Valid From</th>
                    <th>Vali dTo</th>
                    <th>Min Order Amount</th>
                    <th>Max Usage</th>
                    <th>Usage Count</th>
                    <th>Status</th>
                    <th>changeStatus</th>
                    <th>update</th>
                    <th>delete</th>

                </tr>
            </thead>
            <tbody>
                <c:forEach var="discount" items="${requestScope.discountList}">
                    <tr>
                        <td>${discount.discountId}</td>
                        <td>${discount.discountCode}</td>
                        <td>${discount.discountType}</td>
                        <td>${discount.disCountValue}</td>
                        <td>${discount.description}</td>
                        <td>${discount.validFrom}</td>
                        <td>${discount.validTo}</td>
                        <td>${discount.minOrderAmount}</td>
                        <td>${discount.maxUsage}</td>
                        <td>${discount.usageCount}</td>
                        <td>${discount.isActive eq 'true' ? 'Active' : 'Deactive'}</td>
                        <td> 
                            <form action="${pageContext.request.contextPath}/ManageDiscount" method="get">
                                <input type="hidden" name="userAcction" value="updateStatus">
                                <input type="hidden" name="updateStatusId" value="${discount.discountId}">
                                <input type="hidden" name="updateStatus" value="${discount.isActive eq 'true' ? 'false': 'true'}">
                                <input type="submit" value="${discount.isActive eq 'true' ? 'Deactive Discount' : 'Active Discount'}">
                            </form>
                        </td>
                        <td><a href=""> update </a></td>
                        <!--với action add thì ta cần truyền vào: 1. useraction, 2. id cần xóa-->
                        <td>
                            <form action="${pageContext.request.contextPath}/ManageDiscount" method="get">
                                <input type="hidden" name="userAcction" value="delete">
                                <input type="hidden" name="deleteId" value="${discount.discountId}">
                                <input type="submit" value="delete Discount">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
