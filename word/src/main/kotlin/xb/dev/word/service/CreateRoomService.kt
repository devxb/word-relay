package xb.dev.word.service

import org.springframework.stereotype.Service
import xb.dev.core.id.IdGenerator
import xb.dev.word.domain.Room
import xb.dev.word.domain.RoomRepository

@Service
internal class CreateRoomService(
    private val idGenerator: IdGenerator,
    private val roomRepository: RoomRepository
) {

    fun create(ownerId: Long): Long =
        roomRepository.createRoom(Room(idGenerator.generate(), ownerId))
}
