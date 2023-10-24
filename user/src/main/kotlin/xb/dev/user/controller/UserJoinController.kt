package xb.dev.user.controller

import org.springframework.stereotype.Component
import xb.dev.dispatch.Caffeine
import xb.dev.dispatch.Handleable
import xb.dev.user.service.UserJoinService

@Component
internal class UserJoinController(private val userJoinService: UserJoinService) : Handleable {

    private val name = "join"

    override fun isHandleable(methodName: String): Boolean = name == methodName

    override fun handle(caffeine: Caffeine): String {
        val name = caffeine.parameters["name"]
            ?: throw IllegalArgumentException("Missing parameter \"name\"")
        val password = caffeine.parameters["password"]
            ?: throw IllegalArgumentException("Missing parameter \"password\"")

        return userJoinService.join(name, password).toString()
    }
}
