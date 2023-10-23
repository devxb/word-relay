package xb.dev.word.controller

import org.springframework.stereotype.Component
import xb.dev.engine.dispatcher.Handleable
import xb.dev.engine.server.Caffeine
import xb.dev.word.service.SendMessageService

@Component
internal class SendMessageController(private val sendMessageService: SendMessageService) :
    Handleable {

    private val name = "sendMessage"

    override fun isHandleable(methodName: String): Boolean = methodName == name

    override fun handle(caffeine: Caffeine): String {
        val roomId = caffeine.parameters["roomId"]?.toLong()
            ?: throw IllegalArgumentException("Missing parameter \"roomId\"")
        val senderId = caffeine.parameters["senderId"]?.toLong()
            ?: throw IllegalArgumentException("Missing parameter \"senderId\"")
        val message = caffeine.parameters["message"]
            ?: throw IllegalArgumentException("Missing parameter \"message\"")

        sendMessageService.send(roomId, senderId, message)

        return "0"
    }
}
