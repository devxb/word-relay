package xb.dev.word.service

import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service
import xb.dev.core.id.IdGenerator
import xb.dev.word.domain.Room

@Service
internal class CreateRoomService(
    private val idGenerator: IdGenerator,
    private val opsForValue: ValueOperations<String, Room>
) {
    fun create(ownerId: Long): Long {
        val newRoomId = idGenerator.generate()
        val newRoom = Room(newRoomId, ownerId)

        opsForValue[getKey(newRoomId)] = newRoom
        return newRoomId
    }
}
