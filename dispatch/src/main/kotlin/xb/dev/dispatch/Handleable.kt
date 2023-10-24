package xb.dev.dispatch

interface Handleable {

    fun isHandleable(methodName: String): Boolean

    fun handle(caffeine: Caffeine): String

}
