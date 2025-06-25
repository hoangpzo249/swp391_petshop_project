/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function showToast(message, isError = true) {
    const toast = document.getElementById('toast');
    toast.innerText = message;

    if (isError) {
        toast.classList.add('error');
    } else {
        toast.classList.remove('error');
    }

    toast.classList.add('show');

    setTimeout(() => {
        toast.classList.remove('show');
    }, 5000);
}

document.addEventListener('DOMContentLoaded', function () {
    var form = document.getElementById('add_discount');

    form.addEventListener('submit', function (e) {
        var code = document.getElementById("code").value.trim();
        var type = document.getElementById("type").value;
        var value = document.getElementById("value").value.trim();
        var minAmount = document.getElementById("minAmount").value.trim();
        var maxValue = document.getElementById("maxValue").value.trim();
        var maxUsage = document.getElementById("maxUsage").value.trim();
        var validFrom = document.getElementById("validFrom").value;
        var validTo = document.getElementById("validTo").value;




        if (parseFloat(value) <= 0) {
            e.preventDefault();
            showToast("Giá trị giảm phải lớn hơn 0.");
            return;
        }

        if (type === "Percent" && parseFloat(value) > 100) {
            e.preventDefault();
            showToast("Phần trăm giảm không được vượt quá 100%.");
            return;
        }

        if (parseFloat(minAmount) < 0) {
            e.preventDefault();
            showToast("Giá trị đơn hàng tối thiểu không được nhỏ hơn 0.");
            return;
        }


        if (maxValue !== "" && parseFloat(maxValue) < 0) {
            e.preventDefault();
            showToast("Giảm tối đa không hợp lệ.");
            return;
        }


        if (maxUsage !== "" && parseInt(maxUsage) < 0) {
            e.preventDefault();
            showToast("Số lần sử dụng tối đa không hợp lệ.");
            return;
        }
        var from = new Date(validFrom);
        var to = new Date(validTo);
        var today = new Date();
        if (from > to) {
            e.preventDefault();
            showToast("Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
            return;
        }
        if (to < today) {
            e.preventDefault();
            showToast("Ngày hết hạn không được là ngày trong quá khứ.");
            return;
        }
    });
});
