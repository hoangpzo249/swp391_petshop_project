<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết thú cưng</title>
    </head>
    <body>

       
        <c:set var="pet" value="${pet}" />

        <h1>${pet.petName}</h1>

        <img src="${pet.getPetImageBase64()}" alt="${pet.petName}" width="300" />

        <p>Giá: 
            <fmt:formatNumber value="${pet.petPrice}" type="currency" currencySymbol="₫" groupingUsed="true" />
        </p>

        <p>Mô tả: ${pet.petDescription}</p>

        <form action="addtocart" method="GET" class="cartForm" target="cart-response-frame">


            <button type="submit" class="add-to-cart">Thêm vào giỏ hàng</button>



        </form>
        <h2 class="text-favo-item">Thú Cưng Tương Tự</h2>

            
                <c:forEach items="${similarPets}" var="x">

                   
                        <a href="displaypet?id=${x.getPetId()}">
                           
                               
                                <img src="${x.getPetImageBase64()}" width="800" height="800" alt="${x.petName}"/>
                           
                            <p class="item-favo-name">${x.petName}</p>
                        </a>
                        
                           <fmt:formatNumber value="${x.petPrice}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                        
                   
                </c:forEach>

        

        <iframe id="cart-response-frame" name="cart-response-frame" style="display:none;"></iframe>
    </body>
</html>
