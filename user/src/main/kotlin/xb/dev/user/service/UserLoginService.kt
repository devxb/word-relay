package xb.dev.user.service

import org.springframework.stereotype.Service
import xb.dev.user.domain.UserJpaRepository

@Service
internal class UserLoginService(private val userJpaRepository: UserJpaRepository) {

    fun login(name: String, password: String): String =
        userJpaRepository.findByNameOrNull(name)?.let {
            it.validPassword(password)
            return it.id.toString()
        } ?: throw IllegalArgumentException("Cannot find user named \"${name}\"")
}
