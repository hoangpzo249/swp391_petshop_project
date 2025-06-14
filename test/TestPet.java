
import DAO.PetDAO;
import DAO.PetImageDAO;
import DAO.PetImagePathDAO;
import Models.Pet;
import Models.PetImage;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Lenovo
 */
public class TestPet {

    private static PetDAO _dao = new PetDAO();
    private static PetImageDAO _daoimage = new PetImageDAO();
    private static PetImagePathDAO _daoimage2=new PetImagePathDAO();

    public static void main(String[] args) {
        System.out.println(countImage(1));
    }
    
    private static int countImage(int petId) {
        return _daoimage2.countImagesById(petId);
    }
    
    private static int addPet(Pet pet) {
        return _dao.addPet(pet);
    }
    
    private static boolean updatePet(int id, Pet pet) {
        return _dao.updatePetById(id, pet);
    }
    
//    private static List<PetImage> getImages(int id) {
//        return _daoimage.getPetImagesById(id);
//    }
    
    private static int petInPendingOrder(int id) {
        return _dao.getPendingOrderIdForPet(id);
    }
    
    private static boolean updateStatus(int id, int status) {
        return _dao.updatePetStatusById(id, status);
    }
    
    private static boolean update(List<Integer> list) {
        return _dao.updatePetAvailabilityById(list, 1);
    }
    
    private static List<Pet> getPets() {
        return _dao.filterPetsForSeller(null, null, null, null, null, null, null);
    }
    private static List<Pet> getAllPets() {
        return _dao.getAllPets();
    }
//    private static List<Pet> filterPets() {
////        return _dao.f();
//    }
    
    
    
}
