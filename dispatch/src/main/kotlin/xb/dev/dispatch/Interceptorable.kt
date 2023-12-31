package xb.dev.dispatch

abstract class Interceptorable {

    abstract fun isInterceptorable(handler: Handleable): Boolean

    internal fun intercept(caffeine: Caffeine, handler: Handleable): String {
        val preInterceptResult = preIntercept(caffeine)
        require(preInterceptResult) { "intercepting fail" }
        return handler.handle(caffeine)
    }

    abstract fun preIntercept(caffeine: Caffeine): Boolean

}
