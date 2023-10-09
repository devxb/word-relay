package xb.dev.word.domain

import java.io.Serializable

internal class Room(
    val id: Long,
    private val ownerId: Long,
    private val users: MutableSet<Long> = mutableSetOf(),
    private val messages: MutableList<Message> = mutableListOf()
) : Serializable {

    init {
        users.add(ownerId)
    }

    fun join(userId: Long) = users.add(userId)

    fun validMessage(message: Message) {
        validMessageSender(message)
        validNextMessage(message)
        messages.add(message)
    }

    private fun validMessageSender(message: Message) {
        require(users.contains(message.senderId)) { "참가하지 않은 유저 입니다. \"${message.senderId}\"" }
    }

    private fun validNextMessage(nextMessage: Message) {
        if (messages.isEmpty()) {
            return
        }
        val lastMessage = messages.last().message
        lastMessage.last().apply {
            require(nextMessage.message.first() == this) {
                "메시지의 끝 \"${this}\" 과 새로운 메시지의 시작 \"${nextMessage.message.first()}\" 이 일치하지 않습니다."
            }
        }
    }

    companion object {
        private const val serialVersionUID = 1L;
    }
}
