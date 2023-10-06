package xb.dev.word.domain

internal fun defaultRoom(
    id: Long = 1L,
    ownerId: Long = 1L,
    users: MutableSet<Long> = mutableSetOf(),
    messages: MutableList<Message> = mutableListOf()
): Room = Room(id, ownerId, users, messages)

internal fun defaultMessage(
    id: Long = 1L,
    senderId: Long = 1L,
    message: String = "aba"
): Message = Message(id, senderId, message)
