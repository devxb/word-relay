package xb.dev.word.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import org.springframework.test.context.ContextConfiguration
import xb.dev.word.domain.Message
import xb.dev.word.domain.Room
import xb.dev.word.domain.RoomPublisher
import xb.dev.word.domain.RoomRepository

@ContextConfiguration(classes = [SendMessageService::class])
internal class SendMessageServiceTest(
    private val sendMessageService: SendMessageService,
    @MockkBean private val roomPublisher: RoomPublisher,
    @MockkBean private val roomRepository: RoomRepository
) : DescribeSpec({

    beforeTest {
        every { roomPublisher.publish(any(), any()) } returns Unit
        every { roomRepository.findRoomById(any()) } returns null
        every { roomRepository.findRoomById(roomId) } returns room
    }

    describe("send 메소드는") {

        context("roomId와, Message가 입력될 경우,") {
            it("메시지를 방에 pub 한다.") {
                sendMessageService.send(roomId, message)

                verify(exactly = 1) { roomPublisher.publish(roomId, message) }
            }
        }

        context("roomId에 해당하는 Room을 찾을 수 없는 경우,") {
            it("IllegalArgumentException을 던진다.") {
                val exception = shouldThrow<IllegalArgumentException> {
                    sendMessageService.send(2L, message)
                }

                exception.message shouldBe "roomId 에 해당하는 Room을 찾을 수 없습니다. \"2\""
            }
        }
    }
}) {

    companion object {
        private const val roomId = 1L;
        private val room = Room(roomId, 1L)
        private val message = Message(1L, 1L, "안녕하세요")
    }
}
