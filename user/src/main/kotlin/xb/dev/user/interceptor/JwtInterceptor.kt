package xb.dev.user.interceptor

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import xb.dev.dispatch.Caffeine
import xb.dev.dispatch.Handleable
import xb.dev.dispatch.Interceptorable
import xb.dev.user.service.UserAuthService

@Service
internal class JwtInterceptor(
    private val userAuthService: UserAuthService,
    @Value("\${jwt.interceptor.path:joinRoom,createRoom,sendMessage}") private val paths: List<String>,
) : Interceptorable() {

    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    override fun isInterceptorable(handler: Handleable): Boolean =
        paths.any { handler.isHandleable(it) }

    override fun preIntercept(caffeine: Caffeine): Boolean {
        try {
            val token = caffeine.parameters["token"]
                ?: throw IllegalArgumentException("Authenticate fail cause \"EMPTY_TOKEN\"")
            userAuthService.authenticate(token)
        } catch (exception: Exception) {
            return false
        }
        return true
    }

    @PostConstruct
    internal fun jwtInterceptorLoaded() {
        logger.info("jwt path registered $paths")
    }
}
