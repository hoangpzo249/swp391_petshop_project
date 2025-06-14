/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', (event) => {
    const modal = document.getElementById('confirmationModal');
    if (!modal)
        return;

    const modalText = modal.querySelector('#modalText');
    const modalConfirmButton = modal.querySelector('#modalConfirmButton');
    const closeButton = modal.querySelector('.modal-close');
    const cancelButton = modal.querySelector('.modal-footer .btn-outline');

    let formIdToSubmit = null;

    window.showConfirmationModal = function (event, actionText, formId, buttonClass) {
        event.preventDefault();

        formIdToSubmit = formId;

        modalText.innerText = `Bạn có chắc chắn muốn ${actionText}?`;

        modalConfirmButton.className = 'btn';
        modalConfirmButton.classList.add(buttonClass || 'btn-primary');

        modal.classList.add('show');
    };

    function confirmAction() {
        if (formIdToSubmit) {
            const form = document.getElementById(formIdToSubmit);
            if (form) {
                form.submit();
            } else {
                console.error('Confirmation modal error: Form with ID ' + formIdToSubmit + ' not found.');
            }
        }
        closeModal();
    }

    window.closeModal = function () {
        modal.classList.remove('show');
        formIdToSubmit = null; // Clear the reference
    };

    modalConfirmButton.addEventListener('click', confirmAction);
    closeButton.addEventListener('click', closeModal);
    cancelButton.addEventListener('click', closeModal);

    window.addEventListener('click', (e) => {
        if (e.target === modal) {
            closeModal();
        }
    });
});