package xb.dev.client.infra

import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Service
import xb.dev.client.domain.MessageArrivalEvent
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream

@Service
internal class WordMessageListener(private val eventPublisher: ApplicationEventPublisher) :
    MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val arrivalMessage = fromByteArray(message.body)
        eventPublisher.publishEvent(MessageArrivalEvent(arrivalMessage))
    }

    private fun fromByteArray(byteArray: ByteArray): xb.dev.client.domain.Message {
        val byteArrayInputStream = ByteArrayInputStream(byteArray)
        val objectInput = ObjectInputStream(byteArrayInputStream)
        val result = objectInput.readObject() as xb.dev.client.domain.Message
        objectInput.close()
        byteArrayInputStream.close()
        return result
    }
}
