package xb.dev.server.netty

import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.springframework.stereotype.Service
import xb.dev.core.proto.ProtocolRule
import xb.dev.dispatch.Caffeine
import xb.dev.dispatch.Dispatchable

@Service
@Sharable
class DispatcherAdaptor(private val dispatchable: Dispatchable) :
    SimpleChannelInboundHandler<Caffeine>() {

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: Caffeine?) {
        try {
            if (msg!!.isSetupRequest) {
                val future = ctx!!.writeAndFlush(ProtocolRule.LEASE.value)
                future.addListener { ChannelFutureListener.CLOSE }
                return
            }
            val ans = withResponseHeader(dispatchable.dispatch(msg))
            val future = ctx!!.writeAndFlush(ans)
            future.addListener { ChannelFutureListener.CLOSE }
        } catch (exception: Exception) {
            val future = ctx!!.writeAndFlush(withExceptionHeader(exception.message))
            future.addListener { ChannelFutureListener.CLOSE }
        }
    }

    private fun withResponseHeader(response: String?): String {
        return """
            ${ProtocolRule.RESPONSE.value} + ${ProtocolRule.END_OF_LINE.value}
            $response
        """.trimIndent()
    }

    private fun withExceptionHeader(response: String?): String {
        return """
            ${ProtocolRule.EXCEPTION.value} + ${ProtocolRule.END_OF_LINE.value}
            $response
        """.trimIndent()
    }
}
