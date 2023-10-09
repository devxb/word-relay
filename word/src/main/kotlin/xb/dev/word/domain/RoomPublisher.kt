package xb.dev.word.domain

internal fun interface RoomPublisher {

    fun publish(roomId: Long, message: Message)
}
