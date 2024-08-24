package net.diegoqueres.openai.chatbot.domain.service

import net.diegoqueres.openai.chatbot.config.CustomerAssistantProperties
import net.diegoqueres.openai.chatbot.domain.dto.Response
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.stereotype.Service

@Service
class CustomerAssistantService(
    private val customerAssistantProperties: CustomerAssistantProperties,
    private val customerAssistantClient: ChatClient
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

}