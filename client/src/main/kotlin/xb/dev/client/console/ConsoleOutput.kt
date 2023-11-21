package xb.dev.client.console

import org.springframework.stereotype.Service
import xb.dev.client.domain.Outputable

@Service
internal object ConsoleOutput : Outputable {

    override fun output(response: String) {
        println(response)
    }
}
