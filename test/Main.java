import DAO.PetDAO;
import Models.Pet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PetDAO dao = new PetDAO();

        // Gọi hàm filterPets với toàn bộ điều kiện để test lọc theo DOB
        String breed = "";             // tất cả giống
        String species = "";           // tất cả loài
        String search = "";            // không tìm từ khóa
        int minPrice = 0;
        int maxPrice = 0;
        String sort = "az";            // sắp xếp theo tên
        String gender = "Male";            // tất cả giới tính
        String color = "";             // tất cả màu
        String origin = "";            // tất cả xuất xứ

        // Lọc theo ngày sinh từ 01/01/2021 đến 31/12/2022
        String dobFrom = "2021-01-01";    // <-- test thay đổi ngày tại đây
        String dobTo = "2022-12-31";

        String vaccination = "";       // tất cả

        List<Pet> pets = dao.filterPets(
                breed, species, search, minPrice, maxPrice,
                sort, gender, color, origin, dobFrom, dobTo, vaccination
        );

        System.out.println("===== KẾT QUẢ LỌC PET =====");
        System.out.println("Tổng số pet lọc được: " + pets.size());
        for (Pet pet : pets) {
            System.out.println(
                    "Tên: " + pet.getPetName()
                    + " | Giống: " + pet.getBreedName()
                    + " | Giá: " + pet.getPetPrice()
                    + " | Ngày sinh: " + pet.getPetDob()
                    + " | Xuất xứ: " + pet.getPetOrigin()
                    + " | Vaccine: " + (pet.getPetVaccination() == 1 ? "Đã tiêm" : "Chưa tiêm")
            );
        }
    }
}
