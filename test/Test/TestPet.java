/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import DAO.BreedDAO;
import DAO.OrderDAO;
import DAO.PetDAO;
import Models.Breed;
import Models.Order;
import Models.Pet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class TestPet {

    static PetDAO dao = new PetDAO();
    static OrderDAO _daoorder = new OrderDAO();
    static BreedDAO _daobreed = new BreedDAO();

    public static void main(String[] args) {
//        System.out.println(getPet(80).getBreedId());
//        System.out.println(getOrder("1").toString());
List<Breed> list=getBreed();
        for (Breed breed : list) {
            System.out.println(breed.getBreedName()+", "+breed.displayBreedImage());
        }
    }

    private static Pet getPet(int id) {
        return dao.getPetById(id);
    }

    private static Order getOrder(String id) {
        return _daoorder.getOrderById(id);
    }
    
    private static List<Breed> getBreed() {
        return _daobreed.get6BreedHot();
    }
}
