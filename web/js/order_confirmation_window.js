/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', (event) => {

    const modal = document.getElementById('confirmationModal');
    const modalText = document.getElementById('modalText');
    const modalConfirmButton = document.getElementById('modalConfirmButton');
    const reasonContainer = document.getElementById('rejectionReasonContainer');
    const reasonTextarea = document.getElementById('rejectionReason');
    const closeButton = document.querySelector('.modal-close');
    const cancelButton = document.querySelector('.modal-footer .btn-outline');

    let urlToRedirect = '';
    let reasonIsRequired = false;

    window.showConfirmationModal = function (event, actionText, url, buttonClass, requiresReason = false) {
        if (event) {
            event.preventDefault();
        }

        modalText.innerText = `Bạn có chắc chắn muốn ` + actionText + `?`;
        urlToRedirect = url;
        reasonIsRequired = requiresReason;

        if (requiresReason) {
            reasonContainer.style.display = 'block';
            reasonTextarea.value = '';
        } else {
            reasonContainer.style.display = 'none';
        }

        modalConfirmButton.className = 'btn';
        modalConfirmButton.classList.add(buttonClass);
        modal.classList.add('show');
    };

    function confirmAction() {
        let finalUrl = urlToRedirect;

        if (reasonIsRequired) {
            const reason = reasonTextarea.value.trim();
            if (reason === '') {
                alert('Vui lòng nhập lý do từ chối.');
                reasonTextarea.focus();
                return;
            }
            finalUrl += '&reason=' + encodeURIComponent(reason);
        }

        if (finalUrl) {
            window.location.href = finalUrl;
        }
    }

    function closeModal() {
        modal.classList.remove('show');
        urlToRedirect = '';
        reasonIsRequired = false;
    }

    if (modalConfirmButton) {
        modalConfirmButton.addEventListener('click', confirmAction);
    }

    if (closeButton) {
        closeButton.addEventListener('click', closeModal);
    }

    if (cancelButton) {
        cancelButton.addEventListener('click', closeModal);
    }


    window.onclick = function (event) {
        if (event.target === modal) {
            closeModal();
        }
    };
});