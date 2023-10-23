package xb.dev.client

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import xb.dev.client.domain.Handler
import xb.dev.client.service.Inputable

@SpringBootApplication
internal class Application {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val applicationContext = SpringApplication.run(Application::class.java, *args)
            val handlers: List<Handler> =
                applicationContext.getBeansOfType(Handler::class.java).values.toList()
            val inputer = applicationContext.getBean(Inputable::class.java)

            while (true) {
                val request = inputer.input()
                val handler = handlers.find { it.isHandleable(request.methodName) }
                    ?: throw IllegalArgumentException("Cannot find handler named \"${request.methodName}\"")
                handler.handle(request.parameters)
            }
        }
    }

}
