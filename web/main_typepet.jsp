<%-- 
    Document   : type
    Created on : 4 Mar 2025, 10:37:20 pm
    Author     : HuyHoang
--%>

<%--<%@ page import="java.util.List" %>
<%@ page import="Models.Pet" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>PETFPT Shop</title>
   </head>
<body>

    <nav>
        <ul class="menu">
            <li><a href="listmainmenu">Trang Chủ</a></li>
            <li><a href="listshoppet?species=Dog&sort=popular&giapet1=&giapet2=&breed=&search=">Chó Cưng</a></li>
            <li><a href="listshoppet?species=Cat&sort=popular&giapet1=&giapet2=&breed=&search=">Mèo Cưng</a></li>
            
           
        </ul>
    </nav>
</div>

<h2 class="text-item" style=" 
    text-align: center;
    margin-top: 30px;
">Thú Cưng Tìm Kiếm</h2>

        <div class="container">
           

            <aside class="sidebar">
                <h3><a href="listshoppet?species=Dog" style="text-decoration: none; color: black">Tất cả giống chó</a></h3>
<ul>
   <c:forEach items="${listDogBreed}" var="x">
    <li>
        <a href="listshoppet?species=Dog&breed=${x.getBreedId()}"
           class="filter-category">
           ${x.getBreedName()}
        </a>
    </li>
</c:forEach>

<h3><a href="listshoppet?species=Cat" style="text-decoration: none; color: black">Tất cả giống mèo</a></h3>
<ul>
    <c:forEach items="${listCatBreed}" var="x">
        <li>
            <a href="listshoppet?species=Cat&breed=${x.getBreedId()}"
               class="filter-category ">
               ${x.getBreedName()}
            </a>
        </li>
    </c:forEach>
</ul>



               

    
        <form method="get" action="listshoppet" id="filterForm">
           

            <div class="form-group">
            <h3>Bộ lọc thú cưng</h3>
            <label>Tên / giống</label>
            <div class="search-container">
              <input type="text" name="search" value="${param.search}" placeholder="Tìm kiếm thú cưng ..." class="search-input" 
 style="
    width: 195px;
    height: 35px;
">
                <div class="search-button">
                    <button type="submit" class="search-icon-btn">
                        
                    </button>
                </div>
            </div>
</div>
                 <div class="form-group">
            <label>Giới tính</label>
            <select name="gender" style="
    width: 130px;
">
               
                <option value="">Tất cả</option>
                <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Đực</option>
                <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Cái</option>
            </select>
           </div>
 <div class="form-group">
            <label>Màu</label>
            <select name="color" style="
    margin-left: 29px;
    width: 130px;
">
               
                <option value="">Tất cả</option>
                <c:forEach var="c" items="${listColor}">
                    <option value="${c}" ${param.color == c ? 'selected' : ''}>${c}</option>
                </c:forEach>
            </select>
            </div>
 <div class="form-group">
            <label>Xuất xứ</label>
            <select name="origin" style="
    margin-left: 6px;
    width: 130px;
">
             
                <option value="">Tất cả</option>
                <c:forEach var="o" items="${listOrigin}">
                    <option value="${o}" ${param.origin == o ? 'selected' : ''}>${o}</option>
                </c:forEach>
            </select>
            </div>
 <div class="form-group">
            <label>Khoảng giá</label>
           <select name="priceRange" style="
    width: 103px;
    display: flex;
    margin-top: -15px;
    margin-left: 90px;
">
               
                <option value="">Tất cả</option>
                <option value="0-1000000" ${param.priceRange == '0-1000000' ? 'selected' : ''}>Dưới 1 triệu</option>
                <option value="1000000-2000000" ${param.priceRange == '1000000-2000000' ? 'selected' : ''}>1 - 2 triệu</option>
                <option value="2000000-5000000" ${param.priceRange == '2000000-5000000' ? 'selected' : ''}>2 - 5 triệu</option>
                <option value="5000000-10000000" ${param.priceRange == '5000000-10000000' ? 'selected' : ''}>5 - 10 triệu</option>
                <option value="10000000-20000000" ${param.priceRange == '10000000-20000000' ? 'selected' : ''}>10 - 20 triệu</option>
                <option value="20000000-999999999" ${param.priceRange == '20000000-999999999' ? 'selected' : ''}>> 20 triệu</option>
            </select>
            </div>
 <div class="form-group">
            <label>Tuổi</label>
            <select name="age" style="
    margin-left: 57px;
    width: 103px;
">
                
                <option value="">Tất cả</option>
                <option value="under30" ${param.age == 'under30' ? 'selected' : ''}>Dưới 2.5 tuổi</option>
<option value="30-36" ${param.age == '30-36' ? 'selected' : ''}>2.5 – 3 tuổi</option>
<option value="37-44" ${param.age == '37-44' ? 'selected' : ''}>3 – 3.5 tuổi</option>
<option value="above44" ${param.age == 'above44' ? 'selected' : ''}>Trên 3.5 tuổi</option>

            </select>
</div>
 <div class="form-group">
            <label>Tiêm vắc xin</label>
            <select name="vaccination" style="
    width: 102px;
">
                
                <option value="">Tất cả</option>
                <c:forEach var="v" items="${listVaccine}">
                    <option value="${v}" ${param.vaccination == v ? 'selected' : ''}>${v}</option>
                </c:forEach>
            </select>
            </div>
 <div class="form-group">
            <div class="loc">
                <button type="submit" >Lọc</button>
             
            </div>
              
        </form>
    </div>




   



            </aside>


            
            <main class="product-container">
                <div class="sort-container">
                      <label for="sort">Sắp xếp theo:</label>
                    <div class="custom-select">
                      <form action="listshoppet" id="sortForm" method="get">
                          
                          <select>
        <option value="az" ${param.sortpet == 'az' ? 'selected' : ''}>Tên thú cưng từ A-Z</option>
        <option value="za" ${param.sortpet == 'za' ? 'selected' : ''}>Tên thú cưng từ Z-A</option>
        <option value="price-asc" ${param.sortpet == 'price-asc' ? 'selected' : ''}>Giá thấp đến cao</option>
        <option value="price-desc" ${param.sortpet == 'price-desc' ? 'selected' : ''}>Giá cao đến thấp</option>
    </select>
</form>


                    </div>
                </div>
                          <c:if test="${empty listPet}">
    <p style="color: red; font-weight: bold; text-align: center; margin-top: 30px;">
        Không tìm thấy kết quả phù hợp với bộ lọc.
    </p>
</c:if>



              
    <div class="detail-favo-item-product">
       <c:forEach items="${listPet}" var="x">
            <div class="detail-favo-item">
                <a href="displaypet?id=${x.petId}">
                    <div class="image-favo-container">
                        <span class="sale-badge">Sale</span>
                        <c:choose>
                            <c:when test="${not empty x.images && not empty x.petImageBase64}">
                                <img src="${x.petImageBase64}" width="200" height="200" alt="${x.petName}" style="object-fit: cover; border-radius: 8px;" />
                            </c:when>
                            <c:otherwise>
                                <img src="images/default-pet.png" width="200" height="200" alt="default" style="object-fit: cover; border-radius: 8px;" />
                            </c:otherwise>
                        </c:choose>
                    </div>
                   <p class="item-favo-name">
    ${x.petName}
    <span class="item-favo-breed">(${x.breedName})</span>
</p>

                </a>
                <div class="tym-item">
                    <p class="price">
                        <fmt:formatNumber value="${x.petPrice}" type="currency" currencySymbol="₫" groupingUsed="true" />
                    </p>
                </div>
            </div>
        </c:forEach>

    </div>

            </main>
        </div>

       

    </body>
    



</html>
