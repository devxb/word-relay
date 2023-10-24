package xb.dev.word.controller

import org.springframework.stereotype.Component
import xb.dev.dispatch.Caffeine
import xb.dev.dispatch.Handleable
import xb.dev.word.service.CreateRoomService

@Component
internal class CreateRoomController(private val createRoomService: CreateRoomService) : Handleable {

    private val name = "createRoom"

    override fun isHandleable(methodName: String): Boolean = name == methodName

    override fun handle(caffeine: Caffeine): String {
        val ownerId = caffeine.parameters["ownerId"]?.toLong()
            ?: throw IllegalArgumentException("Missing parameter \"ownerId\"")

        return createRoomService.create(ownerId).toString()
    }
}
