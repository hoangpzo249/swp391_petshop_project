// package Test;

// <<<<<<< Updated upstream
// import DAO.DiscountDAO;
// import DAO.PetDAO;
// import Models.Discount;

// import java.sql.Date;
// =======
// import DAO.DBContext;
// import DAO.OrderDAO;
// import Models.Order;
// import java.sql.Connection;
// import java.sql.ResultSet;
// import java.sql.PreparedStatement;
// import java.util.ArrayList;
// >>>>>>> Stashed changes
// import java.util.List;
// import java.util.logging.Level;
// import java.util.logging.Logger;

// public class Main {

//     public static void main(String[] args) {
// <<<<<<< Updated upstream
//         DiscountDAO dao = new DiscountDAO();
//         PetDAO deopet=new PetDAO();

//         // 1. Tạo mới đối tượng Discount để thêm
//         Discount newDiscount = new Discount();
//         newDiscount.setDiscountCode("SUMMER");
//         newDiscount.setDiscountType("Percent");
//         newDiscount.setDiscountValue(50.0);
//         newDiscount.setDescription("Giảm 50% cho mùa hè");
//         newDiscount.setValidFrom(Date.valueOf("2025-06-01"));
//         newDiscount.setValidTo(Date.valueOf("2025-06-30"));
//         newDiscount.setMinOrderAmount(100000.0);
//         newDiscount.setMaxUsage(100);
//         newDiscount.setUsageCount(0);
//         newDiscount.setActive(true);
//         newDiscount.setMaxValue(50000.0);

//         boolean added = dao.addDiscount(newDiscount);
//         System.out.println("Thêm mã giảm giá: " + (added ? "THÀNH CÔNG" : "THẤT BẠI"));

//         // 2. Lấy tất cả discount
//         List<Discount> all = dao.getAllDiscounts();
//         System.out.println("\nDanh sách mã giảm giá:");
//         for (Discount d : all) {
//             System.out.println(d.getDiscountId() + " | " + d.getDiscountCode() + " | " + d.getDiscountValue() + " | " + d.getValidFrom() + " → " + d.getValidTo());
// =======
//         OrderDAO odao = new OrderDAO();
//         List<Order> orderList = odao.getOrderCus(3);
//         for (Order order : orderList) {
//             System.err.println(order);
// >>>>>>> Stashed changes
//         }

// //        DiscountDAO dao = new DiscountDAO();
// //
// //        // 1. Tạo mới đối tượng Discount để thêm
// //        Discount newDiscount = new Discount();
// //        newDiscount.setDiscountCode("SUMMER");
// //        newDiscount.setDiscountType("Percent");
// //        newDiscount.setDiscountValue(50.0);
// //        newDiscount.setDescription("Giảm 50% cho mùa hè");
// //        newDiscount.setValidFrom(Date.valueOf("2025-06-01"));
// //        newDiscount.setValidTo(Date.valueOf("2025-06-30"));
// //        newDiscount.setMinOrderAmount(100000.0);
// //        newDiscount.setMaxUsage(100);
// //        newDiscount.setUsageCount(0);
// //        newDiscount.setActive(true);
// //        newDiscount.setMaxValue(50000.0);
// //
// //        boolean added = dao.addDiscount(newDiscount);
// //        System.out.println("Thêm mã giảm giá: " + (added ? "THÀNH CÔNG" : "THẤT BẠI"));
// //
// //        // 2. Lấy tất cả discount
// //        List<Discount> all = dao.getAllDiscounts();
// //        System.out.println("\nDanh sách mã giảm giá:");
// //        for (Discount d : all) {
// //            System.out.println(d.getDiscountId() + " | " + d.getDiscountCode() + " | " + d.getDiscountValue() + " | " + d.getValidFrom() + " → " + d.getValidTo());
// //        }
// //
// //        // 3. Lấy discount theo ID
// //        if (!all.isEmpty()) {
// //            int idToGet = all.get(0).getDiscountId();
// //            Discount fetched = dao.getDiscountById(idToGet);
// //            if (fetched != null) {
// //                System.out.println("\nMã theo ID " + idToGet + ": " + fetched.getDiscountCode());
// //            } else {
// //                System.out.println("Không tìm thấy mã với ID: " + idToGet);
// //            }
// //
// //            // 4. Cập nhật discount
// //            fetched.setDescription("Cập nhật mô tả mới");
// //            boolean updated = dao.updateDiscount(fetched);
// //            System.out.println("Cập nhật mã ID " + idToGet + ": " + (updated ? "THÀNH CÔNG" : "THẤT BẠI"));
// //
// ////            // 5. Xoá discount
// ////            boolean deleted = dao.deleteDiscount(idToGet);
// ////            System.out.println("Xóa mã ID " + idToGet + ": " + (deleted ? "THÀNH CÔNG" : "THẤT BẠI"));
// //        }
//     }

//     Connection conn;
//     PreparedStatement ps;
//     ResultSet rs;

//     public List<Order> getOrderCus(int accId) {
//         Connection conn = null;
//         PreparedStatement ps = null;
//         DBContext db = new DBContext();
//         List<Order> list = new ArrayList<>();
//         try {
//             conn = db.getConnection();
//             String sql = "SELECT o.orderId, o.accId, o.orderDate, o.orderStatus, o.customerName, o.customerEmail, o.customerPhone,\n"
//                     + "o.customerAddress, o.shipperId, o.paymentMethod, SUM(c.priceAtOrder) AS totalPrice, o.rejectionReason\n"
//                     + "from ordertb o\n"
//                     + "join OrderContentTB c on o.orderid=c.orderid\n"
//                     + "join pettb p on c.petid=p.petid\n"
//                     + "where accId =?\n"
//                     + "group by o.orderId, o.accId, o.orderDate, o.orderStatus, o.customerName, o.customerEmail, o.customerPhone,\n"
//                     + "o.customerAddress, o.shipperId, o.paymentMethod, o.rejectionReason";
//             ps = conn.prepareStatement(sql);
//             ps.setInt(1, accId);
//             rs = ps.executeQuery();
//             while (rs.next()) {
//                 Order o = new Order();
//                 o.setOrderId(rs.getInt("orderId"));
//                 o.setAccId(rs.getInt("accId"));
//                 o.setOrderDate(rs.getTimestamp("orderDate"));
//                 o.setOrderStatus(rs.getString("orderStatus"));
//                 o.setCustomerName(rs.getString("customerName"));
//                 o.setCustomerEmail(rs.getString("customerEmail"));
//                 o.setCustomerPhone(rs.getString("customerPhone"));
//                 o.setCustomerAddress(rs.getString("customerAddress"));
//                 o.setShipperId(rs.getInt("shipperId"));
//                 o.setPaymentMethod(rs.getString("paymentMethod"));
//                 o.setPaymentStatus(rs.getString("paymentStatus"));
//                 o.setRejectionReason(rs.getString("rejectionReason"));
//                 o.setTotalPrice(rs.getDouble("totalPrice"));
//                 list.add(o);
//             }
//         } catch (Exception ex) {
//             Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
//         }
//         return list;
//     }
// }
