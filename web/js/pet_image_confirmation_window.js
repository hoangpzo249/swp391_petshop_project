/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('confirmationModal');
    if (!modal)
        return;

    const modalText = modal.querySelector('#modalText');
    const modalConfirmButton = modal.querySelector('#modalConfirmButton');
    const closeButton = modal.querySelector('.modal-close');
    const cancelButton = modal.querySelector('.modal-footer .btn-outline');

    let confirmCallback = null;

    window.showImageDeleteModal = (event, imageId, petId) => {
        event.preventDefault();
        modalText.innerText = `Bạn có chắc chắn muốn xóa ảnh này?`;
        confirmCallback = () => handleImageDelete(imageId, petId);
        modal.classList.add('show');
    };

    window.closeModal = () => {
        modal.classList.remove('show');
        confirmCallback = null;
    };

    modalConfirmButton.addEventListener('click', () => {
        if (typeof confirmCallback === 'function') {
            confirmCallback();
        }
        closeModal();
    });

    closeButton.addEventListener('click', closeModal);
    cancelButton.addEventListener('click', closeModal);
    window.addEventListener('click', (e) => {
        if (e.target === modal)
            closeModal();
    });
});

async function handleImageDelete(imageId, petId) {
    const formData = new FormData();
    formData.append('action', 'delete');
    formData.append('imageId', imageId);
    formData.append('petId', petId);

    try {
        const response = await fetch('petimageaction', {
            method: 'POST',
            body: formData
        });
        const result = await response.json();

        showAlert(result.message, result.success ? 'success' : 'error');

        if (result.success) {
            if (result.isDefault) {
                const imagePreview = document.getElementById(`image-preview-${imageId}`);
                if (imagePreview)
                    imagePreview.src = result.newUrl;
            } else {
                const imageItem = document.getElementById(`image-item-${imageId}`);
                if (imageItem)
                    imageItem.remove();
            }
        }
    } catch (error) {
        showAlert('Lỗi kết nối đến máy chủ.', 'error');
    }
}

async function handleImageChange(event, imageId, petId) {
    const file = event.target.files[0];
    if (!file)
        return;

    const formData = new FormData();
    formData.append('action', 'change');
    formData.append('imageId', imageId);
    formData.append('petId', petId);
    formData.append('newImageFile', file);

    showAlert('Đang tải ảnh lên, vui lòng chờ...', 'info');

    try {
        const response = await fetch('petimageaction', {
            method: 'POST',
            body: formData
        });
        const result = await response.json();

        showAlert(result.message, result.success ? 'success' : 'error');

        if (result.success) {
            const imagePreview = document.getElementById(`image-preview-${imageId}`);
            if (imagePreview) {
                imagePreview.src = result.newUrl + '?t=' + new Date().getTime();
            }
        }
    } catch (error) {
        showAlert('Lỗi kết nối đến máy chủ.', 'error');
    }
}

function showAlert(message, type = 'success') {
    const container = document.getElementById('alert-container');
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert-message ${type === 'error' ? 'error' : ''}`;
    alertDiv.textContent = message;

    if (container.firstChild) {
        container.removeChild(container.firstChild);
    }
    container.appendChild(alertDiv);

    setTimeout(() => {
        if (alertDiv.parentNode === container) {
            container.removeChild(alertDiv);
        }
    }, 5000);
}