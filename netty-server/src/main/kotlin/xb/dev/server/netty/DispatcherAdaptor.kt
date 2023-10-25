package xb.dev.server.netty

import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.springframework.stereotype.Service
import xb.dev.dispatch.Caffeine
import xb.dev.dispatch.Dispatchable

@Service
@Sharable
class DispatcherAdaptor(private val dispatchable: Dispatchable) :
    SimpleChannelInboundHandler<Caffeine>() {

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: Caffeine?) {
        val ans = dispatchable.dispatch(msg!!)
        val future = ctx!!.writeAndFlush(ans)
        future.addListener { ChannelFutureListener.CLOSE }
    }
}
