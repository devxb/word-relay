package xb.dev.word.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SessionCallback
import org.springframework.stereotype.Service
import xb.dev.word.domain.Room

@Service
internal class JoinRoomService(
    @Qualifier("persistenceRedisTemplate") private val redisTemplate: RedisTemplate<String, Room>
) {

    fun join(roomId: Long, userId: Long): Long {
        val key = getKey(roomId)
        val room = redisTemplate.opsForValue()[key]
            ?: throw IllegalArgumentException("roomId 에 해당하는 Room을 찾을 수 없습니다. \'${roomId}\"")
        room.join(userId)

        redisTemplate.kexecute { operation ->
            operation.watch(key)
            operation.multi()
            operation.opsForValue()[key] = room
            operation.exec()
        }

        return roomId
    }

}

private inline fun <reified K : Any?, reified V : Any?, reified T> RedisTemplate<K, V>.kexecute(
    crossinline callback: (RedisOperations<K, V>) -> T?
): T? {

    return execute(object : SessionCallback<T> {
        @Suppress("UNCHECKED_CAST")
        override fun <KK, VV> execute(operations: RedisOperations<KK, VV>) =
            callback(operations as RedisOperations<K, V>)
    })
}
