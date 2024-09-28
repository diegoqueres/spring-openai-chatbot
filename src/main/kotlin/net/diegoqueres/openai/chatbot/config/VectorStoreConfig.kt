package net.diegoqueres.openai.chatbot.config

import net.diegoqueres.openai.chatbot.domain.model.Faq
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.RedisVectorStore
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class VectorStoreConfig(
    private val mongoTemplate: MongoTemplate,
    private val redisVectorStore: RedisVectorStore
) {
    private val logger: Logger = LoggerFactory.getLogger(VectorStoreConfig::class.java)

    /**
     * A simple vector store in memory
     */
//    @Bean
//    fun chatVectorStore(mongoTemplate: MongoTemplate, embeddingModel: EmbeddingModel): VectorStore {
//        val botVectorStore: VectorStore = SimpleVectorStore(embeddingModel)
//        initialize(mongoTemplate, botVectorStore)
//        return botVectorStore
//    }
//    fun initialize(mongoTemplate: MongoTemplate, botVectorStore: VectorStore) {
//        val mongoDocuments = mongoTemplate.findAll(Faq::class.java, "faqs")
//        val openAiDocuments = mongoDocuments.map {
//            Document(it.content, mapOf("title" to it.title, "category" to it.category, "metatags" to it.metatags))
//        }
//        botVectorStore.accept(openAiDocuments)
//    }

    init {
        cleanVectorStore()
        loadDocumentsOnVectorStore()
    }

    private fun cleanVectorStore() {
        val jedisPooled = redisVectorStore.jedis
        val keys = jedisPooled.keys("default:*")
        if (keys.isNotEmpty()) {
            jedisPooled.del(*keys.toTypedArray())
            logger.info("Deleted {} keys from Redis vector store", keys.size)
        } else {
            logger.info("No keys found to delete in Redis vector store")
        }
    }

    /**
     * Initialize vector store using org.springframework.ai.document.Document
     */
    private final fun loadDocumentsOnVectorStore() {
        val mongoDocuments = mongoTemplate.findAll(Faq::class.java, "faqs")
        logger.info("Loading {} documents on vector store", mongoDocuments.size)

        val openAiDocuments = mongoDocuments.map {
            Document(it.content, mapOf("title" to it.title, "category" to it.category, "metatags" to it.metatags))
        }
        redisVectorStore.add(openAiDocuments)
    }

}