package xb.dev.core.id

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
object SequenceIdGenerator : IdGenerator {

    private val increment: Long = 1L;
    private val sequence: AtomicLong = AtomicLong(1L)

    override fun generate(): Long = sequence.getAndAdd(increment)
}
