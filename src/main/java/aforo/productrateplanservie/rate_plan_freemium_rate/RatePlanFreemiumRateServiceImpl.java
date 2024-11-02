package aforo.productrateplanservie.rate_plan_freemium_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetails;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
public class RatePlanFreemiumRateServiceImpl implements RatePlanFreemiumRateService {

    private final RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanFreemiumRateMapper ratePlanFreemiumRateMapper;
    private final RatePlanFreemiumRateDetailsRepository ratePlanFreemiumRateDetailsRepository;

    public RatePlanFreemiumRateServiceImpl(final RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository, final RatePlanRepository ratePlanRepository, final RatePlanFreemiumRateMapper ratePlanFreemiumRateMapper, final RatePlanFreemiumRateDetailsRepository ratePlanFreemiumRateDetailsRepository) {
        this.ratePlanFreemiumRateRepository = ratePlanFreemiumRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanFreemiumRateMapper = ratePlanFreemiumRateMapper;
        this.ratePlanFreemiumRateDetailsRepository = ratePlanFreemiumRateDetailsRepository;
    }

    @Override
    public Page<RatePlanFreemiumRateDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlanFreemiumRate> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanFreemiumRateRepository.findAllByRatePlanFreemiumRateId(longFilter, pageable);
        } else {
            page = ratePlanFreemiumRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent().stream().map(ratePlanFreemiumRate -> ratePlanFreemiumRateMapper.updateRatePlanFreemiumRateDTO(ratePlanFreemiumRate, new RatePlanFreemiumRateDTO())).toList(), pageable, page.getTotalElements());
    }

    @Override
    public RatePlanFreemiumRateDTO get(final Long ratePlanFreemiumRateId) {
        return ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId).map(ratePlanFreemiumRate -> ratePlanFreemiumRateMapper.updateRatePlanFreemiumRateDTO(ratePlanFreemiumRate, new RatePlanFreemiumRateDTO())).orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(Long ratePlanId, RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
        // Retrieve RatePlan using the path variable ratePlanId
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId).orElseThrow(() -> new NotFoundException("RatePlan not found with ID: " + ratePlanId));

        // Map DTO to entity
        RatePlanFreemiumRate ratePlanFreemiumRate = new RatePlanFreemiumRate();
        ratePlanFreemiumRateMapper.updateRatePlanFreemiumRate(ratePlanFreemiumRateDTO, ratePlanFreemiumRate, ratePlan);

        // Set RatePlan association
        ratePlanFreemiumRate.setRatePlan(ratePlan);

        // Save RatePlanFreemiumRate and its details
        ratePlanFreemiumRate = ratePlanFreemiumRateRepository.save(ratePlanFreemiumRate);
        RatePlanFreemiumRate finalRatePlanFreemiumRate = ratePlanFreemiumRate;
        ratePlanFreemiumRateDTO.getRatePlanFreemiumRateDetailsDTO().forEach(detailDTO -> {
            RatePlanFreemiumRateDetails details = new RatePlanFreemiumRateDetails();
            details.setRatePlanFreemiumRate(finalRatePlanFreemiumRate);
            details.setFreemiumMaxUnitQuantity(detailDTO.getFreemiumMaxUnitQuantity());
            ratePlanFreemiumRateDetailsRepository.save(details);
        });

        return ratePlanFreemiumRate.getRatePlanFreemiumRateId();
    }

    @Override
    @Transactional
    public void update(Long ratePlanFreemiumRateId, RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
        // Retrieve the existing RatePlanFreemiumRate entity from the database
        RatePlanFreemiumRate ratePlanFreemiumRate = ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId).orElseThrow(() -> new NotFoundException("RatePlanFreemiumRate not found with ID: " + ratePlanFreemiumRateId));

        // Map fields from DTO to the existing entity
        ratePlanFreemiumRateMapper.updateRatePlanFreemiumRate(ratePlanFreemiumRateDTO, ratePlanFreemiumRate, ratePlanFreemiumRate.getRatePlan());

        Set<RatePlanFreemiumRateDetails> existingDetails = ratePlanFreemiumRate.getRatePlanFreemiumRateDetails();

        // Update existing or add new details from DTO
        for (RatePlanFreemiumRateDetailsDTO detailDTO : ratePlanFreemiumRateDTO.getRatePlanFreemiumRateDetailsDTO()) {
            RatePlanFreemiumRateDetails existingDetail = existingDetails.stream().filter(d -> d.getId() != null && d.getId().equals(detailDTO.getId())).findFirst().orElse(null);

            if (existingDetail != null) {
                // Update the fields of the existing detail
                existingDetail.setFreemiumMaxUnitQuantity(detailDTO.getFreemiumMaxUnitQuantity());
            } else {
                // Create new detail if it doesn't exist in the current set
                RatePlanFreemiumRateDetails newDetail = new RatePlanFreemiumRateDetails();
                newDetail.setFreemiumMaxUnitQuantity(detailDTO.getFreemiumMaxUnitQuantity());
                newDetail.setRatePlanFreemiumRate(ratePlanFreemiumRate);
                existingDetails.add(newDetail);
            }
        }

        // Remove details that are not present in the incoming DTO list (orphaned)
        existingDetails.removeIf(existingDetail -> ratePlanFreemiumRateDTO.getRatePlanFreemiumRateDetailsDTO().stream().noneMatch(dto -> dto.getId() != null && dto.getId().equals(existingDetail.getId())));

        // Save and flush the updated RatePlanFreemiumRate to manage orphaned entities properly
        ratePlanFreemiumRateRepository.saveAndFlush(ratePlanFreemiumRate);
    }


    @Override
    public void delete(Long ratePlanFreemiumRateId) {
        // Check if the entity exists before attempting to delete
        if (!ratePlanFreemiumRateRepository.existsById(ratePlanFreemiumRateId)) {
            throw new NotFoundException("RatePlanFreemiumRate not found with ID: " + ratePlanFreemiumRateId);
        }

        ratePlanFreemiumRateRepository.deleteById(ratePlanFreemiumRateId);
    }

}


//    @Override
//    public ReferencedWarning getReferencedWarning(final Long ratePlanFreemiumRateId) {
//        final ReferencedWarning referencedWarning = new ReferencedWarning();
//        final RatePlanFreemiumRate ratePlanFreemiumRate = ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId)
//                .orElseThrow(NotFoundException::new);
//        final RatePlanFreemiumRateDetails ratePlanFreemiumRateRatePlanFreemiumRateDetails = ratePlanFreemiumRateDetailsRepository.findFirstByRatePlanFreemiumRate(ratePlanFreemiumRate);
//        if (ratePlanFreemiumRateRatePlanFreemiumRateDetails != null) {
//            referencedWarning.setKey("ratePlanFreemiumRate.ratePlanFreemiumRateDetails.ratePlanFreemiumRate.referenced");
//            referencedWarning.addParam((ratePlanFreemiumRateRatePlanFreemiumRateDetails).getId());
//            return referencedWarning;
//        }
//        return null;
//    }


