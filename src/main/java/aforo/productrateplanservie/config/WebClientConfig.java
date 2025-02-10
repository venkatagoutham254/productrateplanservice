package aforo.productrateplanservie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean(name = "customerWebClient")
    public WebClient customerWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://44.203.171.98:8082")
                .build();

    }
}
