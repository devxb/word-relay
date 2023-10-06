package xb.dev.word.infra

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisSentinelConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import xb.dev.word.domain.Room

@Configuration
internal class RedisPersistenceConfig {

    private val sentinels: Set<String> = setOf("localhost:9901, localhost:9902, localhost:9903")

    @Bean("persistenceRedisTemplate")
    internal fun redisTemplate(): RedisTemplate<String, Room> =
        RedisTemplate<String, Room>().apply {
            this.connectionFactory = redisConnectionFactory()
        }

    @Bean("persistenceRedisConnectionFactory")
    internal fun redisConnectionFactory(): RedisConnectionFactory {
        val redisSentinelConfiguration = RedisSentinelConfiguration("wordMaster", sentinels)
        return LettuceConnectionFactory(redisSentinelConfiguration)
    }
}
