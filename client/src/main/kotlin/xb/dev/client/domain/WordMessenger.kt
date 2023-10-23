package xb.dev.client.domain

internal fun interface WordMessenger {

    fun send(message: SupportMessage): String
}
