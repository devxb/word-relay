package xb.dev.client.domain

internal fun interface Inputable {

    fun input(): Input

    class Input(val methodName: String, val parameters: Map<String, String>)
}
