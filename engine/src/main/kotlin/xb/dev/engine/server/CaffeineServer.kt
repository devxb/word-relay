package xb.dev.engine.server

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

@Service
class CaffeineServer(private val dispatcher: Dispatchable) {

    private val hostName = "localhost"
    private val port = 8089
    private val logger = LoggerFactory.getLogger(this::class.qualifiedName)

    fun start() {
        val selector = Selector.open()
        val channel = ServerSocketChannel.open()
            .apply {
                this.socket().bind(InetSocketAddress(hostName, port))
                this.configureBlocking(false)
                this.register(selector, validOps(), null)
            }
        logger.info("Caffeine Server started on \"${hostName}:${port}\"")
        while (true) {
            try {
                selector.select()
                val iter = selector.selectedKeys().iterator()
                while (iter.hasNext()) {
                    val selectedKeys = iter.next()
                    try {
                        when {
                            selectedKeys.isAcceptable -> accept(selector, channel)
                            selectedKeys.isReadable -> read(selectedKeys)
                        }
                    } catch (exception: Exception) {
                        val client = (selectedKeys.channel() as SocketChannel)
                        client.close()
                    }
                    iter.remove()
                }
            } catch (_: Exception) {
            }
        }
    }

    private fun accept(selector: Selector, mySocket: ServerSocketChannel) = mySocket.accept()
        .apply {
            this.configureBlocking(false)
            this.register(selector, SelectionKey.OP_READ)
        }

    private fun read(key: SelectionKey) {
        val client = (key.channel() as SocketChannel)
        val readBuffer = ByteBuffer.allocate(1024)
        client.read(readBuffer)
        val message = String(readBuffer.array()).trim { it <= ' ' }
        if (message == "EXIT") {
            client.close()
            return
        }

        val caffeine = Caffeine.of(message)
        logger.info("Message input $caffeine")
        val reply = dispatcher.dispatch(caffeine)
        logger.info("Message reply $reply")
        reply(key, reply)
    }

    private fun reply(key: SelectionKey, reply: String) {
        val client = (key.channel() as SocketChannel)
        val replyBuffer = ByteBuffer.allocate(1024)
        replyBuffer.put(reply.toByteArray())
        replyBuffer.flip()
        client.write(replyBuffer)
        replyBuffer.clear()
    }

}
