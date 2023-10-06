package xb.dev.word.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

internal class RoomTest : DescribeSpec({

    describe("publish 메소드는") {

        context("Message와 Publisher가 주어지면,") {
            val room = defaultRoom()
            val message = defaultMessage()

            it("Room을 publisher에게 publishing 한다.") {
                room.publish(message, mockPublisher)

                verify(exactly = 1) { mockPublisher.publish(room) }
            }
        }

        context("User가 Room에 참가중이지 않다면,") {
            val room = defaultRoom()
                .apply { this.publish(defaultMessage(), mockPublisher) }
            val nonCompatibleMessage = defaultMessage(senderId = 2L)

            it("IllegalArgumentException을 던진다.") {
                val exception: IllegalArgumentException = shouldThrow {
                    room.publish(nonCompatibleMessage, mockPublisher)
                }

                exception.message shouldBe "참가하지 않은 유저 입니다. \"2\""
            }
        }

        context("Message의 첫말과 Room의 끝말이 일치하지 않는다면,") {
            val room = defaultRoom()
                .apply { this.publish(defaultMessage(), mockPublisher) }
            val nonCompatibleMessage = defaultMessage(message = "ddd")

            it("IllegalArgumentException을 던진다.") {
                val exception: IllegalArgumentException = shouldThrow {
                    room.publish(nonCompatibleMessage, mockPublisher)
                }

                exception.message shouldBe "메시지의 끝 \"a\" 과 새로운 메시지의 시작 \"d\" 이 일치하지 않습니다."
            }
        }
    }
}) {

    companion object {
        private val mockPublisher = mockk<RoomPublisher>()
            .also {
                every { it.publish(any()) } returns Unit
            }
    }
}
