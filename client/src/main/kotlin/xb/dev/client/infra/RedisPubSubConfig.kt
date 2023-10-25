package xb.dev.client.infra

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import xb.dev.client.domain.JoinRoomEvent

@Configuration
internal class RedisPubSubConfig(private val wordMessageListener: MessageListener) {

    @EventListener(JoinRoomEvent::class)
    internal fun subscribeRoomByKey(joinRoomEvent: JoinRoomEvent) {
        redisMessageListenerContainer().apply {
            this.addMessageListener(wordMessageListener, ChannelTopic(joinRoomEvent.roomKey))
        }
    }

    @Bean
    internal fun redisMessageListenerContainer(): RedisMessageListenerContainer =
        RedisMessageListenerContainer().apply {
            this.connectionFactory = redisConnectionFactory()
        }

    @Bean("pubSubRedisConnectionFactory")
    internal fun redisConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration("localhost", 9800)
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }
}
