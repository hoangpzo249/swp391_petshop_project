<%-- 
    Document   : recover_account_page
    Created on : 31 May 2025, 12:58:07 pm
    Author     : HuyHoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lấy lại mật khẩu</title>
        <link href="css/recover_account_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>
    <body>
        <div class="floating-particle particle1"></div>
        <div class="floating-particle particle2"></div>
        <div class="floating-particle particle3"></div>
        <div class="floating-particle particle4"></div>
        <div class="floating-particle particle5"></div>

        <div class="register-container">

            <c:if test="${empty sendOtpSuccess && empty verifyOtpEmail}">
                <form action="recover" method="post">

                    <div class="otp-header">
                        <h2>Bạn quên mật khẩu?</h2>
                        <p class="instruction">Nhập lại Email của bạn để xác thực OTP và cập nhật mật khẩu mới</p>
                    </div>

                    <div class="input-group">
                        <label for="email">Email của bạn*</label>
                        <input type="email" id="email" name="email" value="${email}">
                    </div>

                    <button type="submit" class="btn">Gửi mã OTP</button>

                    <c:if test="${not empty errMess}">
                        <div class="errMess">
                            <p>${errMess}</p>
                        </div>
                    </c:if>
                </form>
            </c:if>

            <c:if test="${not empty sendOtpSuccess && empty verifyOtpSuccess}">
                <form action="recover?action=otp" method="post">

                    <div class="otp-header">
                        <c:if test="${not empty email}">
                            <p>Gửi mã OTP thành công tới:</p>
                            <p class="email-display">${email}</p>
                        </c:if>

                    </div>
                    <c:if test="${not empty successMess}">
                        <div class="successMess">
                            <p>${successMess}</p>
                        </div>
                    </c:if>

                    <div class="input-group">
                        <label for="otp">Mã OTP (6 số) *</label>
                        <input type="text" id="otp" name="inputotp" value="" maxlength="6" placeholder="Nhập mã OTP 6 số">
                    </div>

                    <button type="submit" class="btn">Xác thực</button>

                    <c:if test="${not empty errMess}">
                        <div class="errMess">
                            <p>${errMess}</p>
                        </div>
                    </c:if>

                    <div class="action-links">
                        <p class="resend-link">
                            Không nhận được mã? 
                            <a href="recover?action=resend-otp">Gửi lại OTP</a>
                        </p>
                    </div>
                </form>
            </c:if>

            <c:if test="${not empty verifyOtpSuccess}">
                <form action="recover?action=recover-pass" method="post">

                    <h2>Cập nhật mật khẩu của bạn</h2><br>

                    <div class="input-group">
                        <label for="email">Mật khẩu mới*</label>
                        <input type="password" id="password" name="password" value="${password}">
                    </div>

                    <div class="input-group">
                        <label for="comfirm_password">Xác nhận mật khẩu *</label>
                        <input type="password" id="comfirm_password" name="comfirm_password" value="${comfirm_password}">
                    </div>

                    <c:if test="${not empty errMess}">
                        <div class="errMess">
                            <p>${errMess}</p>
                        </div>
                    </c:if>

                    <button type="submit" class="btn">Xác nhận</button>
                </form>
            </c:if>

            <p class="login-link">
                <a href="login"><i class="fas fa-arrow-left"></i> Quay lại đăng nhập</a>
            </p>
        </div>
    </body>
</html>
