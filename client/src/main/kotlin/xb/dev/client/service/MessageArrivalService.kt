package xb.dev.client.service

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import xb.dev.client.domain.MessageArrivalEvent

@Service
internal class MessageArrivalService(private val outputer: Outputable) {

    @EventListener(MessageArrivalEvent::class)
    fun messageArrival(messageArrivalEvent: MessageArrivalEvent) {
        val message = messageArrivalEvent.message

        outputer.output(
            """
                {
                    messageId: \"${message.id}\"
                    senderId: \"${message.senderId}\"
                    message: \"${message.message}\"
                }
            """.trimIndent()
        )
    }
}
