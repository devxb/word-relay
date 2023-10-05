package xb.dev.user.domain

import org.springframework.data.jpa.repository.JpaRepository

internal interface UserJpaRepository: JpaRepository<User, Long>
