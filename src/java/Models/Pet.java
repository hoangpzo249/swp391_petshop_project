/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class Pet {

    private int petId;
    private String petName;
    private Date petDob;
    private String petOrigin;
    private String petGender;
    private int petAvailability;
    private int petStatus;
    private String petColor;
    private int petVaccination;
    private String petDescription;
    private double petPrice;
    private int breedId;
    private int createdBy;
    private Breed breed;
    private double priceAtOrder;
    private String breedName;
    private List<byte[]> images;

    public Pet() {
    }

    public Pet(int petId, String petName, Date petDob, String petOrigin, String petGender,
            int petAvailability, String petColor, int petVaccination, String petDescription,
            double petPrice, int breedId, int createdBy, int petStatus) {
        this.petId = petId;
        this.petName = petName;
        this.petDob = petDob;
        this.petOrigin = petOrigin;
        this.petGender = petGender;
        this.petAvailability = petAvailability;
        this.petColor = petColor;
        this.petVaccination = petVaccination;
        this.petDescription = petDescription;
        this.petPrice = petPrice;
        this.breedId = breedId;
        this.createdBy = createdBy;
        this.petStatus = petStatus;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Date getPetDob() {
        return petDob;
    }

    public void setPetDob(Date petDob) {
        this.petDob = petDob;
    }

    public String getPetOrigin() {
        return petOrigin;
    }

    public void setPetOrigin(String petOrigin) {
        this.petOrigin = petOrigin;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public int getPetAvailability() {
        return petAvailability;
    }

    public void setPetAvailability(int petAvailability) {
        this.petAvailability = petAvailability;
    }

    public int getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(int petStatus) {
        this.petStatus = petStatus;
    }

    public String getPetColor() {
        return petColor;
    }

    public void setPetColor(String petColor) {
        this.petColor = petColor;
    }

    public int getPetVaccination() {
        return petVaccination;
    }

    public void setPetVaccination(int petVaccination) {
        this.petVaccination = petVaccination;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public double getPetPrice() {
        return petPrice;
    }

    public void setPetPrice(double petPrice) {
        this.petPrice = petPrice;
    }

    public int getBreedId() {
        return breedId;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public double getPriceAtOrder() {
        return priceAtOrder;
    }

    public void setPriceAtOrder(double priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Pet {"
                + "ID=" + petId
                + ", Name='" + petName + '\''
                + ", DOB=" + petDob
                + ", Gender='" + petGender + '\''
                + ", Origin='" + petOrigin + '\''
                + ", Color='" + petColor + '\''
                + ", Vaccinated=" + (petVaccination == 1 ? "Yes" : "No")
                + ", Price=" + petPrice
                + ", BreedId=" + breedId
                + ", Description='" + petDescription + '\''
                + ", CreatedBy=" + createdBy
                + '}';
    }

    public String getFirstImage() {
        if (images != null && !images.isEmpty()) {
            byte[] imageData = images.get(0);
            if (imageData != null && imageData.length > 0) {
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                return "data:image/jpeg;base64," + base64Image;
            }
        }
        return "images/defaultcatdog.png";
    }

    public String getImage(byte[] imageData) {
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        return "data:image/jpeg;base64," + base64Image;
    }
}
