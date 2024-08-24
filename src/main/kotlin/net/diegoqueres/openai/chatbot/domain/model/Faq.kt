package net.diegoqueres.openai.chatbot.domain.model

import net.diegoqueres.openai.chatbot.domain.model.enums.FaqCategory
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "faqs")
data class Faq (
    @Id val id: String? = null,
    val title: String,
    val content: String,
    val category: FaqCategory,
    val metatags: Set<String> = HashSet()
)