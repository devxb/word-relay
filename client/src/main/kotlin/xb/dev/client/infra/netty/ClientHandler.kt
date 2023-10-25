package xb.dev.client.infra.netty

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import xb.dev.client.domain.SupportMessage

internal class ClientHandler(private val message: SupportMessage) :
    SimpleChannelInboundHandler<String>() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        ctx.writeAndFlush(message).sync()
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        println("receive message : $msg")
        ctx!!.close()
    }
}
