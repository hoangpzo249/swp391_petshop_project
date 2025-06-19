/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author AD
 */

import java.util.Date;

public class BlogPost {
    private int blogId;
    private String title;
    private String content;
    private int authorId;
    private Date createdDate;
    private String status;
    private String featuredImage;

    public BlogPost() {}

    public BlogPost(int blogId, String title, String content, int authorId, Date createdDate, String status, String featuredImage) {
        this.blogId = blogId;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdDate = createdDate;
        this.status = status;
        this.featuredImage = featuredImage;
    }

    
    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }
}
