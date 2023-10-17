package xb.dev.client.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import xb.dev.client.domain.Handler
import xb.dev.client.domain.JoinRoomEvent
import xb.dev.client.domain.SupportMessage
import xb.dev.client.domain.WordMessenger
import xb.dev.word.service.getKey

@Service
internal class JoinRoomHandler(
    private val wordMessenger: WordMessenger,
    private val eventPublisher: ApplicationEventPublisher
) : Handler {

    override fun isHandleable(name: String): Boolean = name == "joinRoom"

    override fun handle(arg: Map<String, String>) {
        val roomId = arg["roomId"]?.toInt()
            ?: throw IllegalArgumentException("Missing parameter \"roomId\"")
        val userId = arg["userId"]?.toInt()
            ?: throw IllegalArgumentException("Missing parameter \"userId\"")
        val token = arg["token"] ?: throw IllegalArgumentException("Missing parameter \"token\"")

        val answer=  wordMessenger.send(SupportMessage.JoinRoom(roomId, userId, token))

        eventPublisher.publishEvent(JoinRoomEvent(getKey(answer)))
    }
}
