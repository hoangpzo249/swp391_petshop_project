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
        <c:set var="Omg" value= "đoán xem"/>
        <h1>${Omg} meo meo heheheheheeee meomeomeooooomeoooo</h1>
        <a href="managerpages/adddiscount.jsp"> ấn vào đây để thêm discount</a>
        <table border="1">
            <thead>
                <tr>
                    <th>discountId</th>
                    <th>discountCode</th>
                    <th>discountType</th>
                    <th>disCountValue</th>
                    <th>description</th>
                    <th>validFrom</th>
                    <th>validTo</th>
                    <th>minOrderAmount</th>
                    <th>maxUsage</th>
                    <th>usageCount</th>
                    <th>isActive</th>
                    <th>changeStatus</th>
                    <th>update</th>
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
                    <td>${discount.isActive}</td>
                    <td><a href=""> change Status</a></td>
                    <td><a href=""> update </a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>
