<%-- 
    Document   : seller_pet_edit
    Created on : 12 Jun 2025, 22:35:01
    Author     : Lenovo
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Thú Cưng - PETFPT Shop</title>
        <link href="css/seller_panel_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <div class="seller-header">
            <div class="logo-container">
                <a href="homepage">
                    <img src="images/logo_banner/logo2.png" alt="PETFPT Shop Logo"/>
                </a>
                <h1 class="seller-title">SELLER</h1>
            </div>

            <div class="seller-profile">
                <c:choose>
                    <c:when test="${not empty sessionScope.userAccount.displayAccImage()}">
                        <img src="${sessionScope.userAccount.displayAccImage()}" alt="Seller Avatar"/>
                    </c:when>
                    <c:otherwise>
                        <img src="images/support button/account.png" alt="Seller Avatar"/>
                    </c:otherwise>
                </c:choose>
                <div class="seller-info">
                    <span class="seller-name">${sessionScope.userAccount.accFname} ${sessionScope.userAccount.accLname}</span>
                    <span class="seller-role">Seller</span>
                </div>
                <div class="seller-actions">
                    <a href="profile" class="seller-action" title="Thông tin cá nhân"><i class="fas fa-user-circle"></i></a>
                    <a href="logout" class="seller-action" title="Đăng xuất"><i class="fas fa-sign-out-alt"></i></a>
                </div>
            </div>
        </div>

        <div id="alert-container">
            <c:if test="${not empty sessionScope.successMess}">
                <div class="alert-message">${sessionScope.successMess}</div>
                <c:remove var="successMess" scope="session" />
            </c:if>
            <c:if test="${not empty sessionScope.errMess}">
                <div class="alert-message error">${sessionScope.errMess}</div>
                <c:remove var="errMess" scope="session" />
            </c:if>
        </div>


        <div class="seller-container">
            <div class="seller-sidebar">
                <div class="sidebar-header">
                    <h2 class="sidebar-title">SELLER PANEL</h2>
                    <p class="sidebar-subtitle">Quản lý hệ thống PETFPT Shop</p>
                </div>
                <div class="sidebar-menu">
                    <div class="menu-category">
                        <h5 class="category-title">Điều hướng</h5>
                        <a href="displaysalesstatistic" class="sidebar-link"><i class="fas fa-tachometer-alt"></i> Tổng quan</a>
                        <a href="sellerdisplayinvoice" class="sidebar-link">
                            <i class="fas fa-file-invoice"></i> Danh sách hóa đơn
                        </a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Quản lý</h5>
                        <a href="displayorder" class="sidebar-link"><i class="fas fa-bag-shopping"></i> Quản lý đơn hàng</a>
                        <a href="displayallpet" class="sidebar-link active"><i class="fas fa-dog"></i> Quản lý thú cưng</a>
                        <a href="displayrefund" class="sidebar-link"><i class="fas fa-undo-alt"></i> Quản lý hoàn tiền</a>
                    </div>
                    <div class="menu-category">
                        <h5 class="category-title">Thao tác</h5>
                        <a href="addpet" class="sidebar-link"><i class="fas fa-paw"></i> Đăng bán thú cưng</a>
                        <a href="add_refund.jsp" class="sidebar-link"><i class="fas fa-plus-circle"></i> Gửi yêu cầu hoàn tiền</a>
                        <a href="profile" class="sidebar-link"><i class="fas fa-user-circle"></i> Tài khoản của tôi</a>
                        <a href="profile?action=change-password" class="sidebar-link"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a href="logout" class="sidebar-link"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </div>

            <div class="seller-content">
                <div class="page-header">
                    <h1 class="page-title"><i class="fas fa-pencil-alt"></i> Chỉnh sửa thú cưng #${pet.petId}</h1>
                    <ul class="breadcrumb">
                        <li><a href="homepage">Trang chủ</a></li>
                        <li><a href="displayallpet">Quản lý thú cưng</a></li>
                        <li>Chỉnh sửa</li>
                    </ul>
                </div>

                <div class="card">
                    <form action="updatepet" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="petId" value="${pet.petId}">
                        <div class="card-body">
                            <div class="form-grid">
                                <div class="form-group">
                                    <label for="petName">Tên thú cưng</label>
                                    <input type="text" id="petName" name="petName" class="form-control" value="${pet.petName}" required>
                                </div>
                                <div class="form-group">
                                    <label for="petGender">Giới tính</label>
                                    <select id="petGender" name="petGender" class="form-control">
                                        <option value="Male" ${pet.petGender == 'Male' ? 'selected' : ''}>Đực</option>
                                        <option value="Female" ${pet.petGender == 'Female' ? 'selected' : ''}>Cái</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="breedId">Giống</label>
                                    <select id="breedId" name="breedId" class="form-control" required>
                                        <c:forEach items="${breedList}" var="b">
                                            <option value="${b.breedId}" ${pet.breedId == b.breedId ? 'selected' : ''}>${b.breedName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="colorInput">Màu sắc</label>
                                    <div class="autocomplete-container">
                                        <input type="text" id="colorInput" class="form-control" placeholder="Ví dụ: Vàng, Trắng" value="${pet.petColor}" autocomplete="off">
                                        <input type="hidden" id="petColor" name="petColor" value="${pet.petColor}">
                                        <div id="color-autocomplete-list" class="autocomplete-items"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="petDob">Ngày sinh</label>
                                    <input type="date" id="petDob" name="petDob" class="form-control" value="<fmt:formatDate value='${pet.petDob}' pattern='yyyy-MM-dd'/>" required>
                                </div>
                                <div class="form-group">
                                    <label for="originInput">Nguồn gốc</label>
                                    <div class="autocomplete-container">
                                        <input type="text" id="originInput" class="form-control" placeholder="Ví dụ: Việt Nam" value="${pet.petOrigin}" autocomplete="off">
                                        <input type="hidden" id="petOrigin" name="petOrigin" value="${pet.petOrigin}">
                                        <div id="origin-autocomplete-list" class="autocomplete-items"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="priceDisplay">Giá bán (₫)</label>

                                    <input type="text" 
                                           id="priceDisplay" 
                                           class="form-control" 
                                           placeholder="Nhập giá của thú cưng" 
                                           required 
                                           autocomplete="off">

                                    <input type="hidden" 
                                           id="petPrice" 
                                           name="petPrice" 
                                           value="${pet.petPrice}">
                                </div>
                                <div></div>
                                <div class="form-full-width">
                                    <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 30px;">
                                        <div class="form-group"><label for="petAvailability">Trạng thái bán</label><select id="petAvailability" name="petAvailability" class="form-control"><option value="1" ${pet.petAvailability == 1 ? 'selected' : ''}>Còn hàng</option><option value="0" ${pet.petAvailability == 0 ? 'selected' : ''}>Đã bán</option></select></div>
                                        <div class="form-group"><label for="petStatus">Trạng thái đăng</label><select id="petStatus" name="petStatus" class="form-control"><option value="1" ${pet.petStatus == 1 ? 'selected' : ''}>Hiển thị</option><option value="0" ${pet.petStatus == 0 ? 'selected' : ''}>Ẩn</option></select></div>
                                        <div class="form-group"><label for="petVaccination">Tiêm phòng</label><select id="petVaccination" name="petVaccination" class="form-control"><option value="1" ${pet.petVaccination == 1 ? 'selected' : ''}>Đã tiêm</option><option value="0" ${pet.petVaccination == 0 ? 'selected' : ''}>Chưa tiêm</option></select></div>
                                    </div>
                                </div>

                                <div class="form-full-width">
                                    <div class="form-group">
                                        <label>Quản lý hình ảnh thú cưng (giới hạn 5 ảnh)</label>
                                        <div class="image-management-grid" id="image-grid-container">
                                            <c:forEach items="${imageList}" var="image">
                                                <div class="image-preview-item" id="image-item-${image.imageId}">
                                                    <img src="${image.getImage()}" alt="Pet Image" id="image-preview-${image.imageId}">
                                                    <div class="image-actions">
                                                        <input type="file" class="hidden-file-input" id="file-input-${image.imageId}" 
                                                               onchange="handleImageChange(event, ${image.imageId}, ${pet.petId})" accept="image/*">
                                                        <button type="button" class="btn-image-action btn-change" 
                                                                onclick="document.getElementById('file-input-${image.imageId}').click();">
                                                            <i class="fas fa-sync-alt"></i> Đổi ảnh
                                                        </button>

                                                        <button type="button" class="btn-image-action btn-delete" 
                                                                onclick="showImageDeleteModal(event, ${image.imageId}, ${pet.petId})">
                                                            <i class="fas fa-trash-alt"></i> Xóa
                                                        </button>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="newImages">Tải lên ảnh mới</label>
                                        <input type="file" id="newImages" name="newImages" class="form-control" multiple accept="image/*" onchange="previewNewImages(event)">
                                        <div id="new-image-previews" class="image-management-grid" style="margin-top: 15px;"></div>
                                    </div>
                                </div>

                                <div class="form-full-width">
                                    <div class="form-group">
                                        <label for="petVaccineInfo">Các vắc-xin mà thú cưng đã được tiêm (nếu áp dụng)</label>
                                        <textarea id="petVaccineInfo" name="petVaccineInfo" class="form-control" rows="5" placeholder="Nhập các vắc-xin mà thú cưng đã được tiêm...">${pet.petVaccineInfo}</textarea>
                                    </div>
                                </div>


                                <div class="form-full-width">
                                    <div class="form-group">
                                        <label for="petDescription">Mô tả</label>
                                        <textarea id="petDescription" name="petDescription" class="form-control" rows="5">${pet.petDescription}</textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <a href="displaypet?id=${pet.petId}" class="btn btn-outline">Chuyển đến trang của thú cưng</a>
                            <a href="displayallpet" class="btn btn-outline">Hủy bỏ</a>
                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="seller-footer">
            © 2025 PETFPT Shop - Hệ thống quản lý
        </div>

        <div class="modal-backdrop" id="confirmationModal">
            <div class="modal-dialog">
                <div class="modal-header">
                    <h3 class="modal-title" id="modalTitle">Xác nhận hành động</h3>
                    <button class="modal-close" onclick="closeModal()">×</button>
                </div>
                <div class="modal-body">
                    <p id="modalText">Bạn có chắc chắn muốn thực hiện hành động này không?</p>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline" onclick="closeModal()">Hủy bỏ</button>
                    <button id="modalConfirmButton" class="btn btn-danger">Xác nhận</button>
                </div>
            </div>
        </div>
        <script>
            const colorData = [
            <c:forEach var="c" items="${colorList}" varStatus="loop">
            '${c}'<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
            ];
            const originData = [
            <c:forEach var="o" items="${originList}" varStatus="loop">
            '${o}'<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
            ];
            function previewNewImages(event) {
                const previewContainer = document.getElementById('new-image-previews');
                const fileInput = event.target;

                previewContainer.innerHTML = '';

                const files = fileInput.files;
                if (!files || files.length === 0) {
                    return;
                }

                const existingImageCount = Number('${existingImageCount}');
                const maxNewImages = Number('${maxNewImages}');

                if (files.length > maxNewImages) {
                    alert(`Bạn đã có ${existingImageCount} ảnh. Bạn chỉ có thể tải lên thêm tối đa ${maxNewImages} ảnh nữa (tổng cộng 5 ảnh).`);
                    fileInput.value = '';
                    return;
                }

                Array.from(files).forEach(file => {
                    if (file.type.startsWith('image/')) {
                        const reader = new FileReader();

                        reader.onload = function (e) {
                            const previewItem = document.createElement('div');
                            previewItem.className = 'image-preview-item';

                            const img = document.createElement('img');
                            img.src = e.target.result;
                            img.alt = 'Xem trước ảnh mới';

                            previewItem.appendChild(img);
                            previewContainer.appendChild(previewItem);
                        };

                        reader.readAsDataURL(file);
                    }
                });
            }
        </script>
        <script src="js/pet_autocomplete.js"></script>
        <script src="js/pet_image_confirmation_window.js"></script>
    </body>
</html>