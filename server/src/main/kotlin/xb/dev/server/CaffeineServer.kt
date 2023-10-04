package xb.dev.server

import org.springframework.stereotype.Service
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

@Service
class CaffeineServer(private val dispatcher: Dispatchable) {

    fun start() {
        val selector = Selector.open()
        val channel = ServerSocketChannel.open()
            .apply {
                this.socket().bind(InetSocketAddress("localhost", 8089))
                this.configureBlocking(false)
                this.register(selector, this.validOps(), null)
            }

        while(true) {
            selector.select()
            val iter = selector.selectedKeys().iterator()

            while (iter.hasNext()) {
                val selectedKeys = iter.next()
                when {
                    selectedKeys.isAcceptable -> accept(selector, channel)
                    selectedKeys.isReadable -> read(selectedKeys)
                }
                iter.remove()
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
        val buffer = ByteBuffer.allocate(1024)
        client.read(buffer)
        val message = String(buffer.array()).trim { it <= ' ' }
        if (message == "close") {
            client.close()
            return
        }

        val caffeine = Caffeine.of(message)
        println(caffeine)
        dispatcher.dispatch(caffeine)
    }

}
