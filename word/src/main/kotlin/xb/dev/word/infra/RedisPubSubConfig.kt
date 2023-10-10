package xb.dev.word.infra

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import xb.dev.word.domain.RoomPublisher
import xb.dev.word.service.getKey

@Configuration
internal class RedisPubSubConfig {

    @Bean
    fun redisRoomPublisher(): RoomPublisher =
        RoomPublisher { roomId, message -> redisTemplate().convertAndSend(getKey(roomId), message) }

    @Bean(name = ["redisTemplate", "pubSubRedisTemplate"])
    fun redisTemplate(): RedisTemplate<String, Message> = RedisTemplate<String, Message>().apply {
        this.connectionFactory = redisConnectionFactory()
    }

    @Bean("pubSubRedisConnectionFactory")
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration("localhost", 9800)
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }
}
