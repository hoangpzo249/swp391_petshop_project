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

                    <div class="summary-row">
                        <div>Tạm tính:</div>
                        <div><fmt:formatNumber value="${total}" type="currency" currencySymbol="₫" groupingUsed="true" /></div>
                    </div>
                    <div class="summary-row">
                        <div>Giảm giá:</div>
                        <div>- 0₫</div>
                    </div>
                    <div class="summary-row total">
                        <div>Tổng cộng:</div>
                        <div><fmt:formatNumber value="${total}" type="currency" currencySymbol="₫" groupingUsed="true" /></div>
                    </div>
                    <input type="hidden" name="totalprice" value="${total}" />

                    <label class="terms-label">
                        <input type="checkbox" required />
                        Tôi đồng ý với <a href="footer_termofuse.jsp" target="_blank">điều khoản</a>.
                    </label>

                    <button type="submit" class="checkout-btn">Tiến hành thanh toán</button>
                </form>
            </div>
        </div>

        <div id="toast" class="toast"></div>

    </body>
</html>
