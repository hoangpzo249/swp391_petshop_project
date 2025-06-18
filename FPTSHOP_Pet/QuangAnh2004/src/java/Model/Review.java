/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author QuangAnh
 */
public class Review {
    private int reviewId;
    private int accId;
    private int rating;
    private String reviewText;
    private Date reviewDate;

    public Review() {
    }

    public Review(int reviewId, int accId, int rating, String reviewText, Date reviewDate) {
        this.reviewId = reviewId;
        this.accId = accId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "Review{" + "reviewId=" + reviewId + ", accId=" + accId + ", rating=" + rating + ", reviewText=" + reviewText + ", reviewDate=" + reviewDate + '}';
    }
    
}