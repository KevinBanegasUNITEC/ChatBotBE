package org.example.chatbot.service;

import com.google.genai.Chat;
import com.google.genai.Client;
import com.google.genai.ResponseStream;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Part;
import org.springframework.stereotype.Service;

@Service
public class GeminiChatService {

    private Client client;
    private Chat chatSession;

    public GeminiChatService() {
        // Initialize the Gemini client
        String apiKey = System.getenv("CHATBOT_API_KEY");
        this.client = Client.builder().apiKey(apiKey).build();
        // Create a new chat session with Gemini
        String configPrompt = """
                Eres un asistente diseñado para ayudar a los usuarios con cualquier solicitud.\s
                Debes responder siempre siguiendo estos principios:
                1. Mantén las respuestas cortas, concisas y directas al punto.
                2. Evita explicaciones innecesarias o párrafos largos.
                3. Proporciona solo la información esencial para resolver la solicitud del usuario.
                4. Si existen varias opciones, resúmelas brevemente.
                5. Usa un lenguaje simple a menos que el usuario pida detalle técnico.
                6. Mantén un tono amable y útil.
                """;
        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .systemInstruction(
                                Content.fromParts(Part.fromText(configPrompt)))
                        .build();
        this.chatSession = client.chats.create("gemini-3-flash-preview", config);
    }

    public String sendMessage(String message) {
        StringBuilder fullResponse = new StringBuilder();

        try {
            // Send message and get streaming response
            ResponseStream<GenerateContentResponse> responseStream =
                    chatSession.sendMessageStream(message);

            // Collect the streamed response
            for (GenerateContentResponse response : responseStream) {
                if (response.text() != null) {
                    fullResponse.append(response.text());
                }
            }

            return fullResponse.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, I encountered an error processing your message: " + e.getMessage();
        }
    }

    public void resetChat() {
        // Create a new chat session to reset the conversation
        this.chatSession = client.chats.create("gemini-3-flash-preview");
    }

    public String getChatHistory() {
        try {
            return chatSession.getHistory(false).toString();
        } catch (Exception e) {
            return "Unable to retrieve chat history";
        }
    }
}
