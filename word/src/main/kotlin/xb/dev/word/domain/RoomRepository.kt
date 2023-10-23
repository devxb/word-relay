package xb.dev.word.domain

internal interface RoomRepository {

    fun createRoom(room: Room): Long

    fun findRoomById(roomId: Long): Room?

    fun updateRoom(room: Room)
}
