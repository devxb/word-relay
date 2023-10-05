package xb.dev.engine.dispatcher

import xb.dev.engine.server.Caffeine

interface Handleable {

    fun isHandleable(methodName: String): Boolean

    fun handle(caffeine: Caffeine): String

}
