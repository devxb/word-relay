package xb.dev.client.domain

import xb.dev.core.proto.ProtocolRule

sealed class SupportMessage private constructor(val methodName: String) {

    internal class Join(
        private val name: String,
        private val password: String
    ) : SupportMessage("join") {
        override fun toMessage(): String {
            return """
                ${ProtocolRule.REQUEST.value} ${ProtocolRule.END_OF_LINE.value}
                method: join(name, password) ${ProtocolRule.END_OF_LINE.value}
                name: $name ${ProtocolRule.END_OF_LINE.value}
                password: $password ${ProtocolRule.END_OF_LINE.value} 
                ${ProtocolRule.END_OF_CONN.value}
            """.trimIndent()
        }
    }

    internal class Login(
        private val name: String,
        private val password: String
    ) : SupportMessage("login") {
        override fun toMessage(): String {
            return """
                ${ProtocolRule.REQUEST.value} ${ProtocolRule.END_OF_LINE.value}
                method: login(name, password) ${ProtocolRule.END_OF_LINE.value}
                name: $name ${ProtocolRule.END_OF_LINE.value}
                password: $password ${ProtocolRule.END_OF_LINE.value}
                ${ProtocolRule.END_OF_CONN.value}
            """.trimIndent()
        }
    }

    internal class CreateRoom(
        private val ownerId: Int,
        private val token: String
    ) : SupportMessage("createRoom") {
        override fun toMessage(): String {
            return """
                ${ProtocolRule.REQUEST.value} ${ProtocolRule.END_OF_LINE.value}
                method: createRoom(ownerId) ${ProtocolRule.END_OF_LINE.value}
                ownerId: $ownerId ${ProtocolRule.END_OF_LINE.value}
                token: $token ${ProtocolRule.END_OF_LINE.value}
                ${ProtocolRule.END_OF_CONN.value}
            """.trimIndent()
        }
    }

    internal class JoinRoom(
        private val roomId: Int,
        private val userId: Int,
        private val token: String
    ) : SupportMessage("joinRoom") {
        override fun toMessage(): String {
            return """
                ${ProtocolRule.REQUEST.value} ${ProtocolRule.END_OF_LINE.value}
                method: joinRoom(roomId, userId) ${ProtocolRule.END_OF_LINE.value}
                roomId: $roomId ${ProtocolRule.END_OF_LINE.value}
                userId: $userId ${ProtocolRule.END_OF_LINE.value}
                token: $token ${ProtocolRule.END_OF_LINE.value}
                ${ProtocolRule.END_OF_CONN.value}
            """.trimIndent()
        }
    }

    internal class SendMessage(
        private val roomId: Int,
        private val senderId: Int,
        private val message: String,
        private val token: String
    ) : SupportMessage("sendMessage") {
        override fun toMessage(): String {
            return """
                ${ProtocolRule.REQUEST.value} ${ProtocolRule.END_OF_LINE.value}
                method: sendMessage(roomId, senderId, message) ${ProtocolRule.END_OF_LINE.value}
                roomId: $roomId ${ProtocolRule.END_OF_LINE.value}
                senderId: $senderId ${ProtocolRule.END_OF_LINE.value}
                message: $message ${ProtocolRule.END_OF_LINE.value}
                token: $token ${ProtocolRule.END_OF_LINE.value}
                ${ProtocolRule.END_OF_CONN.value}
            """.trimIndent()
        }
    }

    abstract fun toMessage(): String

}
