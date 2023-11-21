package xb.dev.dispatch

import xb.dev.core.proto.ProtocolRule

data class Caffeine(
    val method: String,
    val parameters: Map<String, String>,
    val isSetupRequest: Boolean,
) {

    companion object {

        fun of(message: String): Caffeine {
            val lines = message.split(ProtocolRule.END_OF_LINE.value)

            if (isSetupRequest(lines)) {
                return Caffeine("", mapOf(), true)
            }

            validProtocol(lines)

            val methodInfo = lines.find { it.split(":")[0].trim() == "method" }
                ?.let { parseMethod(it) } ?: throw IllegalArgumentException(message)

            val parameters = lines.asSequence()
                .filter {
                    it.split(":")[0] != "method"
                            && it.split(":").size == 2
                }
                .map {
                    val line = it.split(":")
                    val parameterName = line[0].trim()
                    val parameterValue = line[1].trim()

                    parameterName to parameterValue
                }
                .toMap()

            return Caffeine(methodInfo.first.split(":")[1].trim(), parameters, false)
        }

        private fun isSetupRequest(lines: List<String>): Boolean {
            return !lines.find { it == ProtocolRule.SETUP.value }.isNullOrBlank()
        }

        private fun validProtocol(lines: List<String>) {
            requireNotNull(lines.find { it.trim() == ProtocolRule.REQUEST.value }) {
                "올바르지 않은 프로토콜 포맷"
            }
        }

        private fun parseMethod(methodInfo: String): Pair<String, Map<String, Int>> {
            val endOfMethodNameIndex = methodInfo.indexOfFirst { c -> c == '(' }
            val methodName = methodInfo.substring(0, endOfMethodNameIndex)

            val parameterPart =
                methodInfo.substring(endOfMethodNameIndex + 1, methodInfo.length - 1)
            val parameters = parameterPart.split(",")
                .withIndex()
                .asSequence()
                .map { it.value to it.index }
                .toMap()

            return methodName to parameters
        }
    }
}
