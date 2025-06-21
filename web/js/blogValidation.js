/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    const saveBtn = document.querySelector(".btn-save");

    if (saveBtn) {
        saveBtn.addEventListener("click", function (e) {
            const status = document.querySelector('input[name="status"]:checked').value;
            const title = document.getElementById("title").value.trim();
            const content = CKEDITOR.instances.editor.getData().trim();
            const featuredImage = document.getElementById("featuredImageFile").value.trim();

            if (status === "Published") {
                let message = "";
                if (title === "") message += "- Tiêu đề không được để trống\n";
                if (content === "") message += "- Nội dung không được để trống\n";
                if (featuredImage === "") message += "- Ảnh thumbnail không được để trống\n";

                if (message !== "") {
                    alert("Vui lòng điền đầy đủ thông tin trước khi đăng bài:\n" + message);
                    e.preventDefault();
                }
            }
        });
    }
});
