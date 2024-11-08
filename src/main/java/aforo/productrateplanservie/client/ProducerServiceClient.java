package aforo.productrateplanservie.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ProducerServiceClient {
    private final WebClient webClient;

    @Autowired
    public ProducerServiceClient(@Qualifier("producerWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public boolean validateProducerId(Long producerId) {
        try {
            webClient.get()
                    .uri("/v1/api/producers/{producerId}", producerId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            return true;
        } catch (WebClientResponseException.NotFound e) {
            return false;
        } catch (WebClientResponseException.InternalServerError e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to validate producerId");
        }
    }
    
    public boolean validateOrganizationId(Long organizationId) {
        try {
            webClient.get()
                    .uri("/v1/api/organizations/{organizationId}", organizationId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            return true;
        } catch (WebClientResponseException.NotFound e) {
            return false;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to validate organizationId");
        }
    }

    public boolean validateDivisionId(Long divisionId) {
        try {
            webClient.get()
                    .uri("/v1/api/divisions/{divisionId}", divisionId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            return true;
        } catch (WebClientResponseException.NotFound e) {
            return false;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to validate divisionId");
        }
    }
}
