/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('confirmationModal');
    if (!modal)
        return;

    const modalText = document.getElementById('modalText');
    const modalConfirmButton = document.getElementById('modalConfirmButton');

    let urlToRedirect = '';

    window.showConfirmationModal = function (event, actionText, url, buttonClass) {
        if (event) {
            event.preventDefault();
        }

        modalText.innerText = `Bạn có chắc chắn muốn ${actionText}?`;
        urlToRedirect = url;

        modalConfirmButton.className = 'btn';
        if (buttonClass) {
            modalConfirmButton.classList.add(buttonClass);
        }

        modal.classList.add('show');
    };

    function confirmAction() {
        if (urlToRedirect) {
            window.location.href = urlToRedirect;
        }
    }

    window.closeModal = function () {
        modal.classList.remove('show');
        urlToRedirect = '';
    }

    modalConfirmButton.addEventListener('click', confirmAction);

    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            closeModal();
        }
    });
});