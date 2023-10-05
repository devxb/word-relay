package xb.dev.user.domain

import jakarta.persistence.*

@Entity
@Table(name = "users", indexes = [Index(name = "user_name_idx", columnList = "name")])
class User(
    @Id
    @Column(name = "id")
    val id: Long,

    @Column(name = "name", nullable = false, length = 20, unique = true)
    val name: String,

    @Column(name = "password", nullable = false)
    val password: String
) {

    internal fun validPassword(password: String) = require(this.password == password) {
        throw IllegalArgumentException("일치하지 않는 비밀번호 입니다. \"${password}\"")
    }
}
