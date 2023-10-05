package xb.dev.user.service

import org.springframework.stereotype.Service
import xb.dev.core.id.IdGenerator
import xb.dev.user.domain.User
import xb.dev.user.domain.UserJpaRepository

@Service
internal class UserJoinService(
    private val repository: UserJpaRepository,
    private val idGenerator: IdGenerator
) {

    fun join(name: String, password: String): Long = when (repository.existsByName(name)) {
        true -> throw IllegalArgumentException("Duplicated User name \"${name}\"")
        false -> repository.save(User(idGenerator.generate(), name, password)).id
    }

}
