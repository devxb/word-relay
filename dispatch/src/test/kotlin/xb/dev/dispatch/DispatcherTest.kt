package xb.dev.dispatch

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [Dispatcher::class, TestHandler::class])
internal class DispatcherTest(private val dispatcher: Dispatcher) : DescribeSpec({

    describe("dispatch 메소드는") {

        context("handle 가능한 Caffeine 이 주어지면,") {
            it("Handler를 호출한다.") {
                val result = dispatcher.dispatch(handleableCaffeine)

                result shouldBe handleableCaffeine.parameters.toString()
            }
        }

        context("handle 불가능한 Caffeine 이 주어지면,") {
            it("IllegalArgumentException 을 던진다.") {
                val exception = shouldThrow<IllegalArgumentException> {
                    dispatcher.dispatch(notHandleableCaffeine)
                }

                exception.message shouldBe "Cannot find handler \"cannot handle\""
            }
        }
    }


}) {
    companion object {
        private val handleableCaffeine = Caffeine("test", mapOf("p1" to "p2"), false)
        private val notHandleableCaffeine = Caffeine("cannot handle", mapOf("p1" to "p2"), false)
    }
}
