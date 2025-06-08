<%-- 
    Document   : AddDiscount.jsp
    Created on : Jun 7, 2025, 4:24:50 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Discount</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <!-- chỗ này để get trước để lấy param -->
        <form action="${pageContext.request.contextPath}/ManageDiscount" method="get">
            <table>
                <tbody>
                    <tr>
                        <td> discount code:</td>
                        <td><input type="text" required name="discountCode"></td>
                    </tr>
                    <tr>
                        <td>discount type: </td>
                        <td>
                            <select name="discountType">
                                <option value="Fixed">Fixed</option>
                                <option value="Percent">Percent</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>discount value:</td>
                        <td><input type="number" required name="disCountValue" min="0"></td>
                    </tr>
                    <tr>
                        <td>discount description:</td>
                        <td><input type="text" required name="description"></td>
                    </tr>
                    <tr>
                        <td>discount valid From:</td>
                        <td><input type="date" required name="validFrom"></td>
                    </tr>
                    <tr>
                        <td>discount valid to:</td>
                        <td><input type="date" required name="validTo"></td>
                    </tr>
                    <tr>
                        <td>discount min Order Amount:</td>
                        <td><input type="number" required name="minOrderAmount" min="0"></td>
                    </tr>
                    <tr>
                        <td>discount max usage:</td>
                        <td><input type="number" required name="maxUsage"min="1" step="1"></td>
                    </tr>
                    <tr>
                        <td>discount is active:</td>
                        <td>            
                            <select name="isActive">
                                <option value="Active">Active</option>
                                <option value="Deactive">Deactive</option>
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
