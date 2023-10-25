package xb.dev.client.infra.netty

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import xb.dev.client.domain.SupportMessage
import xb.dev.client.service.Outputable

internal class ClientHandler(
    private val message: SupportMessage,
    private val outputable: Outputable
) :
    SimpleChannelInboundHandler<String>() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        ctx.writeAndFlush(message).sync()
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        outputable.output(msg!!)
        ctx!!.close()
    }
}
