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
        <link href="css/checkout.css" rel="stylesheet" type="text/css"/>
        <script src="js/validate_input.js?v=5" type="text/javascript"></script>

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
                <h2 class="text">Xác nhận đơn hàng</h2>

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

                <form id="checkoutForm" action="ajaxServlet" method="POST">
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
                                    <input type="text" id="guestName" name="guestName" required />
                                </div>
                                <div class="form-group">
                                    <label for="guestPhone">Số điện thoại:</label>
                                    <input type="text" id="guestPhone" name="guestPhone" required />
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
                        <%
                   double total = request.getAttribute("total") != null ? (double) request.getAttribute("total") : 0.0;
                   double discountAmount = request.getAttribute("discountAmount") != null ? (double) request.getAttribute("discountAmount") : 0.0;
                   double finalTotal = total - discountAmount;
                        %>
                    <div class="summary-row">
                        <div>Tạm tính:</div>
                        <div><fmt:formatNumber value="${total}" type="currency" currencySymbol="₫" groupingUsed="true" /></div>
                    </div>
                    <div class="summary-row">
                        <div>Giảm giá:</div>
                        <div>
                            - <fmt:formatNumber value="<%= discountAmount %>" type="currency" currencySymbol="₫" groupingUsed="true" />
                        </div>
                    </div>
                    <div class="summary-row total">
                        <div>Tổng cộng:</div>
                        <fmt:formatNumber value="<%= finalTotal %>" type="currency" currencySymbol="₫" groupingUsed="true" />
                    </div>
                    <input type="hidden" name="totalprice" value="<%= finalTotal %>" />
                    <input type="hidden" name="discountCode" value="${discountCode}" />


                    <label class="terms-label">
                        <input type="checkbox" required />
                        Tôi đồng ý với <a href="footer_termofuse.jsp" target="_blank">điều khoản</a>.
                    </label>

                    <button type="submit" class="checkout-btn">Tiến hành thanh toán</button>
                </form>
                <form action="check-discount" method="POST" style="margin-bottom: 10px;">
                    <input type="text" id="discountCode" name="discountCode"
                           value="${discountCode}"
                           placeholder="Nhập mã giảm giá" />


                    <c:forEach var="pet" items="${selectedPets}">
                        <input type="hidden" name="selectedPets" value="${pet.petId}" />
                    </c:forEach>

                    <input type="hidden" name="guestName" value="${param.guestName}" />
                    <input type="hidden" name="guestPhone" value="${param.guestPhone}" />
                    <input type="hidden" name="guestAddress" value="${param.guestAddress}" />
                    <input type="hidden" name="email" value="${param.email}" />

                    <button type="submit">Áp dụng</button>
                </form>
                <c:if test="${not empty discountMessage}">
                    <div style="margin-top: 10px; color: ${sessionScope.discountValid ? 'green' : 'red'};">
                        ${discountMessage}

                    </div>
                </c:if>


            </div>
        </div>

        <div id="toast" class="toast"></div>

    </body>
</html>
