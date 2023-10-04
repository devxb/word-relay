package xb.dev.server

fun interface Dispatchable {

    fun dispatch(caffeine: Caffeine): String

}
