package xb.dev

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import xb.dev.server.caffeine.CaffeineServer

@SpringBootApplication
open class Application {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val applicationContext = SpringApplication.run(Application::class.java, *args)

            applicationContext.getBean(CaffeineServer::class.java).run {
                this.start()
            }
        }
    }

}
