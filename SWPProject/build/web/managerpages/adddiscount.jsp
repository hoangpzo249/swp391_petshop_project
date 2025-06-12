<%-- 
    Document   : AddDiscount.jsp
    Created on : Jun 7, 2025, 4:24:50 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Discount</title>
    </head>
    <body>
        <h1>Add Discount Page</h1>
        <h4 style="color: blue">${requestScope.addStatus}</h4>

        <c:forEach var="error" items="${requestScope.errors}">
            <h4 style="color: red">${error}</h4>
        </c:forEach>

        <a href="${pageContext.request.contextPath}/ManageDiscount">quay lại manage discount page  </a>
        <form action="${pageContext.request.contextPath}/ManageDiscount" method="get">

            <table>
                <tbody>
                    <tr>
                        <td> discount code:</td>
                        <td><input type="text" required name="discountCode" value="${param.discountCode}"></td>
                    </tr>
                    <tr>
                        <td>discount type: </td>
                        <td>
                            <select name="discountType" >
                                <option value="Fixed" >Fixed</option>
                                <option value="Percent" ${param.discountType eq 'Percent' ? 'selected' :''}  >Percent</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>discount value:</td>
                        <td><input type="number" required name="discountValue" min="0" step="any" value="${param.discountValue}"></td>
                    </tr>
                    <tr>
                        <td>discount description:</td>
                        <td><input type="text" required name="description" value="${param.description}"></td>
                    </tr>
                    <tr>
                        <td>discount valid From:</td>
                        <td><input type="date" required name="validFrom" value="${param.validFrom}"></td>
                    </tr>
                    <tr>
                        <td>discount valid to:</td>
                        <td><input type="date" required name="validTo" value="${param.validTo}"></td>
                    </tr>
                    <tr>
                        <td>discount min Order Amount:</td>
                        <td><input type="number" required name="minOrderAmount" min="0"value="${param.minOrderAmount}"></td>
                    </tr>
                    <tr>
                        <td>discount max usage:</td>
                        <td><input type="number" required name="maxUsage"min="1" step="1" value="${param.maxUsage}"></td>
                    </tr>
                    <tr>
                        <td>discount status</td>
                        <td>            
                            <select name="isActive">
                                <option value="true">Active</option>
                                <option value="false" ${param.isActive eq 'false' ? 'selected' :''} >Deactive</option>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>
            <input type="hidden" name="userAcction" value="add">
            <input type="submit">
        </form>
    </body>
</html>
