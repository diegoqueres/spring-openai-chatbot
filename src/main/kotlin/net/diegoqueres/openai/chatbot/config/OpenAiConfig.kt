package net.diegoqueres.openai.chatbot.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.openai.OpenAiEmbeddingModel
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenAiConfig {

    @Value("\${spring.ai.openai.apikey}") lateinit var springOpenAiApiKey: String

    /**
    * Simple chat client bean (with minimal config).
    */
    @Bean
    fun simpleChatClient(chatModel: ChatModel): ChatClient {
        return ChatClient.builder(chatModel)
            .build()
    }

    /**
     * Chat client bean with "in memory chat" (development purposes only).
     */
    @Bean
    fun memoryChatClient(chatModel: ChatModel): ChatClient {
        val chatMemory = InMemoryChatMemory()

        return ChatClient.builder(chatModel)
            .defaultAdvisors(PromptChatMemoryAdvisor(chatMemory))
            .build()
    }

    @Bean
    fun embeddingModel(): EmbeddingModel {
        return OpenAiEmbeddingModel(OpenAiApi(springOpenAiApiKey))
    }

}