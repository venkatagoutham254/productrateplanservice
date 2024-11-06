package aforo.productrateplanservie.product;

public interface ProducerClientService {
    void validateProducerId(Long producerId);
    void validateOrganizationId(Long organizationId);
    void validateDivisionId(Long divisionId);
}
