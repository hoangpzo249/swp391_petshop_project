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
        <link href="css/ai_chat.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link href="css/checkout.css?v=9" rel="stylesheet" type="text/css"/>
        <link href="css/term.css?v=5" rel="stylesheet" type="text/css"/>
        <script src="js/validate_input.js?v=12" type="text/javascript"></script>


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
                    <div class="search">

                        <input type="text" name="search" value="${param.search}" placeholder="Tìm kiếm thú cưng ..." style="border: none" />
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
                                            <a href="displayrevenuestatistic" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Manager</span>
                                            </a>
                                        </c:when>

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Seller'}">
                                            <a href="displaysalesstatistic" class="dropdown-item">
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

                                        <c:otherwise>
                                        </c:otherwise>
                                    </c:choose>

                                    <a href="profile" class="dropdown-item">
                                        <i class="fas fa-user"></i> 
                                        <span>Thông tin cá nhân</span>
                                    </a>
                                    <a href="orders?status=Pending" class="dropdown-item">
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
                    <li><a href="homepage">Trang Chủ</a></li>
                    <li><a href="listshoppet">Thú Cưng</a></li>
                    <li><a href="menu_about.jsp">Giới Thiệu</a></li>
                    <li><a href="menu_contact.jsp">Liên Hệ</a></li>


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
                            <!--<th>Số lượng</th>-->
                            <th>Tạm tính</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${selectedPets}" var="pet">
                            <tr class="cart-item">
                                <td class="item-info">
                                    <a href="displaypet?id=${pet.petId}" class="item-link">
                                        <img src="${pet.getFirstImage()}" alt="${pet.petName}" class="cart-img" />
                                        <div>
                                            <div class="item-title">${pet.petName}</div>
                                            <div class="item-price">
                                                Giá:  <fmt:formatNumber value="${pet.petPrice}"  groupingUsed="true"/>đ
                                            </div>
                                        </div>
                                    </a>
                                </td>
                                <!--                                <td>
                                                                    <input type="number" value="1" class="qty-input" readonly="readonly" />
                                                                </td>-->
                                <td>
                                    <fmt:formatNumber value="${pet.petPrice}"  groupingUsed="true"/>đ
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
                            <c:when test="${not empty sessionScope.userAccount}">
                                <p><strong>Tên:</strong > ${name}</p>
                                <input type="hidden" id="guestName" name="guestName" value="${name}" />

                                <p><strong>Số điện thoại:</strong> ${phone}</p>
                                <input type="hidden" id="guestPhone" name="guestPhone" value="${phone}" />

                                <p><strong>Địa chỉ:</strong> ${address}</p>
                                <input type="hidden" id="guestAddress" name="guestAddress" value="${address}" />

                                <p><strong>Email:</strong> ${email}</p>
                                <input type="hidden" id="email" name="email" value="${email}" />

                                <div class="new-address-alert">
                                    <p>
                                        <i class="fas fa-exclamation-circle icon-alert"></i>
                                        <strong>Thông tin không đúng?</strong> Vui lòng cập nhật trong 
                                        <a href="profile" class="terms-link highlight-red-bold">tài khoản của bạn</a>

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
                    <label>
                        <input type="radio" name="payment-method" value="bank"
                               <c:if test="${paymentMethod == 'bank'}">checked</c:if> /> Chuyển khoản ngân hàng
                        </label>
                        <label>
                            <input type="radio" name="payment-method" value="cod"
                            <c:if test="${paymentMethod == 'cod'}">checked</c:if> /> Trả tiền mặt khi nhận hàng
                        </label>
                
                <div class="summary-row">
                    <div>Tạm tính:</div>
                <fmt:formatNumber value="${total}"  groupingUsed="true"/>đ
            </div>
            <div class="summary-row">
                <div>Giảm giá:</div>
                <div>
                    - <fmt:formatNumber value="${discountAmount}"  groupingUsed="true" />đ
                </div>
            </div>
            <div class="summary-row total">
                <div>Tổng cộng:</div>
                <fmt:formatNumber value="${finalTotal}"  groupingUsed="true"/>đ
            </div>




            <label class="terms-label">
                <input type="checkbox" name="agreedTerms" value="true" ${agreedTerms == 'true' ? 'checked' : ''} required />
                Tôi đồng ý với <a href="#" onclick="openTerms(); return false;" class="terms-link">điều khoản</a>
            </label>


            <input type="hidden" name="amount" value="${finalTotal != null ? finalTotal : total}" />
            <input type="hidden" name="discountAmount" value="${discountAmount != null ? discountAmount : 0}" />

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



<div id="terms" class="modal-overlay" style="display:none;">
    <div class="modal-box">
        <h3>Điều khoản mua bán thú cưng</h3>
        <div class="modal-content">
            <ul>
                <li><strong>1. Thông tin thú cưng:</strong>
                    <ul>
                        <li>Cửa hàng cam kết thú cưng đúng giống, độ tuổi, giới tính, màu sắc, xuất xứ như mô tả.</li>
                        <li>Trường hợp thú cưng giao không đúng mô tả sẽ được <strong>đổi lại miễn phí</strong> trong vòng <strong>48h</strong> nếu:
                            <ul>
                                <li>Khách hàng giữ nguyên tình trạng thú.</li>
                                <li>Có video mở hộp hoặc ảnh nhận hàng trong vòng 12 giờ.</li>
                            </ul>
                        </li>
                    </ul>
                </li>

                <li><strong>2. Chính sách bảo hành sức khỏe (trong 7 ngày):</strong>
                    <ul>
                        <li><strong>Áp dụng với các bệnh sau:</strong>
                            <ul>
                                <li><u>Chó:</u> Parvovirus, Care (Distemper), Coronavirus, viêm ruột truyền nhiễm, bệnh dại (nếu tiêm ngừa thất bại).</li>
                                <li><u>Mèo:</u> Feline panleukopenia, Feline calicivirus, Feline herpesvirus, FIP (Feline Infectious Peritonitis).</li>
                            </ul>
                        </li>
                        <li><strong>Điều kiện bảo hành:</strong>
                            <ul>
                                <li>Không áp dụng nếu thú cưng bị nhiễm do môi trường mới hoặc từ thú khác của khách hàng.</li>
                                <li>Thú cưng phải được cách ly và chăm sóc theo hướng dẫn.</li>
                                <li>Phải cung cấp video triệu chứng, xét nghiệm từ phòng khám được chỉ định hoặc chấp nhận bởi cửa hàng.</li>
                            </ul>
                        </li>
                        <li><strong>Không bảo hành trong các trường hợp:</strong>
                            <ul>
                                <li>Khách tự ý tiêm thuốc, tự ý điều trị tại nhà không có chỉ định.</li>
                                <li>Thú cưng bị chết do tai nạn, ngộ độc, bị đánh, bỏ đói, thời tiết khắc nghiệt, sốc do vận chuyển hoặc chăm sóc sai cách.</li>
                                <li>Khách không thông báo trong vòng 24h sau khi có biểu hiện lạ.</li>
                            </ul>
                        </li>
                    </ul>
                </li>

                <li><strong>3. Chính sách đổi trả:</strong>
                    <ul>
                        <li><strong>Chỉ áp dụng nếu:</strong>
                            <ul>
                                <li>Giao sai giống, sai giới tính, sai màu, sai hình ảnh.</li>
                                <li>Bị bệnh thuộc diện bảo hành trong 7 ngày.</li>
                            </ul>
                        </li>
                        <li><strong>Không áp dụng trong các trường hợp:</strong>
                            <ul>
                                <li>Khách thay đổi ý định, không thích nữa.</li>
                                <li>Lý do chủ quan như thú không thân thiện, quá nghịch, không hợp tuổi phong thủy, sủa nhiều, mèo không kêu,...</li>
                                <li>Thú cưng đã được đưa ra khỏi nhà, mang đi tiêm, tắm hoặc tiếp xúc với vật nuôi khác.</li>
                            </ul>
                        </li>
                    </ul>
                </li>

                <li><strong>4. Thanh toán:</strong>
                    <ul>
                        <li>Khách hàng có thể thanh toán qua: chuyển khoản, tiền mặt, quẹt thẻ, ví điện tử.</li>
                        <li>Đơn hàng chỉ được xác nhận khi thanh toán hoàn tất.</li>
                        <li>Cửa hàng không chịu trách nhiệm nếu khách tự ý chuyển khoản không đúng thông tin.</li>
                    </ul>
                </li>

                <li><strong>5. Giao nhận & rủi ro:</strong>
                    <ul>
                        <li>Giao thú tận nơi tại khu vực nội thành Hà Nội/HCM: có thể miễn phí hoặc tính phí tùy quận.</li>
                        <li>Giao tỉnh qua xe khách/xe tải/máy bay: khách chịu phí vận chuyển và các rủi ro liên quan (nhiệt độ, trễ xe...).</li>
                        <li>Khuyến nghị khách yêu cầu quay video giao nhận nếu không mở trực tiếp tại nơi nhận.</li>
                    </ul>
                </li>

                <li><strong>6. Chế độ chăm sóc sau mua:</strong>
                    <ul>
                        <li>Cửa hàng cung cấp hướng dẫn chăm sóc, ăn uống, lịch tiêm chi tiết.</li>
                        <li>Khách hàng cam kết đọc và làm đúng hướng dẫn.</li>
                        <li>Nếu thú cưng phát bệnh do sai cách chăm (ví dụ: cho ăn xương, sữa tươi, tắm khi mới về, thả thú ra ngoài...) thì cửa hàng không chịu trách nhiệm.</li>
                    </ul>
                </li>

                <li><strong>7. Quyền riêng tư:</strong>
                    <ul>
                        <li>Thông tin khách được bảo mật tuyệt đối.</li>
                        <li>Cửa hàng chỉ sử dụng thông tin để xác nhận đơn hàng, chăm sóc khách, hỗ trợ bảo hành.</li>
                    </ul>
                </li>

                <li><strong>8. Miễn trừ trách nhiệm:</strong>
                    <ul>
                        <li>Cửa hàng không chịu trách nhiệm với các vấn đề xảy ra sau thời gian bảo hành.</li>
                        <li>Không bảo hành với thú cưng bị stress do thay đổi môi trường đột ngột (chuyển nhà, trẻ con đùa giỡn quá mức...).</li>
                        <li>Không chịu trách nhiệm nếu thú bệnh do nhiễm virus từ thú cưng khác của khách hoặc bên ngoài.</li>
                    </ul>
                </li>

                <li><strong>9. Cam kết của khách hàng:</strong>
                    <ul>
                        <li>Không bán lại, bỏ rơi, hoặc tặng thú cưng cho bên thứ ba trong 07 ngày bảo hành mà không thông báo.</li>
                        <li>Cung cấp video, ảnh minh chứng khi yêu cầu hỗ trợ.</li>
                        <li>Hợp tác trong quá trình xử lý bảo hành (đưa thú đến bác sĩ, cách ly, tuân thủ phác đồ điều trị…).</li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="modal-footer">
            <button onclick="closeTerms()">Đóng</button>
        </div>
    </div>
</div>
<button class="chat-toggle-button" id="chatToggleButton" aria-label="Toggle AI Assistant Chat">
    <i class="fas fa-comments"></i>
</button>

<div class="chatbox-container" id="chatboxContainer">
    <div class="chatbox-header">
        <h3>PetFPT</h3>
        <button class="chatbox-close-button" id="chatboxCloseButton" aria-label="Close Chat">×</button>
    </div>
    <div class="chatbox-messages" id="chatboxMessages">
    </div>
    <div class="chatbox-input-area">
        <form id="chatForm">
            <input type="text" id="chatInput" placeholder="Hỏi tôi bất kỳ điều gì..." autocomplete="off" required>
            <button type="submit" id="chatSendButton" aria-label="Send Message"><i class="fas fa-paper-plane"></i></button>
        </form>
    </div>
</div>
<script src="js/ai_chat.js"></script>
<script>
                function openTerms() {
                    document.getElementById("terms").style.display = "flex";
                }

                function closeTerms() {
                    document.getElementById("terms").style.display = "none";
                }
</script>


</body>
</html>
