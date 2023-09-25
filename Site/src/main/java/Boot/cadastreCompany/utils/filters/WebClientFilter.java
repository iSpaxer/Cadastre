package Boot.cadastreCompany.utils.filters;

import Boot.cadastreCompany.exception.DBRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class WebClientFilter {

    private static final Logger log = LoggerFactory.getLogger(WebClientFilter.class);

    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            logMethodAndUrl(request);
            logHeaders(request);

            return Mono.just(request);
        });

    }

    public static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            logStatus(response);
            logHeaders(response);
            return logBody(response);
        });
    }


    private static void logStatus(ClientResponse response) {
        HttpStatus status = (HttpStatus) response.statusCode();
        log.info("Returned status code {} ({})", status.value(), status.getReasonPhrase());
    }


    private static Mono<ClientResponse> logBody(ClientResponse response) {
        if (response.statusCode() != null && (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError())) {
            return response.bodyToMono(String.class)
                    .flatMap(body -> {
                        log.info("Body is {}", body);
                        System.out.println("___________________________");
                        return Mono.error(new DBRequestException(body, response.statusCode().value()));
                    });
        } else {
            return Mono.just(response);
        }
    }


    private static void logHeaders(ClientResponse response) {
        response.headers().asHttpHeaders().forEach((name, values) -> {
            values.forEach(value -> {
                logNameAndValuePair(name, value);
            });
        });
    }


    private static void logHeaders(ClientRequest request) {
        request.headers().forEach((name, values) -> {
            values.forEach(value -> {
                logNameAndValuePair(name, value);
            });
        });
    }


    private static void logNameAndValuePair(String name, String value) {
        log.info("{}={}", name, value);
    }


    private static void logMethodAndUrl(ClientRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.method().name());
        sb.append(" to ");
        sb.append(request.url());
        log.info(sb.toString());
    }
}
