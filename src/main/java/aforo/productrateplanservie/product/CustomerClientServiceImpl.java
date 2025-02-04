package aforo.productrateplanservie.product;

import aforo.productrateplanservie.client.CustomerServiceClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerClientServiceImpl implements CustomerClientService {
    private final CustomerServiceClient customerServiceClient;

    public CustomerClientServiceImpl(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }

    @Override
    public boolean validateCustomerId(Long customerId) {
        return customerServiceClient.validateCustomerId(customerId);
    }

    @Override
    public boolean validateOrganizationId(Long organizationId) {
        return customerServiceClient.validateOrganizationId(organizationId);
    }

    @Override
    public boolean validateDivisionId(Long divisionId) {
        return customerServiceClient.validateDivisionId(divisionId);
    }
}
