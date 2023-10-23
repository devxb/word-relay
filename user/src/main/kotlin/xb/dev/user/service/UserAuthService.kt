package xb.dev.user.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import xb.dev.user.domain.Token
import xb.dev.user.domain.UserAuth
import xb.dev.user.domain.UserJpaRepository

@Service
internal class UserAuthService(
    private val userJpaRepository: UserJpaRepository,
    private val token: Token
) : UserAuth {

    override fun authenticate(token: String) {
        val id = this.token.getId(token)
        userJpaRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Authenticate fail cause \"Cannot find user by token ${token}\"")
    }
}
