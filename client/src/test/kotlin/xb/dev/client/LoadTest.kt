package xb.dev.client

import io.kotest.core.spec.style.AnnotationSpec
import org.springframework.boot.test.context.SpringBootTest
import xb.dev.client.domain.Handler

@SpringBootTest
internal class LoadTest(private val handlers: List<Handler>) : AnnotationSpec() {

    @Test
    fun loadTest() {

    }

    private fun handle(methodName: String, params: Map<String, String>) {
        val handler = handlers.find { it.isHandleable(methodName) }
            ?: throw IllegalArgumentException("Cannot find handler named \"${methodName}\"")
    }
}
