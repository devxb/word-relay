package xb.dev.engine.server

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Service
import xb.dev.engine.server.Caffeine
import xb.dev.engine.server.CaffeineServer
import xb.dev.engine.server.Dispatchable

@SpringBootApplication
open class TestServerRunner {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val applicationContext = SpringApplication.run(TestServerRunner::class.java, *args)
            val caffeineServer = applicationContext.getBean(CaffeineServer::class.java)
            caffeineServer.start()
        }
    }
}

@Service
class LogDispatcher : Dispatchable {

    val logger = LoggerFactory.getLogger(this::class.simpleName)

    override fun dispatch(caffeine: Caffeine): String {
        logger.info(caffeine.toString())
        return ""
    }
}
