package xb.dev.client.domain

interface Handler {

    fun isHandleable(name: String): Boolean

    fun handle(arg: Map<String, String>)
}
