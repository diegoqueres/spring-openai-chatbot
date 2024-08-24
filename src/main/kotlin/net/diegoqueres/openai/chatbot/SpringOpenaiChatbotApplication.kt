package net.diegoqueres.openai.chatbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class SpringOpenaiChatbotApplication

fun main(args: Array<String>) {
	runApplication<SpringOpenaiChatbotApplication>(*args)
}
