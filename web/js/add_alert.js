/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', function () {
    
    var iframe = document.getElementById('add_cart');

    function showCartMessage() {
      
        
            var text = iframe.contentDocument.body.innerText.trim();
            var parts=text.split('|');
            message=parts[0];
            count=parts[1];
            
            alert(message);
            document.getElementById('cart-count').innerText=count;
           
    }

    
    iframe.addEventListener('load', showCartMessage);
});