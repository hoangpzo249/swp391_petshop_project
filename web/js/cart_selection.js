/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', function () {
    var checkboxes = document.querySelectorAll('.item-check');

    var checkoutForm = document.getElementById('checkoutForm');


    checkoutForm.addEventListener('submit', function (e) {

        var hasChecked = false;
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                hasChecked = true;
                break;
            }
        }
        if (!hasChecked) {
            e.preventDefault();
            alert("Vui lòng chọn ít nhất một đơn hàng để thanh toán.");
            return;
        }


        var guestName = document.getElementById('guestName');
        var guestPhone = document.getElementById('guestPhone');
        var guestEmail = document.getElementById('email');
        var guestAddress = document.getElementById('guestAddress');

        if (guestName && guestPhone && guestEmail) {
            var nameValue = guestName.value.trim();
            var phoneValue = guestPhone.value.trim();
            var emailValue = guestEmail.value.trim();
            var addressValue = guestAddress.value.trim();
            

            const phoneRegex = /^(0\d{9}|\+84\d{9})$/;

            if (!phoneRegex.test(phoneValue)) {
                e.preventDefault();
                alert("Số điện thoại không hợp lệ. Phải có 10 chữ số (bắt đầu bằng 0) hoặc theo định dạng +84.");
                return;
            }



            const nameRegex = /^[A-ZÀ-Ỹa-zà-ỹ\s'-]+$/u;

            if (!nameRegex.test(nameValue)) {
                e.preventDefault();
                alert("Tên người nhận không được chứa số ,kí tự đặc biệt hoặc để trống.");
                return;
            }


            const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

            if (!emailRegex.test(emailValue)) {
                e.preventDefault();
                alert("Email phải có dạng 'abc@domain.cde' .Trong đó 'cde' phải có ít nhất 2 kí tự");
                return;
            }
            const addressRegex = /^[A-Za-zÀ-Ỵà-ỹ0-9\s,.'\-\/]{2,}$/u;
            if (!addressRegex.test(addressValue)) {
                e.preventDefault();
                alert("Địa chỉ không hợp lệ. Phải có ít nhất 2 ký tự, không chứa ký tự lạ.");
                return;
            }
        }
    });
});
