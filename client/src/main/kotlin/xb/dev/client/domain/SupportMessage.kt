package xb.dev.client.domain

sealed class SupportMessage private constructor(val methodName: String) {

    internal class Join(
        private val name: String,
        private val password: String
    ) : SupportMessage("join") {
        override fun toMessage(): String {
            return """
                method: join(name, password) $END_OF_LINE
                name: $name $END_OF_LINE
                password: $password $END_OF_LINE 
                $END_OF_CONN
            """.trimIndent()
        }
    }

    internal class Login(
        private val name: String,
        private val password: String
    ) : SupportMessage("login") {
        override fun toMessage(): String {
            return """
                method: login(name, password) $END_OF_LINE
                name: $name $END_OF_LINE
                password: $password $END_OF_LINE
                $END_OF_CONN
            """.trimIndent()
        }
    }

    internal class CreateRoom(
        private val ownerId: Int,
        private val token: String
    ) : SupportMessage("createRoom") {
        override fun toMessage(): String {
            return """
                method: createRoom(ownerId) $END_OF_LINE
                ownerId: $ownerId $END_OF_LINE
                token: $token $END_OF_LINE
                $END_OF_CONN
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
                method: joinRoom(roomId, userId) $END_OF_LINE
                roomId: $roomId $END_OF_LINE
                userId: $userId $END_OF_LINE
                token: $token $END_OF_LINE
                $END_OF_CONN
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
                method: sendMessage(roomId, senderId, message) *EOL*
                roomId: $roomId $END_OF_LINE
                senderId: $senderId $END_OF_LINE
                message: $message $END_OF_LINE
                token: $token $END_OF_LINE
                $END_OF_CONN
            """.trimIndent()
        }
    }

    abstract fun toMessage(): String

    private companion object {
        private const val END_OF_LINE = "*EOL*"
        private const val END_OF_CONN = "*EOC*"
    }
}
