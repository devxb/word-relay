package xb.dev.client.console

import org.springframework.stereotype.Service
import xb.dev.client.service.Inputable
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
internal object ConsoleInputer : Inputable {

    private val reader = BufferedReader(InputStreamReader(System.`in`))

    override fun input(): Inputable.Input {
        val line = reader.readLine().split(" ")
        val methodName = line[0]
        val params = mutableMapOf<String, String>()
        for (i in 1..<line.size) {
            val param = line[i].split(":")
            params[param[0]] = param[1]
        }
        return Inputable.Input(methodName, params)
    }
}
