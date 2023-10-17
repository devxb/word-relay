package xb.dev.client.infra

import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Service

@Service
internal class WordMessageListener(private val eventPublisher: ApplicationEventPublisher) :
    MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        eventPublisher.publishEvent(message)
    }
}
