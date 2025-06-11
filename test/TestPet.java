
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
        List<Pet> list=getAllPets();
        System.out.println(list.size());
    }
    
    private static boolean update(ArrayList<Integer> list) {
        return _dao.updatePetAvailabilityById(list);
    }
    
    private static List<Pet> getPets() {
        return _dao.filterPetsForSeller(null, null, null, null, null, null);
    }
    private static List<Pet> getAllPets() {
        return _dao.getAllPets();
    }
    
    
}
