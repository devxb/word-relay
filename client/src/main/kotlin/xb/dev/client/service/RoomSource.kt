package xb.dev.word.service

private const val HASH = "Room"
private const val SEPARATOR = ":"

internal fun getKey(id: Int): String = "${HASH}${SEPARATOR}${id}"
