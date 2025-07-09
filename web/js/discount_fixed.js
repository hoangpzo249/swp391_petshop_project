/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */



document.addEventListener("DOMContentLoaded", function () {
    const typeSelect = document.getElementById("type");
    const maxValue = document.getElementById("maxValue");

    function onoffMaxValueField() {
        if (!typeSelect || !maxValue)
            return;

        const selectedType = typeSelect.value;
        if (selectedType === "Fixed") {
            maxValue.readOnly=true;
            maxValue.value = "";
        } else {
            maxValue.readOnly=false;
        }
    }

    typeSelect.addEventListener("change", onoffMaxValueField);
    onoffMaxValueField();
});
