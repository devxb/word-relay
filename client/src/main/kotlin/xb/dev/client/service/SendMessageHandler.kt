package xb.dev.client.service

import org.springframework.stereotype.Service
import xb.dev.client.domain.Handler
import xb.dev.client.domain.SupportMessage
import xb.dev.client.domain.WordMessenger

@Service
internal class SendMessageHandler(private val wordMessenger: WordMessenger) : Handler {

    override fun isHandleable(name: String): Boolean = name == "sendMessage"

    override fun handle(arg: Map<String, String>) {
        val roomId = arg["roomId"]?.toInt()
            ?: throw IllegalArgumentException("Missing parameter \"roomId\"")
        val userId = arg["senderId"]?.toInt()
            ?: throw IllegalArgumentException("Missing parameter \"senderId\"")
        val message = arg["message"]
            ?: throw IllegalArgumentException("Missing parameter \"message\"")
        val token = arg["token"] ?: throw IllegalArgumentException("Missing parameter \"token\"")

        wordMessenger.send(SupportMessage.SendMessage(roomId, userId, message, token))
    }
}
