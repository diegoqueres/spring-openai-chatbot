package net.diegoqueres.openai.chatbot.infrastructure.seeder

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import net.diegoqueres.openai.chatbot.domain.model.Faq
import net.diegoqueres.openai.chatbot.domain.repository.FaqRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Profile("local")
class FaqDataSeeder {

    @Bean
    @Transactional
    fun loadData(faqRepository: FaqRepository): CommandLineRunner {
        return CommandLineRunner {
            val isFaqEmpty = faqRepository.count() == 0L
            if (isFaqEmpty) {
                val resource = ClassPathResource("sample-faq-data.json")
                val objectMapper = jacksonObjectMapper()
                val faqs = objectMapper.readValue(resource.inputStream, object : TypeReference<List<Faq>>() {})
                faqRepository.saveAll(faqs)
            }
        }
    }

}