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
    <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="icon" type="image/png" href="images/logo_banner/logo1.png">
    <link href="css/head_about.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/main_type.css?v=4" />

    <script src="js/scroll_chat.js" type="text/javascript"></script>
</head>
<body>
<div class="header">
    <div class="header1">
        <a href="listmainmenu">
            <img src="images/logo.png" width="800" height="360" alt="logo"/>
        </a>
       
       
               
            
        </form>
        <div class="accountcard">
            <div class="account">
                <a href="${not empty sessionScope.account ? 'account_profile_user.jsp' : 'account_login.jsp'}">
                    <img src="images/account.png" width="800" height="800" alt="account"/>
                    <p class="logintext">Tài Khoản</p>
                </a>
            </div>
            <div class="card">
                <a href=displaycart>
                    <img src="images/card.png" width="1859" height="1573" alt="card"/>
                    <p class="cardtext">
                        Giỏ Hàng
                        <span id="cart-count" class="cartcount">${sessionScope.cartcount}</span>
                    </p>
                </a>
            </div>
        </div>
    </div>
    <nav>
        <ul class="menu">
            <li><a href="listmainmenu">Trang Chủ</a></li>
            <li><a href="listshoppet?species=Dog&sort=popular&giapet1=&giapet2=&breed=&search=">Chó Cưng</a></li>
            <li><a href="listshoppet?species=Cat&sort=popular&giapet1=&giapet2=&breed=&search=">Mèo Cưng</a></li>
            
            <li><a href="menu_about.jsp">Giới Thiệu</a></li>
            <li><a href="menu_contact.jsp">Liên Hệ</a></li>
            <c:if test="${not empty sessionScope.account and sessionScope.account.accRole eq 'Admin'}">
                <li><a href="admin.jsp">Admin Panel</a></li>
            </c:if>
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
           class="filter-category ${param.species eq 'Dog' and param.breed eq x.breedId ? 'selected' : ''}">
           ${x.getBreedName()}
        </a>
    </li>
</c:forEach>

<h3><a href="listshoppet?species=Cat" style="text-decoration: none; color: black">Tất cả giống mèo</a></h3>
<ul>
    <c:forEach items="${listCatBreed}" var="x">
        <li>
            <a href="listshoppet?species=Cat&breed=${x.getBreedId()}"
               class="filter-category ${param.species eq 'Cat' and param.breed eq x.breedId ? 'selected' : ''}">
               ${x.getBreedName()}
            </a>
        </li>
    </c:forEach>
</ul>



               

    
        <form method="get" action="listshoppet" id="filterForm">
            <input type="hidden" name="sortpet" value="${param.sortpet}" />
            <input type="hidden" name="species" value="${param.species}" />
<input type="hidden" name="breed" value="${param.breed}" />


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
                <button type="submit" style="   
    box-sizing: border-box;
    margin-bottom: 15px;
    margin-top: 15px;
    width: 65px;
    height: 40px;
    color: white;
    background: orange;
    border: none;">Lọc</button>
             <a href="listshoppet?reset=true" class="reset-button" style="
    margin-left: -66px;
    margin-top: 75px;
    box-sizing: border-box;
    padding: 5px;
    color: white;
    background: orange;
    text-decoration: none;
    ">Reset bộ lọc</a>
            </div>
              
        </form>
    </div>




   



            </aside>


            
            <main class="product-container">
                <div class="sort-container">
                      <label for="sort">Sắp xếp theo:</label>
                    <div class="custom-select">
                      <form action="listshoppet" id="sortForm" method="get">
                          <input type="hidden" name="species" value="${param.species}" />
<input type="hidden" name="breed" value="${param.breed}" />

    <input type="hidden" name="search" value="${param.search}" />
    <input type="hidden" name="gender" value="${param.gender}" />
    <input type="hidden" name="color" value="${param.color}" />
    <input type="hidden" name="origin" value="${param.origin}" />
    <input type="hidden" name="priceRange" value="${param.priceRange}" />
    <input type="hidden" name="age" value="${param.age}" />
    <input type="hidden" name="vaccination" value="${param.vaccination}" />

    <select name="sortpet" onchange="document.getElementById('sortForm').submit()">
        
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

        <!--about-->
        <div class="about-section">
            <div class="about-column">
                <h3>Shop</h3>
                <ul>
                    <li><a href="listshoppet?species=Chó&sort=popular&giapet1=&giapet2=&breed=&search=">Dành Cho Chó</a></li>
                    <li><a href="listshoppet?species=Mèo&sort=popular&giapet1=&giapet2=&breed=&search=">Dành Cho Mèo</a></li>
                    <li><a href="listshop?category=%search=&gia1=&gia2=&sort=popular">Phụ kiện cho Chó & Mèo</a></li>
                </ul>
            </div>

            <div class="about-column">
                <h3>PETFPT Shop</h3>
                <ul>
                    <li><a href="menu_about.jsp">Giới Thiệu</a></li>
                    <li><a href="footer_policy.jsp">Chính sách</a></li>
                    <li><a href="footer_paymentmethod.jsp">Phương Thức Thanh Toán</a></li>
                    <li><a href="footer_termofuse.jsp">Điều Khoản Sử Dụng</a></li>                    
                </ul>
            </div>

            <div class="about-column">
                <h3>Liên Hệ</h3>
                <p class="contactpet">PETFPT Shop</p>
                <p><i class="fas fa-phone"></i>0767676770</p>
                <p><i class="fas fa-map-marker-alt"></i>Khu Công nghệ cao Hòa Lạc <br>
                    Km29 Đại lộ Thăng Long, H. Thạch Thất, TP. Hà Nội</p>
                <p><i class="fas fa-envelope email"></i>
                    <a href="mailto:hoangnhhe181051@fpt.edu.vn">hoangnhhe181051@fpt.edu.vn</a>
                </p>

                <div class="social-container">
                    <a href="https://www.facebook.com/petfptshop" target="_blank" class="social-icon">
                        <i class="fab fa-facebook fa-2x"></i>
                    </a>
                </div>
            </div>
        </div>

        <!-- scroll_chat -->
        <div class="chat-scroll-container">
            <button onclick="scroll()" id="scroll">
                <i class="fas fa-chevron-up"></i>
            </button>

            <div class="chat-container">
                <div id="chat-button" class="chat-button">
                    <img src="images/chat.png" alt="chat"/>
                </div>

                <div id="zalo-button" class="zalo-button">
                    <a href="https://zalo.me/your-zalo-link" target="_blank">
                        <img src="images/zalo.jpg" alt="zalo"/>
                    </a>
                </div>
            </div>
        </div>
        <footer>
            © 2025 PETFPT - Đồng hành cùng bạn và thú cưng mỗi ngày!
        </footer>
        <script>


    </body>
    



</html>
