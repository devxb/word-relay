package xb.dev.client.infra.netty

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import org.springframework.stereotype.Service
import xb.dev.client.domain.SupportMessage

@Service
@Sharable
internal class RequestCaffeineEncoder : MessageToByteEncoder<SupportMessage>() {

    override fun encode(ctx: ChannelHandlerContext?, msg: SupportMessage?, out: ByteBuf?) {

        val sendMessage = msg!!.toMessage()
        out!!.writeInt(sendMessage.length)
            .writeBytes(sendMessage.toByteArray())
    }
}
