

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
        <script src="js/cart_selection.js?v=10" type="text/javascript"></script>
        <link href="css/cart.css?v=3" rel="stylesheet" type="text/css"/>
        <script src="js/cart_tick.js" type="text/javascript"></script>

    </head>

    <body>



        <!-- header -->
        <div class="header">
            <div class="header1">
                <div>
                    <a href="listmainmenu">
                        <img src="images/logo_banner/logo.png" alt=""/>
                    </a>
                </div>
                <form action="listshoppet">
                    <div class="search">
                        <input type="text" name="search" value="${sessionScope.search}" placeholder="Tìm kiếm thú cưng ..." style="border: none;"/>
                        <button type="submit" class="search-button">
                            <img src="images/search.png" width="512" height="512" alt="search" />
                        </button>
                    </div>
                </form>

                <div class="accountcard">
                    <div class="account">
                        <a href="${not empty sessionScope.account ? 'account_profile_user.jsp' : 'account_login.jsp'}">
                            <img src="images/account.png" width="800" height="800" alt="account"/>
                            <p class="logintext">Tài Khoản</p>
                        </a>
                    </div>
                    <div class="card">
                        <a href=displaycart >
                            <img src="images/card.png" width="1859" height="1573" alt="card"/>
                            <p class="cardtext">
                                Giỏ Hàng
                                <span id="cart-count" class="cartcount">${sessionScope.cartcount}</span>
                            </p>
                        </a>
                    </div>
                </div>

            </div>
            <div>
                <nav>
                    <ul class="menu">
                        <li><a href="listmainmenu">Trang Chủ</a></li>
                        <li><a href="listshoppet?species=Dog&sort=popular&giapet1=&giapet2=&breed=&search=">Chó Cưng</a></li>
                        <li><a href="listshoppet?species=Cat&sort=popular&giapet1=&giapet2=&breed=&search=">Mèo Cưng</a></li>

                        <li><a href="menu_about.jsp">Giới Thiệu</a></li>
                        <li><a href="menu_contact.jsp">Liên Hệ</a></li>
                            <c:choose>
                                <c:when test="${not empty sessionScope.account and sessionScope.account.accRole eq 'Admin'}">
                                <li><a href="admin.jsp">Admin Panel</a></li>
                                </c:when>
                                <c:otherwise>
                                </c:otherwise>
                            </c:choose>

                    </ul>
                </nav>
            </div>
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
                                           form="checkoutForm" name="selectedPets" value="${pet.petId}" />
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


                <form id="checkoutForm" action="ajaxServlet" method="POST">
                    <div class="address-info">
                        <h3>Thông tin nhận hàng</h3>
                        <c:choose>
                            <c:when test="${not empty sessionScope.account}">

                                <p><strong>Tên đăng nhập:</strong> ${name}</p>
                                <input type="hidden" name="guestName" value="${name}" />

                                <p><strong>Số điện thoại:</strong> ${phone}</p>
                                <input type="hidden" name="guestPhone" value="${phone}" />

                                <p><strong>Địa chỉ:</strong> ${address}</p>
                                <input type="hidden" name="guestAddress" value="${address}" />
                                <p><strong>Email:</strong> ${email}</p>
                                <input type="hidden" name="email" value="${email}" />

                                <div class="new-address-alert">
                                    <p>
                                        <i class="fas fa-exclamation-circle icon-alert"></i>
                                        <strong>Địa chỉ không đúng?</strong> Vui lòng cập nhật trong 
                                        <a href="account_profile_user.jsp">Tài khoản của bạn</a>
                                    </p>
                                </div>
                            </c:when>

                            <c:otherwise>
                                <div class="form-group">


                                    <label for="guestName">Tên người nhận:</label>
                                    <input type="text" id="guestName" name="guestName" required />
                                    <span class="error" id="nameError"></span>
                                </div>
                                <div class="form-group">

                                    <label for="guestPhone">Số điện thoại:</label>
                                    <input type="text" id="guestPhone" name="guestPhone" required />
                                    <span class="error" id="phoneError"></span>
                                </div>
                                <div class="form-group">
                                    <label for="guestAddress">Địa chỉ:</label>
                                    <input type="text" id="guestAddress" name="guestAddress" required />
                                </div>

                                <div class="form-group">
                                    <label for="email">Email:</label>
                                    <input type="text" id="email" name="email" required />
                                </div>

                            </c:otherwise>
                        </c:choose>
                    </div>


                    <h3>Phương thức thanh toán</h3>
                    <label><input type="radio" name="payment-method" value="bank" checked /> Chuyển khoản ngân hàng</label>
                    <label><input type="radio" name="payment-method" value="cod" /> Trả tiền mặt khi nhận hàng</label>
                    <div class="summary-row">
                        <div>Tạm tính:</div>
                        <div id="subtotal-display">0₫</div>
                    </div>
                    <div class="summary-row">
                        <div>Giảm giá:</div>
                        <div id="discount-display">- 0₫</div>
                    </div>
                    <div class="summary-row total">
                        <div>Tổng cộng:</div>
                        <div id="total-display">0₫</div>
                    </div>
                    <input type="hidden" name="totalprice" id="totalInput" />
                   
                    <label class="terms-label">
                        <input type="checkbox" required />
                        Tôi đồng ý với <a href="footer_termofuse.jsp" target="_blank">điều khoản</a>.
                    </label>

                    <button type="submit" class="checkout-btn">Tiến hành thanh toán</button>
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


                <footer>
                    © 2025 PETFPT - Đồng hành cùng bạn và thú cưng mỗi ngày!
                </footer>

                </body>
                </html>

































