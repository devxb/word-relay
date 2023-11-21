package xb.dev.client.infra.netty

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringDecoder
import org.springframework.stereotype.Service
import xb.dev.client.domain.SupportMessage
import xb.dev.client.domain.WordMessenger
import xb.dev.client.service.Outputable

@Service
internal class NettyCaffeineMessageSender(
    private val requestCaffeineEncoder: RequestCaffeineEncoder,
    private val outputable: Outputable,
) : WordMessenger {

    private val workerGroup = NioEventLoopGroup()

    override fun send(message: SupportMessage): String {
        try {
            val bootstrap = Bootstrap()
                .apply {
                    this.group(workerGroup)
                    this.channel(NioSocketChannel::class.java)
                    this.option(ChannelOption.SO_KEEPALIVE, true)
                    this.handler(object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(ch: SocketChannel) {
                            ch.pipeline()
                                .addLast(requestCaffeineEncoder)
                                .addLast(StringDecoder())
                                .addLast(ClientHandler(message, outputable))
                        }
                    })
                }
            val channelFuture = bootstrap.connect(HOST, PORT).sync()
            channelFuture.channel().closeFuture().sync()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return ""
    }

    companion object {
        private const val PORT = 8089
        private const val HOST = "localhost"
    }
}
