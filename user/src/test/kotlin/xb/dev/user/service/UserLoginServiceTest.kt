package xb.dev.user.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.test.context.ContextConfiguration
import xb.dev.user.domain.User
import xb.dev.user.domain.UserJpaRepository

@ContextConfiguration(classes = [UserLoginService::class])
internal class UserLoginServiceTest(
    private val userLoginService: UserLoginService,
    @MockkBean private val userJpaRepository: UserJpaRepository
) : DescribeSpec({

    beforeTest {
        every { userJpaRepository.findByNameOrNull(any()) } returns null
        every { userJpaRepository.findByNameOrNull(SAVED_USER_NAME) } returns savedUser
        every { userJpaRepository.save(savedUser) } returns savedUser
        userJpaRepository.save(savedUser)
    }

    describe("login 메소드는") {

        context("올바른 username과 userpassword가 주어지면,") {
            it("토큰을 반환한다.") {
                val result: String = userLoginService.login(SAVED_USER_NAME, SAVED_USER_PASSWORD)

                result shouldBe SAVED_USER_ID.toString()
            }
        }

        context("username에 해당하는 user를 찾을 수 없다면,") {
            it("IllegalArgumentException을 던진다.") {
                val exception: IllegalArgumentException = shouldThrow {
                    userLoginService.login(NOT_SAVED_USER_NAME, "123")
                }

                exception.message shouldBe "Cannot find user named \"${NOT_SAVED_USER_NAME}\""
            }
        }
    }
}) {
    companion object {
        private const val SAVED_USER_ID = 1L
        private const val SAVED_USER_NAME = "saved"
        private const val SAVED_USER_PASSWORD = "1234"
        private val savedUser = User(SAVED_USER_ID, SAVED_USER_NAME, SAVED_USER_PASSWORD)
        private const val NOT_SAVED_USER_NAME = "not_saved"
    }
}
