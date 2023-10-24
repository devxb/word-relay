package xb.dev.word.controller

import org.springframework.stereotype.Component
import xb.dev.dispatch.Caffeine
import xb.dev.dispatch.Handleable
import xb.dev.word.service.JoinRoomService

@Component
internal class JoinRoomController(private val joinRoomService: JoinRoomService) : Handleable {

    private val name = "joinRoom"

    override fun isHandleable(methodName: String): Boolean = name == methodName

    override fun handle(caffeine: Caffeine): String {
        val roomId = caffeine.parameters["roomId"]?.toLong()
            ?: throw IllegalArgumentException("Missing parameter \"roomId\"")
        val userId = caffeine.parameters["userId"]?.toLong()
            ?: throw IllegalArgumentException("Missing parameter \"userId\"")

        return joinRoomService.join(roomId, userId).toString()
    }
}
