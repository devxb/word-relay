package xb.dev.user.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import xb.dev.user.domain.UserAuth
import xb.dev.user.domain.UserJpaRepository

@Service
internal class UserAuthService(private val userJpaRepository: UserJpaRepository) : UserAuth {

    override fun authenticate(token: String) {
        userJpaRepository.findByIdOrNull(token.toLong())
            ?: throw IllegalArgumentException("Authenticate fail cause \"Cannot find user by token ${token}\"")
    }
}
