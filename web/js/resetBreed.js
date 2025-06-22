/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */



window.addEventListener("DOMContentLoaded", function () {
    var speciesDropdown = document.getElementById("species");
    var breedDropdown = document.getElementById("breed");
    if (speciesDropdown && breedDropdown) { 
        speciesDropdown.addEventListener("change", function () {           
            breedDropdown.selectedIndex = 0;       
            speciesDropdown.form.submit();
        });
    }
});
