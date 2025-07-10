<%-- 
    Document   : profile_account_page
    Created on : 3 June 2025, 9:52:14 pm
    Author     : HuyHoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thông tin tài khoản</title>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link href="css/profile_account_page.css" rel="stylesheet" type="text/css"/>
        <link href="css/header_footer.css" rel="stylesheet" type="text/css"/>
        <link href="css/ai_chat.css" rel="stylesheet" type="text/css"/>

    </head>

    <body>
        <!-- header -->
        <div class="header">
            <div class="header1">
                <div>
                    <a href="homepage">
                        <img src="images/logo_banner/logo2.png" alt=""/>
                    </a>
                </div>
                <form action="listshoppet" method="get">

                    <input type="hidden" name="species" value="${param.species}" />
                    <input type="hidden" name="breed" value="${param.breed}" />
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
                                    <!-- Cố định kích thước hình ảnh -->
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
                                            <a href="displaybreed" class="dropdown-item">
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
                                            <a href="shipper_panel" class="dropdown-item">
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

                                    <c:choose>
                                        <c:when test="${sessionScope.userAccount.accRole eq 'Customer'}">
                                            <a href="orders?status=Pending" class="dropdown-item">
                                                <i class="fas fa-shopping-bag"></i> 
                                                <span>Đơn hàng đã mua</span>
                                            </a>
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>

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
                                    <img src="images/support button/account.png" width="50" height="50" alt="account"/>
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
            <div>
                <nav>
                    <ul class="menu">
                        <li><a href="homepage">Trang Chủ</a></li>
                        <li><a href="listshoppet">Thú Cưng</a></li>
                        <li><a href="">Giới Thiệu</a></li>
                        <li><a href="">Liên Hệ</a></li>
                    </ul>
                </nav>
            </div>
        </div>

        <c:if test="${not empty requestScope.updateSucess}">
            <div class="login-success-alert">
                ${requestScope.updateSucess}
            </div>
            <c:remove var="updateSucess" scope="session" />
        </c:if>

        <c:if test="${not empty errMess}">
            <div class="errMess">
                <p>${errMess}</p>
            </div>
            <c:remove var="errMess" scope="session" />
        </c:if>


        <div class="profile-container">
            <div class="profile-card">
                <div class="profile-header">
                    <h1 class="profile-title">Thông tin tài khoản</h1>
                    <p class="profile-subtitle">Quản lý thông tin cá nhân của bạn</p>
                </div>

                <div class="profile-content">
                    <div class="profile-sidebar">
                        <div class="current-info-card">
                            <div class="profile-avatar-container">
                                <div class="default-avatar">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.userAccount.displayAccImage()}">
                                            <img src="${sessionScope.userAccount.displayAccImage()}" alt="Avatar" width="150" height="150" class="avatar-img"/>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-user"></i>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>

                            <h3 class="current-info-title">Thông tin hiện tại</h3>

                            <div class="info-item">
                                <i class="fas fa-envelope"></i>
                                <span class="info-label">Email</span>
                                <span class="info-value" id="current-username">${sessionScope.userAccount.accEmail}</span>
                            </div>

                            <div class="info-item">
                                <i class="fas fa-user"></i>
                                <span class="info-label">Tên đăng nhập</span>
                                <span class="info-value" id="current-username">${sessionScope.userAccount.accUsername}</span>
                            </div>

                            <div class="info-item">
                                <i class="fas fa-user-tag"></i>
                                <span class="info-label">Họ</span>
                                <span class="info-value" id="current-firstname">${sessionScope.userAccount.accFname}</span>
                            </div>

                            <div class="info-item">
                                <i class="fas fa-signature"></i>
                                <span class="info-label">Tên</span>
                                <span class="info-value" id="current-lastname">${sessionScope.userAccount.accLname}</span>
                            </div>

                            <div class="info-item">
                                <i class="fas fa-birthday-cake"></i>
                                <span class="info-label">Ngày sinh</span>
                                <span class="info-value" id="current-dob">${sessionScope.userAccount.accDob}</span>
                            </div>

                            <div class="info-item">
                                <i class="fas fa-phone"></i>
                                <span class="info-label">Số điện thoại</span>
                                <span class="info-value" id="current-phone">${sessionScope.userAccount.accPhoneNumber}</span>
                            </div>

                            <div class="info-item">
                                <i class="fas fa-map-marker-alt"></i>
                                <span class="info-label">Địa chỉ</span>
                                <span class="info-value" id="current-address">${sessionScope.userAccount.accAddress}</span>
                            </div>

                            <!--                            <div class="info-item">
                                                            <i class="fas fa-clock"></i>
                                                            <span class="info-label">Ngày tạo tài khoản</span>
                                                            <span class="info-value" id="current-created-date">${sessionScope.userAccount.accCreateDate}</span>
                                                        </div>-->

                            <div class="description-box">
                                <div class="description-title">
                                    <i class="fas fa-quote-left"></i> Mô tả cá nhân
                                </div>
                                <div class="description-content" id="current-description">
                                    ${sessionScope.userAccount.accDescription}
                                </div>
                            </div>

                            <div class="button-group">
                                <!--                                <a href="profile?action=change-email" class="account-btn password-btn">
                                                                    <i class="fas fa-envelope"></i> Đổi Email
                                                                </a>-->
                                <c:choose>
                                    <c:when test="${sessionScope.userAccount.accRole eq 'Admin'}">
                                        <a href="admin-panel?action=change-password" class="account-btn password-btn">
                                            <i class="fas fa-key"></i> Đổi mật khẩu
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="profile?action=change-password" class="account-btn password-btn">
                                            <i class="fas fa-key"></i> Đổi mật khẩu
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                                <a href="logout" class="account-btn logout-btn">
                                    <i class="fas fa-sign-out-alt"></i> Đăng xuất
                                </a>
                            </div>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${param.action eq 'change-password'}">
                            <!-- Change Password Form -->
                            <div class="form-container">
                                <h3 class="form-title">Cập nhật mật khẩu mới</h3>

                                <form action="profile?action=change-password" id="profile-form" method="POST" style="width: 100%;">
                                    <div class="form-group">
                                        <label for="password">Mật khẩu mới</label>
                                        <div class="input-with-icon">
                                            <input type="password" id="password" name="password" class="form-control" required
                                                   placeholder="Nhập mật khẩu mới"/>
                                            <i class="fas fa-lock"></i>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="confirm_password">Xác nhận mật khẩu</label>
                                        <div class="input-with-icon">
                                            <input type="password" id="comfirm_password" name="comfirm_password" class="form-control" required
                                                   placeholder="Nhập lại mật khẩu"> 
                                            <i class="fas fa-lock-open"></i>
                                        </div>
                                    </div>

                                    <div class="form-actions">
                                        <p class="login-link"><a href="profile">Quay lại</a></p>
                                        <button type="submit" class="save-btn">
                                            Lưu thay đổi
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </c:when>
                        <%-- 
                                                <c:when test="${param.action eq 'change-email'}">
                                                    <!-- Change Email Form -->
                                                    <div class="form-container">
                                                        <h3 class="form-title">Cập nhật email mới</h3>

                                <c:if test="${empty sessionScope.sendOtpSuccess}">
                                    <form action="profile?action=change-email" id="profile-form" method="POST" style="width: 100%;">
                                        <div class="form-group">
                                            <label for="password">Email</label>
                                            <div class="input-with-icon">
                                                <input type="email" id="newEmail" name="newEmail" class="form-control" 
                                                       placeholder="Nhập email mới"/>
                                                <i class="fas fa-lock"></i>
                                            </div>
                                        </div>

                                        <c:if test="${not empty errMess}">
                                            <div class="errMess">
                                                <p>${errMess}</p>
                                            </div>
                                        </c:if>

                                        <div class="form-actions">
                                            <p class="login-link"><a href="profile">Quay lại</a></p>
                                            <button type="submit" class="save-btn">
                                                Nhận mã OTP
                                            </button>
                                        </div>
                                    </form>
                                </c:if>
                                
                                <c:if test="${not empty sendOtpSuccess}">
                                    <form action="profile?action=change-email&act=email-otp" method="post">

                                        <div class="otp-header1">
                                            <c:if test="${not empty newEmail}">
                                                <p>Gửi mã OTP thành công tới:</p>
                                                <p class="email-display1">${newEmail}</p>
                                            </c:if>
                                        </div>
                                        
                                        <c:if test="${not empty successMess}">
                                            <div class="successMess">
                                                <p>${successMess}</p>
                                            </div>
                                        </c:if>

                                        <div class="input-group1">
                                            <label for="otp">Mã OTP (6 số) *</label>
                                            <input type="text" id="otp" name="inputotp" maxlength="6" placeholder="Nhập mã OTP 6 số">
                                        </div>

                                        <button type="submit" class="btnotp">Xác thực</button>

                                        <c:if test="${not empty errMess}">
                                            <div class="errMess">
                                                <p>${errMess}</p>
                                            </div>
                                        </c:if>

                                        <div class="action-links1">
                                            <p class="resend-link1">
                                                Không nhận được mã? 
                                                <a href="profile?action=change-email&act=email-otp&taget=verify-otp">Gửi lại OTP</a>
                                            </p>
                                        </div>
                                    </form>
                                </c:if>

                            </div>
                        </c:when>
                        --%>
                        <c:otherwise>
                            <!-- Update Profile Form -->
                            <div class="form-container">
                                <h3 class="form-title">Cập nhật thông tin cá nhân</h3>

                                <form action="profile?action=upload-avatar" method="POST" enctype="multipart/form-data">
                                    <div class="image-upload-container">
                                        <label class="image-upload-label">Ảnh đại diện</label>
                                        <label for="profile-image" class="image-upload-area">
                                            <div class="image-upload-icon">
                                                <i class="fas fa-cloud-upload-alt"></i>
                                            </div>
                                            <div class="image-upload-text">Nhấp để tải ảnh lên</div>
                                        </label>

                                        <input type="file" id="profile-image" name="images" class="file-input" multiple accept="image/*" required />

                                        <div class="save-btn-avatar1">
                                            <button type="submit" name="submitA" class="save-btn-avatar">Cập nhật ảnh đại diện</button>
                                        </div>
                                    </div>
                                </form>

                                <form action="profile?action=update" method="POST" style="width: 100%;">
                                    <div class="form-group">
                                        <label for="username">Tên đăng nhập</label>
                                        <div class="input-with-icon">
                                            <input type="text" id="username" name="username" class="form-control" 
                                                   placeholder="Nhập tên đăng nhập mới"/>

                                            <i class="fas fa-user"></i>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="firstname">Họ</label>
                                        <div class="input-with-icon">
                                            <input type="text" id="firstname" name="firstname" class="form-control" 
                                                   placeholder="Nhập Họ mới" value="${sessionScope.userAccount.accFname}"> 
                                            <i class="fas fa-user-tag"></i>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="lastname">Tên</label>
                                        <div class="input-with-icon">
                                            <input type="text" id="lastname" name="lastname" class="form-control" 
                                                   placeholder="Nhập Tên mới" value="${sessionScope.userAccount.accLname}">
                                            <i class="fas fa-signature"></i>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="birthdate">Ngày sinh</label>
                                        <div class="input-with-icon">
                                            <input type="date" id="dob" name="dob" class="form-control" 
                                                   value="${sessionScope.userAccount.accDob}">
                                            <i class="fas fa-birthday-cake"></i>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="phone">Số điện thoại</label>
                                        <div class="input-with-icon">
                                            <input type="text" id="phone" name="phone" class="form-control" 
                                                   placeholder="Nhập SĐT mới" value="${sessionScope.userAccount.accPhoneNumber}">
                                            <i class="fas fa-phone"></i>
                                        </div>
                                        <span class="error-message">${errMessage2}</span>
                                    </div>

                                    <div class="form-group">
                                        <label for="address">Địa chỉ</label>
                                        <div class="input-with-icon">
                                            <input type="text" id="address" name="address" class="form-control" 
                                                   placeholder="Nhập địa chỉ mới" value="${sessionScope.userAccount.accAddress}">
                                            <i class="fas fa-map-marker-alt"></i>
                                        </div>
                                    </div>

                                    <div class="description-textarea-container">
                                        <label for="description" class="description-label">Mô tả cá nhân</label>
                                        <textarea id="description" name="description" class="description-textarea" 
                                                  placeholder="Giới thiệu về bản thân...">${sessionScope.userAccount.accDescription}</textarea>
                                    </div>


                                    <div class="form-actions">
                                        <button type="submit" name="submitB" class="save-btn">
                                            Lưu thay đổi
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- about -->
        <div class="about-section">
            <div class="about-column">
                <h3>Shop</h3>
                <ul>
                    <li><a href="">Dành Cho Chó</a></li>
                    <li><a href="">Dành Cho Mèo</a></li>
                    <li><a href="">Dành Cho Chuột</a></li>
                </ul>
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
        <footer>
            © 2025 FPTPet - Đồng hành cùng bạn và thú cưng mỗi ngày!
        </footer>
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
                    <input type="text" id="chatInput" placeholder="Ask me anything..." autocomplete="off" required>
                    <button type="submit" id="chatSendButton" aria-label="Send Message"><i class="fas fa-paper-plane"></i></button>
                </form>
            </div>
        </div>
        <script src="js/ai_chat.js"></script>
    </body>
</html>