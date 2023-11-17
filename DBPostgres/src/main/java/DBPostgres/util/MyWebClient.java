package DBPostgres.util;

import DBPostgres.util.filter.WebClientFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MyWebClient {
    @Value("${value.address.tg}")
    private String baseUrlDB;

    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient
                .builder()
                .baseUrl(baseUrlDB)
                .filter(WebClientFilter.logRequest())
                .filter(WebClientFilter.logResponse());
    }
}