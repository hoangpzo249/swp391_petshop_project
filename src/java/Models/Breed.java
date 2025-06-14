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
    private double breedStatus;
    

    public Breed(int breedId, String breedName, String breedSpecies) {
        this.breedId = breedId;
        this.breedName = breedName;
        this.breedSpecies = breedSpecies;
       
        
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

   

   

   

   
    

    @Override
    public String toString() {
        return "Breed{" + "breedId=" + breedId + ", breedName=" + breedName + ", breedSpecies=" + breedSpecies + ", breedStatus=" + breedStatus + '}';
    }

    

    
    
    
}
