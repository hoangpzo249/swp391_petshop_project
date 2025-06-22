
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đặt hàng thành công - PETFPT Shop</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link href="css/cart_confirm.css?v=2" rel="stylesheet" type="text/css"/>
        <link rel="icon" type="png" href="images/logo_banner/logo1.png">
    </head>

    <body>
        <div class="confirmation-container">
            <div class="confirmation-content">
                <div class="checkmark">
                    <i class="fas fa-check-circle"></i>
                </div>
                <h1>Đặt hàng thành công!</h1>
                <p>Cảm ơn bạn đã mua sắm tại <strong>PETFPT Shop</strong>.</p>

                <div class="order-details">
                    <p>Mã đơn hàng: <strong>#PFT${orderId}</strong></p>
                    <p>Ngày đặt hàng: <strong>${date}</strong></p>
                    <p>Phương thức thanh toán: <strong>${method}</strong></p>
                </div>

                <div class="alert-box">
                    <p><strong>Lưu ý:</strong> Nhân viên của chúng tôi sẽ gọi điện xác nhận đơn hàng trong thời gian sớm nhất.<br>
                        Vui lòng giữ điện thoại bên mình để nhận cuộc gọi.</p>
                </div>

                <div class="support-info">
                    <p>Nếu cần hỗ trợ, vui lòng liên hệ:</p>
                    <p><i class="fas fa-phone-volume"></i> <strong>0767 676 770</strong></p>
                    <p><i class="fas fa-envelope"></i> <a href="mailto:hotro@petfptshop.vn">hotro@petfptshop.vn</a></p>
                </div>

                <div class="btn-container">
                    <a href="homepage" class="btn-home">Quay lại Trang chủ</a>
                </div>
            </div>
        </div>

    </body>
</html>
