/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    const formGroups = document.querySelectorAll(".card");

    formGroups.forEach(function (card) {
        const typeSelect = card.querySelector("select[name='type']");
        const maxValue = card.querySelector("input[name='maxValue']");

        if (!typeSelect || !maxValue)
            return;

        function onoffMaxValueField() {
            const selectedType = typeSelect.value;
            if (selectedType === "Fixed") {
                maxValue.readOnly = true;
                maxValue.value = "";
            } else {
                maxValue.readOnly = false;
            }
        }

        typeSelect.addEventListener("change", onoffMaxValueField);
        onoffMaxValueField();
    });
});
