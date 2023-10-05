package xb.dev.user.controller

import org.springframework.stereotype.Component
import xb.dev.engine.dispatcher.Handleable
import xb.dev.engine.server.Caffeine
import xb.dev.user.service.UserLoginService

@Component
internal class UserLoginController(private val userLoginService: UserLoginService) : Handleable {

    private val name = "login"

    override fun isHandleable(methodName: String): Boolean = name == methodName

    override fun handle(caffeine: Caffeine): String {
        val name = caffeine.parameters["name"]
            ?: throw IllegalArgumentException("Missing parameter \"name\"")
        val password = caffeine.parameters["password"]
            ?: throw IllegalArgumentException("Missing parameter \"password\"")

        return userLoginService.login(name, password)
    }
}
