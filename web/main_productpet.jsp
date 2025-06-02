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

<%-- Biến pet đã được set từ Servlet --%>
<c:set var="pet" value="${pet}" />

<h1>${pet.petName}</h1>

<img src="${pet.getPetImageBase64()}" alt="${pet.petName}" width="300" />

<p>Giá: 
    <fmt:formatNumber value="${pet.petPrice}" type="currency" currencySymbol="₫" groupingUsed="true" />
</p>

<p>Mô tả: ${pet.petDescription}</p>

</body>
</html>
