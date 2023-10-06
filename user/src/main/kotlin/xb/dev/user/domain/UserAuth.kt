package xb.dev.user.domain

fun interface UserAuth {

    fun authenticate(token: String)

}
