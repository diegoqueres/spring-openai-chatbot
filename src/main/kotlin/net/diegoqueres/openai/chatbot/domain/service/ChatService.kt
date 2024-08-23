package net.diegoqueres.openai.chatbot.domain.service

import net.diegoqueres.openai.chatbot.domain.dto.Response
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val simpleChatClient: ChatClient
) {

    /**
     * Conventional chat with ChatGPT
     * _Chat without write a system prompt_
     */
    fun chat(message: String): Response {
        val chatResponse = simpleChatClient.prompt()
            .user(message)
            .call()
            .chatResponse()

        return Response(
            chatResponse.result.output.content
        )
    }

}