package xb.dev.word.domain

import java.io.Serializable

internal class Message(
    internal val id: Long,
    internal val senderId: Long,
    internal val message: String
) : Serializable {

    internal fun validNextMessage(nextMessage: Message) {
        require(
            nextMessage.message.first() == message.last()
        ) { "Cannot send message ${message}.last != ${nextMessage}.first" }
    }

    companion object {
        private const val serialVersionUID = 2L;
    }
}
