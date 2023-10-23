package xb.dev.client.domain

import com.fasterxml.jackson.annotation.JsonProperty

internal class Message(
    @JsonProperty("id\$word") internal val id: Long,
    @JsonProperty("senderId\$word") internal val senderId: Long,
    @JsonProperty("message\$word") internal val message: String
)
