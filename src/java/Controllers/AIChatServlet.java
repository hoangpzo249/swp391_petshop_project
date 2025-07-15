/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import Models.Conversation;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class AIChatServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AIChatServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AIChatServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession();
        String query = request.getParameter("query");

        synchronized (session) {
            @SuppressWarnings("unchecked")
            List<Conversation> chatHistory = (List<Conversation>) session.getAttribute("chatHistory");

            if (chatHistory == null) {
                chatHistory = new ArrayList<>();
                String welcomeMessage = "Xin chào! Tôi là trợ lý ảo của PetFPT. Tôi có thể giúp gì cho bạn ngày hôm nay?";
                chatHistory.add(new Conversation(welcomeMessage, "ai"));
                session.setAttribute("chatHistory", chatHistory);
            }

            if (query != null && !query.trim().isEmpty()) {
                chatHistory.add(new Conversation(query, "user"));

                StringBuilder promptBuilder = new StringBuilder();
                promptBuilder.append(getPrompt()).append("\n\nConversation History:\n");
                for (Conversation msg : chatHistory) {
                    String role = "ai".equals(msg.getSender()) ? "assistant" : "user";
                    promptBuilder.append(role).append(": ").append(msg.getText()).append("\n");
                }
                promptBuilder.append("assistant: ");
                try {
                    final String API_KEY = System.getenv("API_KEY");
                    if (API_KEY == null || API_KEY.isEmpty()) {
                        throw new ServletException("API_KEY environment variable not set.");
                    }

                    Client client = Client.builder().apiKey(API_KEY).build();
                    GenerateContentResponse AI_response = client.models.generateContent(
                            "gemini-1.5-flash", // Using a recent model
                            promptBuilder.toString(),
                            null);
                    String reply = AI_response.text();

                    System.out.println("==========AI REPLY: " + reply);

                    chatHistory.add(new Conversation(reply.trim(), "ai"));

                } catch (Exception e) {
                    e.printStackTrace();
                    String errorMessage = "Rất tiếc, tôi đang gặp sự cố. Vui lòng thử lại sau.";
                    chatHistory.add(new Conversation(errorMessage, "ai"));
                }

                session.setAttribute("chatHistory", chatHistory);
            }

            try (PrintWriter out = response.getWriter()) {
                String jsonResponse = convertToJson(chatHistory);
                out.print(jsonResponse);
                out.flush();
            }
        }
    }

    public String getPrompt() {
        BreedDAO _dao = new BreedDAO();
        String breeds = _dao.getBreedPrompt();

        String prompt = "\"Bạn là nhân viên chăm sóc khách hàng cho cửa hàng thú cưng trực tuyến PetFPT.<br>\n"
                + "Cửa hàng cung cấp các giống thú cưng sau:<br>\n"
                + breeds + "<br>\n"
                + "Khi đề cập đến tên thú cưng trong hội thoại, chỉ cần nêu tên giống, không cần kèm ID. ID chỉ dùng để tạo liên kết dẫn đến trang sản phẩm.<br>\n"
                + "Khi gợi ý thú cưng cho khách hàng, hãy chọn ngẫu nhiên từ 3 đến 5 giống thú cưng phù hợp.<br>\n"
                + "Khi gợi ý thú cưng, phải gửi link theo mẫu: <a href=\"listshoppet?breed=breedID\">breedName</a>. Thay breedID và breedName bằng ID và tên tương ứng.<br>\n"
                + "Địa chỉ email hỗ trợ khách hàng: fptpet@gmail.com<br>\n"
                + "Khi trả lời, chỉ dùng văn bản thuần túy. Không dùng định dạng như in đậm, dấu hoa thị (*), dấu thăng (#), dấu ngoặc kép, markdown, new line (\\\\n) hay bất kỳ ký hiệu trang trí nào.<br>\n"
                + "Mỗi câu trả lời nên nằm trên một dòng. Nếu cần xuống dòng, sử dụng thẻ <br> thay vì ký tự xuống dòng thông thường.<br>\n"
                + "Mỗi câu trả lời cần chi tiết và đầy đủ thông tin.<br>\n"
                + "**BẮT BUỘC** phải trả lời bằng cùng ngôn ngữ mà khách hàng đã dùng trong câu hỏi. Tuyệt đối không được dùng ngôn ngữ khác.<br>\n"
                + "Nếu không hiểu được ngôn ngữ đó, hãy yêu cầu người dùng liên hệ email fptpet@gmail.com để được hỗ trợ.<br>\n"
                + "Trả lời ngắn gọn nhưng đầy đủ nội dung.<br>\n"
                + "Khi trả lời, hãy dùng phong cách thân thiện, tự nhiên như đang trò chuyện qua tin nhắn với khách hàng.<br>\n"
                + "Hạn chế dùng câu quá dài, tránh quá trang trọng.<br>\n"
                + "Tránh lặp lại toàn bộ câu hỏi của khách hàng trong câu trả lời.<br>\n"
                + "Trả lời ngắn gọn nhưng đầy đủ ý, và dễ hiểu với người không chuyên.<br>\"";

        return prompt;
    }

    private String convertToJson(List<Conversation> history) {
        Gson gson = new Gson();
        return gson.toJson(history);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
