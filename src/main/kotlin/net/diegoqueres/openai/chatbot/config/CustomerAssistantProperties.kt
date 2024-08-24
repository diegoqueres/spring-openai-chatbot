package net.diegoqueres.openai.chatbot.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("customer.assistant")
data class CustomerAssistantProperties(
    val name: String,
    val prompt: String,
    val promptProduct: String
)