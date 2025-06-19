/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BlogPostDAO;
import Models.BlogPost;
import Utils.ImgBbUploader;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/**
 *
 * @author AD
 */
@MultipartConfig
public class BlogServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BlogServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        BlogPostDAO dao = new BlogPostDAO();

        try {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                BlogPost blog = dao.getBlogById(id);
                request.setAttribute("blogId", blog.getBlogId());
                request.setAttribute("title", blog.getTitle());
                request.setAttribute("content", blog.getContent());
                request.setAttribute("featuredImage", blog.getFeaturedImage());
                request.setAttribute("status", blog.getStatus());
                request.getRequestDispatcher("blog_form.jsp").forward(request, response);

            } else if ("add".equals(action)) {
                request.getRequestDispatcher("blog_form.jsp").forward(request, response);

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.deleteBlog(id);
                response.sendRedirect("blog");

            } else if ("toggle".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String status = request.getParameter("status");
                dao.updateStatus(id, status);
                response.sendRedirect("blog");
            } else if ("view".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                BlogPost blog = dao.getBlogById(id);
                request.setAttribute("title", blog.getTitle());
                request.setAttribute("content", blog.getContent());
                request.setAttribute("featuredImage", blog.getFeaturedImage());
                request.setAttribute("status", blog.getStatus());
                request.setAttribute("createdDate", blog.getCreatedDate());
                request.getRequestDispatcher("blog_view.jsp").forward(request, response);
            } else {
                List<BlogPost> blogList = dao.getAllBlogs();
                request.setAttribute("blogList", blogList);
                request.getRequestDispatcher("blog_list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BlogPostDAO dao = new BlogPostDAO();
        request.setCharacterEncoding("UTF-8");

        String blogIdStr = request.getParameter("blogId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String status = request.getParameter("status");
        
        
        String action = request.getParameter("action");
        if ("upload-only-image".equals(action)) {
            request.setAttribute("blogId", blogIdStr);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("featuredImage", request.getParameter("featuredImage"));
            request.setAttribute("status", status);

            Part imagePart = request.getPart("imageUpload");
            String imageUrl = null;

            if (imagePart != null && imagePart.getSize() > 0) {
                File tempFile = File.createTempFile("upload-", ".tmp");
                try (InputStream is = imagePart.getInputStream()) {
                    Files.copy(is, tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }

                imageUrl = ImgBbUploader.uploadImage(tempFile);
                tempFile.delete();
            }

            if (imageUrl != null) {
                request.setAttribute("imageUploadResult", imageUrl);
            }

            request.getRequestDispatcher("blog_form.jsp").forward(request, response);
            return;
        }
        String featuredImage=null;

        // 👉 Nếu là thêm mới thì xử lý ảnh upload từ form
        if (blogIdStr == null || blogIdStr.isEmpty()) {
            Part imagePart = request.getPart("featuredImageFile");

            if (imagePart != null && imagePart.getSize() > 0) {
                try {
                    File tempFile = File.createTempFile("upload-", ".tmp");
                    try (InputStream is = imagePart.getInputStream()) {
                        Files.copy(is, tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    }

                    featuredImage = ImgBbUploader.uploadImage(tempFile);
                    if (featuredImage == null) {
                        featuredImage = "https://i.ibb.co/NggxZvb7/defaultcatdog.png";
                    }

                    tempFile.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                    featuredImage = "https://i.ibb.co/NggxZvb7/defaultcatdog.png";
                }
            } else {
                featuredImage = "https://i.ibb.co/NggxZvb7/defaultcatdog.png";
            }
        } else {
            // Nếu là cập nhật, lấy link ảnh cũ từ form
            featuredImage = request.getParameter("featuredImage");
        }

        BlogPost blog = new BlogPost();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setFeaturedImage(featuredImage);
        blog.setStatus(status);
        blog.setAuthorId(1); // hardcode

        if (blogIdStr != null && !blogIdStr.isEmpty()) {
            blog.setBlogId(Integer.parseInt(blogIdStr));
            dao.updateBlog(blog);
        } else {
            dao.addBlog(blog);
        }

        response.sendRedirect("blog");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
