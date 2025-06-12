
import DAO.PetDAO;
import Models.Pet;
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

    public static void main(String[] args) {
//        List<Pet> list=getPets();
//        for (Pet pet : list) {
//            System.out.println(pet.toString());
//        }
System.out.println(updateStatus(1, 0));
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
