package xb.dev.core.proto

enum class ProtocolRule(val value: String) {
    SETUP("0x00"),
    LEASE("0x01"),
    REQUEST("0x02"),
    RESPONSE("0x03"),
    EXCEPTION("0x04"),
    END_OF_LINE("0x06"),
    END_OF_CONN("0x07"),
}
