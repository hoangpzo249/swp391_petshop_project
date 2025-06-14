///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//
///**
// *
// * @author ADMIN
// */
//import DAO.BreedDAO;
//import Models.Breed;
//
//import java.util.List;
//
//public class BreedDAOTest {
//    public static void main(String[] args) {
//        BreedDAO breedDAO = new BreedDAO();
//
//       
//        System.out.println(" Danh sách giống chó:");
//        List<Breed> dogBreeds = breedDAO.displayDogBreeds();
//        for (Breed breed : dogBreeds) {
//            System.out.println("- " + breed.getBreedName() + " | ID: " + breed.getBreedId()) ;
//        }
//
//        
//        System.out.println(" Danh sách giống mèo:");
//        List<Breed> catBreeds = breedDAO.displayCatBreeds();
//        for (Breed breed : catBreeds) {
//            System.out.println("- " + breed.getBreedName() + " | ID: " + breed.getBreedId()) ;
//        }
//
//        
//       
//    }
//}
