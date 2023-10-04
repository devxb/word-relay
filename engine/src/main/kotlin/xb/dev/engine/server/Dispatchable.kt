package xb.dev.engine.server

fun interface Dispatchable {

    fun dispatch(caffeine: Caffeine): String

}
