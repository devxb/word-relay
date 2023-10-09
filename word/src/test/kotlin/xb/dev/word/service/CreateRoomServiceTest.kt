package xb.dev.word.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.verify
import org.springframework.test.context.ContextConfiguration
import xb.dev.core.id.IdGenerator
import xb.dev.word.domain.RoomRepository

@ContextConfiguration(classes = [CreateRoomService::class])
internal class CreateRoomServiceTest(
    private val createRoomService: CreateRoomService,
    @MockkBean private val roomRepository: RoomRepository,
    @MockkBean private val idGenerator: IdGenerator
) : DescribeSpec({

    beforeTest {
        every { roomRepository.createRoom(any()) } returns 1L
        every { idGenerator.generate() } returns 1L
    }

    describe("create 메소드는") {
        val ownerId = 1L;

        context("ownerId가 주어지면,") {
            it("새로운 방을 생성한다.") {
                createRoomService.create(ownerId)

                verify(exactly = 1) { roomRepository.createRoom(any()) }
            }
        }
    }
})
