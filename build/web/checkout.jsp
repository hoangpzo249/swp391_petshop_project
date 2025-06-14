<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Xác nhận đơn hàng - PETFPT Shop</title>
        <link rel="icon" type="image/png" href="images/logo_banner/logo1.png">
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

        <link href="css/head_about.css" rel="stylesheet" type="text/css"/>
        <link href="css/header_footer.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link href="css/checkout.css?v=7" rel="stylesheet" type="text/css"/>
        <script src="js/validate_input.js?v=9" type="text/javascript"></script>


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
                <h2 class="text">Xác nhận đơn hàng</h2>
                <c:if test="${not empty warningMessage}">
                    <div class="warning-message animate-shake">
                        <i class="fas fa-exclamation-triangle"></i>
                        ${warningMessage}
                    </div>
                </c:if>


                <table class="cart-table">
                    <thead>
                        <tr class="cart-header">
                            <th>Sản phẩm</th>
                            <th>Số lượng</th>
                            <th>Tạm tính</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${selectedPets}" var="pet">
                            <tr class="cart-item">
                                <td class="item-info">
                                    <a href="displaypet?id=${pet.petId}" class="item-link">
                                        <img src="${pet.petImageBase64}" alt="${pet.petName}" class="cart-img" />
                                        <div>
                                            <div class="item-title">${pet.petName}</div>
                                            <div class="item-price">
                                                Giá: <fmt:formatNumber value="${pet.petPrice}" type="currency" currencySymbol="₫" groupingUsed="true" />
                                            </div>
                                        </div>
                                    </a>
                                </td>
                                <td>
                                    <input type="number" value="1" class="qty-input" readonly="readonly" />
                                </td>
                                <td>
                                    <fmt:formatNumber value="${pet.petPrice}" type="currency" currencySymbol="₫" groupingUsed="true" />
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <form id="checkoutForm" action="check-discount" method="POST" style="margin-bottom: 10px;">
                    <c:forEach var="pet" items="${selectedPets}">
                        <input type="hidden" name="selectedPets" value="${pet.petId}" />
                    </c:forEach>



                    <div class="address-info">
                        <h3>Thông tin nhận hàng</h3>
                        <c:choose>
                            <c:when test="${not empty sessionScope.account}">
                                <p><strong>Tên:</strong> ${name}</p>
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
                                    <input type="text" id="guestName" name="guestName" value="${guestName}" required />
                                </div>
                                <div class="form-group">
                                    <label for="guestPhone">Số điện thoại:</label>
                                    <input type="text" id="guestPhone" name="guestPhone" value="${guestPhone}" required />
                                </div>
                                <div class="form-group">
                                    <label for="guestAddress">Địa chỉ:</label>
                                    <input type="text" id="guestAddress" name="guestAddress" value="${guestAddress}" required />
                                </div>
                                <div class="form-group">
                                    <label for="email">Email:</label>
                                    <input type="text" id="email" name="email" value="${email}" required />
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <h3>Phương thức thanh toán</h3>
                    <label><input type="radio" name="payment-method" value="bank" checked /> Chuyển khoản ngân hàng</label>
                    <label><input type="radio" name="payment-method" value="cod" /> Trả tiền mặt khi nhận hàng</label>

                    <div class="summary-row">
                        <div>Tạm tính:</div>
                        <div><fmt:formatNumber value="${total}" type="currency" currencySymbol="₫" groupingUsed="true" /></div>
                    </div>
                    <div class="summary-row">
                        <div>Giảm giá:</div>
                        <div>
                            - <fmt:formatNumber value="${discountAmount}" type="currency" currencySymbol="₫" groupingUsed="true" />
                        </div>
                    </div>
                    <div class="summary-row total">
                        <div>Tổng cộng:</div>
                        <fmt:formatNumber value="${finalTotal}" type="currency" currencySymbol="₫" groupingUsed="true" />
                    </div>
                    <input type="hidden" name="totalprice" value="${total}" />



                    <label class="terms-label">
                        <input type="checkbox" required />
                        Tôi đồng ý với <a href="footer_termofuse.jsp" target="_blank">điều khoản</a>.
                    </label>

                    <button type="submit" id="checkout" name="action" value="checkout" class="checkout-btn">Tiến hành thanh toán</button>

                    <div class="discount-section">
                        <label for="discountCode" class="discount-label">Nhập mã giảm giá:</label>
                        <div class="discount-input-wrapper">
                            <input type="text" id="discountCode" name="discountCode" class="discount-input" value="${discountCode}" placeholder="Nhập mã ưu đãi tại đây..." />
                            <button type="submit" id="discount" name="action" value="apply-discount" formnovalidate class="discount-button">
                                <i class="fas fa-gift"></i> Áp dụng
                            </button>
                        </div>
                        <p class="discount-note"><i class="fas fa-info-circle"></i> Mỗi đơn hàng chỉ được áp dụng <strong>01 mã giảm giá</strong></p>


                    </div>

                </form>
                <c:if test="${not empty discountMessage}">
                    <div class="discount-message ${discountValid ? 'success' : 'error'} animate-shake">
                        <i class="fas ${discountValid ? 'fa-check-circle' : 'fa-times-circle'}"></i>
                        ${discountMessage}
                    </div>
                </c:if>




            </div>
        </div>

        <div id="toast" class="toast"></div>
        <script>
            window.addEventListener("DOMContentLoaded", function () {
                const warning = document.querySelector('.warning-message');
                if (warning) {
                    setTimeout(() => {
                        warning.style.opacity = '0';
                        warning.style.transform = 'translateY(-10px)';
                        setTimeout(() => warning.remove(), 500); // remove sau animation
                    }, 4000); // 4s
                }
            });
        </script>


    </body>
</html>
