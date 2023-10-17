package xb.dev.client.service

import org.springframework.stereotype.Service
import xb.dev.client.domain.Handler
import xb.dev.client.domain.SupportMessage
import xb.dev.client.domain.WordMessenger

@Service
internal class CreateRoomHandler(private val wordMessenger: WordMessenger): Handler {

    override fun isHandleable(name: String): Boolean = name == "createRoom"

    override fun handle(arg: Map<String, String>) {
        val ownerId = arg["ownerId"]?.toInt()
            ?: throw IllegalArgumentException("Missing parameter \"ownerId\"")
        val token = arg["token"] ?: throw IllegalArgumentException("Missing parameter \"token\"")

        wordMessenger.send(SupportMessage.CreateRoom(ownerId, token))
    }
}
