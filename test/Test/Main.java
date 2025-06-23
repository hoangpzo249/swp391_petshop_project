package Test;

import DAO.DiscountDAO;
import Models.Discount;

import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DiscountDAO dao = new DiscountDAO();

        // 1. Tạo mới đối tượng Discount để thêm
        Discount newDiscount = new Discount();
        newDiscount.setDiscountCode("SUMMER");
        newDiscount.setDiscountType("Percent");
        newDiscount.setDiscountValue(50.0);
        newDiscount.setDescription("Giảm 50% cho mùa hè");
        newDiscount.setValidFrom(Date.valueOf("2025-06-01"));
        newDiscount.setValidTo(Date.valueOf("2025-06-30"));
        newDiscount.setMinOrderAmount(100000.0);
        newDiscount.setMaxUsage(100);
        newDiscount.setUsageCount(0);
        newDiscount.setActive(true);
        newDiscount.setMaxValue(50000.0);

        boolean added = dao.addDiscount(newDiscount);
        System.out.println("Thêm mã giảm giá: " + (added ? "THÀNH CÔNG" : "THẤT BẠI"));

        // 2. Lấy tất cả discount
        List<Discount> all = dao.getAllDiscounts();
        System.out.println("\nDanh sách mã giảm giá:");
        for (Discount d : all) {
            System.out.println(d.getDiscountId() + " | " + d.getDiscountCode() + " | " + d.getDiscountValue() + " | " + d.getValidFrom() + " → " + d.getValidTo());
        }

        // 3. Lấy discount theo ID
        if (!all.isEmpty()) {
            int idToGet = all.get(0).getDiscountId();
            Discount fetched = dao.getDiscountById(idToGet);
            if (fetched != null) {
                System.out.println("\nMã theo ID " + idToGet + ": " + fetched.getDiscountCode());
            } else {
                System.out.println("Không tìm thấy mã với ID: " + idToGet);
            }

            // 4. Cập nhật discount
            fetched.setDescription("Cập nhật mô tả mới");
            boolean updated = dao.updateDiscount(fetched);
            System.out.println("Cập nhật mã ID " + idToGet + ": " + (updated ? "THÀNH CÔNG" : "THẤT BẠI"));

//            // 5. Xoá discount
//            boolean deleted = dao.deleteDiscount(idToGet);
//            System.out.println("Xóa mã ID " + idToGet + ": " + (deleted ? "THÀNH CÔNG" : "THẤT BẠI"));
        }
    }
}
