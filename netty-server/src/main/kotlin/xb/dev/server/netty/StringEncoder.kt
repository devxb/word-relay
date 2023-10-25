package xb.dev.server.netty

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

internal class StringEncoder : MessageToByteEncoder<String>() {

    override fun encode(ctx: ChannelHandlerContext?, msg: String?, out: ByteBuf?) {
        out!!.writeBytes(msg!!.toByteArray())
    }
}
