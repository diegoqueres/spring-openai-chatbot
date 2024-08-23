package net.diegoqueres.openai.chatbot.api.controller

import net.diegoqueres.openai.chatbot.domain.dto.Response
import net.diegoqueres.openai.chatbot.domain.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ChatController(
    private val chatService: ChatService
) {

    @GetMapping("/chat")
    fun chat(@RequestParam message: String): ResponseEntity<Response> =
        ResponseEntity.ok(chatService.chat(message))

    @GetMapping("/chat-with-memory")
    fun chatWithMemory(@RequestParam chatId: String?, @RequestParam message: String): ResponseEntity<Response> =
        ResponseEntity.ok(chatService.chatWithMemory(chatId, message))

}