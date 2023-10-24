package xb.dev.dispatch

import org.springframework.stereotype.Service

@Service
internal class Dispatcher(
    private val handlers: List<Handleable>,
    private val interceptors: List<Interceptorable>
) : Dispatchable {

    override fun dispatch(caffeine: Caffeine): String {
        val handler = handlers.find { it.isHandleable(caffeine.method) }
            ?: throw IllegalArgumentException("Cannot find handler \"${caffeine.method}\"")
        val interceptor = interceptors.find { it.isInterceptorable(handler) }
            ?: return handler.handle(caffeine)

        return interceptor.intercept(caffeine, handler)
    }
}
