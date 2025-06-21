<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm/Sửa Blog</title>
    <script src="ckeditor/ckeditor.js"></script>
</head>
<body>

<h2><%= request.getAttribute("blogId") != null ? "🛠️ Sửa Blog" : "➕ Thêm Blog" %></h2>

<!-- ✅ Form chính để submit bài blog -->
<form action="blog" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" id="action" value="submit-blog">

    <input type="hidden" name="blogId" value="${blogId}">
    <input type="text" name="title" value="${title}" placeholder="Tiêu đề"><br>

    <textarea id="editor" name="content">${content}</textarea><br>

    <input type="hidden" name="featuredImage" value="${featuredImage}">
    <input type="file" name="featuredImageFile" accept="image/*"><br>

    <select name="status">
        <option value="Published" ${status eq 'Published' ? 'selected' : ''}>Hiện</option>
        <option value="Hidden" ${status eq 'Hidden' ? 'selected' : ''}>Ẩn</option>
    </select><br>

    <button type="submit">Lưu</button>

    <hr>
    <h4>📤 Upload ảnh lên ImgBB để chèn vào nội dung</h4>
    <input type="file" name="imageUpload" accept="image/*">
    <button type="submit" onclick="document.getElementById('action').value='upload-only-image';">Tải lên</button>
</form>

<c:if test="${not empty imageUploadResult && imageUploadResult ne 'null'}">
    <p>✅ Ảnh đã tải lên:</p>
    <input type="text" value="${imageUploadResult}" style="width:80%" readonly onclick="this.select()">
    <br>
    <img src="${imageUploadResult}" style="max-width: 300px; margin-top:10px;">
</c:if>



<a href="blog">← Quay lại danh sách</a>

<script>
    CKEDITOR.replace('editor', {
        versionCheck: false,
        height: 400
    });
</script>

</body>
</html>
