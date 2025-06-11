
import DAO.PetDAO;
import java.util.ArrayList;

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
        ArrayList<Integer> list=new ArrayList<>();
        list.add(5);
        list.add(39);
        System.out.println(update(list));
    }
    
    private static boolean update(ArrayList<Integer> list) {
        return _dao.updatePetAvailabilityById(list);
    }
}
