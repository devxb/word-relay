package xb.dev.user.infra

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.test.context.ContextConfiguration
import xb.dev.user.domain.User

@ContextConfiguration(classes = [JwtToken::class])
internal class JwtTokenTest(private val token: JwtToken) : DescribeSpec({

    describe("getToken 메소드는") {
        context("user가 주어지면,") {
            it("JWS를 반환한다.") {
                val result = token.getToken(user)

                result::class shouldBe String::class
            }
        }
    }

    describe("getId 메소드는") {
        context("JWS가 주어지면,") {
            val jws = token.getToken(user)
            val expectedId = USER_ID

            it("userId에 해당하는 payload값을 반환한다.") {
                val result = token.getId(jws)

                result shouldBe expectedId
            }
        }
    }
}) {

    companion object {
        private const val USER_ID = 1L
        private val user = User(USER_ID, "jwt", "jwt123")
    }
}
