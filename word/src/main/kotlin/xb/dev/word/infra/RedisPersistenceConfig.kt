package xb.dev.word.infra

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisSentinelConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.transaction.annotation.EnableTransactionManagement
import xb.dev.word.domain.Room
import xb.dev.word.domain.RoomRepository
import xb.dev.word.service.getKey


@Configuration
@EnableTransactionManagement
internal class RedisPersistenceConfig {

    private val sentinels: Set<String> = setOf("localhost:26379, localhost:26380, localhost:26381")

    @Bean
    internal fun roomRepository(): RoomRepository {
        return object : RoomRepository {
            override fun createRoom(room: Room): Long {
                redisTemplate().opsForValue()[getKey(room.id)] = room
                return room.id
            }

            override fun findRoomById(roomId: Long): Room? =
                redisTemplate().opsForValue()[getKey(roomId)]

            override fun updateRoom(room: Room) {
                redisTemplate().opsForValue()[getKey(room.id)] = room
            }
        }
    }

    @Bean("persistenceRedisTemplate")
    internal fun redisTemplate(): RedisTemplate<String, Room> =
        RedisTemplate<String, Room>().apply {
            this.connectionFactory = redisConnectionFactory()
            this.setEnableTransactionSupport(true)
        }

    @Bean("persistenceRedisConnectionFactory")
    internal fun redisConnectionFactory(): RedisConnectionFactory {
        val redisSentinelConfiguration = RedisSentinelConfiguration("mymaster", sentinels)
        return LettuceConnectionFactory(redisSentinelConfiguration)
    }
}
