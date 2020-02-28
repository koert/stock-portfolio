package nl.zencode.port.stock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class StockApplication {
	@Bean
	fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

	@Bean
	fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build();

}

fun main(args: Array<String>) {
	runApplication<StockApplication>(*args)
}
