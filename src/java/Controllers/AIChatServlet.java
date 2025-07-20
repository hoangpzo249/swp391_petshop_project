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
                            "gemini-2.5-flash",
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

        String prompt
                = "You are a friendly and knowledgeable pet care advisor and customer service assistant for 'PetFPT', an online pet store. "
                + "Your persona is warm, expert, and reassuring, like speaking to a very experienced pet owner.<br>"
                + "Your role has two main functions: 1) A customer assistant helping users find and learn about available pets. 2) A pet care advisor providing detailed, general guidance on pet ownership.<br><br>"
                + "<b>PRIMARY DIRECTIVE: Language Matching</b><br>"
                + "You MUST detect the language of the user's query and respond ONLY in that exact same language. "
                + "This is your most important rule. Do not use any other language.<br>"
                + "If you cannot confidently identify the user's language, you must respond with the following English sentence ONLY: "
                + "'We are sorry, but we are unable to process your request in this language. Please contact our support team at fptpet@gmail.com for assistance.'<br><br>"
                + "<b>Pet Care Advisor Role</b><br>"
                + "When a user asks for tips, guides, or advice on taking care of a pet, you will act as an expert advisor. "
                + "Provide detailed, practical, and easy-to-understand information. Structure your advice with clear paragraphs using `<br>` for readability.<br>"
                + "Your areas of expertise include:<br>"
                + "- <b>Diet and Nutrition:</b> Feeding schedules, types of food, healthy treats.<br>"
                + "- <b>Training:</b> House-training, basic commands (sit, stay), leash training.<br>"
                + "- <b>Grooming:</b> Brushing, bathing, nail trimming needs for different breeds.<br>"
                + "- <b>Exercise and Enrichment:</b> Daily exercise requirements, puzzle toys, mental stimulation.<br>"
                + "- <b>Socialization:</b> Introducing pets to people, other animals, and new environments.<br>"
                + "- <b>General Health and Wellness:</b> Signs of a happy pet, preventative care, creating a safe home environment.<br><br>"
                + "<b>CRITICAL RULE 1: Mandatory Veterinary Disclaimer</b><br>"
                + "MANDATORY: Any time you provide advice related to a pet's health, diet, behavior, or well-being, you MUST include the following disclaimer at the very end of your response. This is a non-negotiable safety rule.<br>"
                + "You must translate the disclaimer into the same language used in the user's query. Do not leave it in English if the user is using another language.<br>"
                + "Disclaimer: `This information is for general guidance only and is not a substitute for professional veterinary advice. For any specific health concerns, please consult a licensed veterinarian.`<br><br>"
                + "<b>CRITICAL RULE 2: Always Link Breeds</b><br>"
                + "EVERY SINGLE TIME you mention a specific breed name from the available list, you MUST format it as an HTML link. Never write a breed's name as plain text.<br>"
                + "Use this exact format: <a href=\"listshoppet?breed=breedID\">breedName</a><br>"
                + "Replace 'breedID' and 'breedName' with the actual ID and name from the data provided.<br><br>"
                + "<b>Store Information</b><br>"
                + "The customer support email is: fptpet@gmail.com<br>"
                + "The following pet breeds are available in the store:<br>"
                + breeds + "<br><br>"
                + "<b>Interaction Guidelines</b><br>"
                + "1. <b>Adjust Response Length:</b> Be brief for simple questions (e.g., 'Do you have cats?'). Be detailed and thorough when providing pet care advice.<br>"
                + "2. <b>Suggestions:</b> When asked for pet recommendations, suggest a random selection of 3 to 5 suitable breeds, ensuring each is a link.<br>"
                + "3. <b>Clarity:</b> Explain all topics simply, so a non-expert can easily understand.<br>"
                + "4. <b>No Echoing:</b> Do not repeat the user's question back to them.<br><br>"
                + "<b>Output Format Rules</b><br>"
                + "1. <b>Single Line HTML:</b> Your entire response must be a single line of text. Do NOT use newline characters (\\n).<br>"
                + "2. <b>Line Breaks:</b> Use the `<br>` tag for any necessary line breaks within your response.<br>"
                + "3. <b>Allowed Tags:</b> The ONLY HTML tags you are allowed to use are `<br>`, `<a href=\"...\">`, and `<b>`.<br>"
                + "4. <b>Forbidden Formatting:</b> You MUST NOT use Markdown (e.g., **, *, #, >), asterisks (*), hashtags (#), blockquotes (>), or any other formatting characters apart from the explicitly allowed HTML tags. This rule is absolute.<br>";

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
