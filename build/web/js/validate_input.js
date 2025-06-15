/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function showToast(message) {
    const toast = document.getElementById('toast');
    toast.innerText = message;
    toast.classList.add('show');


    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

document.addEventListener('DOMContentLoaded', function () {
    var option = "";
    document.getElementById('checkout').addEventListener('click', function () {
        option = "checkout";
    })
    document.getElementById('discount').addEventListener('click', function () {
        option = "discount";
    })
    const checkoutForm = document.getElementById('checkoutForm');

    if (checkoutForm) {
        checkoutForm.addEventListener('submit', function (e) {
            if (option === "discount") {
                return;
            }
            const guestName = document.getElementById('guestName');
            const guestPhone = document.getElementById('guestPhone');
            const guestEmail = document.getElementById('email');
            const guestAddress = document.getElementById('guestAddress');



            const nameValue = guestName.value.trim();
            const phoneValue = guestPhone.value.trim();
            const emailValue = guestEmail.value.trim();
            const addressValue = guestAddress.value.trim();

            const nameRegex = /^[A-ZÀ-Ỹa-zà-ỹ\s'-]+$/u;
            if (!nameRegex.test(nameValue)) {
                e.preventDefault();
                showToast("Tên người nhận không được chứa số, ký tự lạ hoặc để trống.");
                return;
            }
            if (phoneValue.toLowerCase().includes("chưa cập nhật")) {
                e.preventDefault();
                showToast("Vui lòng cập nhật lại số điện thoại.");
                return;
            }
            const phoneRegex = /^(0\d{9}|\+84\d{9})$/;
            if (!phoneRegex.test(phoneValue)) {
                e.preventDefault();
                showToast("Số điện thoại không hợp lệ. Phải có 10 chữ số (bắt đầu bằng 0) hoặc theo định dạng +84.");
                return;
            }

            const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            if (!emailRegex.test(emailValue)) {
                e.preventDefault();
                showToast("Email phải có dạng 'abc@domain.cde'. Trong đó 'cde' phải có ít nhất 2 ký tự.");
                return;
            }
            
            if (addressValue.toLowerCase().includes("chưa cập nhật")) {
                e.preventDefault();
                showToast("Vui lòng cập nhật lại địa chỉ.");
                return;
            }

            const addressRegex = /^[A-Za-zÀ-Ỵà-ỹ0-9\s,.'\-\/]{2,}$/u;
            if (!addressRegex.test(addressValue)) {
                e.preventDefault();
                showToast("Địa chỉ không hợp lệ. Phải có ít nhất 2 ký tự, không chứa ký tự lạ.");
                return;
            }

        });
    }
});

