package net.diegoqueres.openai.chatbot.api.controller

import net.diegoqueres.openai.chatbot.domain.dto.Response
import net.diegoqueres.openai.chatbot.domain.service.CustomerAssistantService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/customer-assistant/")
class CustomerAssistantController(
    private val customerAssistantService: CustomerAssistantService
) {

    @GetMapping("/product-info")
    fun productInformation(@RequestParam(required = true) name: String): ResponseEntity<Response> =
        ResponseEntity.ok(customerAssistantService.productInformation(name))

    @GetMapping("/support")
    fun support(@RequestParam chatId: String?, @RequestParam(required = true) message: String): ResponseEntity<Response> =
        ResponseEntity.ok(customerAssistantService.support(chatId, message))

}