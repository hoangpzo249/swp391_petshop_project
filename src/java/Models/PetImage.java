/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;
import java.util.Base64;

/**
 *
 * @author Lenovo
 */
public class PetImage {
    private Integer imageId;
    private int petId;
    private byte[] imageData;
    private LocalDateTime uploadedAt;
        
    private final String defaultImage="https://ibb.co/PssxwL0t";
    
    public PetImage() {
    }

    public PetImage(Integer imageId, int petId, byte[] imageData, LocalDateTime uploadedAt) {
        this.imageId = imageId;
        this.petId = petId;
        this.imageData = imageData;
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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getDefaultImage() {
        return defaultImage;
    }
    
    @Override
    public String toString() {
        return "PetImage{" + "imageId=" + imageId + ", petId=" + petId + ", imageData=" + imageData + ", uploadedAt=" + uploadedAt + '}';
    }
    
    public String getPetImageBase64() {
        if (imageData != null) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageData);
        }
        return "images/placeholder.jpg";
    }
}
