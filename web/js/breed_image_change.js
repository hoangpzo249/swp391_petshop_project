/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


async function handleBreedImageChange(event, breedId) {
    const file = event.target.files[0];
    if (!file)
        return;

    const formData = new FormData();
    formData.append('action', 'changeImage');
    formData.append('breedId', breedId);
    formData.append('newImageFile', file);

    showAlert('Đang tải ảnh lên, vui lòng chờ...', 'info');

    try {
        const response = await fetch('breedimagechange', {
            method: 'POST',
            body: formData
        });
        const result = await response.json();

        showAlert(result.message, result.success ? 'success' : 'error');

        if (result.success && result.newImageData) {
            const imagePreview = document.getElementById(`image-preview-${breedId}`);
            if (imagePreview) {
                imagePreview.src = `data:image/jpeg;base64,${result.newImageData}`;
            }
        }
    } catch (error) {
        console.error('Error during breed image change:', error);
        showAlert('Lỗi kết nối đến máy chủ.', 'error');
    }

    event.target.value = '';
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