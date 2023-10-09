package xb.dev.word.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class RoomTest : DescribeSpec({

    describe("validMessage 메소드는") {

        context("Message가 주어지면,") {
            val room = defaultRoom()
            val message = defaultMessage()

            it("Message를 검증한다.") {
                room.validMessage(message)
            }
        }

        context("User가 Room에 참가중이지 않다면,") {
            val room = defaultRoom().apply { this.validMessage(defaultMessage()) }
            val nonCompatibleMessage = defaultMessage(senderId = 2L)

            it("IllegalArgumentException을 던진다.") {
                val exception: IllegalArgumentException = shouldThrow {
                    room.validMessage(nonCompatibleMessage)
                }

                exception.message shouldBe "참가하지 않은 유저 입니다. \"2\""
            }
        }

        context("Message의 첫말과 Room의 끝말이 일치하지 않는다면,") {
            val room = defaultRoom().apply { this.validMessage(defaultMessage()) }
            val nonCompatibleMessage = defaultMessage(message = "ddd")

            it("IllegalArgumentException을 던진다.") {
                val exception: IllegalArgumentException = shouldThrow {
                    room.validMessage(nonCompatibleMessage)
                }

                exception.message shouldBe "메시지의 끝 \"a\" 과 새로운 메시지의 시작 \"d\" 이 일치하지 않습니다."
            }
        }
    }
})
