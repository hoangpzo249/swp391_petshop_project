/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    const fileInput = document.getElementById("proofImage");
    const previewImg = document.getElementById("previewImage");

    if (fileInput && previewImg) {
        fileInput.addEventListener("change", function () {
            const file = fileInput.files[0];
            if (!file)
                return;

            const reader = new FileReader();
            reader.onload = function (e) {
                previewImg.src = e.target.result;
                previewImg.style.display = "block";
            };
            reader.readAsDataURL(file);
        });
    }

    const statusSelect = document.getElementById("refundStatus");
    const reasonGroup = document.getElementById("reasonGroup");
    const rejectReason=document.getElementById("rejectReason");

    function onoffReasonField() {
        const selected = statusSelect.value;
        if (selected === "Rejected") {
            reasonGroup.style.display = "block";
        } else {
            rejectReason.value="";
            reasonGroup.style.display = "none";
        }
    }

    if (statusSelect) {
        onoffReasonField();
        statusSelect.addEventListener("change", onoffReasonField);
    }

    const proofRefundedGroup = document.getElementById("proofRefundedGroup");
    const proofRefundedInput = document.getElementById("proofRefundedImage");
    const proofRefundedPreview = document.getElementById("previewRefundedImage");

    function onoffProofRefundedField() {
        if (!statusSelect || !proofRefundedGroup)
            return;
        const selected = statusSelect.value;
        if (selected === "Completed") {
            proofRefundedGroup.style.display = "block";

        } else {
            proofRefundedInput.value = "";
            proofRefundedGroup.style.display = "none";
            if (proofRefundedPreview) {
                proofRefundedPreview.src = "";
                proofRefundedPreview.style.display = "none";
            }
        }
    }

    onoffProofRefundedField();
    if (statusSelect) {
        statusSelect.addEventListener("change", onoffProofRefundedField());
    }

    if (proofRefundedInput && proofRefundedPreview) {
        proofRefundedInput.addEventListener("change", function () {
            const file = proofRefundedInput.files[0];
            if (!file)
                return;

            const reader = new FileReader();
            reader.onload = function (e) {
                proofRefundedPreview.src = e.target.result;
                proofRefundedPreview.style.display = "block";
            };
            reader.readAsDataURL(file);
        });
    }
});
