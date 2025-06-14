<%-- 
    Document   : index
    Created on : 2 Jun 2025, 09:09:35
    Author     : Lenovo
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div style="display: flex; flex-direction: column; justify-content: center; align-items: center; padding: 5px">
            <c:choose>
                <c:when test="${not empty sessionScope.account}">
                    <p>Hello ${sessionScope.account.accUsername}</p>
                </c:when>
                <c:otherwise>
                    <p>Please log in.</p>
                </c:otherwise>
            </c:choose>

            <a href="quicklogin">Login</a>
            <a href="chatAI">AI</a>
            <a href="displayallpet">Pet List</a>
            <a href="displayorder">Order List</a>
        </div>
    </body>
</html>
