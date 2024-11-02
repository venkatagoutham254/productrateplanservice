package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetails;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatePlanFlatRateServiceImpl implements RatePlanFlatRateService {

    private final RatePlanFlatRateRepository ratePlanFlatRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository;
    private final RatePlanFlatRateMapper ratePlanFlatRateMapper;

    public RatePlanFlatRateServiceImpl(final RatePlanFlatRateRepository ratePlanFlatRateRepository,
                                       final RatePlanRepository ratePlanRepository,
                                       final RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository,
                                       final RatePlanFlatRateMapper ratePlanFlatRateMapper) {
        this.ratePlanFlatRateRepository = ratePlanFlatRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanFlatRateDetailsRepository = ratePlanFlatRateDetailsRepository;
        this.ratePlanFlatRateMapper = ratePlanFlatRateMapper;
    }

    @Override
    public Page<RatePlanFlatRateDTO> findAll(String filter, Pageable pageable) {
        Page<RatePlanFlatRate> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (NumberFormatException numberFormatException) {
                // Keep null - no parseable input
            }
            page = ratePlanFlatRateRepository.findAllByRatePlanFlatRateId(longFilter, pageable);
        } else {
            page = ratePlanFlatRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public RatePlanFlatRateDTO get(Long ratePlanFlatRateId) {
        return ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));
    }

    @Override
    @Transactional
    public Long create(Long ratePlanId, RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        // Ensure the RatePlan exists
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new NotFoundException("RatePlan not found with id: " + ratePlanId));

        // Create a new RatePlanFlatRate
        RatePlanFlatRate ratePlanFlatRate = new RatePlanFlatRate();
        ratePlanFlatRateMapper.updateRatePlanFlatRate(ratePlanFlatRateDTO, ratePlanFlatRate, ratePlanRepository);

        // Set the associated RatePlan
        ratePlanFlatRate.setRatePlan(ratePlan);

        // Save the RatePlanFlatRate first
        RatePlanFlatRate savedRatePlanFlatRate = ratePlanFlatRateRepository.save(ratePlanFlatRate);

        // Save RatePlanFlatRateDetails if present
        if (ratePlanFlatRateDTO.getRatePlanFlatRateDetails() != null) {
            for (RatePlanFlatRateDetailsDTO detailsDTO : ratePlanFlatRateDTO.getRatePlanFlatRateDetails()) {
                RatePlanFlatRateDetails details = ratePlanFlatRateMapper.mapToRatePlanFlatRateDetails(detailsDTO);
                details.setRatePlanFlatRate(savedRatePlanFlatRate); // Ensure the relationship is set
                ratePlanFlatRateDetailsRepository.save(details); // Save the details object
            }
        }

        return savedRatePlanFlatRate.getRatePlanFlatRateId();
    }

    @Override
    @Transactional
    public void update(Long ratePlanFlatRateId, RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        // Fetch the existing RatePlanFlatRate
        RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));

        // Update the RatePlanFlatRate fields using the DTO
        ratePlanFlatRateMapper.updateRatePlanFlatRate(ratePlanFlatRateDTO, ratePlanFlatRate, ratePlanRepository);

        // Save the updated RatePlanFlatRate
        RatePlanFlatRate updatedRatePlanFlatRate = ratePlanFlatRateRepository.save(ratePlanFlatRate);

        // Remove old details and save new details if provided
        if (ratePlanFlatRateDTO.getRatePlanFlatRateDetails() != null) {
            // Remove existing RatePlanFlatRateDetails
            ratePlanFlatRateDetailsRepository.deleteAllByRatePlanFlatRate(updatedRatePlanFlatRate);

            // Add the new RatePlanFlatRateDetails
            for (RatePlanFlatRateDetailsDTO detailsDTO : ratePlanFlatRateDTO.getRatePlanFlatRateDetails()) {
                RatePlanFlatRateDetails details = ratePlanFlatRateMapper.mapToRatePlanFlatRateDetails(detailsDTO);
                details.setRatePlanFlatRate(updatedRatePlanFlatRate); // Ensure the relationship is set
                ratePlanFlatRateDetailsRepository.save(details); // Save the details object
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long ratePlanFlatRateId) {
        // Fetch the RatePlanFlatRate
        RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));

        // Delete associated RatePlanFlatRateDetails
        ratePlanFlatRateDetailsRepository.deleteAllByRatePlanFlatRate(ratePlanFlatRate);

        // Delete the RatePlanFlatRate
        ratePlanFlatRateRepository.delete(ratePlanFlatRate);
    }

    @Override
    public ReferencedWarning getReferencedWarning(Long ratePlanFlatRateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));
        // Assume implementation for retrieving referenced warnings
        // Example: Check if the RatePlanFlatRate is referenced elsewhere and populate referencedWarning accordingly
        return referencedWarning;
    }

    @Override
    public Page<RatePlanFlatRateDTO> findAllByRatePlanId(Long ratePlanId, Pageable pageable) {
        Page<RatePlanFlatRate> page = ratePlanFlatRateRepository.findAllByRatePlan_RatePlanId(ratePlanId, pageable);
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }
}
