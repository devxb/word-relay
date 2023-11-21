package xb.dev.server.netty

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import xb.dev.core.proto.ProtocolRule
import java.nio.charset.Charset

@Service
class NettyServer(private val dispatcherAdaptor: DispatcherAdaptor) {

    private val logger = LoggerFactory.getLogger(this::class.qualifiedName)

    fun start() {
        val parentGroup = NioEventLoopGroup()
        val childGroup = NioEventLoopGroup()
        try {
            val serverBootStrap = ServerBootstrap()
            serverBootStrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel) {
                        ch.pipeline()
                            .addLast(
                                DelimiterBasedFrameDecoder(
                                    Int.MAX_VALUE,
                                    true,
                                    true,
                                    Unpooled.copiedBuffer(
                                        ProtocolRule.END_OF_CONN.value,
                                        Charset.forName("UTF-8")
                                    )
                                )
                            )
                            .addLast(CaffeineDecoder())
                            .addLast(StringEncoder())
                            .addLast(dispatcherAdaptor)
                    }
                }).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)

            val channelFuture = serverBootStrap.bind(HOST_NAME, PORT).sync()
            logger.info("Netty server on $HOST_NAME:$PORT")
            channelFuture.channel().closeFuture().sync()
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            parentGroup.shutdownGracefully()
            childGroup.shutdownGracefully()
        }
    }

    companion object {
        private const val HOST_NAME = "localhost"
        private const val PORT = 8089
    }
}
