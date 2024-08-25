package net.diegoqueres.openai.chatbot.config

import net.diegoqueres.openai.chatbot.domain.model.Faq
import org.springframework.ai.document.Document
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.vectorstore.SimpleVectorStore
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class VectorStoreConfig {

    /**
     * A simple vector store in memory
     */
    @Bean
    fun chatVectorStore(mongoTemplate: MongoTemplate, embeddingModel: EmbeddingModel): VectorStore {
        val botVectorStore: VectorStore = SimpleVectorStore(embeddingModel)
        initialize(mongoTemplate, botVectorStore)
        return botVectorStore
    }

    fun initialize(mongoTemplate: MongoTemplate, botVectorStore: VectorStore) {
        val mongoDocuments = mongoTemplate.findAll(Faq::class.java, "faqs")
        val openAiDocuments = mongoDocuments.map {
            Document(it.content, mapOf("title" to it.title, "category" to it.category, "metatags" to it.metatags))
        }
        botVectorStore.accept(openAiDocuments)
    }

}