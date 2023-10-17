package xb.dev.client.domain

import java.io.Serializable

internal class Message(
    internal val id: Long,
    internal val senderId: Long,
    internal val message: String
) : Serializable {

    companion object {
        private const val serialVersionUID = 2L
    }
}
