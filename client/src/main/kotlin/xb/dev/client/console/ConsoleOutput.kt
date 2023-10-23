package xb.dev.client.console

import org.springframework.stereotype.Service
import xb.dev.client.service.Outputable

@Service
internal class ConsoleOutput : Outputable {

    override fun output(response: String) {
        println(response)
    }
}
