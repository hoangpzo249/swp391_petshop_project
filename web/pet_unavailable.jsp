<%-- 
    Document   : pet_unavailable
    Created on : Jun 18, 2025, 4:10:25 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Đặt hàng thất bại - PETFPT Shop</title>
        <meta charset="UTF-8">
        <link rel="icon" type="image/png" href="images/logo_banner/logo1.png">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link href="css/pet_unavailable.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="error-container">
            <div class="error-content">
                <div class="error-icon">
                    <i class="fas fa-times-circle"></i>
                </div>
                <h1>Đặt hàng thất bại!</h1>
                <p>Rất tiếc! Một hoặc nhiều thú cưng bạn chọn hiện không còn khả dụng để mua.</p>

                <c:if test="${not empty unavailableMessage}">
                    <div class="error-message">
                        ${unavailableMessage}
                    </div>
                </c:if>

                <div class="btn-container">
                    <a href="displaycart" class="btn-back">Quay lại giỏ hàng</a>
                </div>
            </div>
        </div>
    </body>
</html>



