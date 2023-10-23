package xb.dev.client.infra

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Service
import xb.dev.client.domain.MessageArrivalEvent

@Service
internal class WordMessageListener(
    private val eventPublisher: ApplicationEventPublisher,
    private val objectMapper: ObjectMapper
) : MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val arrivalMessage = convertToMessage(message.body)
        eventPublisher.publishEvent(MessageArrivalEvent(arrivalMessage))
    }

    private fun convertToMessage(byteArray: ByteArray): xb.dev.client.domain.Message {
        return objectMapper.readValue(byteArray, xb.dev.client.domain.Message::class.java)
    }
}
