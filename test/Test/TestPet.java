///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package Test;
//
//import DAO.AccountDAO;
//import DAO.BreedDAO;
//import DAO.OrderDAO;
//import DAO.PetDAO;
//import DAO.ShipperDAO;
//import Models.Breed;
//import Models.Order;
//import Models.Pet;
//import Models.Shipper;
//import java.util.List;
//
///**
// *
// * @author Lenovo
// */
//public class TestPet {
//
//    static PetDAO dao = new PetDAO();
//    static OrderDAO _daoorder = new OrderDAO();
//    static BreedDAO _daobreed = new BreedDAO();
//    static AccountDAO _daoacc = new AccountDAO();
//    static ShipperDAO _daoshipper = new ShipperDAO();
//
//    public static void main(String[] args) {
//        for (Shipper shipper : getShippers()) {
//            System.out.println(shipper.getShipperAccount().getAccId() + " - " + shipper.getShipperAccount().getAccFname() + " - " + shipper.getShipperAccount().getAccLname() + " - " + shipper.getShipperNote() + " - " + shipper.getLastDeliveryTime() + " - " + shipper.getCurrentShippingOrders());
//        }
//    }
//
//    private static List<Shipper> getShippers() {
//        return _daoshipper.getAvailableShippers();
//    }
//
//    private static void shipperAcc(int id) {
//        _daoacc.shipperAccount(id);
//    }
//
//    private static boolean updateBreed(Breed breed) {
//        return _daobreed.updateBreedById(breed);
//    }
//
//    private static Breed getBreedById(int id) {
//        return _daobreed.getBreedById(id);
//    }
//
//    private static Pet getPet(int id) {
//        return dao.getPetById(id);
//    }
//
//    private static Order getOrder(String id) {
//        return _daoorder.getOrderById(id);
//    }
//
//    private static List<Breed> getBreed() {
//        return _daobreed.get6BreedHot();
//    }
//}
