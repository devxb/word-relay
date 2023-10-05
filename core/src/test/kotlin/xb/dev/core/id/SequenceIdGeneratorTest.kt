package xb.dev.core.id

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [SequenceIdGenerator::class])
internal class SequenceIdGeneratorTest(private val idGenerator: IdGenerator) : DescribeSpec({

    describe("generate 메소드는") {

        context("호출되면,") {
            it("Long type의 id를 반환한다.") {
                val result = idGenerator.generate()

                result::class shouldBe Long::class
            }

            it("호출될때마다, 값이 1씩 증가한다.") {
                val beforeResult = idGenerator.generate()
                val afterResult = idGenerator.generate()

                beforeResult + 1 shouldBe afterResult
            }
        }
    }
})
