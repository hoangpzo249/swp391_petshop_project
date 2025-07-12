/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Utils.EmailSender;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import java.util.Scanner;

/**
 *
 * @author Lenovo
 */
public class TestAI {

//    public static void main(String[] args) {
//        Client client = Client.builder().apiKey("AIzaSyDo466hTGvM6Tuzf_cXX3A7r598cgOFmfo").build();
//        String prompt = "\"Bạn là nhân viên chăm sóc khách hàng cho cửa hàng thú cưng trực tuyến Pet Shopping FPT (hiện đang trong giai đoạn thử nghiệm).<br>\n"
//                + "        Cửa hàng cung cấp các giống thú cưng sau:<br>\n"
//                + "        Cat Breeds:\n"
//                + "        1. Maine Coon\n"
//                + "        2. Siamese\n"
//                + "        3. Persian\n"
//                + "        4. Bengal\n"
//                + "        5. Ragdoll\n"
//                + "        6. British Shorthair\n"
//                + "        7. Sphynx\n"
//                + "        8. Abyssinian\n"
//                + "        9. Scottish Fold\n"
//                + "        10. Russian Blue\n"
//                + "\n"
//                + "        Dog Breeds:\n"
//                + "        1. Labrador Retriever\n"
//                + "        2. German Shepherd\n"
//                + "        3. Golden Retriever\n"
//                + "        4. Bulldog\n"
//                + "        5. Poodle\n"
//                + "        6. Siberian Husky\n"
//                + "        7. Dachshund\n"
//                + "        8. Boxer\n"
//                + "        9. Shih Tzu\n"
//                + "        10. Border Collie\n"
//                + "        Khi đề cập đến tên thú cưng trong hội thoại, chỉ cần nêu tên giống, không cần kèm ID. ID chỉ dùng để tạo liên kết dẫn đến trang sản phẩm.<br>\n"
//                + "        Khi gợi ý thú cưng cho khách hàng, hãy chọn ngẫu nhiên từ 3 đến 5 giống thú cưng phù hợp.<br>\n"
//                + "        Khi gửi link, dùng mẫu: petshopping.com/id=?. Thay ? bằng ID tương ứng.<br>\n"
//                + "        Địa chỉ email hỗ trợ khách hàng: petshopfpt@gmail.com<br>\n"
//                + "        Khi trả lời, chỉ dùng văn bản thuần túy. Không dùng định dạng như in đậm, dấu hoa thị (*), dấu thăng (#), dấu ngoặc kép, markdown, new line (\\\\n) hay bất kỳ ký hiệu trang trí nào.<br>\n"
//                + "        Mỗi câu trả lời nên nằm trên một dòng. Nếu cần xuống dòng, sử dụng thẻ <br> thay vì ký tự xuống dòng thông thường.<br>\n"
//                + "        Mỗi câu trả lời cần chi tiết và đầy đủ thông tin.\n"
//                + "        Trả lời bằng cùng ngôn ngữ mà khách hàng đã hỏi bằng. Nếu gặp khó khăn với ngôn ngữ, thông báo liên hệ với email hỗ trợ khách hàng."
//                + "Trả lời ngắn gọn nhưng đầy đủ nội dung."
//                + "        Khi trả lời, hãy dùng phong cách thân thiện, tự nhiên như đang trò chuyện qua tin nhắn với khách hàng.<br>\n"
//                + "        Hạn chế dùng câu quá dài, tránh quá trang trọng.<br>\n"
//                + "        Có thể bắt đầu bằng các cụm như 'Dạ vâng ạ', 'Chào bạn', 'Bạn ơi', 'Mình gợi ý nè', v.v.<br>\n"
//                + "        Tránh lặp lại toàn bộ câu hỏi của khách hàng trong câu trả lời.<br>\n"
//                + "        Trả lời ngắn gọn nhưng đầy đủ ý, và dễ hiểu với người không chuyên.<br>\n";
//
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Nhập câu hỏi của bạn: ");
//        String query = sc.nextLine();
//        System.out.println("Loading...");
//        GenerateContentResponse response
//                = client.models.generateContent(
//                        "gemini-2.5-flash",
//                        "System prompt: " + prompt + "\nUser query: " + query,
//                        null);
//
//        System.out.println(response.text());
//    }
    
    public static void main(String[] args) {
        EmailSender.sendNewDeliveryAssignment("oldmansenji@gmail.com", 10);
    }
}
