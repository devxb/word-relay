package xb.dev.client

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import xb.dev.client.domain.SupportMessage
import xb.dev.client.domain.WordMessenger

@SpringBootApplication
internal class Application {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val applicationContext = SpringApplication.run(Application::class.java, *args)
            val messenger = applicationContext.getBean(WordMessenger::class.java)
            println("join ${messenger.send(SupportMessage.Join("name", "password"))}")
            println("login ${messenger.send(SupportMessage.Login(
                "name", "password"))}")
        }
    }

}
