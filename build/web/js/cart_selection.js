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
    var checkboxes = document.querySelectorAll('.item-check');
    var subTotalDisplay = document.getElementById("subtotal-display");
    var confirmForm = document.getElementById('confirmForm');

    function calculateTotal() {
        var total = 0;
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                total += parseFloat(checkboxes[i].dataset.price);
            }
        }
        return total;
    }

    function updateTotal() {
        var total = calculateTotal();
        subTotalDisplay.innerText = total.toLocaleString('vi-VN') + '₫';
    }


    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].addEventListener('change', updateTotal);
    }

    confirmForm.addEventListener('submit', function (e) {

        var hasChecked = false;
        var countCheck = 0;
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                hasChecked = true;
                countCheck++;
            }
        }
        if (countCheck > 10) {
            e.preventDefault();
            showToast("Bạn chỉ được chọn tối đa 10 đơn hàng để thanh toán.Nếu muốn mua số lượng lớn hơn vui lòng liên hệ trực tiếp qua hotline của chúng tôi.");
            return;
        }
        if (!hasChecked) {
            e.preventDefault();
            showToast("Vui lòng chọn ít nhất một đơn hàng để thanh toán.");
            return;
        }




    });
});
