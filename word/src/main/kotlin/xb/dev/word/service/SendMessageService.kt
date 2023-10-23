package xb.dev.word.service

import org.springframework.stereotype.Service
import xb.dev.core.id.IdGenerator
import xb.dev.word.domain.Message
import xb.dev.word.domain.RoomPublisher
import xb.dev.word.domain.RoomRepository

@Service
internal class SendMessageService(
    private val roomPublisher: RoomPublisher,
    private val roomRepository: RoomRepository,
    private val idGenerator: IdGenerator
) {

    fun send(roomId: Long, senderId: Long, message: String) {
        val room = roomRepository.findRoomById(roomId)
            ?: throw IllegalArgumentException("roomId 에 해당하는 Room을 찾을 수 없습니다. \"${roomId}\"")
        val nextMessage = Message(idGenerator.generate(), senderId, message)
        room.validMessage(nextMessage)
        roomRepository.updateRoom(room)
        roomPublisher.publish(roomId, nextMessage)
    }
}
