package xb.dev.client.infra

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class MapperConfig {

    @Bean
    internal fun objectMapper(): ObjectMapper = ObjectMapper().apply {
        this.registerModule(ParameterNamesModule())
    }

}
