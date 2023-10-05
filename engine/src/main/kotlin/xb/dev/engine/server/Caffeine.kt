package xb.dev.engine.server

data class Caffeine(val method: String, val parameters: Map<String, String>) {

    companion object {

        private const val END_OF_LINE = " *EOL*"

        fun of(message: String): Caffeine {
            val lines = message.split(END_OF_LINE)

            val methodInfo = lines.find { it.split(":")[0] == "method" }
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

            return Caffeine(methodInfo.first.split(":")[1].trim(), parameters)
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
