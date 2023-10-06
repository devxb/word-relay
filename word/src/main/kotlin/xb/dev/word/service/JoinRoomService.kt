package xb.dev.word.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xb.dev.word.domain.Room

@Service
internal class JoinRoomService(private val redisTemplate: RedisTemplate<String, Room>) {

    @Transactional(transactionManager = "redisTransactionManager")
    fun join(roomId: Long, userId: Long): Long {
        val key = getKey(roomId)
        redisTemplate.watch(key)
        val room = redisTemplate.opsForValue()[key]
            ?: throw IllegalArgumentException("roomId에 해당하는 방을 찾을 수 없습니다. \"${roomId}\"")

        room.join(userId)
        redisTemplate.opsForValue()[key] = room
        return roomId
    }
}
