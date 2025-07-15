/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    const typeSelect = document.getElementById("type");
    const valueInput = document.getElementById("value");
    const maxValueInput = document.getElementById("maxValue");
    const minAmountInput = document.getElementById("minAmount");

    function formatInput(input) {
        let raw = input.value.replace(/\./g, "").trim();
        if (/^\d+$/.test(raw)) {
            input.value = Number(raw).toLocaleString("vi-VN");
        }
    }

   function setupListeners() {
        valueInput.removeEventListener("input", onValueInput);
        maxValueInput.removeEventListener("input", onMaxValueInput);

        if (typeSelect.value === "Fixed") {
            valueInput.addEventListener("input", onValueInput);
        } else {
            maxValueInput.addEventListener("input", onMaxValueInput);
        }

        minAmountInput.addEventListener("input", onMinAmountInput);
    }

    function onValueInput() {
        formatInput(valueInput);
    }

    function onMaxValueInput() {
        formatInput(maxValueInput);
    }
     function onMinAmountInput() {
        formatInput(minAmountInput);
    }
    setupListeners();
    typeSelect.addEventListener("change", setupListeners);



});
