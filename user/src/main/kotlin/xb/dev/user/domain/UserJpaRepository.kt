package xb.dev.user.domain

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository: JpaRepository<User, Long> {

    fun existsByName(name: String): Boolean
    fun findByNameOrNull(name: String): User?

}
