package xb.dev.user.domain

internal interface Token {

    fun getToken(user: User): String

    fun getId(token: String): Long

}
