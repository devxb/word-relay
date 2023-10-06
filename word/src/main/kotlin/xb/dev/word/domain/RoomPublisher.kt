package xb.dev.word.domain

internal fun interface RoomPublisher {

    fun publish(event: Room)
}
