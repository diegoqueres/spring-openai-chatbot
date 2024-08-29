package net.diegoqueres.openai.chatbot.domain.service

import net.diegoqueres.openai.chatbot.config.CustomerAssistantProperties
import net.diegoqueres.openai.chatbot.domain.dto.Response
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.ChatClient.AdvisorSpec
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerAssistantService(
    private val customerAssistantProperties: CustomerAssistantProperties,
    private val customerAssistantClient: ChatClient,
    private val vectorStore: VectorStore
) {

    /**
     * Requests information about a specific product
     *
     * Example using prompt template to use more targeted prompts that receive variables to create closed questions.
     *
     * For example: "Briefly provide the main features of the product {product}"
     */
    fun productInformation(product: String): Response {
        val promptTemplate = PromptTemplate(customerAssistantProperties.promptProduct)
        promptTemplate.add("product", product)

        val chatResponse = customerAssistantClient.prompt(promptTemplate.create())
            .call()
            .chatResponse()

        return Response(
            null,
            chatResponse.result.output.content
        )
    }

    /**
     * Chat with context loaded in Vector Store.
     *
     * It can be used with "Customer Assistant" endpoint.
     *
     * It uses a vector store loaded with the ecommerce FAQ records.
     *
     * This way, the AI can answer specific ecommerce questions. Some examples of questions that only exist on the FAQ page:
     * - How long is the gift card valid for?
     * - What is the delivery time for supermarket orders?
     */
    fun support(chatId: String?, message: String): Response {
        val chatId: String = chatId ?: UUID.randomUUID().toString()

        val searchRequest = SearchRequest.query(message)

        val chatResponse = customerAssistantClient.prompt()
            .user(message)
            .advisors { advisor: AdvisorSpec ->
                advisor.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                    .advisors(QuestionAnswerAdvisor(vectorStore, searchRequest))
            }
            .call()
            .chatResponse()

        return Response(
            chatId,
            chatResponse.result.output.content
        )
    }

}