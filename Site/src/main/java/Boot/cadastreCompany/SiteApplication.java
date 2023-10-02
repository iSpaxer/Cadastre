package Boot.cadastreCompany;

import Boot.cadastreCompany.utils.filters.WebClientFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SiteApplication {

	@Value("${value.address.db}")
	private String baseUrlDB;

	@Bean
	public WebClient.Builder getWebClientBuilder() {
		return WebClient
				.builder()
				.baseUrl(baseUrlDB)
				.filter(WebClientFilter.logRequest())
				.filter(WebClientFilter.logResponse());
	}

	public static void main(String[] args) {
		SpringApplication.run(SiteApplication.class, args);
	}

}
