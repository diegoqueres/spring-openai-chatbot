package net.diegoqueres.openai.chatbot.domain.repository

import net.diegoqueres.openai.chatbot.domain.model.Faq
import org.springframework.data.mongodb.repository.MongoRepository

interface FaqRepository : MongoRepository<Faq, String>