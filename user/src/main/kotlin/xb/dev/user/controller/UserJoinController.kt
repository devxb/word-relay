package xb.dev.user.controller

import org.springframework.stereotype.Component
import xb.dev.engine.dispatcher.Handleable
import xb.dev.engine.server.Caffeine
import xb.dev.user.service.UserJoinService

@Component
internal class UserJoinController(private val userJoinService: UserJoinService) : Handleable {

    override fun isHandleable(methodName: String): Boolean = NAME == methodName

    override fun handle(caffeine: Caffeine): String {
        val name = caffeine.parameters["name"]
            ?: throw IllegalArgumentException("Missing parameter \"name\"")
        val password = caffeine.parameters["password"]
            ?: throw IllegalArgumentException("Missing parameter \"password\"")

        return userJoinService.join(name, password).toString()
    }

    companion object {
        private const val NAME = "join"
    }
}
