package xb.dev.user.service

import org.springframework.stereotype.Service
import xb.dev.user.domain.Token
import xb.dev.user.domain.UserJpaRepository

@Service
internal class UserLoginService(
    private val userJpaRepository: UserJpaRepository,
    private val token: Token
) {

    fun login(name: String, password: String): String =
        userJpaRepository.findByNameOrNull(name)?.let {
            it.validPassword(password)
            return token.getToken(it)
        } ?: throw IllegalArgumentException("Cannot find user named \"${name}\"")
}
