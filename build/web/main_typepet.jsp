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
        <link rel="stylesheet" href="css/main_type.css?v=22" />
        <link href="css/header_footer.css" rel="stylesheet" type="text/css"/>
        <link href="css/advance_footer.css?v=3" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>
    <body>

        <div class="header">
            <div class="header1">
                <div>
                    <a href="homepage">
                        <img src="images/logo2.png" alt="logo"/>
                    </a>
                </div>

                <form action="listshoppet" method="get">

                    <input type="hidden" name="species" value="${param.species}" />
                    <input type="hidden" name="breed" value="${breed}" />
                    <input type="hidden" name="gender" value="${param.gender}" />
                    <input type="hidden" name="color" value="${param.color}" />
                    <input type="hidden" name="origin" value="${param.origin}" />
                    <input type="hidden" name="dobFrom" value="${param.dobFrom}" />
                    <input type="hidden" name="dobTo" value="${param.dobTo}" />
                    <input type="hidden" name="priceRange" value="${param.priceRange}" />
                    <input type="hidden" name="vaccination" value="${param.vaccination}" />
                    <div class="search">
                        <input type="text" name="search" value="${param.search}" placeholder="Tìm kiếm thú cưng ..." />
                        <button type="submit" class="search-button">
                            <img src="images/support button/search.png" width="20" height="20" alt="search" />
                        </button>
                    </div>
                </form>

                <div class="accountcard">
                    <c:choose>
                        <c:when test="${sessionScope.userAccount != null}">
                            <div class="account-dropdown">
                                <a href="#" class="account-trigger">
                                    <img src="images/support button/account.png" width="50" height="50" alt="account"/>
                                    <p class="username">Tài khoản</p>
                                </a>
                                <div class="dropdown-content">
                                    <c:choose>
                                        <c:when test="${sessionScope.userAccount.accRole eq 'Admin'}">
                                            <a href="admin-panel" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Admin</span>
                                            </a>
                                        </c:when>
                                        <c:when test="${sessionScope.userAccount.accRole eq 'Manager'}">
                                            <a href="profile" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Manager</span>
                                            </a>
                                        </c:when>
                                        <c:when test="${sessionScope.userAccount.accRole eq 'Seller'}">
                                            <a href="displayorder" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Seller</span>
                                            </a>
                                        </c:when>
                                        <c:when test="${sessionScope.userAccount.accRole eq 'Seller'}">
                                            <a href="displayorder" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Seller</span>
                                            </a>
                                        </c:when>
                                        <c:when test="${sessionScope.userAccount.accRole eq 'Shipper'}">
                                            <a href="profile" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Shipper</span>
                                            </a>
                                        </c:when>
                                    </c:choose>
                                    <a href="profile" class="dropdown-item">
                                        <i class="fas fa-user"></i> 
                                        <span>Thông tin cá nhân</span>
                                    </a>
                                    <a href="orders" class="dropdown-item">
                                        <i class="fas fa-shopping-bag"></i> 
                                        <span>Đơn hàng đã mua</span>
                                    </a>
                                    <a href="logout" class="dropdown-item logout">
                                        <i class="fas fa-sign-out-alt"></i> 
                                        <span>Đăng xuất</span>
                                    </a>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="account">
                                <a href="login">
                                    <img src="images/account.png" width="50" height="50" alt="account"/>
                                    <p class="logintext">Đăng nhập</p>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${empty sessionScope.userAccount or sessionScope.userAccount.accRole eq 'Customer'}">

                        <div class="card">
                            <a href="displaycart">
                                <img src="images/card.png" width="50" height="50" alt="card"/>
                                <p class="cardtext">
                                    Giỏ Hàng
                                    <span id="cart-count" class="cartcount">${sessionScope.cartcount}</span>
                                </p>
                            </a>
                        </div>
                    </c:if>
                </div>
            </div>

            <nav>
                <ul class="menu">
                    <li><a href="homepage">Trang Chủ</a></li>
                    <li><a href="listshoppet">Thú Cưng</a></li>
                    <li><a href="menu_about.jsp">Giới Thiệu</a></li>
                    <li><a href="menu_contact.jsp">Liên Hệ</a></li>
                </ul>
            </nav>
        </div>

        <h2 class="text-item" style="text-align: center; margin-top: 30px;">
            Thú Cưng Tìm Kiếm
        </h2>

        <div class="container">
            <aside class="sidebar" style="margin-bottom: 89px;">
                <form method="get" action="listshoppet">
                    <div class="form-group">
                        <h3>Bộ lọc thú cưng</h3>
                    </div>
                    <div class="form-group">
                        <label>Loài</label>
                        <select name="species" onchange="this.form.submit()">
                            <option value="">Tất cả</option>
                            <c:forEach var="s" items="${listSpecies}">
                                <option value="${s}" ${param.species == s ? 'selected' : ''}>${s}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Giống</label>
                        <select name="breed" onchange="this.form.submit()">
                            <option value="">Tất cả</option>
                            <c:forEach var="b" items="${listBreedBySpecies}">
<<<<<<< HEAD
                                 <option value="${b}" ${param.breed == b ? 'selected' : ''}>${b}</option>
=======
                                 <option value="${b.breedId}" ${param.breed == b.breedId ? 'selected' : ''}>${b.breedName}</option>
>>>>>>> nam
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Giới tính</label>
                        <select name="gender" onchange="this.form.submit()">
                            <option value="">Tất cả</option>
                            <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Đực</option>
                            <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Cái</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Màu</label>
                        <select name="color" onchange="this.form.submit()">
                            <option value="">Tất cả</option>
                            <c:forEach var="c" items="${listColor}">
                                <option value="${c}" ${param.color == c ? 'selected' : ''}>${c}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Xuất xứ</label>
                        <select name="origin" onchange="this.form.submit()">
                            <option value="">Tất cả</option>
                            <c:forEach var="o" items="${listOrigin}">
                                <option value="${o}" ${param.origin == o ? 'selected' : ''}>${o}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Khoảng giá</label>
                        <select name="priceRange" onchange="this.form.submit()">
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
                        <label>Ngày sinh từ:</label>
                        <input type="date" name="dobFrom" value="${param.dobFrom}" onchange="this.form.submit()" />
                    </div>
                    <div class="form-group">
                        <label>Đến ngày:</label>
                        <input type="date" name="dobTo" value="${param.dobTo}" onchange="this.form.submit()" />
                    </div>
                    <div class="form-group">
                        <label>Tiêm vắc xin</label>
                        <select name="vaccination" onchange="this.form.submit()">
                            <option value="">Tất cả</option>
                            <c:forEach var="v" items="${listVaccine}">
                                <option value="${v}" ${param.vaccination == v ? 'selected' : ''}>${v}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <div class="loc">
                            <a href="listshoppet" class="reset-button">Reset bộ lọc</a>
                        </div>
                    </div>
                </form>
            </aside>

            <main class="product-container">
                <div class="sort-container">
                    <label for="sort">Sắp xếp theo:</label>
                    <div class="custom-select">
                        <form action="listshoppet" method="get">

                            <input type="hidden" name="species" value="${param.species}" />
                            <input type="hidden" name="breed" value="${breed}" />
                            <input type="hidden" name="search" value="${param.search}" />
                            <input type="hidden" name="gender" value="${param.gender}" />
                            <input type="hidden" name="color" value="${param.color}" />
                            <input type="hidden" name="origin" value="${param.origin}" />
                            <input type="hidden" name="dobFrom" value="${param.dobFrom}" />
                            <input type="hidden" name="dobTo" value="${param.dobTo}" />
                            <input type="hidden" name="priceRange" value="${param.priceRange}" />
                            <input type="hidden" name="vaccination" value="${param.vaccination}" />
                            <select name="sortpet" onchange="this.form.submit()">
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
                                        <c:when test="${not empty x.getImages()}">
                                            <img src="${x.getFirstImage()}" width="200" height="200"
                                                 alt="${x.petName}"
                                                 style="object-fit: cover; border-radius: 8px;" />
                                        </c:when>
                                        <c:otherwise>
                                            <img src="images/default-pet.png" width="200" height="200"
                                                 alt="default"
                                                 style="object-fit: cover; border-radius: 8px;" />
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
                                    <fmt:formatNumber value="${x.petPrice}"
                                                      type="currency"
                                                      currencySymbol="₫"
                                                      groupingUsed="true" />
                                </p>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="pagination">
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <a href="listshoppet?page=${i}&breed=${param.breed}&species=${param.species}&search=${param.search}&priceRange=${param.priceRange}&sortpet=${param.sortpet}&gender=${param.gender}&color=${param.color}&origin=${param.origin}&dobFrom=${param.dobFrom}&dobTo=${param.dobTo}&vaccination=${param.vaccination}"
                           class="${i == currentPage ? 'active' : ''}">
                            ${i}
                        </a>
                    </c:forEach>
                </div>
            </main>
        </div>

        <div class="about-section">
            <div class="about-column newsletter-column">
                <h3>Shop</h3>
                <ul>
                    <li><a href="">Dành Cho Chó</a></li>
                    <li><a href="">Dành Cho Mèo</a></li>
                    <li><a href="">Dành Cho Chuột</a></li>
                </ul>


                <div class="newsletter-block">
                    <h3>Đăng Ký Nhận Tin</h3>
                    <p>Cập nhật thông tin về thú cưng và ưu đãi hấp dẫn</p>
                    <div class="newsletter-form">
                        <form action="" method="post">
                            <div class="newsletter-input-container">
                                <input type="email" name="email" placeholder="Email của bạn" required>
                                <button type="submit"><i class="fas fa-paper-plane"></i></button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>

            <div class="about-column">
                <h3>PETFPT Shop</h3>
                <ul>
                    <li><a href="">Giới Thiệu</a></li>
                    <li><a href="">Chính sách</a></li>
                    <li><a href="">Phương Thức Thanh Toán</a></li>
                    <li><a href="">Điều Khoản Sử Dụng</a></li>                    
                </ul>
            </div>

            <div class="about-column">
                <h3>Liên Hệ</h3>
                <p class="contactpet">PETFPT Shop</p>
                <p><i class="fas fa-phone"></i>0767676770</p>
                <p><i class="fas fa-map-marker-alt"></i>Khu Công nghệ cao Hòa Lạc <br>
                    Km29 Đại lộ Thăng Long, H. Thạch Thất, TP. Hà Nội</p>
                <p><i class="fas fa-envelope email"></i>
                    <a href="mailto:hoangnhhe181051@fpt.edu.vn">petfpt@gmail.com</a>
                </p>
                <div class="social-container">
                    <a href="https://www.facebook.com/petfptshop" target="_blank" class="social-icon">
                        <i class="fab fa-facebook fa-2x"></i>
                    </a>
                </div>
            </div>
        </div>

        <footer style="background: linear-gradient(to bottom, #fff3e6, #ffe5cc); color: black">
            © 2025 PETFPT - Đồng hành cùng bạn và thú cưng mỗi ngày!
        </footer>

    </body>
</html>
