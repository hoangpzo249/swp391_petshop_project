

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Giỏ hàng - PETFPT Shop</title>
        <link rel="icon" type="png" href="images/logo_banner/logo1.png">
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link href="css/head_about.css" rel="stylesheet" type="text/css"/>
        <script src="js/scroll_chat.js" type="text/javascript"></script>
        <link href="css/header_footer.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <script src="js/cart_selection.js?v=19" type="text/javascript"></script>
        <link href="css/cart.css?v=9" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">




    </head>

    <body>



        <div class="header">
            <div class="header1">
                <!-- Logo -->
                <div>
                    <a href="listmainmenu">
                        <img src="images/logo2.png" alt="logo"/>
                    </a>
                </div>


                <form action="listshoppet" method="get">
                    <div class="search">

                        <input type="text" name="search" value="${param.search}" placeholder="Tìm kiếm thú cưng ..." style="border: none" />
                        <button type="submit" class="search-button">
                            <img src="images/support button/search.png" width="20" height="20" alt="search" />
                        </button>
                    </div>
                </form>


                <div class="accountcard">
                    <c:choose>
                        <c:when test="${not empty sessionScope.account}">
                            <div class="account-dropdown">
                                <a href="#" class="account-trigger">
                                    <img src="images/account.png" width="50" height="50" alt="account"/>
                                    <p class="username">Tài khoản</p>
                                </a>
                                <div class="dropdown-content">
                                    <c:if test="${sessionScope.account.accRole eq 'Admin'}">
                                        <a href="admin.jsp" class="dropdown-item">
                                            <i class="fas fa-user-cog"></i> 
                                            <span>Admin Panel</span>
                                        </a>
                                    </c:if>
                                    <a href="account_profile_user.jsp" class="dropdown-item">
                                        <i class="fas fa-user"></i> 
                                        <span>Thông tin cá nhân</span>
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
                                <a href="account_login.jsp">
                                    <img src="images/account.png" width="50" height="50" alt="account"/>
                                    <p class="logintext">Đăng nhập</p>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <div class="card">
                        <a href="displaycart">
                            <img src="images/card.png" width="50" height="50" alt="card"/>
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
                    <li><a href="listshoppet?species=Dog&sort=popular">Chó Cưng</a></li>
                    <li><a href="listshoppet?species=Cat&sort=popular">Mèo Cưng</a></li>
                    <li><a href="menu_about.jsp">Giới Thiệu</a></li>
                    <li><a href="menu_contact.jsp">Liên Hệ</a></li>
                        <c:if test="${not empty sessionScope.account and sessionScope.account.accRole eq 'Admin'}">
                        <li><a href="admin.jsp">Admin Panel</a></li>
                        </c:if>
                </ul>
            </nav>
        </div>





        <div class="body-bg">
            <div class="cart-container">
                <h2 class="text">Giỏ hàng của bạn</h2>






                <table class="cart-table">
                    <thead>
                        <tr class="cart-header">
                            <th>Chọn</th> 
                            <th>Sản phẩm</th>
                            <th>Số lượng</th>
                            <th>Tạm tính</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>




                        <c:forEach items="${pets}" var="pet">
                            <tr class="cart-item">
                                <td>
                                    <input type="checkbox" class="item-check" data-price="${pet.petPrice}" 
                                           form="confirmForm" name="selectedPets" value="${pet.petId}" />
                                </td>


                                <td class="item-info">
                                    <a href="displaypet?id=${pet.petId}" class="item-link">
                                        <img src="${pet.petImageBase64}" alt="${pet.petName}" class="cart-img" />
                                        <div>
                                            <div class="item-title">${pet.petName}</div>
                                            <div class="item-price">Giá: <fmt:formatNumber value="${pet.petPrice}" type="currency" currencySymbol="₫" groupingUsed="true" /></div>
                                        </div>
                                    </a>
                                </td>
                                <td>
                                    <input type="number" name="quantity" value="1" class="qty-input" readonly="readonly" />
                                </td>
                                <td> <fmt:formatNumber value="${pet.petPrice}" type="currency" currencySymbol="₫" groupingUsed="true" /></td>





                                <td class="item-action">

                                    <form action="deletefromcart" method="GET" style="display:inline;">
                                        <input type="hidden" name="id" value="${pet.petId}" />

                                        <button class="btn-delete" title="Xóa sản phẩm">
                                            <i class="fa-solid fa-trash"></i>
                                        </button>
                                    </form>
                                </td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>


                <form id="confirmForm" action="checkout" method="POST">

                    <div class="summary-row">
                        <div>Tạm tính:</div>
                        <div id="subtotal-display">0₫</div>
                    </div>



                    <c:if test="${empty sessionScope.account or sessionScope.account.accRole eq 'Customer'}">
                        <button type="submit" class="checkout-btn">Xác nhận giỏ hàng</button>
                    </c:if>
                </form>


                <!-- about -->
                <div class="about-section">
                    <div class="about-column">
                        <h3>Shop</h3>
                        <ul>
                            <li><a href="dog.jsp">Dành Cho Chó</a></li>
                            <li><a href="cat.jsp">Dành Cho Mèo</a></li>
                            <li><a href="petaccessory.jsp">Phụ kiện cho Chó & Mèo</a></li>
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
                            <img src="images/scroll_chat/chat.png" alt=""/>
                        </div>

                        <div id="zalo-button" class="zalo-button">
                            <a href="https://zalo.me/your-zalo-link" target="_blank">
                                <img src="images/scroll_chat/zalo.jpg" alt=""/>
                            </a>
                        </div>
                    </div>
                </div>
                <div id="toast" class="toast-message"></div> 

                <script>
                    function showToast(message, type = "error") {
                        const toast = document.getElementById('toast');
                        toast.textContent = message;
                        toast.className = `toast-message ${type} show`;

                        setTimeout(() => {
                            toast.classList.remove("show");
                        }, 3500);
                    }


                    <c:if test="${not empty sessionScope.cartMessage}">
                    showToast('${sessionScope.cartMessage}', 'error');
                    </c:if>
                </script>
                <c:if test="${not empty sessionScope.cartMessage}">
                    <c:remove var="cartMessage" scope="session"/>
                </c:if>



                <footer>
                    © 2025 PETFPT - Đồng hành cùng bạn và thú cưng mỗi ngày!
                </footer>
               

                

                </body>
                </html>


































