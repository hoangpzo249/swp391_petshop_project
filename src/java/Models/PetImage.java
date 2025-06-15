/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;

/**
 *
 * @author Lenovo
 */
public class PetImage {

    private Integer imageId;
    private int petId;
    private String imagePath;
    private LocalDateTime uploadedAt;

    public PetImage() {
    }

    public PetImage(Integer imageId, int petId, String imagePath, LocalDateTime uploadedAt) {
        this.imageId = imageId;
        this.petId = petId;
        this.imagePath = imagePath;
        this.uploadedAt = uploadedAt;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

}
