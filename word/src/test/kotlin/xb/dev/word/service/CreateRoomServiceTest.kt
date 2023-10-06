package xb.dev.word.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.verify
import org.springframework.data.redis.core.ValueOperations
import org.springframework.test.context.ContextConfiguration
import xb.dev.core.id.IdGenerator
import xb.dev.word.domain.Room

@ContextConfiguration(classes = [CreateRoomService::class])
internal class CreateRoomServiceTest(
    private val createRoomService: CreateRoomService,
    @MockkBean private val opsForValue: ValueOperations<String, Room>,
    @MockkBean private val idGenerator: IdGenerator
) : DescribeSpec({

    beforeTest {
        every { opsForValue.set(any(), any()) } returns Unit
        every { idGenerator.generate() } returns 1L
    }

    describe("create 메소드는") {
        val ownerId = 1L;

        context("ownerId가 주어지면,") {
            it("새로운 방을 생성한다.") {
                createRoomService.create(ownerId)

                verify(exactly = 1) { opsForValue[any()] = any() }
            }
        }
    }
})
