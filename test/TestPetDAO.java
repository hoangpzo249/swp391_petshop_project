

import DAO.PetDAO;
import Models.Pet;

import java.util.List;

public class TestPetDAO {
    public static void main(String[] args) {
        PetDAO dao = new PetDAO();

        System.out.println("Test getAllPets():");
        List<Pet> allPets = dao.getAllPets();
        System.out.println("Tổng số thú cưng: " + allPets.size());

        for (Pet pet : allPets) {
            System.out.println(pet);
        }

        System.out.println("\n Test getPetById(1):");
        Pet petById = dao.getPetById(1);
        System.out.println(petById);

        

        System.out.println("\n Test getImagesByPetId(petId = 1):");
        List<byte[]> images = dao.getImagesByPetId(1);
        System.out.println("Số ảnh: " + images.size());

        System.out.println("\n Test getAllOrigins():");
        List<String> origins = dao.getAllOrigins();
        for (String origin : origins) {
            System.out.println(origin);
        }

        System.out.println("\n Test getAllGenders():");
        List<String> genders = dao.getAllGenders();
        for (String gender : genders) {
            System.out.println(gender);
        }

        System.out.println("\n Test getAllColors():");
        List<String> colors = dao.getAllColors();
        for (String color : colors) {
            System.out.println(color);
        }

        System.out.println("\n Test getAllAgeRanges():");
        List<String> ageRanges = dao.getAllAgeRanges();
        for (String range : ageRanges) {
            System.out.println(range);
        }

        System.out.println("\n Test getAllVaccinationStatus():");
        List<String> vaccinationStatus = dao.getAllVaccinationStatus();
        for (String status : vaccinationStatus) {
            System.out.println(status);
        }
          String breed = "3"; 
        String species = ""; 
        String search = ""; 
        int num1 = 0; 
        int num2 = 0; 
        String sort = "price-asc"; 
        String gender = "Female"; 
        String color = ""; 
        String origin = ""; 
        String ageRange = ""; 
        String vaccinationStatus1 = "Đã tiêm"; 

        
        List<Pet> pets = dao.filterPets(breed, species, search, num1, num2, sort,
                gender, color, origin, ageRange, vaccinationStatus1);

        
        System.out.println("Số thú cưng tìm được: " + pets.size());
        for (Pet pet : pets) {
            System.out.println(pet); 
        }
    }
 
    }

