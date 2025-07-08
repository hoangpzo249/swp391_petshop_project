/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import DAO.AccountDAO;
import DAO.BreedDAO;
import DAO.InvoiceDAO;
import DAO.OrderDAO;
import DAO.PetDAO;
import DAO.ShipperDAO;
import Models.Breed;
import Models.Invoice;
import Models.Order;
import Models.Pet;
import Models.Revenue;
import Models.Shipper;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class TestPet {

    static PetDAO dao = new PetDAO();
    static OrderDAO _daoorder = new OrderDAO();
    static BreedDAO _daobreed = new BreedDAO();
    static AccountDAO _daoacc = new AccountDAO();
    static ShipperDAO _daoshipper = new ShipperDAO();
    static InvoiceDAO _daoinvoice = new InvoiceDAO();

    public static void main(String[] args) {
//        for (Breed breed : topBreed()) {
//            System.out.println(breed.getBreedId() + " - " + breed.getBreedName() + " - " + breed.getTotalPurchases() + " - " + breed.getTotalRevenue());
//        }

        for (Revenue i : revenueDate()) {
            System.out.println(i.getDate() + " - " + i.getTotalRevenue() + " D");
        }
        for (Revenue i : revenueWeek()) {
            System.out.println(i.getDate() + " - " + i.getTotalRevenue() + " W");
        }
        for (Revenue i : revenueMonth()) {
            System.out.println(i.getDate() + " - " + i.getTotalRevenue() + " M");
        }
    }

    private static List<Revenue> revenueMonth() {
        return _daoorder.getRevenueByMonth(new Date(0).valueOf("2025-01-01"), new Date(0).valueOf("2025-12-01"));
    }

    private static List<Revenue> revenueWeek() {
        return _daoorder.getRevenueByWeek(new Date(0).valueOf("2025-01-01"), new Date(0).valueOf("2025-12-01"));
    }

    private static List<Revenue> revenueDate() {
        return _daoorder.getRevenueByDay(new Date(0).valueOf("2025-01-01"), new Date(0).valueOf("2025-12-01"));
    }

    private static List<Breed> topBreed() {
        return _daobreed.getTopPricedBreeds(new Date(0).valueOf("2025-01-01"), new Date(0).valueOf("2025-12-01"), 10);
    }

    private static List<Order> top10Order() {
        return _daoorder.getTopPricedOrders(new Date(0).valueOf("2025-01-01"), new Date(0).valueOf("2025-12-01"), 10);
    }

    private static Breed topBreed(Date start, Date end) {
        return _daobreed.mostPopularBreed(start, end);
    }

    private static int totalOrders(Date start, Date end) {
        return _daoorder.totalOrdersFulfilled(start, end);
    }

    private static int totalPetsSold(Date start, Date end) {
        return dao.totalPetsSold(start, end);
    }

    private static Invoice getInvoiceContent(int id) {
        return _daoinvoice.getInvoiceDetailById(id);
    }

    private static List<Shipper> getShippers() {
        return _daoshipper.getAvailableShippers();
    }

    private static void shipperAcc(int id) {
        _daoacc.shipperAccount(id);
    }

    private static boolean updateBreed(Breed breed) {
        return _daobreed.updateBreedById(breed);
    }

    private static Breed getBreedById(int id) {
        return _daobreed.getBreedById(id);
    }

    private static Pet getPet(int id) {
        return dao.getPetById(id);
    }

//    private static Order getOrder(String id) {
//        return _daoorder.getOrderById(id);
//    }
    private static List<Breed> getBreed() {
        return _daobreed.get6BreedHot();
    }
}
