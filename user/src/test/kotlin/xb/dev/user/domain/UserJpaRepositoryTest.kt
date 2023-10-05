package xb.dev.user.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.shouldBe
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ContextConfiguration
import xb.dev.user.TestRoot

@DataJpaTest
@ContextConfiguration(classes = [TestRoot::class])
internal class UserJpaRepositoryTest(private val repository: UserJpaRepository) : DescribeSpec({

    beforeEach { repository.save(defaultUser) }

    describe("findByIdOrNull 메소드는") {

        context("저장된 유저를 조회할 경우") {
            it("User를 반환한다.") {
                val result = repository.findByIdOrNull(savedUserId)!!

                result shouldBeEqualUsingFields defaultUser
            }
        }

        context("저장되지 않은 유저를 조회할 경우") {
            it("Null을 반환한다.") {
                val result: User? = repository.findByIdOrNull(notSavedUserId)

                result shouldBe null
            }
        }
    }
}) {

    companion object {

        private val defaultUser = User(1L, "hello", "12345")
        private const val savedUserId = 1L;
        private const val notSavedUserId = 2L;
    }
}
