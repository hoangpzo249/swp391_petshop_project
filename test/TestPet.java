
import DAO.PetDAO;
import DAO.PetImageDAO;
import Models.Pet;
import Models.PetImage;
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

    public static void main(String[] args) {
        List<PetImage> list=getImages(1);
        for (PetImage petImage : list) {
            System.out.println(petImage.toString());
        }
    }
    
    private static List<PetImage> getImages(int id) {
        return _daoimage.getPetImagesById(id);
    }
    
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
