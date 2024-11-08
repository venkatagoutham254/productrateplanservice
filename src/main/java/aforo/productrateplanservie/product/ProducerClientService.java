package aforo.productrateplanservie.product;

public interface ProducerClientService {
    boolean validateProducerId(Long producerId);
    boolean validateOrganizationId(Long organizationId);
    boolean validateDivisionId(Long divisionId);
}
