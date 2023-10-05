package xb.dev.user.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.test.context.ContextConfiguration
import xb.dev.core.id.SequenceIdGenerator
import xb.dev.user.TestRoot
import xb.dev.user.domain.User
import xb.dev.user.domain.UserJpaRepository

@ContextConfiguration(classes = [TestRoot::class, SequenceIdGenerator::class, UserJoinService::class])
internal class UserJoinServiceTest(
    val userJoinService: UserJoinService,
    @MockkBean val userJpaRepository: UserJpaRepository
) : DescribeSpec({

    beforeTest {
        every { userJpaRepository.existsByName(NEW_NAME) } returns false
        every { userJpaRepository.existsByName(DUPLICATED_NAME) } returns true
        every { userJpaRepository.save(any()) } returns User(1L, NEW_NAME, PASSWORD)
    }

    describe("join 메소드는") {

        context("새로운 유저의 name이 입력될 경우,") {

            it("새로운 user를 생성하고 id를 반환한다.") {
                val result = userJoinService.join(NEW_NAME, PASSWORD)

                result::class shouldBe Long::class
            }
        }

        context("중복된 유저의 name이 입력될 경우,") {

            it("IllegalArgumentException을 던진다.") {
                val exception: IllegalArgumentException =
                    shouldThrow { userJoinService.join(DUPLICATED_NAME, PASSWORD) }

                exception.message shouldBe "Duplicated User name \"${DUPLICATED_NAME}\""
            }
        }
    }
}) {

    companion object {
        private const val NEW_NAME = "new"
        private const val DUPLICATED_NAME = "duplicated"
        private const val PASSWORD = "1234"
    }
}
