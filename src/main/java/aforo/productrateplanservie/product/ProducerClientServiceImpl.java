package aforo.productrateplanservie.product;

import aforo.productrateplanservie.client.ProducerServiceClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProducerClientServiceImpl implements ProducerClientService {
    private final ProducerServiceClient producerServiceClient;

    public ProducerClientServiceImpl(ProducerServiceClient producerServiceClient) {
        this.producerServiceClient = producerServiceClient;
    }

    @Override
    public boolean validateProducerId(Long producerId) {
        return producerServiceClient.validateProducerId(producerId);
    }

    @Override
    public boolean validateOrganizationId(Long organizationId) {
        return producerServiceClient.validateOrganizationId(organizationId);
    }

    @Override
    public boolean validateDivisionId(Long divisionId) {
        return producerServiceClient.validateDivisionId(divisionId);
    }
}
