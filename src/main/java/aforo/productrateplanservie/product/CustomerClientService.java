package aforo.productrateplanservie.product;

public interface CustomerClientService {
    boolean validateCustomerId(Long customerId);
    boolean validateOrganizationId(Long organizationId);
    boolean validateDivisionId(Long divisionId);
}
