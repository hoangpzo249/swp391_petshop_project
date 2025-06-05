/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author QuangAnh
 */
public class Pet {

    private int petId;
    private String petName;
    private Date petDob;
    private String petOrigin;
    private String petGender;
    private boolean petAvailability;
    private String petColor;
    private boolean petVaccination;
    private String petDescription;
    private double petPrice;
    private int breedId;
    private String createdBy;

    // Getters & Setters...

    public Pet() {
    }

    public Pet(int petId, String petName, Date petDob, String petOrigin, String petGender, boolean petAvailability, String petColor, boolean petVaccination, String petDescription, double petPrice, int breedId, String createdBy) {
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

    public boolean isPetAvailability() {
        return petAvailability;
    }

    public void setPetAvailability(boolean petAvailability) {
        this.petAvailability = petAvailability;
    }

    public String getPetColor() {
        return petColor;
    }

    public void setPetColor(String petColor) {
        this.petColor = petColor;
    }

    public boolean isPetVaccination() {
        return petVaccination;
    }

    public void setPetVaccination(boolean petVaccination) {
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Pet{" + "petId=" + petId + ", petName=" + petName + ", petDob=" + petDob + ", petOrigin=" + petOrigin + ", petGender=" + petGender + ", petAvailability=" + petAvailability + ", petColor=" + petColor + ", petVaccination=" + petVaccination + ", petDescription=" + petDescription + ", petPrice=" + petPrice + ", breedId=" + breedId + ", createdBy=" + createdBy + '}';
    }
    
}


