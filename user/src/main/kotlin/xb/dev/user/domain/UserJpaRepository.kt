package xb.dev.user.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserJpaRepository : JpaRepository<User, Long> {

    fun existsByName(name: String): Boolean

    @Query("select u from User as u where u.name = :name")
    fun findByNameOrNull(@Param("name") name: String): User?

}
