package xb.dev.client.infra

import org.springframework.stereotype.Service
import xb.dev.client.domain.SupportMessage
import xb.dev.client.domain.WordMessenger
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

@Service
internal class CaffeineMessageSender : WordMessenger {

    override fun send(message: SupportMessage): String {
        val client = SocketChannel.open(
            InetSocketAddress("localhost", 8089)
        )
        sendMessage(client, message)
        val ans = receiveMessage(client)
        closeConnect(client)
        return ans
    }

    private fun sendMessage(client: SocketChannel, message: SupportMessage) {
        val buffer = ByteBuffer.allocate(1024)
        buffer.put(message.toMessage().toByteArray())
        buffer.flip()
        client.write(buffer)
        buffer.clear()
    }

    private fun receiveMessage(client: SocketChannel): String {
        val buffer = ByteBuffer.allocate(1024)
        client.read(buffer)
        buffer.flip()
        return String(buffer.array()).trim { it <= ' ' }
    }

    private fun closeConnect(client: SocketChannel) {
        val buffer = ByteBuffer.allocate(1024)
        buffer.put("EXIT".toByteArray())
        buffer.flip()
        client.write(buffer)
        client.close()
    }
}
