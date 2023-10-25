package xb.dev.server.netty

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ReplayingDecoder
import xb.dev.dispatch.Caffeine
import java.nio.charset.Charset

internal class CaffeineDecoder : ReplayingDecoder<Caffeine>() {

    override fun decode(ctx: ChannelHandlerContext?, buf: ByteBuf?, out: MutableList<Any>?) {
        try {
            val length = buf!!.readInt() - CANT_TOUCHABLE_ZONE

            val message = buf.readCharSequence(length, Charset.forName("UTF-8")).toString()
            out!!.add(Caffeine.of(message))
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private companion object {
        private const val CANT_TOUCHABLE_ZONE = 5
    }
}
