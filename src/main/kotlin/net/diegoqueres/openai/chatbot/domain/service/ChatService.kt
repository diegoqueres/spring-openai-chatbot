package net.diegoqueres.openai.chatbot.domain.service

import net.diegoqueres.openai.chatbot.domain.dto.Response
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.ChatClient.AdvisorSpec
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService(
    private val simpleChatClient: ChatClient,
    private val memoryChatClient: ChatClient
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
            null,
            chatResponse.result.output.content
        )
    }

    /**
     * Chat that stores the conversation memory with the user.
     *
     * Uses the default ecommerce assistant prompt.
     */
    fun chatWithMemory(chatId: String?, message: String): Response {
        val chatId: String = chatId ?: UUID.randomUUID().toString()

        val chatResponse = memoryChatClient.prompt()
            .user(message)
            .advisors { advisor: AdvisorSpec ->
                advisor.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
            }
            .call()
            .chatResponse()

        return Response(
            chatId,
            chatResponse.result.output.content
        )
    }

}