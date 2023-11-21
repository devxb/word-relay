package xb.dev.client

internal class ActiveUser() : Runnable {

    override fun run() {
        
    }

    private fun join(name: String, password: String): Map<String, String> =
        mapOf("name" to name, "password" to password)

    private fun login(name: String, password: String): Map<String, String> =
        mapOf("name" to name, "password" to password)

    private fun createRoom(ownerId: String, token: String): Map<String, String> =
        mapOf("ownerId" to ownerId, "token" to token)

    private fun joinRoom(roomId: String, userId: String, token: String): Map<String, String> =
        mapOf("roomId" to roomId, "userId" to userId, "token" to token)

    private fun sendMessage(
        roomId: String,
        senderId: String,
        message: String,
        token: String
    ): Map<String, String> =
        mapOf("roomId" to roomId, "senderId" to senderId, "message" to message, "token" to token)
}
