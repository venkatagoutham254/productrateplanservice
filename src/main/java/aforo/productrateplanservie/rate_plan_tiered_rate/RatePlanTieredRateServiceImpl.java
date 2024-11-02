package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetails;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RatePlanTieredRateServiceImpl implements RatePlanTieredRateService {

    private final RatePlanTieredRateRepository ratePlanTieredRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanTieredRateMapper ratePlanTieredRateMapper;
    private final RatePlanTieredRateDetailsRepository ratePlanTieredRateDetailsRepository;

    public RatePlanTieredRateServiceImpl(final RatePlanTieredRateRepository ratePlanTieredRateRepository, final RatePlanRepository ratePlanRepository, final RatePlanTieredRateMapper ratePlanTieredRateMapper, final RatePlanTieredRateDetailsRepository ratePlanTieredRateDetailsRepository) {
        this.ratePlanTieredRateRepository = ratePlanTieredRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanTieredRateMapper = ratePlanTieredRateMapper;
        this.ratePlanTieredRateDetailsRepository = ratePlanTieredRateDetailsRepository;
    }

    @Override
    public Page<RatePlanTieredRateDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlanTieredRate> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanTieredRateRepository.findAllByRatePlanTieredRateId(longFilter, pageable);
        } else {
            page = ratePlanTieredRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent().stream().map(ratePlanTieredRate -> ratePlanTieredRateMapper.updateRatePlanTieredRateDTO(ratePlanTieredRate, new RatePlanTieredRateDTO())).toList(), pageable, page.getTotalElements());
    }

    @Override
    public RatePlanTieredRateDTO get(final Long ratePlanTieredRateId) {
        return ratePlanTieredRateRepository.findById(ratePlanTieredRateId).map(ratePlanTieredRate -> ratePlanTieredRateMapper.updateRatePlanTieredRateDTO(ratePlanTieredRate, new RatePlanTieredRateDTO())).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public Long create(Long ratePlanId, RatePlanTieredRateDTO ratePlanTieredRateDTO) {
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId).orElseThrow(() -> new NotFoundException("RatePlan not found with id: " + ratePlanId));
        RatePlanTieredRate ratePlanTieredRate = new RatePlanTieredRate();
        ratePlanTieredRateMapper.updateRatePlanTieredRate(ratePlanTieredRateDTO, ratePlanTieredRate, ratePlanRepository);
        ratePlanTieredRate.setRatePlan(ratePlan);

        RatePlanTieredRate savedRatePlanTieredRate = ratePlanTieredRateRepository.save(ratePlanTieredRate);


        if (ratePlanTieredRateDTO.getRatePlanTieredRateDetailsDTO() != null) {
            for (RatePlanTieredRateDetailsDTO detailsDTO : ratePlanTieredRateDTO.getRatePlanTieredRateDetailsDTO()) {
                RatePlanTieredRateDetails details = ratePlanTieredRateMapper.mapToRatePlanTieredRateDetails(detailsDTO);
                details.setRatePlanTieredRate(savedRatePlanTieredRate); // Ensure the relationship is set
                ratePlanTieredRateDetailsRepository.save(details); // Save the details object
            }
        }

        return savedRatePlanTieredRate.getRatePlanTieredRateId();
    }

    @Override
    @Transactional
    public void update(Long ratePlanTieredRateId, RatePlanTieredRateDTO ratePlanTieredRateDTO) {
        RatePlanTieredRate ratePlanTieredRate = ratePlanTieredRateRepository.findById(ratePlanTieredRateId).orElseThrow(() -> new NotFoundException("RatePlanTieredRate not found with id: " + ratePlanTieredRateId));

        // Update the RatePlanTieredRate itself
        ratePlanTieredRateMapper.updateRatePlanTieredRate(ratePlanTieredRateDTO, ratePlanTieredRate, ratePlanRepository);

        RatePlanTieredRate updatedRatePlanTieredRate = ratePlanTieredRateRepository.save(ratePlanTieredRate);

        // Handle the updating of RatePlanTieredRateDetails
        if (ratePlanTieredRateDTO.getRatePlanTieredRateDetailsDTO() != null) {
            // First, delete existing RatePlanTieredRateDetails
            ratePlanTieredRateDetailsRepository.deleteAllByRatePlanTieredRate(updatedRatePlanTieredRate);

            // Now, add the new RatePlanTieredRateDetails
            for (RatePlanTieredRateDetailsDTO detailsDTO : ratePlanTieredRateDTO.getRatePlanTieredRateDetailsDTO()) {
                RatePlanTieredRateDetails details = ratePlanTieredRateMapper.mapToRatePlanTieredRateDetails(detailsDTO);
                details.setRatePlanTieredRate(updatedRatePlanTieredRate); // Set the relationship
                ratePlanTieredRateDetailsRepository.save(details);
            }
        }
    }


    @Transactional
    @Override
    public void delete(final Long ratePlanTieredRateId) {
        // Fetch the RatePlanTieredRate
        RatePlanTieredRate ratePlanTieredRate = ratePlanTieredRateRepository.findById(ratePlanTieredRateId).orElseThrow(() -> new NotFoundException("RatePlanTieredRate not found with id: " + ratePlanTieredRateId));
        ratePlanTieredRateRepository.delete(ratePlanTieredRate);
    }


    @Override
    public ReferencedWarning getReferencedWarning(final Long ratePlanTieredRateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RatePlanTieredRate ratePlanTieredRate = ratePlanTieredRateRepository.findById(ratePlanTieredRateId).orElseThrow(NotFoundException::new);
        final RatePlanTieredRateDetails ratePlanTieredRateRatePlanTieredRateDetails = ratePlanTieredRateDetailsRepository.findFirstByRatePlanTieredRate(ratePlanTieredRate);
        if (ratePlanTieredRateRatePlanTieredRateDetails != null) {
            referencedWarning.setKey("ratePlanTieredRate.ratePlanTieredRateDetails.ratePlanTieredRate.referenced");
            referencedWarning.addParam(ratePlanTieredRateRatePlanTieredRateDetails.getTierNumber());
            return referencedWarning;
        }
        return null;
    }

}
