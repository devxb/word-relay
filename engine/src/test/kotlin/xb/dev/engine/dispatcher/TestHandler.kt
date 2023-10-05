package xb.dev.engine.dispatcher

import xb.dev.engine.server.Caffeine

object TestHandler : Handleable {

    private const val handlerName = "test"

    override fun isHandleable(methodName: String): Boolean = methodName == handlerName

    override fun handle(caffeine: Caffeine): String {
        return caffeine.parameters.toString()
    }
}
