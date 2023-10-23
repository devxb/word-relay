package xb.dev.user.infra

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import xb.dev.user.domain.Token
import xb.dev.user.domain.User
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
internal class JwtToken : Token {

    override fun getToken(user: User): String = Jwts.builder()
        .issuer(ISSUER)
        .subject(SUBJECT)
        .expiration(expiration)
        .claim(USER_ID, user.id)
        .signWith(key)
        .compact()

    override fun getId(token: String): Long = Jwts.parser()
        .verifyWith(key)
        .requireIssuer(ISSUER)
        .requireSubject(SUBJECT)
        .build()
        .parseSignedClaims(token)
        .payload[USER_ID].toString().toLong()

    companion object {
        private const val ISSUER = "word-relay"
        private const val SUBJECT = "user-auth-token"
        private const val USER_ID = "id"

        private val key = Jwts.SIG.HS256.key().build();
        private val expiration: Date = Date.from(Instant.now().plus(7, ChronoUnit.DAYS))
    }
}
