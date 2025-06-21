<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html>

        <head>
            <title>Thêm/Sửa Blog</title>
            <meta charset="UTF-8">
            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

            <link href="css/blogForm.css?v=9" rel="stylesheet" type="text/css" />
            <link href="css/head_about.css" rel="stylesheet" type="text/css" />
            <link href="css/header_footer.css" rel="stylesheet" type="text/css" />
            <script src="ckeditor/ckeditor.js"></script>
            <script src="js/blogPreview.js"></script>
            <script src="js/blogValidation.js"></script>

        </head>

        <body>
            <div class="header">
                <div class="header1">
                    <div>
                        <a href="homepage">
                            <img src="images/logo2.png" alt="logo" />
                        </a>
                    </div>

                    <form action="listshoppet" method="get">

                        <input type="hidden" name="species" value="${species}" />
                        <input type="hidden" name="breed" value="${breed}" />
                        <input type="hidden" name="gender" value="${param.gender}" />
                        <input type="hidden" name="color" value="${param.color}" />
                        <input type="hidden" name="origin" value="${param.origin}" />
                        <input type="hidden" name="dobFrom" value="${param.dobFrom}" />
                        <input type="hidden" name="dobTo" value="${param.dobTo}" />
                        <input type="hidden" name="priceRange" value="${param.priceRange}" />
                        <input type="hidden" name="vaccination" value="${param.vaccination}" />
                        <div class="search">
                            <input type="text" name="search" value="${param.search}" placeholder="Tìm kiếm thú cưng ..."
                                style="border: none;" />
                            <button type="submit" class="search-button">
                                <img src="images/support button/search.png" width="20" height="20" alt="search" />
                            </button>
                        </div>
                    </form>

                    <div class="accountcard">
                        <c:choose>
                            <c:when test="${sessionScope.userAccount != null}">
                                <div class="account-dropdown">
                                    <a href="#" class="account-trigger">
                                        <img src="images/support button/account.png" width="50" height="50"
                                            alt="account" />
                                        <p class="username">Tài khoản</p>
                                    </a>
                                    <div class="dropdown-content">
                                        <c:choose>
                                            <c:when test="${sessionScope.userAccount.accRole eq 'Admin'}">
                                                <a href="admin-panel" class="dropdown-item">
                                                    <i class="fas fa-user"></i>
                                                    <span>Quản lý Admin</span>
                                                </a>
                                            </c:when>
                                            <c:when test="${sessionScope.userAccount.accRole eq 'Manager'}">
                                                <a href="profile" class="dropdown-item">
                                                    <i class="fas fa-user"></i>
                                                    <span>Quản lý Manager</span>
                                                </a>
                                            </c:when>
                                            <c:when test="${sessionScope.userAccount.accRole eq 'Seller'}">
                                                <a href="displayorder" class="dropdown-item">
                                                    <i class="fas fa-user"></i>
                                                    <span>Quản lý Seller</span>
                                                </a>
                                            </c:when>
                                            <c:when test="${sessionScope.userAccount.accRole eq 'Seller'}">
                                                <a href="displayorder" class="dropdown-item">
                                                    <i class="fas fa-user"></i>
                                                    <span>Quản lý Seller</span>
                                                </a>
                                            </c:when>
                                            <c:when test="${sessionScope.userAccount.accRole eq 'Shipper'}">
                                                <a href="profile" class="dropdown-item">
                                                    <i class="fas fa-user"></i>
                                                    <span>Quản lý Shipper</span>
                                                </a>
                                            </c:when>
                                        </c:choose>
                                        <a href="profile" class="dropdown-item">
                                            <i class="fas fa-user"></i>
                                            <span>Thông tin cá nhân</span>
                                        </a>
                                        <a href="orders" class="dropdown-item">
                                            <i class="fas fa-shopping-bag"></i>
                                            <span>Đơn hàng đã mua</span>
                                        </a>
                                        <a href="logout" class="dropdown-item logout">
                                            <i class="fas fa-sign-out-alt"></i>
                                            <span>Đăng xuất</span>
                                        </a>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="account">
                                    <a href="login">
                                        <img src="images/account.png" width="50" height="50" alt="account" />
                                        <p class="logintext">Đăng nhập</p>
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <c:if
                            test="${empty sessionScope.userAccount or sessionScope.userAccount.accRole eq 'Customer'}">

                            <div class="card">
                                <a href="displaycart">
                                    <img src="images/card.png" width="50" height="50" alt="card" />
                                    <p class="cardtext">
                                        Giỏ Hàng
                                        <span id="cart-count" class="cartcount">${sessionScope.cartcount}</span>
                                    </p>
                                </a>
                            </div>
                        </c:if>
                    </div>
                </div>

                <nav>
                    <ul class="menu">
                        <li><a href="homepage">Trang Chủ</a></li>
                        <li><a href="listshoppet">Thú Cưng</a></li>
                        <li><a href="menu_about.jsp">Giới Thiệu</a></li>
                        <li><a href="menu_contact.jsp">Liên Hệ</a></li>
                    </ul>
                </nav>
            </div>
            <div class="form-container">
                <form id="blogForm" action="blog" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" id="action" value="submit-blog">
                    <input type="hidden" name="blogId" value="${blogId}">
                    <input type="hidden" id="feature" name="featuredImage" value="${featuredImage}">

                    <div class="card">
                        <h2 class="card-title"><i class="fa-solid fa-circle-info"></i> Thông tin cơ bản</h2>
                        <div class="form-group">
                            <label class="section-label">Tiêu đề bài viết <span class="required">*</span></label>
                            <input type="text" id="title" name="title" value="${title}" placeholder="Nhập tiêu đề...">
                            <small>Giới hạn 5 - 30 từ để tối ưu SEO</small>
                        </div>

                        <div class="form-group">
                            <label class="section-label">
                                 Trạng thái hiển thị <span class="required">*</span>
                            </label>
                            <div class="radio-group">
                                <div class="radio-option public">
                                    <input type="radio" name="status" id="statusPublic" value="Published" checked>
                                    <label for="statusPublic">Public</label>
                                </div>
                                <div class="radio-option draft">
                                    <input type="radio" name="status" id="statusDraft" value="Draft">
                                    <label for="statusDraft">Draft</label>
                                </div>
                                <div class="radio-option private">
                                    <input type="radio" name="status" id="statusPrivate" value="Hidden">
                                    <label for="statusPrivate">Hidden</label>
                                </div>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="section-label">Ảnh Thumbnail</label>
                            <img id="preview" src="${featuredImage}" alt="Ảnh đại diện bài viết"
                                style="max-width: 300px; display: ${not empty featuredImage ? 'block' : 'none'};" />

                            <input type="file" name="featuredImageFile" id="featuredImageFile" accept="image/*"
                                onchange="previewThumbnail(this)">
                            <small>Kích thước tối đa 5MB. Tỷ lệ khuyến nghị: 16:9</small>
                        </div>
                       


                    </div>

                    <div class="card">
                        <h2 class="card-title"><i class="fa-solid fa-pen-to-square"></i> Nội dung bài viết</h2>
                        <div class="form-group">
                            <label for="section-label">Nội dung chi tiết</label>
                            <textarea id="editor" name="content">${content}</textarea>
                        </div>
                    </div>

                    <div class="card">
                        <label class="section-label"><i class="fa-solid fa-cloud-arrow-up"></i> Tải ảnh</label>
                        <div class="form-group">
                            <input type="file" name="imageUpload" multiple accept="image/*">
                            <button type="submit"
                                onclick="document.getElementById('action').value = 'upload-only-image';">Tải
                                lên</button>
                        </div>

                        <c:if test="${not empty imageUploadResultList}">
                            <div class="form-group">
                                <label>Ảnh đã tải lên:</label>
                                <textarea readonly onclick="this.select()">${imageUploadResult}</textarea>
                                <div class="image-preview-vertical">
                                    <c:forEach var="img" items="${imageUploadResultList}">
                                        <img src="${img}" alt="Uploaded Image">
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn-save">SAVE CHANGE</button>
                        <button type="button" onclick="setActionAndSubmit('save-draft')" class="btn-back">← Quay lại
                            danh sách</button>
                    </div>
                </form>
            </div>


            <script>
                CKEDITOR.replace('editor', {
                    height: 400,
                    versionCheck: false
                });
            </script>
        </body>

        </html>