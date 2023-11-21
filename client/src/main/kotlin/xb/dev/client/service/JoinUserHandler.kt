package xb.dev.client.service

import org.springframework.stereotype.Service
import xb.dev.client.domain.Handler
import xb.dev.client.domain.SupportMessage
import xb.dev.client.domain.WordMessenger

@Service
internal class JoinUserHandler(private val wordMessenger: WordMessenger) : Handler {

    override fun isHandleable(name: String): Boolean = name == "join"

    override fun handle(arg: Map<String, String>) {
        val name = arg["name"]
            ?: throw IllegalArgumentException("Missing parameter \"name\"")
        val password = arg["password"]
            ?: throw IllegalArgumentException("Missing parameter \"password\"")

        wordMessenger.send(SupportMessage.Setup())
        wordMessenger.send(SupportMessage.Join(name, password))
    }
}
