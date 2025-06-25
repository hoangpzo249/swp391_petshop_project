/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Base64;

/**
 *
 * @author Lenovo
 */
public class Breed {

    private int breedId;
    private String breedName;
    private String breedSpecies;
    private boolean breedStatus;
    private byte[] breedImage;
    private int totalPurchases;

    public Breed() {
    }

    public Breed(int breedId, String breedName, String breedSpecies, boolean breedStatus, byte[] breedImage) {
        this.breedId = breedId;
        this.breedName = breedName;
        this.breedSpecies = breedSpecies;
        this.breedStatus = breedStatus;
        this.breedImage = breedImage;
    }

    public Breed(int breedId, String breedName, String breedSpecies, boolean breedStatus, byte[] breedImage, int totalPurchases) {
        this.breedId = breedId;
        this.breedName = breedName;
        this.breedSpecies = breedSpecies;
        this.breedStatus = breedStatus;
        this.breedImage = breedImage;
        this.totalPurchases = totalPurchases;
    }

    public int getBreedId() {
        return breedId;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getBreedSpecies() {
        return breedSpecies;
    }

    public void setBreedSpecies(String breedSpecies) {
        this.breedSpecies = breedSpecies;
    }

    public boolean isBreedStatus() {
        return breedStatus;
    }

    public void setBreedStatus(boolean breedStatus) {
        this.breedStatus = breedStatus;
    }

    public byte[] getBreedImage() {
        return breedImage;
    }

    public void setBreedImage(byte[] breedImage) {
        this.breedImage = breedImage;
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(int totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

//    public String getBreedImageBase64() {
//        if (breedImage != null) {
//            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(breedImage);
//        }
//        if (breedSpecies != null && breedSpecies.equals("Chó")) {
//            return "images/defaultdog.jpg";
//        } else {
//            return "images/defaultcat.jpg";
//        }
//    }
    @Override
    public String toString() {
        return "Breed{"
                + "breedId=" + breedId
                + ", breedName=" + breedName
                + ", breedSpecies=" + breedSpecies
                + ", breedStatus=" + breedStatus
                + '}';
    }
    
    public String displayBreedImage() {
        String base64Image = Base64.getEncoder().encodeToString(breedImage);
        return "data:image/jpeg;base64," + base64Image;
    }
}
