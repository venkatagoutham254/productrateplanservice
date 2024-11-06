package aforo.productrateplanservie.product;

import aforo.productrateplanservie.client.ProducerServiceClient;
import org.springframework.stereotype.Service;

@Service
public class ProducerClientServiceImpl implements ProducerClientService {
    private final ProducerServiceClient producerServiceClient;

    public ProducerClientServiceImpl(ProducerServiceClient producerServiceClient) {
        this.producerServiceClient = producerServiceClient;
    }

    @Override
    public void validateProducerId(Long producerId) {
        producerServiceClient.validateProducerId(producerId);
    }

    @Override
    public void validateOrganizationId(Long organizationId) {
        producerServiceClient.validateOrganizationId(organizationId);
    }

    @Override
    public void validateDivisionId(Long divisionId) {
        producerServiceClient.validateDivisionId(divisionId);
    }
}
